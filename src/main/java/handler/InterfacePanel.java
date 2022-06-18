package handler;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

import java.io.IOException;

public interface InterfacePanel {

    public void init() throws IOException;

    public void exit();

    public void setStyle(Node node, String backRGB, int radius);

    public void setStyle(Node node, String backRGB, int radius, String fontFamily, int fontSize);

    public void setButtonBorder(Button button,
                                  Paint paint,
                                  BorderStrokeStyle borderStrokeStyle,
                                  CornerRadii cornerRadii,
                                  BorderWidths borderWidth
    );

    public void setMouseMoveColorChange(Node node, String sRGB, String dRGB, int radius);

    public void setMouseClickColorChange(Node node, String sRGB, String dRGB, int radius);

    public void createAlert(Alert.AlertType alertType, String msg);

}
