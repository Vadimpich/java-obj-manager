package kg2020.task4.models;

import kg2020.task4.math.Vector3;
import kg2020.task4.threeD.IModel;
import kg2020.task4.threeD.PolyLine3D;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Dodecahedron implements IModel {
    private final Vector3 LTF;
    private final float length;

    public Dodecahedron(Vector3 LTF, float length) {
        this.length = length;
        this.LTF = LTF;
    }

    @Override
    public List<PolyLine3D> getLines() {
        LinkedList<PolyLine3D> lines = new LinkedList<>();
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(0,0.2f,0),
                new Vector3(0,-0.2f,0),
                new Vector3(-0.38f,-0.324f,0),
                new Vector3(-0.616f,0,0),
                new Vector3(-0.38f,0.324f,0)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(0,0.2f,0),
                new Vector3(0,-0.2f,0),
                new Vector3(0.17f,-0.324f,0.34f),
                new Vector3(0.275f,0,0.551f),
                new Vector3(0.17f,0.324f,0.34f)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(0,0.2f,0),
                new Vector3(0.17f,0.324f,0.34f),
                new Vector3(-0.105f,0.524f,0.551f),
                new Vector3(-0.445f,0.524f,0.34f),
                new Vector3(-0.38f,0.324f,0)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(-0.38f,0.324f,0),
                new Vector3(-0.445f,0.524f,0.34f),
                new Vector3(-0.721f,0.324f,0.551f),
                new Vector3(-0.826f,0,0.34f),
                new Vector3(-0.616f,0,0)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(-0.616f,0,0),
                new Vector3(-0.826f,0,0.34f),
                new Vector3(-0.721f,-0.324f,0.551f),
                new Vector3(-0.445f,-0.524f,0.34f),
                new Vector3(-0.38f,-0.324f,0)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(-0.38f,-0.324f,0),
                new Vector3(-0.445f,-0.524f,0.34f),
                new Vector3(-0.105f,-0.524f,0.55f),
                new Vector3(0.17f,-0.324f,0.34f),
                new Vector3(0,-0.2f,0)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(0.275f,0,0.551f),
                new Vector3(0.065f,0,0.891f),
                new Vector3(-0.17f,0.324f,0.891f),
                new Vector3(-0.105f,0.524f,0.551f),
                new Vector3(0.17f,0.324f,0.34f)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(-0.105f,0.524f,0.551f),
                new Vector3(-0.17f,0.324f,0.891f),
                new Vector3(-0.551f,0.2f,0.891f),
                new Vector3(-0.721f,0.324f,0.551f),
                new Vector3(-0.445f,0.524f,0.34f)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(-0.551f,0.2f,0.891f),
                new Vector3(-0.721f,0.324f,0.551f),
                new Vector3(-0.826f,0,0.34f),
                new Vector3(-0.721f,-0.324f,0.551f),
                new Vector3(-0.551f,-0.2f,0.891f)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(-0.551f,-0.2f,0.891f),
                new Vector3(-0.721f,-0.324f,0.551f),
                new Vector3(-0.445f,-0.524f,0.34f),
                new Vector3(-0.105f,-0.524f,0.551f),
                new Vector3(-0.17f,-0.324f,0.891f)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(-0.17f,-0.324f,0.891f),
                new Vector3(0.065f,0,0.891f),
                new Vector3(0.275f,0,0.551f),
                new Vector3(0.17f,-0.324f,0.34f),
                new Vector3(-0.105f,-0.524f,0.551f)
        }), true));
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(-0.551f,0.2f,0.891f),
                new Vector3(-0.17f,0.324f,0.891f),
                new Vector3(0.065f,0,0.891f),
                new Vector3(-0.17f,-0.324f,0.891f),
                new Vector3(-0.551f,-0.2f,0.891f)
        }), true));
        return lines;
    }
}