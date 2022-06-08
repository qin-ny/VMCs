package vmcs;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Start extends Application {
//    public static void main(String[] args) {
//        launch(args);
//    }

    @Override
    public void start(Stage stage) throws Exception {
        Button button = new Button("test");
        button.setLayoutX(200);
        button.setLayoutY(200);
        button.setFont(Font.font("sans-serif", 20));

        button.setPrefWidth(100);
        button.setPrefHeight(100);

        BackgroundFill bgf = new BackgroundFill(
                Paint.valueOf("#0099FF"),
                new CornerRadii(20),
                new Insets(10)
        );
        Background bg = new Background(bgf);

        button.setBackground(bg);
        button.setOpacity(0.8);
//        button.set

        AnchorPane pane = new AnchorPane();

//        pane.getChildren().add(button);

//        Platform.runLater(new Runnable() {
//          @Override
//          public void run() {
//
//          }
//        }
//        );

        Group group = new Group();
        group.getChildren().add(button);

        Scene scene = new Scene(group);

        stage.setScene(scene);
        stage.setMaxWidth(500);
        stage.setMaxHeight(500);
        stage.setOpacity(0.9);
        stage.setTitle("test");
        stage.show();
    }
}
