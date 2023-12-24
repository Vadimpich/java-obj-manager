package models;

import kg2019examples_task4threedimensions.math.Matrix4;
import kg2019examples_task4threedimensions.math.Matrix4Factories;
import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.math.Vector4;
import kg2019examples_task4threedimensions.third.IModel;

import java.util.List;

public abstract class Lenin implements IModel {
    class Pair {
        private double angle;
        private int axis;

        public Pair(double angle, int axis) {
            this.angle = angle;
            this.axis = axis;
        }

        double getAngle() {
            return angle;
        }

        int getAxis() {
            return axis;
        }
    }

    abstract Vector3[] getPath(int dimension, float radius, float z);

    //abstract Vector3[] getCircle(int dimension, float radius, float z);

    Vector3[] getCircle(float torusThickness, int dimension, float z) {
        double delta = Math.PI * 2 / dimension;
        Vector3[] slice = new Vector3[dimension];
        for (int j = 1; j <= dimension; j++) {
            // строим базовые точки круга
            float x = (float) (torusThickness * Math.cos(delta * (j)));
            float y = (float) (torusThickness * Math.sin(delta * (j)));
/*
            // поворот на 90 градусов
            Vector4 v = new Vector4(x, y, 0.0f);
            Matrix4 m = Matrix4Factories.rotationXYZ(Math.PI / 2, 1);
            v = m.mul(v);

            // завершаем создание круга, ориентированного по ходу линии
            slice[j - 1] = new Vector3(v.getX(), v.getY(),
                    (float) (z + torusThickness * Math.cos(delta * j)));
        }

 */
            slice[j - 1] = new Vector3(x, y,
                    (float) (z + torusThickness * Math.cos(delta * j)));
        }
        return slice;
    }

    Vector3[] transformVector(List<Pair> transformation, Vector3[] circlePoints) {
        for (Pair p :
                transformation) {
            Matrix4 m = Matrix4Factories.rotationXYZ(p.angle, p.axis);
            for (int i = 0; i < circlePoints.length; i++) {
                Vector4 v4 = m.mul(new Vector4(circlePoints[i]));
                circlePoints[i] = new Vector3(v4.getX(), v4.getY(), v4.getZ());
            }
        }
        return circlePoints;
    }

    Vector3 transformVector(List<Pair> transformation, Vector3 circlePoints) {
        for (Pair p :
                transformation) {
            Matrix4 m = Matrix4Factories.rotationXYZ(p.angle, p.axis);
            Vector4 v4 = m.mul(new Vector4(circlePoints));
            circlePoints = new Vector3(v4.getX(), v4.getY(), v4.getZ());
        }
        return circlePoints;
    }
}
