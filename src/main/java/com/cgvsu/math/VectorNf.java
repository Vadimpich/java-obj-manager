package com.cgvsu.math;

import static java.lang.Math.abs;

public class VectorNf implements Vector {

    final protected float[] values;
    protected int dimensional;
    protected float length;

    @Override
    public int getDimensional() {
        return dimensional;
    }

    public VectorNf(float... args) {
        dimensional = args.length;
        if (dimensional < 1) {
            throw new ArithmeticException("Нельзя создать пустой вектор");
        }
        values = args;
        length = 0;
        for (float value : values)
            length += value * value;
        length = (float) Math.pow(length, 0.5);
        roundVector();
    }

    @Override
    public float getLength() {
        return length;
    }

    @Override
    public float[] getArrValues() {
        return values;
    }

    @Override
    public Vector subtraction(Vector vector) {
        if (vector.getDimensional() != dimensional) {
            throw new ArithmeticException();
        }
        float[] newValues = new float[dimensional];
        for (int i = 0; i < dimensional; i++) {
            newValues[i] = values[i] - vector.getArrValues()[i];
        }
        return new VectorNf(newValues);
    }

    @Override
    public Vector addition(Vector vector) {
        if (vector.getDimensional() != dimensional) {
            throw new ArithmeticException();
        }
        float[] newValues = new float[dimensional];
        for (int i = 0; i < dimensional; i++) {
            newValues[i] = values[i] + vector.getArrValues()[i];
        }
        return new VectorNf(newValues);
    }

    @Override
    public Vector multiply(float a) {
        float[] newValues = new float[dimensional];
        for (int i = 0; i < dimensional; i++) {
            newValues[i] = values[i] * a;
        }
        return new VectorNf(newValues);
    }

    @Override
    public float length() {
        return length;
    }

    @Override
    public VectorNf normalize() {
        float[] newValues = new float[dimensional];
        for (int i = 0; i < dimensional; i++) {
            newValues[i] = values[i] / length;
        }
        return new VectorNf(newValues);
    }

    @Override
    public float scalarProduct(Vector vector) {
        if (vector.getDimensional() != dimensional) {
            throw new ArithmeticException("Размерности векторов не совпадают");
        }
        float scalarProd = 0;
        for (int i = 0; i < dimensional; i++) {
            scalarProd += values[i] * vector.getArrValues()[i];
        }
        return scalarProd;
    }

    @Override
    public float cosAngleBetweenVectors(Vector vector) {
        if (vector.getDimensional() != dimensional) {
            throw new ArithmeticException("Размерности векторов не совпадают");
        }
        if (length == 0 || vector.getLength() == 0) {
            throw new ArithmeticException("Нулевой вектор");
        }
        return scalarProduct(vector) / (length * vector.getLength());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VectorNf that = (VectorNf) o;
        if (dimensional != that.dimensional) {
            return false;
        }
        for (int i = 0; i < dimensional; i++) {
            if (abs(that.values[i] - values[i]) > 0.00000001) {
                return false;
            }
        }
        return true;
    }

    private void roundVector() {
        for (int i = 0; i < dimensional; i++) {
            if (abs(values[i]) < 0.0000000001) {
                values[i] = 0;
            }
        }
    }

    public float cross(VectorNf vector) {
        return this.getArrValues()[0] * vector.getArrValues()[1] - this.getArrValues()[1] * vector.getArrValues()[0];
    }

    public Vector3f vectorProduct(VectorNf vector) {
        if (this.dimensional != 3) {
            throw new ArithmeticException("Неправильная размерность");
        }
        if (vector.dimensional != 3) {
            throw new ArithmeticException("Неправильная размерность");
        }

        return new Vector3f(this.getArrValues()[0], this.getArrValues()[1], this.getArrValues()[2]).vectorProduct(
                new Vector3f(vector.getArrValues()[0], vector.getArrValues()[1], vector.getArrValues()[2])
        );
    }
}
