package com.cgvsu.render_engine;

import java.util.ArrayList;
import java.util.List;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Polygon;
import com.cgvsu.objreader.ObjReader;
import javafx.scene.canvas.GraphicsContext;
import javax.vecmath.*;
import com.cgvsu.model.Model;
import javafx.scene.shape.TriangleMesh;

import static com.cgvsu.render_engine.GraphicConveyor.*;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final List<Model> models,
            final int width,
            final int height)
    {


        for (Model mesh : models) {
            if (mesh.viewMesh) renderModel(graphicsContext,mesh,camera, width, height);
        }
    }

    private static void renderModel(
            final GraphicsContext graphicsContext,
            final Model mesh,
            Camera camera,
            final int width,

            final int height)
    {
        Matrix4f modelMatrix = rotateScaleTranslate();
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        Matrix4f modelViewProjectionMatrix = new Matrix4f(modelMatrix);
        modelViewProjectionMatrix.mul(viewMatrix);
        modelViewProjectionMatrix.mul(projectionMatrix);

        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Point2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                javax.vecmath.Vector3f vertexVecmath = new javax.vecmath.Vector3f(vertex.x, vertex.y, vertex.z);

                Point2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
                resultPoints.add(resultPoint);
            }

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                graphicsContext.strokeLine(
                        resultPoints.get(vertexInPolygonInd - 1).x,
                        resultPoints.get(vertexInPolygonInd - 1).y,
                        resultPoints.get(vertexInPolygonInd).x,
                        resultPoints.get(vertexInPolygonInd).y);
            }

            if (nVerticesInPolygon > 0)
                graphicsContext.strokeLine(
                        resultPoints.get(nVerticesInPolygon - 1).x,
                        resultPoints.get(nVerticesInPolygon - 1).y,
                        resultPoints.get(0).x,
                        resultPoints.get(0).y);
        }
    }

    public void renderTexture(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final List<Model> models,
            final int width,
            final int height
    ){
        for (Model mesh : models){
            if (mesh.viewTexture){
                renderModelTexture(graphicsContext,mesh);
            }
        }
    }

    public void renderModelTexture(GraphicsContext graphicsContext,Model mesh) {
        for (Polygon polygon : mesh.polygons) {
            List<Integer> vertices = polygon.getVertexIndices();
            List<Integer> textureVertices = polygon.getTextureVertexIndices();

            if (vertices.size() == textureVertices.size() && vertices.size() > 2) {
                for (int i = 0; i < vertices.size() - 2; i++) {
                    Vector3f v0 = vertices.get(0);
                    Vector3f v1 = vertices.get(i + 1);
                    Vector3f v2 = vertices.get(i + 2);

                    Vector2f t0 = textureVertices.get(0);
                    Vector2f t1 = textureVertices.get(i + 1);
                    Vector2f t2 = textureVertices.get(i + 2);

                    renderTriangleWithTexture(graphicsContext, v0, v1, v2, t0, t1, t2);
                }
            }
        }
    }

}