package main;

import controller.*;
import javafx.application.Application;
import javafx.stage.Stage;

import handler.*;
import objects.Machine;
import util.Constants;
import util.JsonMachineConverter;
import view.*;

import java.util.HashMap;
import java.util.Map;


public class Start extends Application {

    public enum ViewType {
        MACHINERY_PANEL_VIEW,
        SIMULATOR_CONTROL_PANEL_VIEW,
        MAINTAINER_PANEL_VIEW,
        CUSTOMER_PANEL_VIEW
    }
//    public final static enum machineryPanelView = 1;
//    public final static int simulatorControlPanelView = 2;
//    public final static int maintainerPanelView = 3;
//    public final static int customerPanelView = 4;

    private static Map<ViewType, BaseView> views = new HashMap<>(){};
    private static Machine machine;
    private static boolean simulationStatus;


    public static BaseView getView(ViewType view) {
        return views.get(view);
    }

    @Override
    public void start(Stage stage) throws Exception {
        views.put(ViewType.MACHINERY_PANEL_VIEW, new MachineryPanelView());
        views.put(ViewType.SIMULATOR_CONTROL_PANEL_VIEW, new SimulatorControlPanelView());
        views.put(ViewType.MAINTAINER_PANEL_VIEW, new MaintainerPanelView());
        views.put(ViewType.CUSTOMER_PANEL_VIEW, new CustomerPanelView());

        machine = JsonMachineConverter.jsonToMachineObject(Constants.DATA_SOURCE);
        simulationStatus = false;

        getView(ViewType.SIMULATOR_CONTROL_PANEL_VIEW).init();
    }

    public static Machine getMachine() {
        return machine;
    }

    public static void beginSimulation() {
        simulationStatus = true;
    }

    public static void endSimulation() {
        simulationStatus = false;
//        JsonMachineConverter.machineObjectToJson(machine, Constants.DATA_SOURCE);
    }

    public static boolean getSimulationStatus() {
        return simulationStatus;
    }
}
