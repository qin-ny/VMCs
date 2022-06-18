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

    public static Map<String, BaseController> controllers = new HashMap<String, BaseController>(){};
    public static Map<String, Panel> panels = new HashMap<String, Panel>(){};
    public static Map<String, BaseView> views = new HashMap<String, BaseView>(){};
    public static JsonMachineConverter jsonMachineConverter;


    private DataController data_controller;

    public DataController getDataController() { return data_controller; }

    @Override
    public void start(Stage stage) throws Exception {
        controllers.put("operatorController", new OperatorController(this));
        controllers.put("maintainerController", new MaintainerController(this));
        controllers.put("customerController", new CustomerController(this));

        panels.put("machineryPanel", new MachineryPanel());
        panels.put("simulatorControlPanel", new SimulatorControlPanel());
        panels.put("maintainerPanel", new MaintainerPanel());
        panels.put("customerPanel", new CustomerPanel());

        views.put("machineryPanelView", new MachineryPanelView());
        views.put("simulatorControlPanelView", new SimulatorControlPanelView());
        views.put("maintainerPanelView", new MaintainerPanelView());
        views.put("customerPanelView", new CustomerPanelView());

        jsonMachineConverter = new JsonMachineConverter();

        data_controller = new DataController();

        views.get("simulatorControlPanelView").init();
    }
}
