package com.dekarti.gs;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * ���������� ����� ������, ������� ��������� �� ������� ��������� ����� �����, � ������� ����� �����������
 * ������������ ���������� �� �������.
 *
 * <pre>
 *     // �������� ������, ������������� �� ����������� (400, 700) � ��������� 10�19,
 *     // �������������� ������ ����������� �������� �������.
 *     Image img = new Image("/path/to/image");
 *     Object obj = new Object(400, 700, 10, 10, img);
 *
 *     // ������ ��� ���� ����������� ���� ������ �� �����. ������� ��� ��������� �������:
 *     // � ������ ������� �������� ��������� {@link Group}, � ������� ������� ����� �����������
 *     // ����������� �� �����.
 *     // �� ������ ������� ������� ���� ��������� ������ � ���� ���������.
 *     // � ������� � ������� ������� ��������� ���� ��������� �� �����.
 *     Group root = new Group();
 *     root.getChildren().add(obj);
 *     Scene scene = new Scene(root);
 *
 *     primaryStage.setScene(scene);
 *     primaryStage.show();
 *
 *     // �������� ���� ����� ���� �� ������ �����, �� ������� � ����� (400, 700) �����
 *     // ����������� ������� ��������� 10�10. ��� �� �� ����� ����� ����� ������� ����� ����� �������.
 *     // ����� ����� ��������� ���� ������ ��� �������� �� ������������ �����.
 *
 *
 *
 * </pre>
 */
public class Object extends ImageView {
    /**
     * ����� �� ������� �������� ������
     */
    protected Scene parentScene;

    /**
     * ����� �������
     */
    protected Scene scene;

    /**
     * ��������� �������� �����
     */
    protected Group root;

    /**
     * ������ ������
     */
    protected HBox hBox;

    /**
     * ������� ������ � �������� �������������, �������� � ������������ �������.
     *
     * @param x �������������� ���������� �������
     * @param y ������������ ���������� �������
     * @param width ������ �������
     * @param height ������ �������
     * @param image ����������� �������
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
     * ������� ������ � �������� �������� � ������������.
     *
     * @param width ������ �������
     * @param height ������ �������
     * @param image ����������� �������
     */
    public Object(double width, double height, Image image) {

        super(image);
        this.setFitWidth(width);
        this.setFitHeight(height);
        buildScene();



    }

    /**
     * ������� ������ � �������� ������������.
     *
     * @param image ����������� �������
     */
    public Object(Image image) {

        super(image);
        buildScene();



    }

    /**
     * ���������� ����� �������.
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
     * ���������� ����� �������.
     *
     * @return ����� �������
     */
    public Scene getObjectScene() {

        return scene;



    }


    /**
     * ������������� ��� �������� ������� ������������ �����.
     *
     * @param parentScene ������������ �����
     */
    public void setParent(Scene parentScene) {

        this.parentScene = parentScene;



    }

    /**
     * ��������� ����� ������� �����.
     *
     * @param node ����� ������� �����
     */
    public void addNodeToScene(Node node) {

        this.root.getChildren().add(node);



    }



}
