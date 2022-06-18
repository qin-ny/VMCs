package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MachineryPanelView extends BaseView {

    public MachineryPanelView() {
        this.stage = new Stage();
        this.fxml = "machinery_panel.fxml";
        this.css = "machinery_panel.css";
        this.title = "VMCs - Machinery Panel";
    }

//    @Override
//    public void init() throws IOException {
//
//    };

}
