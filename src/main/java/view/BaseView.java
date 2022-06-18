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
        //        PasswordField pass = new PasswordField();
//        Platform.runLater(new Runnable() {
//          @Override
//          public void run() {
//
//          }
//        }
//        );

//        Group group = new Group();
//        group.getChildren().add(button);
//        Parent root = FXMLLoader.load(getClass().getResource("fxml/simulator_control_panel.fxml"),
//                ResourceBundle.getBundle("config/config"));
//        URL resource = getClass().getResource("");
//        System.out.println(resource.toString());
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

    public void setSuccessButton(Button button) {
        button.setStyle("-fx-background-color: #33CC33");
    }

    public void setFailureLabel(Label label) {
        label.getStyleClass().remove("successLabel");
        label.getStyleClass().add("failureLabel");
    }

    public Stage getStage() {
        return this.stage;
    }

    public void setNormalRadioButton(RadioButton radioButton) {
//        radioButton.
    }

    public void setUnFocusRadioButton(RadioButton radioButton) {

    }
}
