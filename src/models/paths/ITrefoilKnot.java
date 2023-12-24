package models.paths;

import kg2019examples_task4threedimensions.math.Vector3;

public class ITrefoilKnot implements ILine {
    private float trefoilRad;
    private Vector3 trefoilCenter;

    public ITrefoilKnot(float trefoilRad, Vector3 trefoilCenter) {
        this.trefoilRad = trefoilRad;
        this.trefoilCenter = trefoilCenter;
    }

    @Override
    public Vector3 getPoint(double t) {
        t *= Math.PI * 2;
        float x = trefoilCenter.getX() + (float) (Math.tan(trefoilRad) * (Math.sin(t) + 2 * Math.sin(2 * t)));
        float y = trefoilCenter.getY() + (float) (Math.tan(trefoilRad) * (Math.cos(t) - 2 * Math.cos(2 * t)));
        float z = trefoilCenter.getZ() + (float) (Math.tan(trefoilRad) * (-Math.sin(3 * t)));
        return new Vector3(x, y, z);
    }
}
