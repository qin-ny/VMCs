package view;

import controller.SimulatorControlPanelController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Start;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SimulatorControlPanelView extends BaseView implements Initializable {

    @FXML
    private Button beginSimulation;
    @FXML
    private Button endSimulation;
    @FXML
    private Button customerPanel;
    @FXML
    private Button maintainerPanel;
    @FXML
    private Button machineryPanel;

    public SimulatorControlPanelView() {
        this.stage = new Stage();
        this.fxml = "simulator_control_panel.fxml";
        this.css = "simulator_control_panel.css";
        this.title = "VMCs - Simulator Control Panel";
    }

    public SimulatorControlPanelView getHandler() {
        return (SimulatorControlPanelView) viewHandler;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleButtonAction(ActionEvent actionEvent) {
        SimulatorControlPanelController controller = (SimulatorControlPanelController) Start.getController(
                Start.ControllerType.SIMULATOR_CONTROL_PANEL_CONTROLLER);
        Button button = (Button)actionEvent.getSource();
        switch (button.getId()) {
            case "beginSimulation":
                controller.beginSimulation();
                break;
            case "endSimulation":
                controller.endSimulation();
                break;
            case "customerPanel":
                controller.activePanel(Start.ViewType.CUSTOMER_PANEL_VIEW);
                break;
            case "maintainerPanel":
                controller.activePanel(Start.ViewType.MAINTAINER_PANEL_VIEW);
                break;
            case "machineryPanel":
                controller.activePanel(Start.ViewType.MACHINERY_PANEL_VIEW);
                break;
        }
    }
}
