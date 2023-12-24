package models.outlines;

public class Cylinder implements IOutline {
    private float radius;

    public Cylinder(float radius) {
        this.radius = radius;
    }

    @Override
    public float getRadius(double t) {
        return this.radius;
    }

    @Override
    public float getRadius() {
        return this.radius;
    }
}
