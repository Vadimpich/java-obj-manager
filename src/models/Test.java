package models;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.util.LinkedList;
import java.util.List;

public class Test implements IModel {
    private Vector3 center;
    private float radius;

    public Test(Vector3 center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public List<PolyLine3D> getLines() {
        List<PolyLine3D> list = new LinkedList<>();
        float x = 0;
        float y = 0;
        double angle = Math.PI*2/32;
        for (int i = 0; i < 32; i++) {
            x = (float)(radius*Math.cos(angle*i));
            y = (float)(radius*Math.sin(angle*i));
            float z1 = 0;
            float z2 = 0.5f;
            Line3D line1 = new Line3D(new Vector3(x,y,z1), new Vector3(x,y,z2));
            list.addAll(line1.getLines());
        }
        return list;
    }
}
