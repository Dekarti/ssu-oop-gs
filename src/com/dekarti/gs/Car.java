package com.dekarti.gs;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.*;

/**
 * Определеяет класс Машина.
 */
public class Car extends Object {

    /**
     * Ширина машины
     */
    final static public double CAR_WIDTH  = 50;

    /**
     * Длина машины
     */
    final static public double CAR_HEIGHT = 125;

    /**
     * Модели машин
     */
    static public Image[] models = {
            new Image("res/car3.png"),
            new Image("res/car4.png"),
            new Image("res/car2.png"),
            new Image("res/car1.png")
    };

    /**
     * Текущий запас топлива
     */
    private double gasolineStocks;

    /**
     * Объем топливного бака
     */
    private double fuelCapacity;

    /**
     * Текущая заправочная станция
     */
    private Station currentGasStation;

    /**
     * Текущая колонка
     */
    private Dispenser currentDispenser;

    /**
     * Спереди стоящая машина
     */
    private Car nextCar;

    /**
     * Позади стоящая машина
     */
    private Car previousCar;

    /**
     * Информация о состоянии машины
     */
    private Text info;

    /**
     * Текущая {@link Target цель} машины
     */
    private Target target;

    /**
     * Индикатор состояния топлива
     */
    private ProgressBar fuelState;

    /**
     * Октановый рейтинг топлива
     */
    final private OctaneRating recommendedRating;

    /**
     * Создает машину с определенным начальным состоянием топлива, его типом, объемом бака, и моделью.
     * @param gasolineStocks состояние бака
     * @param recommendedRating рекомендуемый тип топлива
     * @param fuelCapacity объем бака
     * @param image модель машины
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
     * Обновляет информацию о состоянии машины.
     */
    public void updateInfo() {

        info.setText(this.toString());
        fuelState.setProgress(this.gasolineStocks / this.fuelCapacity);



    }

    /**
     * Возвращает текущий запас топлива.
     *
     * @return запас топлива
     */
    public double getGasolineStocks() {

        return gasolineStocks;



    }

    /**
     * Возвращает объем топливного бака.
     * @return объем топливного бака
     */
    public double getFuelCapacity() {

        return fuelCapacity;



    }

    /**
     * Увеличивает запас топлива на единицу.
     */
    public void increaseGasolineStocks() {

        this.gasolineStocks += 1;



    }

    /**
     * Возвращает рекомендуемый тип топлива.
     *
     * @return тип топлива
     */
    public OctaneRating getRecommendedRating() {

        return recommendedRating;



    }

    /**
     * Устанавливает текущую бензозаправочную станцию.
     *
     * @param gs
     */
    public void setGS(Station gs) {

        this.currentGasStation = gs;
        this.currentGasStation.addToCarStream(this);
        currentGasStation.addToScene(this);



    }

    /**
     * Сдвигает машину на единицую растояния.
     */
    public void move() {

        this.setY(this.getY() + 1);



    }

    /**
     * Назначает новую цель.
     * @param target цель
     */
    public void setTarget(Target target) {

        this.target = target;



    }

    /**
     * Выбирает наиболее выгодную колонку на станции.
     */
    public void chooseDispener() {

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
     * Ставит в очередь на колонку.
     */
    public void getInLineStepOne() {

        if (this.currentDispenser.getQueueSize() != 0) {
            this.setY(this.currentDispenser.getCarQueue().getLast().getY() - 2 * CAR_HEIGHT);
        } else {
            this.setY(0);
        }
        currentDispenser.pushToQueue(this);
        this.setX(this.currentDispenser.getIdOfDispenser() % 2 == 0
                ? this.currentDispenser.getX() + CAR_WIDTH + 10.0
                : this.currentDispenser.getX() - CAR_WIDTH - 10.0);
        this.target = Target.GET_IN_LINE;



    }

    /**
     * Двигает до тех пор пока не встанет позади последней машины в очереди.
     */
    public void getInLineStepTwo() {

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
     * Продвигает в очереди.
     */
    public void moveInLine() {

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
     * Заправляет до полного бака.
     */
    public void refuel() {

        if (this.getGasolineStocks() == this.getFuelCapacity()) {
            this.setTarget(Target.GET_AWAY);
        } else {
            this.increaseGasolineStocks();
            this.currentDispenser.increaseTotal(this.getRecommendedRating());
        }



    }

    /**
     * Освобождает колонку.
     */
    public void getAway() {

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
     * Убирает машину с заправки.
     */
    public void removeFromGS() {

        this.currentGasStation.root.getChildren().remove(this);
        /*this.currentDispenser = null;
        this.currentGasStation = null;
        this.scene = null;
        this.root = null;
        this.currentGasStation = null;*/



    }

    /**
     * Обновляет положение машины на сцене и ее состояние.
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
            case GET_AWAY:
                this.getAway();
                break;
            case GET_AWAY_FROM_GS:
                this.removeFromGS();
        }

        this.updateInfo();



    }

    /**
     * Возвращает машину, идущую спереди в очереди.
     * @return машина спереди
     */
    public Car getNextCar() {

        return nextCar;



    }

    /**
     * Устанавливает следующую машину
     * @param nextCar следующая машина
     */
    public void setNextCar(Car nextCar) {

        this.nextCar = nextCar;



    }

    /**
     * Возвращает машину, идующую сзади.
     * @return машина сзади
     */
    public Car getPreviousCar() {

        return previousCar;



    }

    /**
     * Устанавливает машину сздаи.
     * @param previousCar машина сзади
     */
    public void setPreviousCar(Car previousCar) {

        this.previousCar = previousCar;



    }


    /**
     * Переводит информацию о машине в текстовый формат.
     * @return информация о машине
     */
    @Override
    public String toString() {

        return "Target: " + this.target + "\n" +
               "Gas state: " + this.getGasolineStocks() + "/" + this.getFuelCapacity() + "\n" +
               "ID: " + this.getId() + "\n" +
               "Next car: " + (this.getNextCar() != null ? this.getNextCar().getId() : "null") + "\n" +
               "Previous car: " + (this.getPreviousCar() != null ? this.getPreviousCar().getId() : "null") + "\n";



    }



}
