package com.cgvsu.math;

public class Vector2f extends VectorNf {

    final private  float x;
    final private float y;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vector2f(float x, float y) {
        super(x, y);
        this.x = x;
        this.y = y;
    }
    public static Vector2f addition(Vector2f a, Vector2f b){
        return new Vector2f(a.getX() + b.getX(), a.getY() + b.getY());
    }

    public static Vector2f subtraction(Vector2f a, Vector2f b){
        return new Vector2f(a.getX() - b.getX(), a.getY() - b.getY());
    }

    @Override
    public float[] getArrValues() {
        return new float[]{x, y};
    }
    public static Vector2f scale(float a, Vector2f vector) {
        return  new Vector2f(vector.getX() * a, vector.getY() * a);
    }
    @Override
    public float length() {
        return this.length;
    }

    @Override
    public Vector2f normalize() {
        return new Vector2f(this.x / length, this.y / length);
    }
    public static float scalarProduct(Vector2f vector1, Vector2f vector2){
        return vector1.length * vector2.length * vector1.cosAngleBetweenVectors( vector2);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2f vector = (Vector2f) o;
        return Float.compare(vector.x, x) == 0 && Float.compare(vector.y, y) == 0;
    }
}
