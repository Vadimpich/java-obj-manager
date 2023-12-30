package com.cgvsu.math;


public class Vector3f extends VectorNf {

    private float x;
    private float y;
    private float z;


    public Vector3f(float x, float y, float z) {
        super(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public void setZ(float z){
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public static Vector3f addition(Vector3f vector1, Vector3f vector2) {
        return new Vector3f(
                vector1.x + vector2.x,
                vector1.y + vector2.y,
                vector1.z + vector2.z
        );
    }
    public static Vector3f subtraction(Vector3f vector1, Vector3f vector2) {
        return new Vector3f(
                vector1.x - vector2.x,
                vector1.y - vector2.y,
                vector1.z - vector2.z
        );
    }
    @Override
    public float length() {
        return length;
    }

    @Override
    public Vector3f normalize() {
        return new Vector3f(this.x / length, this.y / length, this.z / length);
    }
    @Override
    public float[] getArrValues() {
        return new float[]{x, y, z};
    }

    public Vector3f vectorProduct(Vector3f vector) {
        float i = y * vector.getZ() - z * vector.getY();
        float j = x * vector.getZ() - z * vector.getX();
        float k = x * vector.getY() - y * vector.getX();
        return new Vector3f(i, -j, k);
    }

    public javax.vecmath.Vector3f toJavax(){
        return new javax.vecmath.Vector3f(x,y,z);
    }

}
