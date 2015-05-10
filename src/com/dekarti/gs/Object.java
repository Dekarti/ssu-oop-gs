package com.dekarti.gs;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Определяет класс объект, который реагирует на нажатие открытием новой сцены, в которой может содержаться
 * произвольная информация об объекте.
 */
public class Object extends ImageView {
    /**
     * сцена из которой вызывали объект
     */
    protected Scene parentScene;

    /**
     * сцена объекта
     */
    protected Scene scene;

    /**
     * Контейнер объектов сцены
     */
    protected Group root;

    /**
     * Панель кнопок
     */
    protected HBox hBox;

    /**
     * Создает Объект с заданным расположением, размером и изображением объекта.
     *
     * @param x горизонтольная координата объекта
     * @param y вертикальная координата объекта
     * @param width ширина объекта
     * @param height высота объекта
     * @param image изображение объекта
     */
    public Object(double x, double y, double width, double height, Image image) {

        super(image);
        this.setX(x);
        this.setY(y);
        this.setFitWidth(width);
        this.setFitHeight(height);
        buildScene();



    }

    /**
     * Создает Объект с заданным размером и изображением.
     *
     * @param width ширина объекта
     * @param height высота объекта
     * @param image изображение объекта
     */
    public Object(double width, double height, Image image) {

        super(image);
        this.setFitWidth(width);
        this.setFitHeight(height);
        buildScene();



    }

    /**
     * Создает Объект с заданным изображением.
     *
     * @param image изображение объекта
     */
    public Object(Image image) {

        super(image);
        buildScene();



    }

    /**
     * Генерирует сцену объекта.
     */
    private void buildScene() {

        hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setLayoutX(10);
        hBox.setLayoutY(10);

        root = new Group();
        Button returnBtn = new Button("Go back");
        hBox.getChildren().add(returnBtn);

        scene = new Scene(root, Main.WIDTH, Main.HEIGHT);

        returnBtn.setOnAction(event1 -> {
            Main.primaryStage.setScene(parentScene);
        });

        this.addNodeToScene(hBox);



    }

    /**
     * Возвращает сцену объекта.
     *
     * @return сцена объекта
     */
    public Scene getObjectScene() {

        return scene;



    }


    /**
     * Устанавливает для текущего объекта родительскую сцену.
     *
     * @param parentScene родительская сцена
     */
    public void setParent(Scene parentScene) {

        this.parentScene = parentScene;



    }

    /**
     * Добавляет новый элемент сцены.
     *
     * @param node новый элемент сцены
     */
    public void addNodeToScene(Node node) {

        this.root.getChildren().add(node);



    }



}
