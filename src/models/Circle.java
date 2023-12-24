package models;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Circle implements IModel {
    private Vector3 center;
    private float radius;
    private final int EDGES = 32;


    public Circle(Vector3 center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    public Vector3 getCenter() {
        return center;
    }

    public void setCenter(Vector3 center) {
        this.center = center;
    }

    @Override
    public List<PolyLine3D> getLines() {
        Vector3[] slice = new Vector3[EDGES];
        double delta = Math.PI * 2 / EDGES;
        for (int i = 1; i <= EDGES; i++) {
            float x = (float) (radius * Math.cos(delta * (i)));
            float y = (float) (radius * Math.sin(delta * (i)));
            float z = center.getZ();
            slice[i - 1] = new Vector3(x, y, z);
        }
        List<PolyLine3D> list = new LinkedList<>();
        list.add(new PolyLine3D(Arrays.asList(slice), true));
        return list;
    }
}
