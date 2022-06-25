package controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import objects.Coin;
import objects.Door;
import objects.Machine;
import objects.Slot;
import observer.*;

public abstract class BaseController {

    protected void registerObserver(Observable observable, InterfaceObserver observer) {
        observable.addObserver(observer);
    }

    protected void addStyleClass(Node node, String styleClass) {
        if (!node.getStyleClass().contains(styleClass)) node.getStyleClass().add(styleClass);
    }

    protected void setSuccessLabel(Label label) {
        label.getStyleClass().remove("failureLabel");
        label.getStyleClass().remove("originLabel");
        addStyleClass(label, "successLabel");
    }

    protected void setFailureLabel(Label label) {
        label.getStyleClass().remove("successLabel");
        label.getStyleClass().remove("originLabel");
        addStyleClass(label, "failureLabel");
    }

    protected void setDefaultLabel(Label label) {
        label.getStyleClass().remove("successLabel");
        label.getStyleClass().remove("failureLabel");
        addStyleClass(label, "originLabel");
    }

    protected void setRadioButton(RadioButton radioButton, ToggleGroup toggleGroup) {
        radioButton.getStyleClass().remove("radio-button");
        addStyleClass(radioButton, "toggle-button");
        radioButton.setToggleGroup(toggleGroup);
    }

    protected String getUniqueId(String type, String id, String suffix) {
        return String.join("-", type, String.valueOf(id), suffix);
    }

    protected String getUniqueId(String type, int id, String suffix) {
        return getUniqueId(type, String.valueOf(id), suffix);
    }

    protected int getSlotIdByUniqueId(String uniqueId) {
        String[] valueList = uniqueId.split("-");
        return Integer.parseInt(valueList[1]);
    }

    protected String getCoinNameByUniqueId(String uniqueId) {
        String[] valueList = uniqueId.split("-");
        return valueList[1];
    }
}
