package main;

import controller.*;
import javafx.application.Application;
import javafx.stage.Stage;

import handler.*;
import util.JsonMachineConverter;
import view.*;

import java.util.HashMap;
import java.util.Map;


public class Start extends Application {

    public static Map<String, BaseView> views = new HashMap<String, BaseView>(){};
    public static JsonMachineConverter jsonMachineConverter;

    private DataController data_controller;

    @Override
    public void start(Stage stage) throws Exception {

        views.put("machineryPanelView", new MachineryPanelView());
        views.put("simulatorControlPanelView", new SimulatorControlPanelView());
        views.put("maintainerPanelView", new MaintainerPanelView());
        views.put("customerPanelView", new CustomerPanelView());

        jsonMachineConverter = new JsonMachineConverter();

        data_controller = new DataController();

        views.get("simulatorControlPanelView").init();
    }
}
