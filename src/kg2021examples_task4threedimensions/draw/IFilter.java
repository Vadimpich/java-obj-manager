/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2021examples_task4threedimensions.draw;

/**
 * Интерфейс декларирует метод, который будет отвечать, подходит ли заданный экземпляр класса какому-либо условию
 */
public interface IFilter<T> {
    boolean permit(T value);
}
