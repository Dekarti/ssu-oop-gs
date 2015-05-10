package com.dekarti.gs;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

/**
 * ���������� ����� ����������� ������� � 6-� ���������.
 */
public class Station extends Object {

    /**
     * ���������� �������
     */
    final static public int NUM_OF_DISPENSERS = 6;

    /**
     * ����� �����
     */
    private LinkedList<Car> carStream;

    /**
     * �������
     */
    private Vector<Dispenser> dispensers;

    /**
     * ������� ������� � ��������� ������������ � ���������.
     * @param x �������������� ����������
     * @param y ������������ ����������
     * @param width ������ �������
     * @param height ������ �������
     */
    public Station(double x, double y, double width, double height) {

        super(new Image("res/gs.jpg"));
        this.setFitWidth(Main.WIDTH);

        dispensers = new Vector<>(NUM_OF_DISPENSERS);
        for (int i = 0; i < NUM_OF_DISPENSERS; i++) {
            Dispenser dispenser = new Dispenser(i + 1);
            dispensers.add(dispenser);
            this.addNodeToScene(dispenser);
        }

        //scene.setFill(Color.valueOf("353535ff"));
        scene.setFill(Color.WHITESMOKE);
        System.out.println(scene.getFill().toString());

        Button addCarBtn = new Button("Add car");

        final Integer[] counter = {1};
        addCarBtn.setOnAction(event -> {

            Car car = new Car(new Random().nextInt(200), OctaneRating.AI_92, 500, Car.models[new Random().nextInt(4)]);
            car.setRotate(180);
            car.setGS(this);
            car.setId(counter[0].toString());
            counter[0] = counter[0] + 1;
        });

        Button clearStationBtn = new Button("Clear");

        clearStationBtn.setOnAction(event -> {
            for (Dispenser e : dispensers) {
                for (Car c : e.getCarQueue()) {
                    this.root.getChildren().remove(c);
                }
                e.getCarQueue().clear();
            }
        });

        hBox.getChildren().add(addCarBtn);
        hBox.getChildren().add(clearStationBtn);
        carStream = new LinkedList<>();



    }

    /**
     * ���������� ������ �������
     * @return ������ �������
     */
    public Vector<Dispenser> getDispensers() {

        return dispensers;



    }

    /**
     * ��������� �� ����� ������
     * @param car ������
     */
    public void addToScene(Car car) {

        root.getChildren().add(car);



    }


    /**
     * ��������� � ����� ������ ����� ��������� ������
     * @param car ����� ��������� ������
     */
    public void addToCarStream(Car car) {

        this.carStream.add(car);



    }

    /**
     * ��������� ��������� �������� �� �������
     */
    public void update() {

        for (Car e : this.carStream) {
            e.update();
        }



    }



}
