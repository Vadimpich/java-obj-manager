package com.cgvsu;

import com.cgvsu.math.Matrix;
import com.cgvsu.math.Matrix4F;
import com.cgvsu.math.VectorNf;

public class AffineTransformation {
    public Matrix4F transformationMatrix;

    public AffineTransformation() {
        this.transformationMatrix = new Matrix4F(
                new VectorNf(1,1,1,1),
                new VectorNf(1,1,1,1),
                new VectorNf(1,1,1,1),
                new VectorNf(1,1,1,1)
        );
    }

    public Matrix scale(double sX, double sY, double sZ) {
        Matrix4F scaleMatrix = new Matrix4F(
                new VectorNf((float) sX,0,0,0),
                new VectorNf(0,(float)sY,0,0),
                new VectorNf(0,0, (float)sZ,0),
                new VectorNf(0,0,0,1)
        );


        return scaleMatrix;
    }


    public Matrix rotate(float rX, float rY, float rZ) {
        float cosX = (float) Math.cos(rX);
        float sinX = (float) Math.sin(rX);
        float cosY = (float) Math.cos(rY);
        float sinY = (float) Math.sin(rY);
        float cosZ = (float) Math.cos(rZ);
        float sinZ = (float) Math.sin(rZ);

        Matrix4F rotationMatrix = new Matrix4F(
                new VectorNf(cosY * cosZ,-cosX * sinZ + sinX * sinY * cosZ ,sinX * sinZ + cosX * sinY * cosZ, 0),
                new VectorNf( cosY * sinZ,cosX * cosZ + sinX * sinY * sinZ,-sinX * cosZ + cosX * sinY * sinZ, 0 ),
                new VectorNf(-sinY, sinX * cosY, cosX * cosY, 0),
                new VectorNf(0, 0,0,1)
        );




        return rotationMatrix;
    }


    public Matrix translate(float tX, float tY, float tZ) {
        Matrix4F translationMatrix = new Matrix4F(
                new VectorNf(1,0,0,0),
                new VectorNf(0,1,0,0),
                new VectorNf(0,0,1,0),
                new VectorNf(tX, tY, tZ, 1)
        );
        return translationMatrix;
    }

    @Override
    public String toString() {
        return "AffineTransformation{" +
                "transformationMatrix=" + transformationMatrix +
                '}';
    }
}
