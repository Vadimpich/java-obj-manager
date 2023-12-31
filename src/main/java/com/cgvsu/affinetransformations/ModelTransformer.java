package com.cgvsu.affinetransformations;

import javax.vecmath.Matrix4f;
import com.cgvsu.math.Vector4f;
import com.cgvsu.model.Model;

public class ModelTransformer {

    private final Vector4f intermediateCalculationVector = new Vector4f(0, 0, 0, 1);
    private Matrix4f transformingMatrix;

    public ModelTransformer(Matrix4f transformingMatrix) {
        this.transformingMatrix = transformingMatrix;
    }

    public void transformModel(Model model) {
        for (var v : model.vertices) {
            intermediateCalculationVector.x = v.x;
            intermediateCalculationVector.y = v.y;
            intermediateCalculationVector.z = v.z;

            //transformingMatrix.mul(intermediateCalculationVector);

            v.x = intermediateCalculationVector.x;
            v.y = intermediateCalculationVector.y;
            v.z = intermediateCalculationVector.z;
        }

    }


}
