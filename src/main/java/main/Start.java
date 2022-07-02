package main;

import controller.*;
import javafx.application.Application;
import javafx.stage.Stage;

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

    public enum ControllerType {
        MACHINERY_PANEL_CONTROLLER,
        SIMULATOR_CONTROL_PANEL_CONTROLLER,
        MAINTAINER_PANEL_CONTROLLER,
        CUSTOMER_PANEL_CONTROLLER
    }

    private static Map<ViewType, BaseView> views = new HashMap<>(){};
    private static Map<ControllerType, BaseController> controllers = new HashMap<>(){};
    private static Machine machine;
    private static boolean simulationStatus;

    public static BaseView getView(ViewType view) {
        return views.get(view);
    }

    public static BaseController getController(ControllerType controller) {
        return controllers.get(controller);
    }

    @Override
    public void start(Stage stage) throws Exception {
        machine = JsonMachineConverter.jsonToMachineObject(Constants.DATA_SOURCE);
        simulationStatus = false;

        views.put(ViewType.MACHINERY_PANEL_VIEW, new MachineryPanelView());
        views.put(ViewType.SIMULATOR_CONTROL_PANEL_VIEW, new SimulatorControlPanelView());
        views.put(ViewType.MAINTAINER_PANEL_VIEW, new MaintainerPanelView());
        views.put(ViewType.CUSTOMER_PANEL_VIEW, new CustomerPanelView());

        controllers.put(ControllerType.MACHINERY_PANEL_CONTROLLER, new MachineryPanelController());
        controllers.put(ControllerType.SIMULATOR_CONTROL_PANEL_CONTROLLER, new SimulatorControlPanelController());
        controllers.put(ControllerType.MAINTAINER_PANEL_CONTROLLER, new MaintainerPanelController());
        controllers.put(ControllerType.CUSTOMER_PANEL_CONTROLLER, new CustomerPanelController());

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
        //Save the machine data to json file
        JsonMachineConverter.machineObjectToJson(machine, Constants.DATA_SOURCE);
    }

    public static boolean getSimulationStatus() {
        return simulationStatus;
    }
}
