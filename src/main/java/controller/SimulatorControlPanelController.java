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

public class SimulatorControlPanelController extends BaseController {

    private final SimulatorControlPanelView view;

    public SimulatorControlPanelController() {
        this.view = (SimulatorControlPanelView) Start.getView(Start.ViewType.SIMULATOR_CONTROL_PANEL_VIEW);
    }

    public boolean checkSimulationStatus() {
        return  Start.getSimulationStatus();
    }

    public void beginSimulation() {
        Start.beginSimulation();
    }

    public void endSimulation() {
        Start.endSimulation();
        Start.getView(Start.ViewType.CUSTOMER_PANEL_VIEW).exit();
        Start.getView(Start.ViewType.MAINTAINER_PANEL_VIEW).exit();
        Start.getView(Start.ViewType.MACHINERY_PANEL_VIEW).exit();
    }

    public void activePanel(Start.ViewType viewType) {
        if (Start.getSimulationStatus()) {
            try {
                Start.getView(viewType).init();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            view.createAlert(Alert.AlertType.WARNING, "System hasn't begun the simulation yet!");
        }
    }

}
