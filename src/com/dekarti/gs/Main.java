package com.dekarti.gs;

import com.dekarti.gs.Station;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.Random;

/**
 * Класс Main определяет точку входа программы.
 * Пример:
 * <hr>
 * <pre>
 *      public class Main extends Application {
 *
 *          // определяем статическую переменную, которая будет отвечать за ширину сцены
 *          static public double WIDTH = 1200;
 *
 *          // определяем статическую переменную, которая будет отвечать за высоту сцены
 *          static public double HEIGHT = 900;
 *
 *          // определяем главную сцену приложения, которая будет родительской для всех ее элементов
 *          public static Stage primaryStage;
 *
 *          // определяем main функцию
 *          public static void main(String[] args) {
 *              launch(args);
 *          }
 *
 *          private int i = 0;
 *
 *          // т.к. мы наследовали класс Main от абстрактного класса Application
 *          // в котором обязательно должен быть реализован метод start(), то нам следует его переопределить
 *          \@Override
 *          public void start(Stage stage) {
 *
 *              // инициализируем родительскую сцену, которую мы определили ранее
 *              primaryStage = stage;
 *              // устанавливаем заголовок
 *              primaryStage.setTitle("Gas Station");
 *
 *              // устанавливаем атрибуты
 *              primaryStage.setWidth(WIDTH);
 *              primaryStage.setHeight(HEIGHT);
 *
 *              // формируем контейнер объектов
 *              Group root = new Group();
 *
 *              // создаем станцию размерами 500х500 в точке (100, 100)
 *              Station station = new Station(500, 500, 100, 100);
 *
 *              // определяем ключевые кадры нашей симуляции заправки
 *              Timeline timeLine = new Timeline();
 *              timeLine.setCycleCount(Timeline.INDEFINITE);
 *              timeLine.getKeyFrames().add(
 *                      new KeyFrame(Duration.millis(10),
 *                              t -> {
 *                                  // при каждом обновлении сцены, принимаем решение о создании новой машины
 *                                  // тем самым генерируя произвольное появляение машины на станции
 *                                  if (i >= new Random().nextInt(100) + 50) {
 *                                      Car car = new Car(
 *                                              new Random().nextInt(200),
 *                                              OctaneRating.values()[new Random().nextInt(3)],
 *                                              500,
 *                                              Car.models[new Random().nextInt(4)]);
 *                                      car.setRotate(180);
 *                                      car.setGS(station);
 *
 *                                      i = 0;
 *                                  }
 *                                  i++;
 *                                  station.update();
 *                              }
 *                      )
 *              );
 *              timeLine.play();
 *
 *              // добавляем к контейнер объектов сцены нашу станцию
 *              root.getChildren().add(station);
 *              Scene scene = new Scene(root, WIDTH, HEIGHT);
 *
 *
 *              station.setOnMouseClicked(event -> {
 *                  station.setParent(scene);
 *                  primaryStage.setScene(station.getObjectScene());
 *              });
 *
 *              primaryStage.setScene(scene);
 *              primaryStage.show();
 *
 *          }
 *      }
 * </pre>
 * <hr>
 */
public class Main extends Application {

    static public double WIDTH = 1200;

    static public double HEIGHT = 900;
    public static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    private int i = 0;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Gas Station");


        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);

        Group root = new Group();
        Station station = new Station(500, 500, 100, 100);

        Timeline timeLine = new Timeline();
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.getKeyFrames().add(
                new KeyFrame(Duration.millis(10),
                        t -> {
                            if (i >= new Random().nextInt(100) + 50) {
                                Car car = new Car(
                                        new Random().nextInt(200),
                                        OctaneRating.values()[new Random().nextInt(3)],
                                        500,
                                        Car.models[new Random().nextInt(4)]);
                                car.setRotate(180);
                                car.setGS(station);

                                    i = 0;
                            }
                            i++;
                            station.update();
                        }
                )
        );
        timeLine.play();


        root.getChildren().add(station);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        station.setOnMouseClicked(event -> {
            station.setParent(scene);
            primaryStage.setScene(station.getObjectScene());
        });

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}