package com.dekarti.gs;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Date;
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
     * ����� ���������� � ��������
     */
    private TextArea information;

    /**
     * ����������� ����������� �����
     */
    private double loss = 0;

    /**
     * ����������� ����������� �����
     */
    private double profit = 0;

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

        Button addCarBtn = new Button("Add car");

        final Integer[] counter = {1};
        addCarBtn.setOnAction(event -> {

            Car car = new Car(
                    new Random().nextInt(200),
                    OctaneRating.values()[new Random().nextInt(3)],
                    500,
                    Car.models[new Random().nextInt(4)]);
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

        this.information = new TextArea(this.toString());
        ToggleButton getInfoBtn = new ToggleButton("Info");
        this.information.visibleProperty().setValue(false);
        getInfoBtn.setOnAction(event -> {
            this.information.visibleProperty().setValue(!this.information.isVisible());
        });

        hBox.getChildren().add(addCarBtn);
        hBox.getChildren().add(clearStationBtn);
        hBox.getChildren().add(getInfoBtn);
        hBox.getChildren().add(information);
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
        information.setText(this.toString());

    }

    public double getTotal(OctaneRating octaneRating) {

        double total = 0;
        for (Dispenser e : dispensers) {
            total += e.getTotalOfDispenser(octaneRating);
        }
        return  total;



    }

    /**
     * ����������� ������� ����������� �����.
     */
    public void increaseLoss() {

        this.loss++;



    }

    /**
     * ����������� ������� ����������� �����.
     */
    public void increaseProfit() {

        this.profit++;



    }

    /**
     * �������������� ����� toString() ��� ������ �������.
     */
    @Override
    public String toString() {

        return "Total AI_92: " + this.getTotal(OctaneRating.AI_92) + "\n" +
               "Total AI_95: " + this.getTotal(OctaneRating.AI_95) + "\n" +
               "Total AI_98: " + this.getTotal(OctaneRating.AI_98) + "\n" +
               "Losses: " + this.loss + "\n" +
               "Profit: " + this.profit + "\n";
    }



}
