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
import java.util.Optional;


public class Start extends Application {

    public static Map<String, BaseView> views = new HashMap<String, BaseView>(){};
    private static Machine machine;
    private static boolean simulationStatus;

    @Override
    public void start(Stage stage) throws Exception {

        views.put("machineryPanelView", new MachineryPanelView());
        views.put("simulatorControlPanelView", new SimulatorControlPanelView());
        views.put("maintainerPanelView", new MaintainerPanelView());
        views.put("customerPanelView", new CustomerPanelView());

        machine = JsonMachineConverter.jsonToMachineObject(Constants.DATA_SOURCE);
        simulationStatus = false;

        views.get("simulatorControlPanelView").init();
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
