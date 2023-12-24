package kg2020.task4.math;

import java.util.SimpleTimeZone;

/**
 * отвечает за хранение и работу матрицы преобразований
 */
public class Matrix4 {
    private float[] matrix;
    private static final int SIZE = 4;

    /**
     * Создает внутреннее хранилище параметров матрицы преобразования, переводя двумерный массив в одномерный
     *
     * @param m двумерная матрица преобразования
     */
    public Matrix4(float[][] m) {
        matrix = new float[16];
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                matrix[i * SIZE + j] = m[i][j];
            }
    }

    private Matrix4(float[] arr) {
        matrix = arr;
    }

    /**
     * Возвращает элемент матрицы по строке и столбцу
     *
     * @param row    номер строки
     * @param column номер столбца
     * @return значение элемента
     */
    public float getAt(int row, int column) {
        return matrix[row * SIZE + column];
    }

    /**
     * Задает элемент матрицы по строке и столбцу
     *
     * @param row    номер строки
     * @param column номер столбца
     * @return значение элемента
     */
    public void setAt(int row, int column, float value) {
        matrix[row * SIZE + column] = value;
    }

    /**
     * Создает нулевую матрицу
     *
     * @return матрица, заполненная нулями
     */

    public static Matrix4 zeroMatrix() {
        return new Matrix4(new float[16]);
    }

    /**
     * Создает единичную матрицу
     *
     * @return возвращает матрицу, заполненную нулями и единицами по главной диагонали
     */
    public static Matrix4 oneMatrix() {
        Matrix4 m = zeroMatrix();
        for (int i = 0; i < SIZE; i++) {
            m.setAt(i, i, 1);
        }
        return m;
    }

    /**
     * Умножение матрицы на число
     *
     * @param a число, на которое будут умножать
     * @return матрицу, помноженную на коэффициент а
     */
    public Matrix4 mul(float a) {
        Matrix4 m = zeroMatrix();
        for (int i = 0; i < this.matrix.length; i++) {
            m.matrix[i] = this.matrix[i] * a;
        }
        return m;
    }

    /**
     * Умножение матрицы на вектор.
     *
     * @param vector вектор, на который умножаем
     * @return результат умножения матрицы на вектор
     */
    public Vector4 mul(Vector4 vector) {
        float[] result = new float[4];
        for (int i = 0; i < SIZE; i++) {
            float sum = 0;
            for (int j = 0; j < SIZE; j++) {
                sum += this.getAt(i, j) * vector.at(j);
            }
            result[i] = sum;
        }
        return new Vector4(result[0], result[1], result[2], result[3]);
    }

    /**
     * возвращает результат умножения одной матрицы на другую
     *
     * @param other матрица, на которую умножаем
     * @return результат умножения на other
     */
    public Matrix4 mul(Matrix4 other) {
        Matrix4 result = Matrix4.zeroMatrix();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                float sum = 0;
                for (int k = 0; k < SIZE; k++) {
                    sum += this.getAt(i, k) * other.getAt(k, j);
                }
                result.setAt(i, j, sum);
            }
        }
        return result;
    }

    /**
     * Возвращает результат сложения одной матрицы с другой
     *
     * @param other матрица, с которой производим сложение
     * @return результат сложения матриц
     */
    public Matrix4 add(Matrix4 other) {
        Matrix4 result = Matrix4.zeroMatrix();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                result.setAt(i, j, this.getAt(i, j) + other.getAt(i, j));
            }
        }
        return result;
    }
}