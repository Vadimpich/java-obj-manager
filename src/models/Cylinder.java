package models;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * попытка в цилиндр
 */

public class Cylinder implements IModel {
    private Vector3 startCenter, endCenter;
    private float radius;
    public static final int POLYGONS = 32;

    /**
     * Задает параметры цилиндра (использует внутреннюю константу POLYGONS для описания круга в приближении):
     * @param startCenter {@link Vector3} центр окружности начала/низа
     * @param endCenter {@link Vector3} центp окружности конца/верха
     * @param radius радиус обоих окружностей
     */
    public Cylinder(Vector3 startCenter, Vector3 endCenter, float radius) {
        this.startCenter = startCenter;
        this.endCenter = endCenter;
        this.radius = radius;
    }

    /**
     * Строит математическую модель цилиндра с приближенной моделью окружности из 32 точек.
     * @return список из {@link PolyLine3D}: Два среза цилиндра и грани в виде прямоугольников, соединяющие их
     */

    @Override
    public List<PolyLine3D> getLines() {
        List<PolyLine3D> lines = new LinkedList<>();

        double deltaAngle = Math.PI * 2 / POLYGONS;
        Vector3[] startSlice = new Vector3[POLYGONS];
        Vector3[] endSlice = new Vector3[POLYGONS];

        for (int i = 1; i <= POLYGONS; i++) {
            float x = (float) (radius * Math.cos(deltaAngle * (i)));
            float y = (float) (radius * Math.sin(deltaAngle * (i)));
            float z1 = startCenter.getZ();
            startSlice[i - 1] = new Vector3(x, y, z1);

            float z2 = endCenter.getZ();
            endSlice[i - 1] = new Vector3(x, y, z2);

            Vector3[] sidePolygon = new Vector3[]{
                    new Vector3(x, y, z1),
                    new Vector3(x, y, z2),
                    new Vector3((float) (radius * Math.cos(deltaAngle * (i + 1))),
                            (float) (radius * Math.sin(deltaAngle * (i + 1))),
                            z2),
                    new Vector3((float) (radius * Math.cos(deltaAngle * (i + 1))),
                            (float) (radius * Math.sin(deltaAngle * (i + 1))),
                            z1),
            };
            lines.add(new PolyLine3D(Arrays.asList(sidePolygon), true));
        }
        lines.add(new PolyLine3D(Arrays.asList(startSlice), true));
        lines.add(new PolyLine3D(Arrays.asList(endSlice), true));
        /*
         * махия
         */
        return lines;
    }
}
