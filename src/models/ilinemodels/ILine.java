package models.ilinemodels;

import kg2019examples_task4threedimensions.math.Vector3;

import java.util.List;

public interface ILine {
    Vector3 getPoint(double t);
    List<Vector3> getPoint(int i, int dimension);
}
