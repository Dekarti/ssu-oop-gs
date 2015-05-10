package com.dekarti.gs;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * ќпредел€ет класс  олонка на газовой станции, котора€ может раздавать разные типы топлива.
 * ћаксимальный размер очереди на колонку ограничен четырьм€ машинами.
 */
public class Dispenser extends ImageView {

    /**
     * Ўирина колонки
     */
    final static public double DISPENSER_WIDTH = 50;

    /**
     * ¬ысота колонки
     */
    final static public double DISPENSER_HEIGHT = 125;

    /**
     * Ќомер колонки
     */
    private int id;

    /**
     * ќчередь {@link Car машин} на колонку, представленна€ в виде {@link LinkedList}
     */
    private LinkedList<Car> carQueue;

    /**
     * ќбщие расходы топлива {@link OctaneRating типа} AI_92 на текущей колонке
     */
    private int totalAI_92;

    /**
     * ќбщие расходы топлива {@link OctaneRating типа} AI_95 на текущей колонке
     */
    private int totalAI_95;

    /**
     * ќбщие расходы топлива {@link OctaneRating типа} AI_98 на текущей колонке
     */
    private int totalAI_98;

    /**
     * —оздает колонку с заданным номером.
     *
     * @param id номер колонки
     */
    public Dispenser(int id) {

        super(new Image("res/green-gas-pump-hi.png"));
        if (id % 2 != 0) {
            this.setImage(new Image("res/green-gas-pump-hiR.png"));
        }
        this.setSmooth(true);
        this.setCache(true);
        this.setX(id % 2 != 0
                ? Main.WIDTH * (id / 6.0) - 55.0
                : Main.WIDTH * ((id - 1) / 6.0) + 5.0);
        this.setY(Main.HEIGHT / 2.0);

        this.fitHeightProperty().setValue(DISPENSER_HEIGHT);
        this.fitWidthProperty().setValue(DISPENSER_WIDTH);

        this.id = id;
        carQueue = new LinkedList<>();



    }

    /**
     * ƒобавл€ет машину в очередь на колонку.
     *
     * @param car машина, добавл€ема€ в очередь
     */
    public void pushToQueue(Car car) {

        if (this.getQueueSize() != 0) {
            this.getCarQueue().getLast().setPreviousCar(car);
            car.setNextCar(this.getCarQueue().getLast());
        }
        this.carQueue.add(car);



    }

    /**
     * ¬озвращает размер очереди на колонку.
     *
     * @return размер очереди на колонку
     */
    public int getQueueSize() {

        return carQueue.size();



    }

    /**
     * ¬озвращает очередь на колонку
     *
     * @return очередь
     */
    public LinkedList<Car> getCarQueue() {

        return carQueue;



    }

    /**
     * ¬озвращает номер колонки.
     *
     * @return номер колонки
     */
    public int getIdOfDispenser() {

        return id;



    }

    /**
     * ¬озвращает расходы топлива всех типов этой колонки.
     *
     * @return расход топлива
     */
    public int getTotalOfDispenser() {

        return totalAI_92 + totalAI_95 + totalAI_98;



    }


    /**
     * ¬озвращает {@link Comparator компаратор}, сравнивающий колонки по размеру очереди.
     *
     * @return компаратор
     */
    public static Comparator<Dispenser> getComparator() {

        Comparator<Dispenser> dispenserComparator = new Comparator<Dispenser>() {
            @Override
            public int compare(Dispenser o1, Dispenser o2) {
                if (o1.getQueueSize() < o2.getQueueSize()) {
                    return -1;
                }
                else if (o1.getQueueSize() > o2.getQueueSize()) {
                    return  1;
                }
                return 0;
            }
        };
        return dispenserComparator;



    }

    /**
     * ќбновл€ет информацию о расходах на данной колонке данного типа топлива.
     *
     * @param octaneRating тип топлива
     */
    public void increaseTotal(OctaneRating octaneRating) {

        switch (octaneRating) {
            case AI_92:
                totalAI_92++;
                break;
            case AI_95:
                totalAI_95++;
                break;
            case AI_98:
                totalAI_98++;
                break;
        }



    }



}
