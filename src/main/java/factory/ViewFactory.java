package factory;

import main.Start;
import view.*;

public class ViewFactory {
    public static BaseView getView(Start.ViewType viewType){
        switch (viewType) {
            case MACHINERY_PANEL_VIEW:
                return new MachineryPanelView();
            case SIMULATOR_CONTROL_PANEL_VIEW:
                return new SimulatorControlPanelView();
            case MAINTAINER_PANEL_VIEW:
                return new MaintainerPanelView();
            case CUSTOMER_PANEL_VIEW:
                return new CustomerPanelView();
            default:
                return null;
        }
    }
}
