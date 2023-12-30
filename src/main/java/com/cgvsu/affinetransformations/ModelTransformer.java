package main.java.cgvsu.affinetransformations;

import main.java.cgvsu.math.matrix.Matrix4f;
import main.java.cgvsu.math.vector.Vector4f;
import main.java.cgvsu.model.Model;

public class ModelTransformer {

    private final Vector4f intermediateCalculationVector = new Vector4f(new float[]{0, 0, 0, 1});
    private Matrix4f transformingMatrix = new Matrix4f();

    public ModelTransformer(Matrix4f transformingMatrix) {
        this.transformingMatrix = transformingMatrix;
    }

    public void transformModel(Model model) {
        for (var v : model.vertices) {
            intermediateCalculationVector.x = v.x;
            intermediateCalculationVector.y = v.y;
            intermediateCalculationVector.z = v.z;

            transformingMatrix.mul(intermediateCalculationVector);

            v.x = intermediateCalculationVector.x;
            v.y = intermediateCalculationVector.y;
            v.z = intermediateCalculationVector.z;
        }

    }


}
