package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class MaintainerPanelView extends BaseView {

    private ToggleGroup slotToggleGroup;
    private ToggleGroup coinToggleGroup;

    public MaintainerPanelView() {
        this.stage = new Stage();
        this.fxml = "maintainer_panel.fxml";
        this.css = "maintainer_panel.css";
        this.title = "VMCs - Maintainer Panel";
        this.slotToggleGroup = new ToggleGroup();
        this.coinToggleGroup = new ToggleGroup();
    }

    public void setSlotToggleGroup(ToggleGroup drinkToggleGroup) {
        this.slotToggleGroup = drinkToggleGroup;
    }

    public ToggleGroup getSlotToggleGroup() {
        return this.slotToggleGroup;
    }

    public void setCoinToggleGroup(ToggleGroup drinkToggleGroup) {
        this.coinToggleGroup = drinkToggleGroup;
    }

    public ToggleGroup getCoinToggleGroup() {
        return this.coinToggleGroup;
    }

//    @Override
//    public void init() throws IOException {
//
//    };
}
