package com.cgvsu.render_engine;

import java.util.ArrayList;
import java.util.List;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Polygon;
import javafx.scene.canvas.GraphicsContext;

import javax.vecmath.*;

import com.cgvsu.model.Model;
import javafx.scene.paint.Color;

import static com.cgvsu.render_engine.GraphicConveyor.*;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final List<Model> models,
            final int width,
            final int height) {


        for (Model mesh : models) {
            if (mesh.viewMesh) renderModel(graphicsContext, mesh, camera, width, height);
        }
    }

    private static class PointVertexModel {

        public PointVertexModel(Point2f point, int vertexIndex, Model model) {
            this.point = new Point2f((float) Math.floor(point.x), (float) Math.floor(point.y));
            this.vertexIndex = vertexIndex;
            this.model = model;
        }

        public Point2f point;
        public int vertexIndex;

        public Model model;

        public boolean nearPoint(Point2f other) {
            final int MAX_DELTA = 5;
            return Math.abs(this.point.x - other.x) < MAX_DELTA && Math.abs(this.point.y - other.y) < MAX_DELTA;
        }
    }

    private static List<PointVertexModel> currentFramePoints = new ArrayList<>();

    private static int selectedVertex = -2;

    public static void deleteVertex() {
        for (PointVertexModel pvm : currentFramePoints) {
            if (pvm.vertexIndex == selectedVertex) {
                List<Integer> newPolyVertices = new ArrayList<>();
                int nPoligons = pvm.model.polygons.size();
                for (int i = 0; i < nPoligons; ++i) {
                    Polygon poly = pvm.model.polygons.get(i);
                    if (poly.getVertexIndices().contains(pvm.vertexIndex)) {
                        poly.getVertexIndices().remove((Integer) pvm.vertexIndex);
                        newPolyVertices.addAll(poly.getVertexIndices());
                        pvm.model.polygons.remove(i);
                        --i;
                        --nPoligons;
                    }
                }
                Polygon newPoly = new Polygon();
                newPoly.getVertexIndices().addAll(newPolyVertices);
                break;
            }
        }
    }

    public static void findVertexIndexFromClick(Point2f mousePoint, Camera camera, List<Model> models, int width, int height) {
        for (Model mesh : models) {
            if (mesh.selected) {
                Matrix4f modelMatrix = rotateScaleTranslate();
                Matrix4f viewMatrix = camera.getViewMatrix();
                Matrix4f projectionMatrix = camera.getProjectionMatrix();

                Matrix4f modelViewProjectionMatrix = new Matrix4f(modelMatrix);
                modelViewProjectionMatrix.mul(viewMatrix);
                modelViewProjectionMatrix.mul(projectionMatrix);

                final int nPolygons = mesh.polygons.size();
                for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
                    final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

                    for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                        Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                        javax.vecmath.Vector3f vertexVecmath = new javax.vecmath.Vector3f(vertex.x, vertex.y, vertex.z);

                        Point2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);

                        if (pointIsNearMouse(resultPoint, mousePoint)) {
                            selectedVertex = mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd);
                        }
                    }
                }
            }
        }
    }

    public static void deselectVertex() {
        selectedVertex = -2;
    }

    private static boolean pointIsNearMouse(Point2f point, Point2f mousePoint) {
        final int MAX_DELTA = 5;
        return Math.abs(point.x - mousePoint.x) < MAX_DELTA && Math.abs(point.y - mousePoint.y) < MAX_DELTA;
    }


    private static void renderModel(
            final GraphicsContext graphicsContext,
            final Model mesh,
            Camera camera,
            final int width,
            final int height) {
        Matrix4f modelMatrix = rotateScaleTranslate();
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        Matrix4f modelViewProjectionMatrix = new Matrix4f(modelMatrix);
        modelViewProjectionMatrix.mul(viewMatrix);
        modelViewProjectionMatrix.mul(projectionMatrix);

        currentFramePoints = new ArrayList<>();
        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Point2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                javax.vecmath.Vector3f vertexVecmath = new javax.vecmath.Vector3f(vertex.x, vertex.y, vertex.z);

                Point2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
                currentFramePoints.add(new PointVertexModel(resultPoint, mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd), mesh));

                resultPoints.add(resultPoint);
            }

            graphicsContext.setStroke((mesh.selected) ? Color.BLACK : Color.GRAY);

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                graphicsContext.setStroke(
                        (mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd) == selectedVertex
                                || mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd - 1) == selectedVertex
                        ) ? Color.RED : graphicsContext.getStroke()
                );
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
    ) {
        for (Model mesh : models) {
            if (mesh.viewTexture) {
                renderModelTexture(graphicsContext, mesh);
            }
        }
    }

    public void renderModelTexture(GraphicsContext graphicsContext, Model mesh) {
    }

}