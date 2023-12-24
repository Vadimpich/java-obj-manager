package models.ilinemodels;

import kg2019examples_task4threedimensions.math.Vector3;

import java.util.LinkedList;
import java.util.List;

public class ITrefoilKnot implements ILine {
    private float trefoilRad;
    private Vector3 trefoilCenter;

    public ITrefoilKnot(float trefoilRad, Vector3 trefoilCenter) {
        this.trefoilRad = trefoilRad;
        this.trefoilCenter = trefoilCenter;
    }

    @Override
    public Vector3 getPoint(double t) {
        float x = trefoilCenter.getX() + (float) (Math.sin(t) + 2 * Math.sin(2 * t));
        float y = trefoilCenter.getY() + (float) (Math.cos(t) - 2 * Math.cos(2 * t));
        float z = trefoilCenter.getZ() + (float) (-Math.sin(3 * t));
        return new Vector3(x, y, z);
    }

    @Override
    public List<Vector3> getPoint(int i, int dimension) {
        double t = i * Math.PI * 2 / (double) dimension;
        float x = trefoilCenter.getX() + (float) (Math.tan(trefoilRad) * (Math.sin(t) + 2 * Math.sin(2 * t)));
        float y = trefoilCenter.getY() + (float) (Math.tan(trefoilRad) * (Math.cos(t) - 2 * Math.cos(2 * t)));
        float z = trefoilCenter.getZ() + (float) (Math.tan(trefoilRad) * (-Math.sin(3 * t)));
        List<Vector3> list = new LinkedList<>();
        list.add(new Vector3(x, y, z));
        return list;
    }
}
