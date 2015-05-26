package com.dekarti.gs;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Определяет класс объект, который реагирует на нажатие открытием новой сцены, в которой может содержаться
 * произвольная информация об объекте.
 *
 * <pre>
 *     // Создадим объект, расположенный по коориданатм (400, 700) и размерами 10х19,
 *     // предварительно создав изображение будущего объекта.
 *     Image img = new Image("/path/to/image");
 *     Object obj = new Object(400, 700, 10, 10, img);
 *
 *     // Теперь нам надо расположить этот объект на сцене. Сделаем это следующим образом:
 *     // В первой строчке создадим контейнер {@link Group}, в котором объекты можно произвольно
 *     // располагать на сцене.
 *     // Во второй строчке добавим нами созданный объект в этот контейнер.
 *     // И наконец в третьей строчке разместим этот контейнер на сцене.
 *     Group root = new Group();
 *     root.getChildren().add(obj);
 *     Scene scene = new Scene(root);
 *
 *     primaryStage.setScene(scene);
 *     primaryStage.show();
 *
 *     // Запустив этот кусок кода мы увидим сцену, на которой в точке (400, 700) будет
 *     // изображение объекта размерами 10х10. Так же по клику можно будет открыть сцену этого объекта.
 *     // Сцена будет содержать лишь кнопку для возврата на родительскую сцену.
 *
 *
 *
 * </pre>
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
