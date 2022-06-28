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
    protected BaseView viewHandler;

    public void init() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(baseFxmlPath + fxml));
        Parent root = loader.load();
        viewHandler = loader.getController();
        viewHandler.setStage(stage);

        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource(baseCssPath + baseCss).toExternalForm());
        scene.getStylesheets().add(getClass().getResource(baseCssPath + css).toExternalForm());

        scene.getRoot().setStyle("-fx-font-family: " + normalFontFamily);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setMinWidth(300);
        stage.setMinHeight(350);
        stage.show();
    };

    public void exit() {
        stage.close();
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

    public void setStage(Stage stage) {
        this.stage = stage;
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

    protected String getUniqueId(String type, String id, String suffix) {
        return String.join("-", String.valueOf(id), type, suffix);
    }

    protected String getUniqueId(String type, int id, String suffix) {
        return getUniqueId(type, String.valueOf(id), suffix);
    }

    protected String fetchIdByUniqueId(String uniqueId) {
        String[] valueList = uniqueId.split("-");
        return valueList[0];
    }

}
