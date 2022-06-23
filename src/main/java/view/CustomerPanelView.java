package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import objects.Drink;
import objects.Slot;

import java.io.IOException;

public class CustomerPanelView extends BaseView {


    private ToggleGroup drinkToggleGroup;

    public CustomerPanelView() {
        this.stage = new Stage();
        this.fxml = "customer_panel.fxml";
        this.css = "customer_panel.css";
        this.title = "VMCs - Customer Panel";
        this.drinkToggleGroup = new ToggleGroup();
    }


    public void setDrinkToggleGroup(ToggleGroup drinkToggleGroup) {
        this.drinkToggleGroup = drinkToggleGroup;
    }

    public ToggleGroup getDrinkToggleGroup() {
        return this.drinkToggleGroup;
    }

}
