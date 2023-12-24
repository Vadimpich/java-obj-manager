/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2020.task4.drawer;

import kg2020.task4.math.Vector3;
import kg2020.task4.screen.ScreenConverter;
import kg2020.task4.screen.ScreenCoordinates;
import kg2020.task4.screen.ScreenPoint;
import kg2020.task4.threeD.PolyLine3D;

import java.awt.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Реализация рисователя полигонов с помощью рёбер.
 * @author Alexey
 */
public class SimpleEdgeDrawer extends ScreenGraphicsDrawer {

    public SimpleEdgeDrawer(ScreenConverter sc, Graphics2D g) {
        super(sc, g);
    }
    
    /**
     * Рисует одну полилинию на графиксе.
     * @param polyline полилиния
     */
    @Override
    protected void oneDraw(PolyLine3D polyline) {
        LinkedList<ScreenPoint> points = new LinkedList<>();
        /*переводим все точки в экранные*/
        for (Vector3 v : polyline.getPoints())
            points.add(getScreenConverter().r2s(v));
        getGraphics().setColor(Color.BLACK);
        /*если точек меньше двух, то рисуем отдельными алгоритмами*/
        if (points.size() < 2) {
            if (points.size() > 0)
                getGraphics().fillRect(points.get(0).getI(), points.get(0).getJ(), 1, 1);
            return;
        }
        /*создаём хранилище этих точек в виде двух массивов*/
        ScreenCoordinates crds = new ScreenCoordinates(points);
        /*если линия замкнута - рисем полиго, иначе - полилинию*/
        if (polyline.isClosed())
            getGraphics().fillPolygon(crds.getXx(), crds.getYy(), crds.size());
        else
            getGraphics().drawPolyline(crds.getXx(), crds.getYy(), crds.size());
    }

    /**
     * В данной реализации возвращаем фильтр, который одобряет все полилинии.
     * @return фильтр полилиний
     */
    @Override
    protected IFilter<PolyLine3D> getFilter() {
        return new IFilter<PolyLine3D>() {
            @Override
            public boolean permit(PolyLine3D line) {
                return true;
            }
        };
    }

    /**
     * Сравниваем полилинии по среднему Z.
     * @return компаратор
     */
    @Override
    protected Comparator<PolyLine3D> getComparator() {
        return new Comparator<PolyLine3D>() {
            private static final float EPSILON = 1e-10f;
            @Override
            public int compare(PolyLine3D o1, PolyLine3D o2) {
                float d = o1.avgZ() - o2.avgZ();
                if (-EPSILON < d && d < EPSILON)
                    return 0;
                return d < 0 ? -1 : 1;
            }
        };
    }

    @Override
    public void draw(List<PolyLine3D> lines) {

    }
}
