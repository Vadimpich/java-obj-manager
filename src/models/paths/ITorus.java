package models.paths;

import kg2019examples_task4threedimensions.math.Vector3;

public class ITorus implements ILine {
    private float torusRad;
    private Vector3 torusCenter;

    public ITorus(float torusRad, Vector3 torusCenter) {
        this.torusRad = torusRad;
        this.torusCenter = torusCenter;
    }

    @Override
    public Vector3 getPoint(double t) {
        float z = torusCenter.getX() + (float) (torusRad * Math.cos(t * Math.PI * 2));
        float y = torusCenter.getY() + (float) (torusRad * Math.sin(t * Math.PI * 2));
        float x = torusCenter.getZ();

        return new Vector3(x, y, z);
    }
}
