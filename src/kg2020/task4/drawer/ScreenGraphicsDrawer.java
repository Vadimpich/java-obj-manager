/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2020.task4.drawer;

import kg2020.task4.screen.ScreenConverter;
import kg2020.task4.threeD.PolyLine3D;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Рисовальщик на графиксе экрана
 * @author Alexey
 */
public abstract class ScreenGraphicsDrawer implements IDrawer {
    private ScreenConverter sc;
    private Graphics2D gr;

    /**
     * Создаёт экземпляр рисовальщика
     * @param sc преобразователь координат
     * @param gr графикс
     */
    public ScreenGraphicsDrawer(ScreenConverter sc, Graphics2D gr) {
        this.sc = sc;
        this.gr = gr;
    }

    public Graphics2D getGraphics() {
        return gr;
    }

    public ScreenConverter getScreenConverter() {
        return sc;
    }
    
    @Override
    public void draw(Collection<PolyLine3D> polylines) {
        LinkedList<PolyLine3D> lines = new LinkedList<>();
        IFilter<PolyLine3D> filter = getFilter();
        for (PolyLine3D pl : polylines) {
            if (filter.permit(pl))
                lines.add(pl);
        }
        PolyLine3D[] arr = lines.toArray(new PolyLine3D[0]);
        Arrays.sort(arr, getComparator());
        for (PolyLine3D pl : arr) {
            oneDraw(pl);
        }
    }

    /**
     * Метод, "очищающий" экран в какой-то цвет
     * @param color цвет
     */
    
    @Override
    public void clear(int color) {
        Graphics2D g = getGraphics();
        Color c = g.getColor();
        g.setColor(new Color(color));
        g.fillRect(0, 0, sc.getWs(), sc.getHs());
        g.setColor(c);
    }
    
    /**
     * Метод, умеющий рисовать одну полилинию
     * @param polyline полилиния, которую требуется нарисовать
     */
    protected abstract void oneDraw(PolyLine3D polyline);
    
    /**
     * Должен возвращать фильтр рисуемых полилиний.
     * С помощью него можно оставить только те из них, которые следует рисовать.
     * Например, можно исключить те линии, которые находятся "позади"
     * @return фильтр
     */
    protected abstract IFilter<PolyLine3D> getFilter();
    
    /**
     * Должен возвращать компаратор полилиний для упорядочивания их.
     * @return компаратор
     */
    protected abstract Comparator<PolyLine3D> getComparator();
}
