package models;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.third.IModel;

import java.util.List;

public interface Round extends IModel {
    //Vector3[] getPath(int dimension, float torusThickness);
    List<Vector3> getPath(int dimension, float torusThickness);
    Vector3[] getCircle(int dimension, float z);
}
