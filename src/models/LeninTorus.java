package models;

import kg2019examples_task4threedimensions.math.Matrix4;
import kg2019examples_task4threedimensions.math.Matrix4Factories;
import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.math.Vector4;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LeninTorus extends Lenin {
    private Vector3 torusCenter;
    private float torusRad;
    private float torusThickness;
    private static final int EDGES = 30;

    public LeninTorus(Vector3 torusCenter, float torusRad, float torusThickness) {
        this.torusCenter = torusCenter;
        this.torusRad = torusRad;
        this.torusThickness = torusThickness;
    }

    @Override
    Vector3[] getPath(int dimension, float torusRad, float z) {
        double delta = Math.PI * 2 / dimension;

        Vector3[] circles = new Vector3[dimension];
        for (int i = 1; i <= dimension; i++) {
            float x = (float) (torusRad * Math.cos(delta * (i)));
            float y = (float) (torusRad * Math.sin(delta * (i)));
            circles[i - 1] = new Vector3(x, y, z);
        }
        return circles;
    }

    @Override
    public List<PolyLine3D> getLines() {
        List<PolyLine3D> lines = new ArrayList<>();
        Vector3[] circles = getPath(EDGES, torusRad, torusThickness);
        List<Vector3> angleVectors = new LinkedList<>();
        for (int o = 0; o < EDGES - 1; o++) {
            float x = circles[o + 1].getX() - circles[o].getX();
            float y = circles[o + 1].getY() - circles[o].getY();
            float z = circles[o + 1].getZ() - circles[o].getZ();
            angleVectors.add(new Vector3(x, y, z));
        }
        angleVectors.add(new Vector3(circles[EDGES - 1].getX() - circles[0].getX(),
                circles[EDGES - 1].getY() - circles[0].getY(),
                circles[EDGES - 1].getZ() - circles[0].getZ()));

        for (int i = 0; i < EDGES; i++) {
            Vector3[] slice = getCircle(torusThickness, EDGES, circles[i].getZ());
            Vector3 vec = angleVectors.get(i);
            Vector3 cv = circles[i].getX() < 0 ? new Vector3(1, 0, 0) : new Vector3(-1, 0, 0);

            double angle = Math.acos((vec.getX() * cv.getX() + vec.getY() * cv.getY() + vec.getZ() * cv.getZ()) /
                    (Math.sqrt(vec.getX() * vec.getX() + vec.getY() * vec.getY() + vec.getZ() * vec.getZ()) *
                            Math.sqrt(cv.getX() * cv.getX() + cv.getY() * cv.getY() + cv.getZ() * cv.getZ())
                    ));
            Matrix4 m = Matrix4Factories.rotationXYZ(angle, 2);
            Matrix4 t = Matrix4Factories.rotationXYZ(Math.PI / 2, 1);
            for (int j = 0; j < EDGES; j++) {
                Vector4 v4 = m.mul(new Vector4(slice[j]));
                v4 = t.mul(v4);
                slice[j] = new Vector3(v4.getX() + circles[i].getX(), v4.getY() + circles[i].getY(), v4.getZ() + circles[i].getZ() + 2 * torusThickness);
            }
            PolyLine3D line = new PolyLine3D(Arrays.asList(slice), true);
            lines.add(line);
        }
        for (int i = 0; i < EDGES - 1; i++) {
            addPolyCircle(lines, i, i + 1);
        }
        addPolyCircle(lines, EDGES - 1, 0);

        return lines;
    }

    private void addPolygon(PolyLine3D curr, PolyLine3D next, List<PolyLine3D> lines, int nCurr, int nPrev) {
        Vector3[] points = new Vector3[4];
        points[0] = curr.get(nCurr);
        points[1] = next.get(nCurr);
        points[2] = next.get(nPrev);
        points[3] = curr.get(nPrev);
        PolyLine3D l = new PolyLine3D(Arrays.asList(points), true);
        lines.add(l);
    }

    private void addPolyCircle(List<PolyLine3D> lines, int nCurr, int nPrev) {
        PolyLine3D curr = lines.get(nCurr);
        PolyLine3D next = lines.get(nPrev);
        if (nCurr == EDGES / 4 - 1 || nCurr == 3 * EDGES / 4 - 1) {
            aVoid(curr, next, lines);
        } else {
            for (int j = 0; j < EDGES - 1; j++) {
                addPolygon(curr, next, lines, j, j + 1);
            }
            addPolygon(curr, next, lines, EDGES - 1, 0);
        }
    }

    private void aVoid(PolyLine3D curr, PolyLine3D next, List<PolyLine3D> lines) {
        for (int j = 0; j < EDGES - 3; j++) {
            Vector3[] points = new Vector3[4];
            points[0] = curr.get(j);
            points[1] = curr.get(j + 1);
            int a = EDGES - 3 - j;
            points[2] = next.get(EDGES - 3 - j);
            points[3] = next.get(EDGES - 2 - j);
            PolyLine3D l = new PolyLine3D(Arrays.asList(points), true);
            lines.add(l);
        }
        Vector3[] points = new Vector3[4];
        points[0] = curr.get(EDGES - 1);
        points[1] = curr.get(0);
        points[2] = next.get(EDGES - 2);
        points[3] = next.get(EDGES - 1);
        PolyLine3D l = new PolyLine3D(Arrays.asList(points), true);
        lines.add(l);
        points = new Vector3[4];
        points[0] = curr.get(EDGES - 2);
        points[1] = curr.get(EDGES - 1);
        points[2] = next.get(EDGES - 1);
        points[3] = next.get(0);
        l = new PolyLine3D(Arrays.asList(points), true);
        lines.add(l);
        points = new Vector3[4];
        points[0] = curr.get(EDGES - 3);
        points[1] = curr.get(EDGES - 2);
        points[2] = next.get(0);
        points[3] = next.get(1);
        l = new PolyLine3D(Arrays.asList(points), true);
        lines.add(l);
    }
}
