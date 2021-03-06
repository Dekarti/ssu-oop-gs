package com.dekarti.gs;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.*;

/**
 * ����������� ����� ������.<br> <hr>
 *
 * <b>������ �������������:</b>
 * <pre>
 *     // ������� ������ ������ � ������� ������� ������� ������ 140/500, � 92 ��������� ������
 *     // � ��������� ������������ ������, ������� �������� ������� Image,
 *     // ����������� �������� ��������� ���� � �����������.
 *     Car car = new Car(140, OctaneRating.AI_92, 500, new Image("your/path/to/cars/pic"));
 *
 *     // ������� ������� �������� ������� � �������
 *     System.out.println("�� ��������: " + car.getGasolineStocks());
 *
 *     // �������� ����� ������� �� 10 �.�.
 *     for (int i = 0; i < 10; ++i) {
 *          car.increaseGasolineStocks();
 *     }
 *
 *     // ������� ������� �������� ������� � �������
 *     System.out.println("����� ��������: " + car.getGasolineStocks() + "\n");
 *
 *     // ������� ������� ���������� ������ �� ��� �������. ��� ����� ����� ���� �.�.
 *     // ������ ��� �� ��������� ����������� �������.
 *     System.out.println("���������� �� ��� ������� �� ������������: " + car.getY());
 *
 *     // ���������� ������ ������ �� 10 �.�
 *     for (int i = 0; i < 10; ++i) {
 *          car.move();
 *     }
 *
 *     // ������� ������� ��������� ������ �� ��� �������
 *     System.out.println("���������� �� ��� ������� ����� ������������: " + car.getY());
 *
 *     // �������� ������������ ����������� ������� � �������� �� ����� ������
 *     Station station = new Station();
 *     car.setGS(station);
 *
 *     // ������� �� ����� ���������� � ������
 *     System.out.println(car.toString());
 *
 *
 * </pre>
 * <hr>
 * <b>�����:</b><br>
 * �� ��������: 140 <br>
 * ����� ��������: 150 <br>
 * <br>
 * ���������� �� ��� ������� �� ������������: 0 <br>
 * ���������� �� ��� ������� ����� ������������: 10 <br>
 * <br>
 * Target: CHOOSE_DISPENSER <br>
 * Gas state: 10.0/500.0 <br>
 * ID: null <br>
 * Next car: null <br>
 * Previous car: null <br>
 * Octane ratingAI_92 <br>
 *
 *
 */
public class Car extends Object {

    /**
     * ������ ������
     */
    final static public double CAR_WIDTH  = 50;

    /**
     * ����� ������
     */
    final static public double CAR_HEIGHT = 125;

    /**
     * ������ �����
     */
    static public Image[] models = {
            new Image("res/car3.png"),
            new Image("res/car4.png"),
            new Image("res/car2.png"),
            new Image("res/car1.png")
    };

    /**
     * ������� ����� �������
     */
    private double gasolineStocks;

    /**
     * ����� ���������� ����
     */
    private double fuelCapacity;

    /**
     * ������� ����������� �������
     */
    private Station currentGasStation;

    /**
     * ������� �������
     */
    private Dispenser currentDispenser;

    /**
     * ������� ������� ������
     */
    private Car nextCar;

    /**
     * ������ ������� ������
     */
    private Car previousCar;

    /**
     * ���������� � ��������� ������
     */
    private Text info;

    /**
     * ������� {@link Target ����} ������
     */
    private Target target;

    /**
     * ��������� ��������� �������
     */
    private ProgressBar fuelState;

    /**
     * ��������� ������� �������
     */
    final private OctaneRating recommendedRating;

    /**
     * ������� ������ � ������������ ��������� ���������� �������, ��� �����, ������� ����, � �������.
     * @param gasolineStocks ��������� ����
     * @param recommendedRating ������������� ��� �������
     * @param fuelCapacity ����� ����
     * @param image ������ ������
     */
    public Car(double gasolineStocks, OctaneRating recommendedRating, double fuelCapacity, Image image) {

        super(CAR_WIDTH, CAR_HEIGHT, image);
        this.setSmooth(true);
        this.setCache(true);

        this.gasolineStocks = gasolineStocks;
        this.recommendedRating = recommendedRating;
        this.fuelCapacity = fuelCapacity;
        this.currentGasStation = null;
        this.target = Target.CHOOSE_DISPENSER;

        ImageView carImage = new ImageView(image);
        carImage.setSmooth(true);
        carImage.setCache(true);
        carImage.setFitWidth(CAR_WIDTH * 2);
        carImage.setFitHeight(CAR_HEIGHT * 2);
        carImage.setX(Main.WIDTH / 2);
        carImage.setY(CAR_HEIGHT);

        fuelState = new ProgressBar(gasolineStocks / fuelCapacity);
        fuelState.setLayoutY(carImage.getY() - 40);
        fuelState.setLayoutX(carImage.getX());

        info = new Text(200, carImage.getY(), this.toString());
        info.setFont(Font.font("Verdana", 20));
        info.setFill(Color.DARKORCHID);

        root.getChildren().add(carImage);
        root.getChildren().add(info);
        root.getChildren().add(fuelState);
        this.setOnMouseClicked(event -> {
            this.setParent(this.currentGasStation.getObjectScene());
            Main.primaryStage.setScene(this.getObjectScene());
        });



    }

    /**
     * ��������� ���������� � ��������� ������.
     */
    public void updateInfo() {

        info.setText(this.toString());
        fuelState.setProgress(this.gasolineStocks / this.fuelCapacity);



    }

    /**
     * ���������� ������� ����� �������.
     *
     * @return ����� �������
     */
    public double getGasolineStocks() {

        return gasolineStocks;



    }

    /**
     * ���������� ����� ���������� ����.
     * @return ����� ���������� ����
     */
    public double getFuelCapacity() {

        return fuelCapacity;



    }

    /**
     * ����������� ����� ������� �� �������.
     */
    public void increaseGasolineStocks() {

        this.gasolineStocks += 1;



    }

    /**
     * ���������� ������������� ��� �������.
     *
     * @return ��� �������
     */
    public OctaneRating getRecommendedRating() {

        return recommendedRating;



    }

    /**
     * ������������� ������� ���������������� �������.
     *
     * @param gs
     */
    public void setGS(Station gs) {

        this.currentGasStation = gs;
        this.currentGasStation.addToCarStream(this);




    }

    /**
     * �������� ������ �� �������� ���������.
     */
    public void move() {

        this.setY(this.getY() + 1);



    }

    /**
     * ��������� ����� ����.
     * @param target ����
     */
    public void setTarget(Target target) {

        this.target = target;



    }

    /**
     * �������� �������� �������� ������� �� �������.
     */
    private void chooseDispener() {

        Dispenser mostComfortableDispenser = Collections.min(currentGasStation.getDispensers(),
                Dispenser.getComparator());

        if (mostComfortableDispenser.getQueueSize() >= 3) {
            this.currentGasStation.increaseLoss();
            this.setTarget(Target.GET_AWAY_FROM_GS);
        } else {
            this.currentDispenser = mostComfortableDispenser;
            this.currentGasStation.increaseProfit();
        }



    }

    /**
     * ������ � ������� �� �������.
     */
    private void getInLineStepOne() {

        if (this.currentDispenser.getQueueSize() != 0) {
            this.setY(this.currentDispenser.getCarQueue().getLast().getY() - 2 * CAR_HEIGHT);
        } else {
            this.setY(0);
        }
        currentDispenser.pushToQueue(this);
        this.setX(this.currentDispenser.getIdOfDispenser() % 2 == 0
                ? this.currentDispenser.getX() + CAR_WIDTH + 10.0
                : this.currentDispenser.getX() - CAR_WIDTH - 10.0);
        currentGasStation.addToScene(this);
        this.target = Target.GET_IN_LINE;



    }

    /**
     * ���������� ������ �� ��� ��� ���� �� ������� ������ ��������� ������ � �������.
     */
    private void getInLineStepTwo() {

        if (this.getNextCar() == null) {
            if (this.getY() == this.currentDispenser.getY()) {
                this.setTarget(Target.REFUEL);
            } else {
                this.move();
            }
        } else {
            if (this.getY() != this.getNextCar().getY() - CAR_HEIGHT - 5) {
                this.move();
            }
        }



    }

    /**
     * ���������� � �������.
     */
    private void moveInLine() {

        if (this.getNextCar() == null) {
            if (this.getY() == this.currentDispenser.getY()) {
                this.setTarget(Target.REFUEL);
            } else {
                this.move();
            }
        } else {
            if (this.getY() != this.getNextCar().getY() - CAR_HEIGHT - 5) {
                this.move();
            }
        }



    }

    /**
     * ���������� �� ������� ����.
     */
    private void refuel() {

        if (this.getGasolineStocks() == this.getFuelCapacity()) {
            this.setTarget(Target.GET_AWAY_FROM_DISPENSER);
        } else {
            this.increaseGasolineStocks();
            this.currentDispenser.increaseTotal(this.getRecommendedRating());
        }



    }

    /**
     * ����������� �������.
     */
    private void getAway() {

        this.currentDispenser.getCarQueue().remove(this);
        if (this.getPreviousCar() != null) {
            this.getPreviousCar().setNextCar(null);
        }
        this.setY(this.getY() + 2);
        if (this.getY() == Main.HEIGHT - 200) {
            this.setTarget(Target.GET_AWAY_FROM_GS);
        }



    }

    /**
     * ������� ������ � ��������.
     */
    private void removeFromGS() {

        this.currentGasStation.root.getChildren().remove(this);
        /*this.currentDispenser = null;
        this.currentGasStation = null;
        this.scene = null;
        this.root = null;
        this.currentGasStation = null;*/



    }

    /**
     * ��������� ��������� ������ �� ����� � �� ���������.
     */
    public void update() {

        switch (target) {
            case CHOOSE_DISPENSER:
                if (this.currentDispenser == null) {
                    this.chooseDispener();
                } else {
                    this.getInLineStepOne();
                }
                break;
            case GET_IN_LINE:
                this.getInLineStepTwo();
                break;
            case MOVE_IN_LINE:
                this.moveInLine();
                break;
            case REFUEL:
                this.refuel();
                break;
            case GET_AWAY_FROM_DISPENSER:
                this.getAway();
                break;
            case GET_AWAY_FROM_GS:
                this.removeFromGS();
        }

        this.updateInfo();



    }

    /**
     * ���������� ������, ������ ������� � �������.
     * @return ������ �������
     */
    private Car getNextCar() {

        return nextCar;



    }

    /**
     * ������������� ��������� ������
     * @param nextCar ��������� ������
     */
    public void setNextCar(Car nextCar) {

        this.nextCar = nextCar;



    }

    /**
     * ���������� ������, ������� �����.
     * @return ������ �����
     */
    private Car getPreviousCar() {

        return previousCar;



    }

    /**
     * ������������� ������ �����.
     * @param previousCar ������ �����
     */
    public void setPreviousCar(Car previousCar) {

        this.previousCar = previousCar;



    }


    /**
     * ��������� ���������� � ������ � ��������� ������.
     * @return ���������� � ������
     */
    @Override
    public String toString() {

        return "Target: " + this.target + "\n" +
               "Gas state: " + this.getGasolineStocks() + "/" + this.getFuelCapacity() + "\n" +
               "ID: " + this.getId() + "\n" +
               "Next car: " + (this.getNextCar() != null ? this.getNextCar().getId() : "null") + "\n" +
               "Previous car: " + (this.getPreviousCar() != null ? this.getPreviousCar().getId() : "null") + "\n" +
               "Octane rating" + this.getRecommendedRating();



    }



}
