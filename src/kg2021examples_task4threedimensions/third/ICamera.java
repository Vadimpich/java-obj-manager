/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2021examples_task4threedimensions.third;

import kg2021examples_task4threedimensions.math.Vector3;

/**
 * Описывает основную функциональность камеры - превращение координат 
 * из мировой системы координат в систему координат камеры.
 */
public interface ICamera {
    /**
     * Преобразует точку из мировой системы координат в систему координат камеры
     * @param v преобразуемая точка
     * @return новая точка
     */
    public Vector3 w2s(Vector3 v);
    boolean ifRotateModel();
    void setAngleModel (float angle);
    float getAngleModel();

    void setRotateModel(float angle);
}
