package com.cgvsu.math;

public class Matrix4F extends MatrixNf {
    final private Vector vector1;
    final private Vector vector2;
    final private Vector vector3;
    final private Vector vector4;

    public Matrix4F(
            Vector vector1,
            Vector vector2,
            Vector vector3,
            Vector vector4) {
        super(vector1, vector2, vector3, vector4);
        this.vector1 = vector1;
        this.vector2 = vector2;
        this.vector3 = vector3;
        this.vector4 = vector4;
        matrixInVectors = new Vector[]{vector1, vector2, vector3, vector4};
        dimensional = 4;
    }
    public Matrix4F(VectorNf... vectors){
        super(vectors);
        if(matrixInVectors[0].getDimensional() != 4){
            throw new ArithmeticException("Неправильная размерность вектора");
        }
        this.vector1 = matrixInVectors[0];
        this.vector2 = matrixInVectors[1];
        this.vector3 = matrixInVectors[2];
        this.vector4 = matrixInVectors[3];
    }

    public Matrix4F() {
        dimensional = 4;
        this.vector1 = new VectorNf(1, 0, 0, 0);
        this.vector2 = new VectorNf(0, 1, 0, 0);
        this.vector3 = new VectorNf(0, 0, 1, 0);
        this.vector4 = new VectorNf(0, 0, 0, 1);

        matrixInVectors = new VectorNf[]{
                (VectorNf) vector1,
                (VectorNf) vector2,
                (VectorNf) vector3,
                (VectorNf) vector4
        };
    }

    public Matrix4F(float[] matrix) {
        super(matrix);
    }

    public Vector getVector1() {
        return vector1;
    }

    public Vector getVector2() {
        return vector2;
    }

    public Vector getVector3() {
        return vector3;
    }

    public Vector getVector4() {
        return vector4;
    }

}
