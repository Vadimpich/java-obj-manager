package kg2020.task4.threeD;

import kg2020.task4.math.Vector3;

public class Camera implements ICamera {
    /**
     * Метод, преобразуюший точку из мировой системы координат в систему координат камеры.
     * Сначала к вектору применяется масштаб(S), далее поворот(R), потом перенос(T) и в конце - проекция(P).
     * r = P * T * R * S * v
     * @param v вектор, который надо перевести
     * @return новый вектор
     */
    @Override
    public Vector3 w2s(Vector3 point) {
        return new Vector3(point.getX(), point.getY(), point.getZ());
    }
}
