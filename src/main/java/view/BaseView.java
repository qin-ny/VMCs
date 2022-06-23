package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    protected void addStyleClass(Node node, String styleClass) {
        if (!node.getStyleClass().contains(styleClass)) node.getStyleClass().add(styleClass);
    }

    protected void setSuccessLabel(Label label) {
        label.getStyleClass().remove("failureLabel");
        label.getStyleClass().remove("originLabel");
        addStyleClass(label, "successLabel");
    }

    protected void setFailureLabel(Label label) {
        label.getStyleClass().remove("successLabel");
        label.getStyleClass().remove("originLabel");
        addStyleClass(label, "failureLabel");
    }

    protected void setDefaultLabel(Label label) {
        label.getStyleClass().remove("successLabel");
        label.getStyleClass().remove("failureLabel");
        addStyleClass(label, "originLabel");
    }

    protected void setRadioButton(RadioButton radioButton, ToggleGroup toggleGroup) {
        radioButton.getStyleClass().remove("radio-button");
        addStyleClass(radioButton, "toggle-button");
        radioButton.setToggleGroup(toggleGroup);
    }

}
