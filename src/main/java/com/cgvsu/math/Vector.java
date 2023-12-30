package com.cgvsu.math;


public interface Vector {
    public static float cosAngleBetweenVectors(Vector vector1, Vector vector2) {
        return (vector1.scalarProduct(vector2)) / (vector1.length() * vector2.length());
    }

    public float[] getArrValues();

    public Vector subtraction(Vector vector);

    public Vector addition(Vector vector);

    public Vector multiply(float a);

    public float length();

    public Vector normalize();

    public float scalarProduct(Vector vector);

    public float cosAngleBetweenVectors(Vector vector);

    public int getDimensional();

    public float getLength();

}
