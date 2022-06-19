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
        label.getStyleClass().add("successLabel");
        label.getStyleClass().remove("failureLabel");
    }

    protected void setFailureLabel(Label label) {
        label.getStyleClass().add("failureLabel");
        label.getStyleClass().remove("successLabel");
    }

    protected void setRadioButton(RadioButton radioButton, ToggleGroup toggleGroup) {
        radioButton.getStyleClass().remove("radio-button");
        radioButton.getStyleClass().add("toggle-button");
        radioButton.setToggleGroup(toggleGroup);
    }
}
