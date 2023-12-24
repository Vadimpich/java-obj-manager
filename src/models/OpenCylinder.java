package models;

import kg2019examples_task4threedimensions.math.Matrix4;
import kg2019examples_task4threedimensions.math.Matrix4Factories;
import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.math.Vector4;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.PolyLine3D;
import models.outlines.IOutline;
import models.paths.ILine;

import javax.swing.text.EditorKit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс, описывающий фигуру "тор"/"бублик"
 */
public class OpenCylinder implements IModel {
    private static final int EDGES = 70;
    private IOutline outline;
    private ILine line;
    private boolean isClosed;

    public OpenCylinder(ILine iLine, IOutline outline, boolean isClosed) {
        this.line = iLine;
        //this.innerThickness = innerThickness;
        this.outline = outline;
        this.isClosed = isClosed;
    }

    @Override
    public List<PolyLine3D> getLines() {
        List<PolyLine3D> lines = new ArrayList<>();
        //double delta = Math.PI * 2 / EDGES;
        Vector3[] path = getPath();
        //список векторов по направлению линии
        List<Vector3> angleVectors = new LinkedList<>();
        for (int o = 0; o < EDGES - 1; o++) {
            float x = path[o + 1].getX() - path[o].getX();
            float y = path[o + 1].getY() - path[o].getY();
            float z = path[o + 1].getZ() - path[o].getZ();
            angleVectors.add(new Vector3(x, y, z));
        }
        angleVectors.add(new Vector3(path[EDGES - 1].getX() - path[0].getX(),
                path[EDGES - 1].getY() - path[0].getY(),
                path[EDGES - 1].getZ() - path[0].getZ()));
        // подготовительные данные собраны:
        // центры кругов circles,
        // векторы направляющей прямой angleVectors,
        // дельта угол delta

        for (int i = 0; i < EDGES; i++) {
            // разбираем один круг
            Vector3[] slice = getCircle(path[i].getZ(), i, path);
            // берем вектор для этого круга, берем вектор самого круга
            Vector3 vec = angleVectors.get(i);
            Vector3 cv = path[i].getX() < 0 ? new Vector3(1, 0, 0) : new Vector3(-1, 0, 0);
            //System.out.println("circle " + i + ": " + cv.getX());
            // вычисление угла между направлящим вектором прямой и нормалью текущей окружности
            double a = (vec.getX() * cv.getX() + vec.getY() * cv.getY() + vec.getZ() * cv.getZ());
            double b = Math.sqrt(vec.getX() * vec.getX() + vec.getY() * vec.getY() + vec.getZ() * vec.getZ()) *
                    Math.sqrt(cv.getX() * cv.getX() + cv.getY() * cv.getY() + cv.getZ() * cv.getZ());
            double angle;
            if (Math.abs(a / b) > 1)
                angle = Math.acos(1);
            else angle = Math.acos(a / b);

            // матрица поворота на угол по тору: для каждого круга ++delta
            Matrix4 m = Matrix4Factories.rotationXYZ(angle, 2);
            // поворот вокруг оси z для всех точек круга, лежащих в slice
            float innerThickness = 0;//outline.getRadius(i / (double) EDGES);
            for (int j = 0; j < EDGES; j++) {
                // перезапись точек окружности
                Vector4 v4 = new Vector4(slice[j]);
                v4 = m.mul(new Vector4(slice[j]));
                slice[j] = new Vector3(v4.getX() + path[i].getX(), v4.getY() + path[i].getY(), v4.getZ() + path[i].getZ() + 2 * innerThickness);
            }
            // создание полилинии одного круга и добавление ее в список линий
            PolyLine3D line = new PolyLine3D(Arrays.asList(slice), true);
            lines.add(line);
            // создание полигончиков, наконец-то
        }
        for (int i = 0; i < EDGES - 1; i++) {
            addPolyCircle(lines, i, i + 1);
        }
        if (isClosed)
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

        /*if (nCurr == EDGES / 4 - 1 || nCurr == 3 * EDGES / 4 - 1) {
            aVoid(curr, next, lines);
        } else {*/
        for (int j = 0; j < EDGES - 1; j++) {
            addPolygon(curr, next, lines, j, j + 1);
        }
        addPolygon(curr, next, lines, EDGES - 1, 0);
        //}

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

    private Vector3[] getPath() {
        //double delta = Math.PI * 2 / OpenCylinder.EDGES;

        Vector3[] circles = new Vector3[OpenCylinder.EDGES];
        for (int i = 1; i <= OpenCylinder.EDGES; i++) {
            circles[i - 1] = line.getPoint((double) i / OpenCylinder.EDGES);
        }
        return circles;
    }

    private Vector3[] getCircle(float z, int i, Vector3[] path) {
        double delta = Math.PI * 2 / OpenCylinder.EDGES;
        Vector3[] slice = new Vector3[OpenCylinder.EDGES];
        for (int j = 1; j <= OpenCylinder.EDGES; j++) {
            // строим базовые точки круга
            float radius = outline.getRadius(i / (double) OpenCylinder.EDGES);
            float x = (float) (radius * Math.cos(delta * (j)));
            float y = (float) (radius * Math.sin(delta * (j)));

            // поворот на 90 градусов
            Vector4 v = new Vector4(x, y, 0.0f);
            Matrix4 m = Matrix4Factories.rotationXYZ(Math.PI / 2, 1);
            v = m.mul(v);

            Vector3 cv = path[i].getX() < 0 ? new Vector3(1, 0, 0) : new Vector3(-1, 0, 0);
            // аддитивная штуковання
            double ad = cv.getX() < 0 ? Math.PI : 0;
            // компенсирующая матрица
            Matrix4 compM = Matrix4Factories.rotationXYZ(ad, 0);
            v = compM.mul(v);
            // завершаем создание круга, ориентированного по ходу линии
            slice[j - 1] = new Vector3(v.getX(), v.getY(),
                    (float) (z + radius * Math.cos(delta * j)));
        }
        return slice;
    }
}
