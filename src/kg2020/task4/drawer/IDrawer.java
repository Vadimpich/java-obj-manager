package kg2020.task4.drawer;

import kg2020.task4.threeD.PolyLine3D;

import java.util.Collection;
import java.util.List;

public interface IDrawer {
    /**
     * Очищает область заданным цветом
     * @param color цвет
     */
    public void clear(int color);

    /**
     * Рисует все полилинии
     * @param polyline набор рисуемых полилиний.
     */
    public void draw(Collection<PolyLine3D> polyline);
    void draw(List<PolyLine3D> lines);
}
