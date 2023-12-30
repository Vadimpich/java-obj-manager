package main.java.cgvsu.affinetransformations;

import main.java.cgvsu.affinetransformations.enums.AngleMetric;
import main.java.cgvsu.math.matrix.Matrix4f;
import main.java.cgvsu.math.vector.Vector3f;

import static main.java.cgvsu.math.MathUtils.PI;
import static main.java.cgvsu.math.matrix.Matrix4f.*;

public class TransformerBuilder {
    private final Matrix4f operationMatrix = new Matrix4f(
            new float[]{
                    1, 0, 0, 0,
                    0, 1, 0, 0,
                    0, 0, 1, 0,
                    0, 0, 0, 1
            }
    );
    private final Matrix4f templateMatrix = new Matrix4f(
            new float[]{
                    1, 0, 0, 0,
                    0, 1, 0, 0,
                    0, 0, 1, 0,
                    0, 0, 0, 1
            }
    );

    public TransformerBuilder(AngleMetric metric) {
        switch (metric) {
            case DEGREES -> metricMultiplier = (float) 1 / 180 * PI;
            default -> metricMultiplier = 1;
        }
    }

    private final float metricMultiplier;

    public TransformerBuilder rotateXCounterClockwise(float radiansAngle) {
        var cos = (float) Math.cos(radiansAngle * metricMultiplier);
        var sin = (float) Math.sin(radiansAngle * metricMultiplier);
        templateMatrix.val[Matrix4f.M11] = 1;
        templateMatrix.val[Matrix4f.M12] = 0;
        templateMatrix.val[Matrix4f.M13] = 0;
        templateMatrix.val[Matrix4f.M21] = 0;
        templateMatrix.val[Matrix4f.M22] = cos;
        templateMatrix.val[Matrix4f.M23] = -sin;
        templateMatrix.val[Matrix4f.M31] = 0;
        templateMatrix.val[Matrix4f.M32] = sin;
        templateMatrix.val[Matrix4f.M33] = cos;
        operationMatrix.mul(templateMatrix);
        return this;
    }

    public TransformerBuilder rotateZCounterClockwise(float radiansAngle) {
        var cos = (float) Math.cos(radiansAngle * metricMultiplier);
        var sin = (float) Math.sin(radiansAngle * metricMultiplier);
        templateMatrix.val[Matrix4f.M11] = cos;
        templateMatrix.val[Matrix4f.M12] = -sin;
        templateMatrix.val[Matrix4f.M13] = 0;
        templateMatrix.val[Matrix4f.M21] = sin;
        templateMatrix.val[Matrix4f.M22] = cos;
        templateMatrix.val[Matrix4f.M23] = 0;
        templateMatrix.val[Matrix4f.M31] = 0;
        templateMatrix.val[Matrix4f.M32] = 0;
        templateMatrix.val[Matrix4f.M33] = 1;
        operationMatrix.mul(templateMatrix);
        return this;
    }

    public TransformerBuilder rotateYCounterClockwise(float radiansAngle) {
        var cos = (float) Math.cos(radiansAngle * metricMultiplier);
        var sin = (float) Math.sin(radiansAngle * metricMultiplier);

        templateMatrix.val[Matrix4f.M11] = cos;
        templateMatrix.val[Matrix4f.M12] = 0;
        templateMatrix.val[Matrix4f.M13] = -sin;
        templateMatrix.val[Matrix4f.M21] = 0;
        templateMatrix.val[Matrix4f.M22] = 1;
        templateMatrix.val[Matrix4f.M23] = 0;
        templateMatrix.val[Matrix4f.M31] = sin;
        templateMatrix.val[Matrix4f.M32] = 0;
        templateMatrix.val[Matrix4f.M33] = cos;

        operationMatrix.mul(templateMatrix);
        return this;
    }

    public TransformerBuilder rotateXClockwise(float radiansAngle) {
        return rotateXCounterClockwise(-radiansAngle);
    }

    public TransformerBuilder rotateZClockwise(float radiansAngle) {
        return rotateZCounterClockwise(-radiansAngle);
    }

    public TransformerBuilder rotateYClockwise(float radiansAngle) {
        return rotateYCounterClockwise(-radiansAngle);
    }

    public TransformerBuilder scaleX(float scaleFactor) {
        operationMatrix.val[M11] *= scaleFactor;
        return this;
    }

    public TransformerBuilder scaleZ(float scaleFactor) {
        operationMatrix.val[M33] *= scaleFactor;
        return this;
    }

    public TransformerBuilder scaleY(float scaleFactor) {
        operationMatrix.val[M22] *= scaleFactor;
        return this;

    }

    public TransformerBuilder scaleByVector(Vector3f v) {
        operationMatrix.val[M11] *= v.x;
        operationMatrix.val[M22] *= v.y;
        operationMatrix.val[M33] *= v.z;
        return this;
    }


    public TransformerBuilder translateX(float distance) {
        operationMatrix.val[M14] += distance;
        return this;
    }

    public TransformerBuilder translateY(float distance) {
        operationMatrix.val[M24] += distance;
        return this;

    }

    public TransformerBuilder translateZ(float distance) {
        operationMatrix.val[M34] += distance;
        return this;
    }

    public TransformerBuilder translateByVector(Vector3f v) {
        operationMatrix.val[M14] += v.x;
        operationMatrix.val[M24] += v.y;
        operationMatrix.val[M34] += v.z;
        return this;
    }

    public ModelTransformer build() {
        var mt = new ModelTransformer(operationMatrix.cpy());
        restoreOperationMatrix();
        return mt;
    }

    private void restoreOperationMatrix() {
        operationMatrix.val[M11] = 1;
        operationMatrix.val[M12] = 0;
        operationMatrix.val[M13] = 0;
        operationMatrix.val[M14] = 0;
        operationMatrix.val[M21] = 0;
        operationMatrix.val[M22] = 1;
        operationMatrix.val[M23] = 0;
        operationMatrix.val[M24] = 0;
        operationMatrix.val[M31] = 0;
        operationMatrix.val[M32] = 0;
        operationMatrix.val[M33] = 1;
        operationMatrix.val[M34] = 0;
        operationMatrix.val[M41] = 0;
        operationMatrix.val[M42] = 0;
        operationMatrix.val[M43] = 0;
        operationMatrix.val[M44] = 1;
    }
}
