package kg2020.task4.threeD;

import java.util.List;

/**
 * Интерфейс для простейших 3Д моделей
 */
public interface IModel {
    /**
     * Модель метода, возвращающая список линий, описывающих контуры трехмерной фигуры
     * @return Список из {@link PolyLine3D}
     */
    List<PolyLine3D> getLines();
}
