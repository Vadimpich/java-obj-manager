package com.cgvsu.math;

public interface Matrix {
    public Vector[] getMatrixInVectors();

    public int getDimensional();

    public Matrix addition(Matrix matrix);

    public Vector multiplyVector(Vector vector);

    public Matrix multiplyMatrix(Matrix vector);

    public Matrix transponate();

    public void printMatrix();

    public double getDeterminant();
}
