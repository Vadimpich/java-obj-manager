package com.cgvsu.model;

import com.cgvsu.AffineTransformation;
import com.cgvsu.math.Matrix4F;
import com.cgvsu.math.MatrixNf;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.render_engine.ZBuffer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Model {

    public List<Vector3f> vertices = new ArrayList<>();
    public ArrayList<Vector3f> sceneVertices = new ArrayList<>();
    public List<Vector2f> textureVertices = new ArrayList<>();
    public List<Vector3f> normals = new ArrayList<>();
    public List<Polygon> polygons = new ArrayList<>();
    public float xSize = 0;

    public boolean selected = false;

    public boolean viewMesh = true;
    public boolean viewTexture = false;

    public boolean viewLighting = false;

    public Image texture = null;

    private List<Group> groups = new ArrayList<>();

    public float scaleX = 1;
    public float scaleY = 1;
    public float scaleZ = 1;
    public int translateX = 1;
    public int translateY = 1;
    public int translateZ = 1;
    public float rotateX = 0;
    public float rotateY = 0;
    public float rotateZ = 0;

    public boolean isTriangulate = false;
    public Color fillingColor = null;
    public boolean isFill;
    public boolean isTextured = false;

    public javafx.scene.image.Image image = null;

    public void addVertex(Vector3f vertex) {
        vertices.add(vertex);
    }

    public void addTextureVertex(Vector2f textureVertex) {
        textureVertices.add(textureVertex);
    }

    public void addNormal(Vector3f normal) {
        normals.add(normal);
    }

    public void addPolygon(Polygon polygon) {
        polygons.add(polygon);
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public Polygon getFirstPolygon() {
        return polygons.get(0);
    }


    public int getVerticesSize() {
        return vertices.size();
    }

    public int getTextureVerticesSize() {
        return textureVertices.size();
    }

    public int getNormalsSize() {
        return normals.size();
    }

    public int getPolygonsSize() {
        return polygons.size();
    }

    public List<Vector3f> getVertices() {
        return vertices;
    }

    public List<Vector2f> getTextureVertices() {
        return textureVertices;
    }

    public List<Vector3f> getNormals() {
        return normals;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public List<Group> getGroups() {
        return groups;
    }



    public ArrayList<Polygon> triangulate() {
        return (ArrayList<Polygon>) Triangulation.triangulatePolygons(polygons);
    }
    public void changeModel(){
        sceneVertices.clear();
        Matrix4F m = (Matrix4F)new AffineTransformation().scale(scaleX, scaleY, scaleZ)
                .multiplyMatrix(new AffineTransformation().rotate((float) rotateX, (float) rotateY, (float) rotateZ))
                .multiplyMatrix(new AffineTransformation().translate(translateX, translateY, translateZ));
        for (int i = 0; i <this.vertices.size(); i++){
            sceneVertices.add(multiplyMatrix4ByVector3(m, vertices.get(i)));
        }
    }
    public static Vector3f multiplyMatrix4ByVector3(final Matrix4F matrix, final Vector3f vertex) {
        final float x  = matrix.getMatrixInVectors()[0].getArrValues()[0] * vertex.getX() +  matrix.getMatrixInVectors()[1].getArrValues()[0] * vertex.getY() + matrix.getMatrixInVectors()[2].getArrValues()[0] * vertex.getZ() + matrix.getMatrixInVectors()[3].getArrValues()[0];
        final float y  = matrix.getMatrixInVectors()[0].getArrValues()[1] * vertex.getX() +  matrix.getMatrixInVectors()[1].getArrValues()[1] * vertex.getY() + matrix.getMatrixInVectors()[2].getArrValues()[1] * vertex.getZ() + matrix.getMatrixInVectors()[3].getArrValues()[1];
        final float z = matrix.getMatrixInVectors()[0].getArrValues()[2] * vertex.getX() +  matrix.getMatrixInVectors()[1].getArrValues()[2] * vertex.getY() + matrix.getMatrixInVectors()[2].getArrValues()[2] * vertex.getZ() + matrix.getMatrixInVectors()[3].getArrValues()[2];
        final float w  = matrix.getMatrixInVectors()[0].getArrValues()[3] * vertex.getX() +  matrix.getMatrixInVectors()[1].getArrValues()[3] * vertex.getY() + matrix.getMatrixInVectors()[2].getArrValues()[3] * vertex.getZ() + matrix.getMatrixInVectors()[3].getArrValues()[3];
        return new Vector3f(x/w, y/w, z/w);
    }
    public void draw(GraphicsContext g, MatrixNf modelViewProjectionMatrix, int width, int height , ZBuffer zbuffer){
        List<Polygon> currPoligons = polygons;
        changeModel();
        PixelReader pixelReader = null;
        if (image != null){
            pixelReader = image.getPixelReader();
        }

        if(isTriangulate || fillingColor == null){
            currPoligons = triangulate();
        }
        for (Polygon p : currPoligons){
            p.drawPolygon(g, modelViewProjectionMatrix, this,
                    width,
                    height,
                    fillingColor,
                    isFill,
                    zbuffer,
                    isTextured, pixelReader);
        }
    }
}
