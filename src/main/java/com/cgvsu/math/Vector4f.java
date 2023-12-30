package com.cgvsu.math;


public class Vector4f extends VectorNf {
    final private float x;
    final private float y;
    final private float z;
    final private float k;
    public Vector4f(float x, float y, float z, float k){
        super(x, y, z, k);
        this.x = x;
        this.y = y;
        this.z = z;
        this.k = k;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getK() {
        return k;
    }

    public static Vector4f subtraction(Vector4f vector1, Vector4f vector2){
        return new Vector4f(
                vector1.x - vector2.x,
                vector1.y - vector2.y,
                vector1.z - vector2.z,
                vector1.k - vector2.k
        );
    }
    public static Vector4f addition(Vector4f vector1, Vector4f vector2){
        return new Vector4f(
                vector1.x + vector2.x,
                vector1.y + vector2.y,
                vector1.z + vector2.z,
                vector1.k + vector2.k
        );
    }
    @Override
    public Vector4f multiply(float a) {
        return new Vector4f(
                this.x * a,
                this.y * a,
                this.z * a,
                this.k * a
        );
    }

    @Override
    public float length() {
        return length;
    }

    @Override
    public Vector4f normalize() {
        return new Vector4f(this.x / length, this.y / length, this.z / length, this.k / length);
    }
    @Override
    public float[] getArrValues() {
        return new float[]{x, y, z, k};
    }

}
