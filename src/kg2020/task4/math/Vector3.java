package kg2020.task4.math;

/**
 * Класс для описания аналога RealPoint в трехмерном пространства
 */
public class Vector3 {
    private float[] values;

    /**
     * Конструктор трехмерной точки
     *
     * @param x - составляющая Х
     * @param y - составляющая У
     * @param z - составляющая Z
     */

    public Vector3(float x, float y, float z) {
        this.values = new float[]{x, y, z};
    }

    /**
     * @return Х - составляющую
     */
    public float getX() {
        return values[0];
    }

    /**
     * @return У - составляющую
     */
    public float getY() {
        return values[1];
    }

    /**
     * @return Z - составляющую
     */
    public float getZ() {
        return values[2];
    }

    /**
     * Возвращает координаты точки по индексу во внутреннем массиве
     * @param index индекс возвращаемой координаты
     * @return значение координаты
     */
    public float at(int index) {
        return values[index];
    }
}
