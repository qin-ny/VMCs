package factory;

import controller.*;
import main.Start;

public class ControllerFactory {
    public static BaseController getController(Start.ControllerType controllerType){
        switch (controllerType) {
            case MACHINERY_PANEL_CONTROLLER:
                return new MachineryPanelController();
            case SIMULATOR_CONTROL_PANEL_CONTROLLER:
                return new SimulatorControlPanelController();
            case MAINTAINER_PANEL_CONTROLLER:
                return new MaintainerPanelController();
            case CUSTOMER_PANEL_CONTROLLER:
                return new CustomerPanelController();
            default:
                return null;
        }
    }
}

