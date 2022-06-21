package handler;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import main.Start;
import view.CustomerPanelView;

public abstract class BaseController {

    public abstract void handleButtonAction(ActionEvent actionEvent);

    protected void setSuccessLabel(Label label) {
        label.getStyleClass().remove("failureLabel");
        label.getStyleClass().remove("originLabel");
        if (!label.getStyleClass().contains("successLabel")) label.getStyleClass().add("successLabel");
    }

    protected void setFailureLabel(Label label) {
        label.getStyleClass().remove("successLabel");
        label.getStyleClass().remove("originLabel");
        if (!label.getStyleClass().contains("failureLabel")) label.getStyleClass().add("failureLabel");
    }

    protected void setDefaultLabel(Label label) {
        label.getStyleClass().remove("successLabel");
        label.getStyleClass().remove("failureLabel");
        if (!label.getStyleClass().contains("originLabel")) label.getStyleClass().add("originLabel");
    }

    protected void setRadioButton(RadioButton radioButton, ToggleGroup toggleGroup) {
        radioButton.getStyleClass().remove("radio-button");
        radioButton.getStyleClass().add("toggle-button");
        radioButton.setToggleGroup(toggleGroup);
    }

    protected String getUniqueId(String type, int index, String suffix) {
        return String.join("-", type, String.valueOf(index), suffix);
    }

    protected int getSlotIndexByFXId(String FXId) {
        String[] valueList = FXId.split("-");
        return Integer.parseInt(valueList[1]);
    }
}
