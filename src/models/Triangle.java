package models;

import kg2021examples_task4threedimensions.math.Vector3;
import kg2021examples_task4threedimensions.third.IModel;
import kg2021examples_task4threedimensions.third.PolyLine3D;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Triangle implements IModel {
    private Vector3 pointA;
    private Vector3 pointB;
    private Vector3 pointC;

    public Triangle(Vector3 pointA, Vector3 pointB, Vector3 pointC) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.pointC = pointC;
    }

    @Override
    public List<PolyLine3D> getLines() {
        List<PolyLine3D> lines = new LinkedList<>();
        lines.add(new PolyLine3D(Arrays.asList(pointA, pointB, pointC), true, Color.black));
        return lines;
    }

    @Override
    public void rotate(float angle) {

    }
}
