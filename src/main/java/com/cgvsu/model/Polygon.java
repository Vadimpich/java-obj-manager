package com.cgvsu.model;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.objreader.exceptions.FaceWordIndexException;
import com.cgvsu.rasterization.TriangleRasterization;
import com.cgvsu.render_engine.ZBuffer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.cgvsu.model.Model.multiplyMatrix4ByVector3;
import static com.cgvsu.render_engine.GraphicConveyor.vertexToSurface;

public class Polygon {
    private List<Integer> vertexIndices;
    private List<Integer> textureVertexIndices;
    private List<Integer> normalIndices;

    public Polygon() {
        vertexIndices = new ArrayList<>();
        textureVertexIndices = new ArrayList<>();
        normalIndices = new ArrayList<>();
    }

    public List<Integer> getVertexIndices() {
        return vertexIndices;
    }

    public void setVertexIndices(List<Integer> vertexIndices) {
        this.vertexIndices = vertexIndices;
    }

    public List<Integer> getTextureVertexIndices() {
        return textureVertexIndices;
    }

    public void setTextureVertexIndices(List<Integer> textureVertexIndices) {
        this.textureVertexIndices = textureVertexIndices;
    }

    public List<Integer> getNormalIndices() {
        return normalIndices;
    }

    public void setNormalIndices(List<Integer> normalIndices) {
        this.normalIndices = normalIndices;
    }


    private int lineIndex;

    public boolean hasTexture() {
        return !textureVertexIndices.isEmpty();
    }

    public void checkIndices(int verticesSize, int textureVerticesSize, int normalsSize) {
        for (int i = 0; i < vertexIndices.size(); i++) {
            int vertexIndex = vertexIndices.get(i);
            if (vertexIndex >= verticesSize || vertexIndex < 0) {
                throw new FaceWordIndexException("vertex", lineIndex, i + 1);
            }
        }

        for (int i = 0; i < textureVertexIndices.size(); i++) {
            int textureVertexIndex = textureVertexIndices.get(i);
            if (textureVertexIndex >= textureVerticesSize || textureVertexIndex < 0) {
                throw new FaceWordIndexException("texture vertex", lineIndex, i + 1);
            }
        }

        for (int i = 0; i < normalIndices.size(); i++) {
            int normalIndex = normalIndices.get(i);
            if (normalIndex >= normalsSize || normalIndex < 0) {
                throw new FaceWordIndexException("normal", lineIndex, i + 1);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polygon polygon = (Polygon) o;
        return Objects.equals(vertexIndices, polygon.vertexIndices) &&
                Objects.equals(textureVertexIndices, polygon.textureVertexIndices) &&
                Objects.equals(normalIndices, polygon.normalIndices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertexIndices, textureVertexIndices, normalIndices);
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    private void drawingPolygonNet(GraphicsContext g, ArrayList<Vector3f> resultPoints){
        for (int vertexInPolygonInd = 1; vertexInPolygonInd < vertexIndices.size(); ++vertexInPolygonInd) {
            g.strokeLine(
                    resultPoints.get(vertexInPolygonInd - 1).getX(),
                    resultPoints.get(vertexInPolygonInd - 1).getY(),
                    resultPoints.get(vertexInPolygonInd).getX(),
                    resultPoints.get(vertexInPolygonInd).getY());
        }
        if (vertexIndices.size()> 0)
            g.strokeLine(
                    resultPoints.get(vertexIndices.size() - 1).getX(),
                    resultPoints.get(vertexIndices.size() - 1).getY(),
                    resultPoints.get(0).getX(),
                    resultPoints.get(0).getY());
    }

    public void drawPolygon(GraphicsContext g,
                            Matrix4f modelViewProjectionMatrix,
                            Model mesh,
                            int width, int height,
                            Color fillingColor,
                            boolean isFill,
                            ZBuffer zbuffer, boolean isTexturing, PixelReader pixelReader){


        ArrayList<Vector3f> resultPoints = new ArrayList<>();
        ArrayList<Vector2f> textureVertexes = new ArrayList<>();
        int nVerticesInPolygon = vertexIndices.size();
        for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
            Vector3f vertex2 =  mesh.sceneVertices.get(vertexIndices.get(vertexInPolygonInd));
            javax.vecmath.Vector3f vertex = vertexToSurface(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex2).toJavax(), width, height);

            resultPoints.add(new Vector3f(vertex.x, vertex.y, vertex.z));

            if(isTexturing && mesh.image != null){
                textureVertexes.add(mesh.textureVertices.get(textureVertexIndices.get(vertexInPolygonInd)));
            }
        }


        if( isFill ) {
            TriangleRasterization.drawTriangle(g.getPixelWriter(),
                    new Vector3f(resultPoints.get(0).getX(), resultPoints.get(0).getY(), resultPoints.get(0).getZ()),
                    new Vector3f(resultPoints.get(1).getX(), resultPoints.get(1).getY(), resultPoints.get(1).getZ()),
                    new Vector3f(resultPoints.get(2).getX(), resultPoints.get(2).getY(), resultPoints.get(2).getZ()),
                    fillingColor,
                    textureVertexes,
                    zbuffer, pixelReader);
            textureVertexes.clear();
        }else {
            drawingPolygonNet(g, resultPoints);
        }
    }
}
