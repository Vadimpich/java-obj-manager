package kg2020.task4.models;

import kg2020.task4.math.Vector3;
import kg2020.task4.threeD.IModel;
import kg2020.task4.threeD.PolyLine3D;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Tetrahedron implements IModel {
    private final Vector3 LTF;
    private final float length;

    public Tetrahedron(Vector3 LTF, float length) {
        this.length = length;
        this.LTF = LTF;
    }

    @Override
    public List<PolyLine3D> getLines() {
        LinkedList<PolyLine3D> lines = new LinkedList<>();
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX() + length, LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ() + length)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX() + length, LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX() + length / 2, LTF.getY() + (length * (float) Math.sqrt(6) / 3f),
                        LTF.getZ() + (length * (float) Math.sqrt(3) / 6f))
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ() + length),
                new Vector3(LTF.getX() + length / 2, LTF.getY() + (length * (float) Math.sqrt(6) / 3f),
                        LTF.getZ() + (length * (float) Math.sqrt(3) / 6f))
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(LTF.getX() + length / 2, LTF.getY() + (length * (float) Math.sqrt(6) / 3f),
                        LTF.getZ() + (length * (float) Math.sqrt(3) / 6f)),
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ() + length),
                new Vector3(LTF.getX() + length, LTF.getY(), LTF.getZ())
        }), true));
        return lines;
    }
}


