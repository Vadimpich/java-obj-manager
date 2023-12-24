/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.PolyLine3D;

/**
 * Описывает параллелипипед по двум диагональным точкам
 * @author Alexey
 */
public class Parallelepiped implements IModel {
    private Vector3 LTF, RBN;

    /**
     * Создаёт экземпляр параллелипипеда
     * @param LTF Левая Верхняя Дальняя точка (Left Top Far)
     * @param RBN Правая Нижняя Ближняя точка (Right Bottom Near)
     */
    public Parallelepiped(Vector3 LTF, Vector3 RBN) {
        this.LTF = LTF;
        this.RBN = RBN;
    }
    

    @Override
    public List<PolyLine3D> getLines() {
        LinkedList<PolyLine3D> lines = new LinkedList<>();
        /*Дальняя сторона (Z фиксирован и вязт у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                    new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                    new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()),
                    new Vector3(RBN.getX(), RBN.getY(), LTF.getZ()),
                    new Vector3(RBN.getX(), LTF.getY(), LTF.getZ())
                }), true));
        /*Ближняя сторона  (Z фиксирован и вязт у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                    new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()),
                    new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()),
                    new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                    new Vector3(RBN.getX(), LTF.getY(), RBN.getZ())
                }), true));
        
        /*Верхняя сторона (Y фиксирован и вязт у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                    new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                    new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()),
                    new Vector3(RBN.getX(), LTF.getY(), RBN.getZ()),
                    new Vector3(RBN.getX(), LTF.getY(), LTF.getZ())
                }), true));
        /*Нижняя сторона (Y фиксирован и вязт у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                    new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()),
                    new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()),
                    new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                    new Vector3(RBN.getX(), RBN.getY(), LTF.getZ())
                }), true));
        
        /*Левая сторона (X фиксирован и вязт у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                    new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                    new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()),
                    new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()),
                    new Vector3(LTF.getX(), RBN.getY(), LTF.getZ())
                }), true));
        /*Правая сторона (X фиксирован и вязт у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                    new Vector3(RBN.getX(), LTF.getY(), LTF.getZ()),
                    new Vector3(RBN.getX(), LTF.getY(), RBN.getZ()),
                    new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                    new Vector3(RBN.getX(), RBN.getY(), LTF.getZ())
                }), true));
        
        return lines;
    }
    
}
