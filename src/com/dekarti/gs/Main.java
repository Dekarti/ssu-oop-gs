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