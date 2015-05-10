package com.dekarti.gs;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * ���������� ����� ������� �� ������� �������, ������� ����� ��������� ������ ���� �������.
 * ������������ ������ ������� �� ������� ��������� �������� ��������.
 */
public class Dispenser extends ImageView {

    /**
     * ������ �������
     */
    final static public double DISPENSER_WIDTH = 50;

    /**
     * ������ �������
     */
    final static public double DISPENSER_HEIGHT = 125;

    /**
     * ����� �������
     */
    private int id;

    /**
     * ������� {@link Car �����} �� �������, �������������� � ���� {@link LinkedList}
     */
    private LinkedList<Car> carQueue;

    /**
     * ����� ������� ������� {@link OctaneRating ����} AI_92 �� ������� �������
     */
    private int totalAI_92;

    /**
     * ����� ������� ������� {@link OctaneRating ����} AI_95 �� ������� �������
     */
    private int totalAI_95;

    /**
     * ����� ������� ������� {@link OctaneRating ����} AI_98 �� ������� �������
     */
    private int totalAI_98;

    /**
     * ������� ������� � �������� �������.
     *
     * @param id ����� �������
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
     * ��������� ������ � ������� �� �������.
     *
     * @param car ������, ����������� � �������
     */
    public void pushToQueue(Car car) {

        if (this.getQueueSize() != 0) {
            this.getCarQueue().getLast().setPreviousCar(car);
            car.setNextCar(this.getCarQueue().getLast());
        }
        this.carQueue.add(car);



    }

    /**
     * ���������� ������ ������� �� �������.
     *
     * @return ������ ������� �� �������
     */
    public int getQueueSize() {

        return carQueue.size();



    }

    /**
     * ���������� ������� �� �������
     *
     * @return �������
     */
    public LinkedList<Car> getCarQueue() {

        return carQueue;



    }

    /**
     * ���������� ����� �������.
     *
     * @return ����� �������
     */
    public int getIdOfDispenser() {

        return id;



    }

    /**
     * ���������� ������� ������� ���� ����� ���� �������.
     *
     * @return ������ �������
     */
    public int getTotalOfDispenser() {

        return totalAI_92 + totalAI_95 + totalAI_98;



    }


    /**
     * ���������� {@link Comparator ����������}, ������������ ������� �� ������� �������.
     *
     * @return ����������
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
     * ��������� ���������� � �������� �� ������ ������� ������� ���� �������.
     *
     * @param octaneRating ��� �������
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
