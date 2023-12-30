package com.cgvsu.math;


public class MatrixNf implements Matrix {
    protected int dimensional;
    public Vector[] matrixInVectors;

    public MatrixNf(Vector... vectors) {

        dimensional = vectors.length;
        matrixInVectors = vectors;
        if (dimensional < 1) {
            throw new ArithmeticException("Нельзя создать матрицу столь малой размерности");
        }
        for (Vector vector : matrixInVectors) {
            if (vector.getDimensional() != dimensional) {
                throw new ArithmeticException("Размерность векторов не совпадает с размерностью матрицы");
            }
        }
    }

    @Override
    public Vector[] getMatrixInVectors() {
        return matrixInVectors;
    }

    public int getDimensional() {
        return dimensional;
    }

    @Override
    public Matrix addition(Matrix matrix) {
        if (dimensional != matrix.getDimensional()) {
            throw new ArithmeticException("Нельзя сложить матрицы разных размерностей");
        }
        Vector[] newVectors = new VectorNf[dimensional];
        for (int i = 0; i < dimensional; i++) {
            newVectors[i] = matrixInVectors[i].addition(matrix.getMatrixInVectors()[i]);
        }
        return new MatrixNf(newVectors);

    }

    @Override
    public Vector multiplyVector(Vector vector) {
        VectorNf newVector = null;
        try {
            newVector = (VectorNf) vector;
        } catch (Exception e) {
            throw new RuntimeException("Неправильная размерность вектора");
        }
        if (newVector.getDimensional() != dimensional) {
            throw new RuntimeException("Неправильная размерность вектора");
        }
        float[] newValues = new float[dimensional];
        for (int i = 0; i < dimensional; i++) {
            float total = 0;
            for (int j = 0; j < dimensional; j++) {
                total += matrixInVectors[i].getArrValues()[j] * vector.getArrValues()[j];

            }
            newValues[i] = total;
        }
        return new VectorNf(newValues);
    }

    @Override
    public Matrix multiplyMatrix(Matrix matrix) {
        if (dimensional != matrix.getDimensional()) {
            throw new RuntimeException("Неправильная размерность матрицы");
        }
        VectorNf[] newVectors = new VectorNf[dimensional];
        for (int i = 0; i < dimensional; i++) {
            float[] values = new float[dimensional];
            for (int j = 0; j < dimensional; j++) {
                float sum = 0;
                for (int k = 0; k < dimensional; k++) {
                    sum += getMatrixInVectors()[i].getArrValues()[k] * matrix.getMatrixInVectors()[k].getArrValues()[j];
                }
                values[j] = sum;
            }
            newVectors[i] = new VectorNf(values);
        }
        return new MatrixNf(newVectors);
    }

    @Override
    public Matrix transponate() {
        VectorNf[] newVectors = new VectorNf[dimensional];
        for (int i = 0; i < dimensional; i++) {
            float[] values = new float[dimensional];
            for (int j = 0; j < dimensional; j++) {
                values[j] = matrixInVectors[j].getArrValues()[i];
            }
            newVectors[i] = new VectorNf(values);
        }
        return new MatrixNf(newVectors);
    }

    @Override
    public void printMatrix() {
        System.out.println("_______________________");
        for (int j = 0; j < dimensional; j++) {
            for (int i = 0; i < dimensional; i++) {
                System.out.print(matrixInVectors[i].getArrValues()[j] + " ");
            }
            System.out.println();
        }
        System.out.println("_______________________");
    }


    @Override
    public double getDeterminant() {
        double deter = 0;
        if (dimensional == 1) {
            deter = matrixInVectors[0].getArrValues()[0];
            return deter;
        }
        for (int k = 0; k < dimensional; k++) {
            VectorNf[] vectors = new VectorNf[dimensional - 1];
            int counter = 0;
            for (int i = 0; i < dimensional; i++) {
                if (i == k) {
                    continue;
                }
                float[] values = new float[dimensional - 1];
                for (int j = 1; j < dimensional; j++) {
                    values[j - 1] = matrixInVectors[j].getArrValues()[i];
                }
                vectors[counter] = new VectorNf(values);
                counter++;
            }

            MatrixNf tmp = new MatrixNf(vectors);
            double tmpDeter = tmp.getDeterminant();
            if (k % 2 == 0) {
                deter += tmpDeter * matrixInVectors[0].getArrValues()[k];
            } else {
                deter -= tmpDeter * matrixInVectors[0].getArrValues()[k];
            }
        }


        return deter;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatrixNf that = (MatrixNf) o;
        if (dimensional != that.getDimensional()) {
            return false;
        }
        for (int i = 0; i < dimensional; i++) {
            if (!matrixInVectors[i].equals(that.matrixInVectors[i])) {
                return false;
            }
        }
        return true;
    }


}
