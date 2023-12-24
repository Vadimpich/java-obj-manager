package kg2020.task4.threeD;

import kg2020.task4.math.Vector3;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс, описывающий линию из точек в трехмерном пространстве
 */
public class PolyLine3D {
    private List<Vector3> points;
    private boolean closed;

    /**
     * Конструктор линии в трехмерном пространстве
     *
     * @param points - LinkedList из {@link Vector3}
     */
    public PolyLine3D(List<Vector3> points) {
        this.points = new LinkedList<>();
        this.points.addAll(points);
    }

    /**
     * Создаёт новую полилинию на основе трёхмерных точек.
     *
     * @param points список точек-вершин ломанной
     * @param closed признак замкнутостит линии
     */
    public PolyLine3D(Collection<Vector3> points, boolean closed) {
        this.points = new LinkedList<Vector3>(points);
        this.closed = closed;
    }

    /**
     * Признак закрытости
     *
     * @return возвращает истину, если линия замкнута, иначе - ложь.
     */
    public boolean isClosed() {
        return closed;
    }


    /**
     * возвращает список точек в трехмерном пространстве
     *
     * @return LinkedList из {@link Vector3}
     */
    public List<Vector3> getPoints() {
        return new LinkedList<>(points);
    }

    /**
     * Вычисляет среднее арифметическое по оси Z.
     *
     * @return среднее по Z для полилинии.
     */
    public float avgZ() {
        if (points == null || points.size() == 0)
            return 0;
        float sum = 0;
        for (Vector3 v : points)
            sum += v.getZ();
        return sum / points.size();
    }
}
