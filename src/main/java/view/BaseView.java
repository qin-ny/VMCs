package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class BaseView {
    protected final static String baseFxmlPath = "../fxml/";
    protected final static String baseCssPath = "../css/";
    protected final static String baseCss = "panel.css";
    protected final static String normalFontFamily = "sans-serif";
    protected Stage stage;
    protected String css, fxml, title;

    public void init() throws IOException {
//        Platform.runLater(new Runnable() {
//          @Override
//          public void run() {
//
//          }
//        }
//        );

        Parent root = FXMLLoader.load(getClass().getResource(baseFxmlPath + fxml));
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource(baseCssPath + baseCss).toExternalForm());
        scene.getStylesheets().add(getClass().getResource(baseCssPath + css).toExternalForm());

        scene.getRoot().setStyle("-fx-font-family: " + normalFontFamily);
        this.stage.setTitle(title);
        stage.setScene(scene);
        stage.setMinWidth(300);
        stage.setMinHeight(350);
        stage.show();
    };

    public void exit() {
        this.stage.close();
    }

    public void createAlert(Alert.AlertType alertType, String msg) {
        Alert alert = new Alert(
                alertType,
                msg
        );
        alert.showAndWait();
    }

    public Stage getStage() {
        return this.stage;
    }

}
