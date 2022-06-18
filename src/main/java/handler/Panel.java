package handler;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Start;

import java.io.IOException;

public abstract class Panel implements InterfacePanel {

    protected Start start_obj;
    protected String fxmlPath;
    protected String cssPath;
    protected String name;
    protected String title;

    protected Stage stage = new Stage();
    protected Label caption = new Label();
    protected final String normalFontFamily = "sans-serif";
    protected final String visibleCoinType = "c";
    protected final String coinLabelColor = "#FF9933";
    protected final String successColor = "#33CC33";
    protected final String failureColor = "#CC3300";
    protected final static String baseFxmlPath = "../fxml/";
    protected final static String baseCssPath = "../css/";


    @Override
    public void setStyle(
            Node node,
            String backRGB,
            int radius
    ) {
        setStyle(node, backRGB, radius, normalFontFamily, 15);
    };

    @Override
    public void setStyle(
            Node node,
            String backRGB,
            int radius,
            String fontFamily,
            int fontSize
    ) {
        String statement = "";
        if (backRGB != "") {
            statement += "-fx-background-color: " + backRGB;
        }
        if (radius > 0) {
            statement += ";-fx-background-radius: " + radius;
        }
        if (fontFamily != "") {
            statement += ";-fx-font-family: " + fontFamily;
        }
        if (fontSize > 0) {
            statement += ";-fx-font-size: " + fontSize;
        }
        node.setStyle(statement);
    };

    protected ScrollPane getScrollPane() {
        ScrollPane sPane = new ScrollPane();
        sPane.setStyle("-fx-background-color: transparent;" +
                "-fx-padding: 0;" +
                "-fx-border-color: transparent;" +
                "-fx-hbar-policy: never;" +
                "-fx-vbar-policy: never");
        return sPane;
    }

    @Override
    public void setButtonBorder(
            Button button,
            Paint paint,
            BorderStrokeStyle borderStrokeStyle,
            CornerRadii cornerRadii,
            BorderWidths borderWidth
    ) {
        BorderStroke bs = new BorderStroke(
                paint,
                borderStrokeStyle,
                cornerRadii,
                borderWidth
        );
        Border border = new Border(bs);
        button.setBorder(border);
    }

    @Override
    public void setMouseMoveColorChange(Node node, String sRGB, String dRGB, int radius) {
        node.onMouseMovedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyle(node, dRGB, radius);
            }
        });
        node.onMouseExitedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStyle(node, sRGB, radius);
            }
        });
    }

    @Override
    public void setMouseClickColorChange(Node node, String sRGB, String dRGB, int radius) {
        node.onMousePressedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyle(node, dRGB, radius);
            }
        });
        node.onMouseReleasedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyle(node, sRGB, radius);
            }
        });
    }

    @Override
    public void createAlert(Alert.AlertType alertType, String msg) {
        Alert alert = new Alert(
                alertType,
                msg
        );
        alert.showAndWait();
    }

    protected RadioButton getRadioButton(String name) {
        RadioButton radioButton = new RadioButton(name);
        radioButton.getStyleClass().remove("radio-button");
        radioButton.getStyleClass().add("toggle-button");
        return  radioButton;
    }

    protected abstract BorderPane getBorderPane();

    protected Label getCaption() {
        caption.setFont(Font.font(normalFontFamily, 20));
        return caption;
    }

    protected BorderPane getTopPane() {
        BorderPane topBPane = new BorderPane();
        Label caption = getCaption();

        topBPane.setCenter(caption);
        return topBPane;
    }

    protected abstract HBox getCenterHBox();

    @Override
    public void init() throws IOException {
        BorderPane bPane = getBorderPane();

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
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/simulator_control_panel.fxml"));

//        Label caption = (Label) root.lookup("#caption");
//        caption.setText("test");

//        Scene scene = new Scene(bPane);
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("../css/panel.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource(this.cssPath).toExternalForm());
        scene.getRoot().setStyle("-fx-font-family: " + normalFontFamily);
        this.stage.setTitle(title);
        stage.setScene(scene);
        stage.setMinWidth(300);
        stage.setMinHeight(350);
        stage.show();
    };

    protected void setButtonStyle(
            Button button,
            String protoColor,
            String moveColor,
            String clickColor,
            int radius,
            int width,
            int height,
            boolean setCursor
    ) {
//        button.setFont(Font.font(normalFontFamily, 15));
        if (width > 0) {
            button.setPrefWidth(width);
        }
        if (height > 0) {
            button.setPrefHeight(height);
        }

        setStyle(button, protoColor, radius);

        setMouseMoveColorChange(button, protoColor, moveColor, radius);
        setMouseClickColorChange(button, moveColor, clickColor, radius);

        if (setCursor) {
            button.setCursor(Cursor.HAND);
        }
        button.setOpacity(0.8);
    }

    protected void setButtonStyle(
            RadioButton button,
            String protoColor,
            String moveColor,
            String clickColor,
            int radius,
            int width,
            int height,
            boolean setCursor
    ) {
//        button.setFont(Font.font(normalFontFamily, 15));
        if (width > 0) {
            button.setPrefWidth(width);
        }
        if (height > 0) {
            button.setPrefHeight(height);
        }

        setStyle(button, protoColor, radius);

//        setMouseMoveColorChange(button, protoColor, moveColor, radius);
        setMouseClickColorChange(button, moveColor, clickColor, radius);

        if (setCursor) {
            button.setCursor(Cursor.HAND);
        }
        button.setOpacity(0.8);
    }

    protected void setButtonStyle(
            Button button
    ) {
        setButtonStyle(button, "#CCCCFF", "#99CCFF", "#66CCFF", 10, 200, 100);
    }

    protected void setButtonStyle(
            RadioButton button
    ) {
        setButtonStyle(button, "#CCCCFF", "#99CCFF", "#66CCFF", 10, 200, 100);
    }

    protected void setButtonStyle(
            Button button,
            String protoColor,
            String moveColor,
            String clickColor,
            int radius,
            int width,
            int height
    ) {
        setButtonStyle(button, protoColor, moveColor, clickColor, radius, width, height, true);
    }

    protected void setButtonStyle(
            RadioButton button,
            String protoColor,
            String moveColor,
            String clickColor,
            int radius,
            int width,
            int height
    ) {
        setButtonStyle(button, protoColor, moveColor, clickColor, radius, width, height, true);
    }

    protected void setButtonStyle(
            Button button,
            int radius,
            int width,
            int height
    ) {
        setButtonStyle(button, "#CCCCFF", "#99CCFF", "#66CCFF", radius, width, height);
    }

    protected void setButtonStyle(
            RadioButton button,
            int radius,
            int width,
            int height
    ) {
        setButtonStyle(button, "#CCCCFF", "#99CCFF", "#66CCFF", radius, width, height);
    }

    protected void setButtonStyle(
            Button button,
            int width,
            int height
    ) {
        setButtonStyle(button, "#CCCCFF", "#99CCFF", "#66CCFF", 10, width, height);
    }

    protected void setButtonStyle(
            RadioButton button,
            int width,
            int height
    ) {
        setButtonStyle(button, "#CCCCFF", "#99CCFF", "#66CCFF", 10, width, height);
    }

    @Override
    public abstract void exit();


}
