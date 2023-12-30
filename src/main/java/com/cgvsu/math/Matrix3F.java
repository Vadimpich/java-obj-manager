package com.cgvsu.math;

public class Matrix3F extends MatrixNf {
    final private Vector3f vector1;
    final private Vector3f vector2;
    final private Vector3f vector3;

    public Matrix3F(Vector3f vector1,
                    Vector3f vector2,
                    Vector3f vector3) {
        super(vector1,vector2,vector3);
        if(vector1.getDimensional() != 3 ){
            throw new ArithmeticException("Неправильная размерность векторов");
        }
        this.vector1 = vector1;
        this.vector2 = vector2;
        this.vector3 = vector3;
        dimensional = 3;
    }

    public Matrix3F() {
        dimensional = 3;
        this.vector1 = new Vector3f(1, 0, 0);
        this.vector2 = new Vector3f(0, 1, 0);
        this.vector3 = new Vector3f(0, 0, 1);
        matrixInVectors = new Vector3f[]{
                vector1,
                vector2,
                vector3
        };
    }
    @Override
    public int getDimensional() {
        return dimensional;
    }
    @Override
    public Vector[] getMatrixInVectors() {
        return matrixInVectors;
    }

    public Vector3f getVector1() {
        return vector1;
    }

    public Vector3f getVector2() {
        return vector2;
    }

    public Vector3f getVector3() {
        return vector3;
    }
}
