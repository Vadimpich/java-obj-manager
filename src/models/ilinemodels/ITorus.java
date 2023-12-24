package models.ilinemodels;

import kg2019examples_task4threedimensions.math.Vector3;

import java.util.LinkedList;
import java.util.List;

public class ITorus implements ILine {
    private float torusRad;
    private Vector3 torusCenter;

    public ITorus(float torusRad, Vector3 torusCenter) {
        this.torusRad = torusRad;
        this.torusCenter = torusCenter;
    }

    @Override
    public Vector3 getPoint(double t) {
        float x = torusCenter.getX() + (float) (torusRad * Math.cos(t * Math.PI * 2));
        float y = torusCenter.getY() + (float) (torusRad * Math.sin(t * Math.PI * 2));
        float z = torusCenter.getZ();

        return new Vector3(x, y, z);
    }

    @Override
    public List<Vector3> getPoint(int i, int dimension) {
        double t = (double) i / dimension;
        float x = torusCenter.getX() + (float) (torusRad * Math.cos(t * Math.PI * 2));
        float y = torusCenter.getY() + (float) (torusRad * Math.sin(t * Math.PI * 2));
        float z = torusCenter.getZ();
        List<Vector3> list = new LinkedList<>();
        list.add(new Vector3(x, y, z));
        return list;
    }
}
