package kg2020.task4.models;

import kg2020.task4.math.Vector3;
import kg2020.task4.threeD.IModel;
import kg2020.task4.threeD.PolyLine3D;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Cube implements IModel {
    private final Vector3 LTF;
    private final float length;

    public Cube(float length, Vector3 LTF) {
        this.length = length;
        this.LTF = LTF;
    }

    @Override
    public List<PolyLine3D> getLines() {
        LinkedList<PolyLine3D> lines = new LinkedList<>();
        /*Дальняя сторона (Z фиксирован и вязт у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY() - length, LTF.getZ()),
                new Vector3(LTF.getX() + length, LTF.getY() - length, LTF.getZ()),
                new Vector3(LTF.getX() + length, LTF.getY(), LTF.getZ())
        }), true));
        /*Ближняя сторона  (Z фиксирован и вязт у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ() - length),
                new Vector3(LTF.getX(), LTF.getY() - length,LTF.getZ() - length),
                new Vector3(LTF.getX() + length, LTF.getY() - length, LTF.getZ() - length),
                new Vector3(LTF.getX() + length, LTF.getY(), LTF.getZ() - length)
        }), true));

        /*Верхняя сторона (Y фиксирован и вязт у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ() - length),
                new Vector3(LTF.getX() + length, LTF.getY(), LTF.getZ() - length),
                new Vector3(LTF.getX() + length, LTF.getY(), LTF.getZ())
        }), true));
        /*Нижняя сторона (Y фиксирован и вязт у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(LTF.getX(), LTF.getY() - length, LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY() - length, LTF.getZ() - length),
                new Vector3(LTF.getX() + length, LTF.getY() - length, LTF.getZ() - length),
                new Vector3(LTF.getX() + length, LTF.getY() - length, LTF.getZ())
        }), true));

        /*Левая сторона (X фиксирован и вязт у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ() - length),
                new Vector3(LTF.getX(), LTF.getY() - length, LTF.getZ() - length),
                new Vector3(LTF.getX(), LTF.getY() - length, LTF.getZ())
        }), true));
        /*Правая сторона (X фиксирован и вязт у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(LTF.getX() + length, LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX() + length, LTF.getY(), LTF.getZ() - length),
                new Vector3(LTF.getX() + length, LTF.getY() - length, LTF.getZ() - length),
                new Vector3(LTF.getX() + length, LTF.getY() - length, LTF.getZ())
        }), true));

        return lines;
    }
}
