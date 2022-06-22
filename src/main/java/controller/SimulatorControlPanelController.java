package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import main.Start;
import view.SimulatorControlPanelView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SimulatorControlPanelController implements Initializable {

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

//    @FXML
//    protected void onHelloButtonClick() {
//
//    }

//    @FXML
//    public void initialize(Start startObj) {
//
//        List<Panel> activatePanels = startObj.getActivatingPanels();
//        for (Panel panel: activatePanels) {
//            Button button = new Button("Activate " + panel.name);
//            button.setId(panel.name);
//            VBActiveButtons.getChildren().add(button);
//        }
//    }



    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        System.out.println("test");
    }

    public void handleButtonAction(ActionEvent actionEvent) {
        SimulatorControlPanelView view = (SimulatorControlPanelView) Start.getView(Start.ViewType.SIMULATOR_CONTROL_PANEL_VIEW);
        Button button = (Button)actionEvent.getSource();
        switch (button.getId()) {
            case "beginSimulation":
                beginSimulation();
                break;
            case "endSimulation":
                endSimulation();
                break;
            case "customerPanel":
                if (checkSimulationStatus()) {
                    activePanel(Start.ViewType.CUSTOMER_PANEL_VIEW);
                } else {
                    view.createAlert(Alert.AlertType.WARNING, "System hasn't begun the simulation yet!");
                }
                break;
            case "maintainerPanel":
                if (checkSimulationStatus()) {
                    activePanel(Start.ViewType.MAINTAINER_PANEL_VIEW);
                } else {
                    view.createAlert(Alert.AlertType.WARNING, "System hasn't begun the simulation yet!");
                }
                break;
            case "machineryPanel":
                if (checkSimulationStatus()) {
                    activePanel(Start.ViewType.MACHINERY_PANEL_VIEW);
                } else {
                    view.createAlert(Alert.AlertType.WARNING, "System hasn't begun the simulation yet!");
                }
                break;
        }
    }

    private boolean checkSimulationStatus() {
        return  Start.getSimulationStatus();
    }

    private void beginSimulation() {
        Start.beginSimulation();
    }

    private void endSimulation() {
        Start.endSimulation();
        Start.getView(Start.ViewType.SIMULATOR_CONTROL_PANEL_VIEW).exit();
    }

    public void activePanel(Start.ViewType viewType) {
        try {
            Start.getView(viewType).init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

    }

}
