package models.paths;

import kg2019examples_task4threedimensions.math.Vector3;

public class I_Line implements ILine {
    private Vector3 startPoint;
    private float k;

    public I_Line(Vector3 startPoint, float k) {
        this.startPoint = startPoint;
        this.k = k;
    }

    @Override
    public Vector3 getPoint(double t) {
        float x = startPoint.getX();
        float y = startPoint.getY() + k * startPoint.getZ();
        float z = startPoint.getZ() + (float) t;
        return new Vector3(x, y, z);
    }
}
