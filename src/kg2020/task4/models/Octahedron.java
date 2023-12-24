package kg2020.task4.models;

import kg2020.task4.math.Vector3;
import kg2020.task4.threeD.IModel;
import kg2020.task4.threeD.PolyLine3D;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Octahedron implements IModel {
    private Vector3 LTF, RBN;
    private final float length;

    public Octahedron(Vector3 LTF, float length) {
        this.LTF = LTF; // верхняя точка
        this.length = length;
        this.RBN = new Vector3(LTF.getX(), LTF.getY() - 2 * length, LTF.getZ()); // нижняя точка
    }

    @Override
    public List<PolyLine3D> getLines() {
        LinkedList<PolyLine3D> lines = new LinkedList<>();

        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX() + length, LTF.getY() - length, LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY() - length, LTF.getZ() - length)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX() - length, LTF.getY() - length, LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY() - length, LTF.getZ() - length)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX() - length, LTF.getY() - length, LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY() - length, LTF.getZ() + length)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY() - length, LTF.getZ() + length),
                new Vector3(LTF.getX() + length, LTF.getY() - length, LTF.getZ())
        }), true));

        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(LTF.getX() + length, LTF.getY() - length, LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY() - length, LTF.getZ() - length)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(LTF.getX() - length, LTF.getY() - length, LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY() - length, LTF.getZ() - length)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(LTF.getX() - length, LTF.getY() - length, LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY() - length, LTF.getZ() + length)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(LTF.getX(), LTF.getY() - length, LTF.getZ() + length),
                new Vector3(LTF.getX() + length, LTF.getY() - length, LTF.getZ())
        }), true));
        return lines;
    }





    /*private List<PolyLine3D> planes = new ArrayList<>();
    private Vector3 LTF;
    private Vector3 RBN;

    public Octahedron(Vector3 LTF, float size, int step) {
        step *= 2;
        this.LTF = LTF;
        RBN = new Vector3(LTF.getX() + size, LTF.getY() - size, LTF.getZ() - size);
        createSphere(step);

    }

    private void createSphere(int step){
        Parallelepiped cube = new Parallelepiped(LTF, RBN);

        Vector3 centreSphere = new Vector3(
                (LTF.getX() + RBN.getX()) / 2,
                (LTF.getY() + RBN.getY()) / 2,
                (LTF.getZ() + RBN.getZ()) / 2);
        Vector3 centreEdge1 = findCentreEdge(cube.getLines().get(0).getPoints());
        Vector3 vect1 = new Vector3(centreEdge1.getX() - centreSphere.getX(),
                centreEdge1.getY() - centreSphere.getY(),
                centreEdge1.getZ() - centreSphere.getZ());
//        Vector3 vect2 = new Vector3(-vect1.getX(), -vect1.getY(), -vect1.getZ());
        double r = Math.sqrt(Math.pow(vect1.getX(), 2) + Math.pow(vect1.getY(), 2) + Math.pow(vect1.getZ(), 2));

        List<Vector3> pointsCircle = createFirstCircle(r, step);
        List<Vector3> prevPoints = turnCircle(pointsCircle, step, 0, centreSphere);

        for (int i = 0; i < step + 1; i++) {
            List<Vector3> points = turnCircle(pointsCircle, step, i, centreSphere);
            for (int j = 0; j < step / 2 + 1; j++) {
                if ((i > 0) && (j > 0)) {
                    List<Vector3> p = new ArrayList<>();
                    p.add(points.get(j));
                    p.add(prevPoints.get(j - 1));
                    if (j == 1) {
                        p.add(prevPoints.get(j));
                    } else {
                        if (j == step / 2) {
                            p.add(points.get(j - 1));
                        } else {
                            p.add(prevPoints.get(j));
                            planes.add(new PolyLine3D(p, true));
                            p.remove(2);
                            p.add(points.get(j - 1));
                        }
                    }
                    planes.add(new PolyLine3D(p, true));
                }
            }
            prevPoints = turnCircle(pointsCircle, step, i, centreSphere);
        }
        System.out.println("planes = " + planes.size());
    }

    private List<Vector3> turnCircle(List<Vector3> pointCircle, int step, int i, Vector3 centreSphere) {
        double rad = 2 * i * Math.PI / step;
        List<Vector3> points = new ArrayList<>();

        for (Vector3 p : pointCircle) {
            points.add(new Vector3(p.getX() + centreSphere.getX(), (float) (p.getY() * Math.sin(rad)) + centreSphere.getY(),
                    (float) (p.getY() * Math.cos(rad)) + centreSphere.getZ()));
//            for (int j = 0; j < points.size(); j++) {
//                System.out.println("p" +points.get(j).getX() + " "+
//                        points.get(j).getY() +" "+
//                        points.get(j).getZ());
//            }
        }
        return points;
    }

    private List<Vector3> createFirstCircle(double r, int step) {
        List<Vector3> points = new ArrayList<>();
        double rad = 2 * Math.PI / step;
        for (int i = 0; i < step; i++) {
            double dx1 = r * Math.cos(rad * i);
            double dy1 = r * Math.sin(rad * i);
            points.add(new Vector3((float) dx1, (float) dy1, 0));
        }
        return points;
    }

    private Vector3 findCentreEdge(List<Vector3> p) {
        double max = -1;
        Vector3 pFar = p.get(0);
        for (int i = 0; i < 3; i++) {
            double len = Math.sqrt((p.get(i + 1).getX() - p.get(0).getX()) * (p.get(i + 1).getX() - p.get(0).getX())
                    + (p.get(i + 1).getY() - p.get(0).getY()) * (p.get(i + 1).getY() - p.get(0).getY())
                    + (p.get(i + 1).getZ() - p.get(0).getZ()) * (p.get(i + 1).getZ() - p.get(0).getZ()));
            if (max < len) {
                max = len;
                pFar = p.get(i + 1);
            }
        }
        return new Vector3((pFar.getX() + p.get(0).getX()) / 2, (pFar.getY() + p.get(0).getY()) / 2, (pFar.getZ() + p.get(0).getZ()) / 2);
    }

    @Override
    public List<PolyLine3D> getLines() {
        return planes;
    }


   /* public Vector3 getMin() {
        Vector3 currMin = planes.get(0).getPoints().get(0);
        float minX = currMin.getX();
        float minY = currMin.getY();
        float minZ = currMin.getZ();

        for (PolyLine3D line : planes) {
            for (Vector3 v : line.getPoints()) {
                if (v.getX() <= minX) {
                    minX = v.getX();
                }
                if (v.getY() <= minY) {
                    minY = v.getY();
                }
                if (v.getZ() <= minZ) {
                    minZ = v.getZ();
                }
            }
        }
        return new Vector3(minX, minY, minZ);
    }


    public Vector3 getMax() {
        Vector3 currMax = planes.get(0).getPoints().get(0);
        float maxX = currMax.getX();
        float maxY = currMax.getY();
        float maxZ = currMax.getZ();

        for (PolyLine3D line : planes) {
            for (Vector3 v : line.getPoints()) {
                System.out.println(maxX + " " + maxY + " " + maxZ);
                if (v.getX() >= maxX) {
                    maxX = v.getX();
                }
                if (v.getY() >= maxY) {
                    maxY = v.getY();
                }
                if (v.getZ() >= maxZ) {
                    maxZ = v.getZ();
                }
            }
        }
        return new Vector3(maxX, maxY, maxZ);
    }*/

}
