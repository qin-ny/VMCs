package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulatorControlPanelView extends BaseView {


    public SimulatorControlPanelView() {
        this.stage = new Stage();
        this.fxml = "simulator_control_panel.fxml";
        this.css = "simulator_control_panel.css";
        this.title = "VMCs - Simulator Control Panel";
    }

//    @Override
//    public void init() throws IOException {
//
//    };


}
