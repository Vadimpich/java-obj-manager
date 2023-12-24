package models.outlines;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.third.IModel;

import java.util.List;

public interface IOutline {
    float getRadius(double t);

    float getRadius();
}