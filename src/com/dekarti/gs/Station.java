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
 * ќпредел€ет класс «аправочна€ станци€ с 6-ю колонками.
 * <hr>
 * <b>ѕример использовани€:</b>
 * <pre>
 *     // создаем станцию размером 100х100 и располагаем в точке (500, 500)
 *     Station station = new Station(500, 500, 100, 100);
 *
 *     // создадим несколько машин и поставим их на заправку в нашей станции
 *     Car car1 = new Car(140, OctaneRating.AI_92, 500, new Image("your/path/to/cars/pic"));
 *     Car car2 = new Car(140, OctaneRating.AI_92, 500, new Image("your/path/to/cars/pic"));
 *     Car car3 = new Car(140, OctaneRating.AI_92, 500, new Image("your/path/to/cars/pic"));
 *     car1.setGS(station);
 *     car2.setGS(station);
 *     car3.setGS(station);
 *
 *
 *     // вывод информации о машинах, которые покинули заправку не заправшившись
 *     System.out.println(station.getLoss());
 *
 *     // вывод информации о машинах, успешно заправившихс€
 *     System.out.println(station.getProfit());
 *
 *
 * </pre>
 *
 */
public class Station extends Object {

    /**
     *  оличество колонок
     */
    final static public int NUM_OF_DISPENSERS = 6;

    /**
     * ѕоток машин
     */
    private LinkedList<Car> carStream;

    /**
     *  олонки
     */
    private Vector<Dispenser> dispensers;

    /**
     * ќбща€ информаци€ о заправке
     */
    private TextArea information;

    /**
     *  олличество пропущенных машин
     */
    private double loss = 0;

    /**
     *  олличество обслуженных машин
     */
    private double profit = 0;

    /**
     * —оздает станцию с заданными координатами и размерами.
     * @param x горизонтальна€ координата
     * @param y вертикальна€ координата
     * @param width ширина колонки
     * @param height высота колонки
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
     * ¬озвращает список колонок
     * @return список колонок
     */
    public Vector<Dispenser> getDispensers() {

        return dispensers;



    }

    /**
     * ƒобавл€ет на сцену машину
     * @param car машина
     */
    public void addToScene(Car car) {

        root.getChildren().add(car);



    }


    /**
     * ƒобавл€ет в поток машины вновь прибывшую машину
     * @param car вновь прибывша€ машина
     */
    public void addToCarStream(Car car) {

        this.carStream.add(car);



    }

    /**
     * ќбновл€ет положение очередей на станции
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
     * ”величивает счетчик пропущенных машин.
     */
    public void increaseLoss() {

        this.loss++;



    }

    /**
     * ”величивает счетчик обслуженных машин.
     */
    public void increaseProfit() {

        this.profit++;



    }

    /**
     * ѕереопредел€ет метод toString() дл€ класса —танци€.
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
