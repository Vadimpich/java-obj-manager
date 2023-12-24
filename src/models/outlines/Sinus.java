package models.outlines;

public class Sinus implements IOutline {
    private float radius;
    private float k;

    public Sinus(float radius, float k) {
        this.radius = radius;
        this.k = k;
    }

    @Override
    public float getRadius(double t) {
        return (float) (k * Math.sin(t * 20 * Math.PI) + radius);
    }

    @Override
    public float getRadius() {
        return 0;
    }
}
