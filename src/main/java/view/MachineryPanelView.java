package view;

import controller.CustomerPanelController;
import controller.MachineryPanelController;
import controller.MaintainerPanelController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Start;
import objects.Coin;
import objects.Door;
import objects.Slot;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class MachineryPanelView extends BaseView implements Initializable {

    @FXML
    public VBox coinMenuVBox;
    @FXML
    public VBox drinkMenuVBox;
    @FXML
    public Label coinWarning;
    @FXML
    public Label drinkWarning;
    @FXML
    public CheckBox doorLock;

    private MachineryPanelController controller;

    public MachineryPanelView() {
        this.stage = new Stage();
        this.fxml = "machinery_panel.fxml";
        this.css = "machinery_panel.css";
        this.title = "VMCs - Machinery Panel";
        this.controller = (MachineryPanelController) Start.getController(Start.ControllerType.MACHINERY_PANEL_CONTROLLER);
    }

    public MachineryPanelView getHandler() {
        return (MachineryPanelView) viewHandler;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coinWarning.setVisible(false);
        drinkWarning.setVisible(false);
        initCoinMenuVBox();
        initSlotMenuVBox();
        initDoorLock();
    }

    private void initCoinMenuVBox() {
        Map<String, Coin> coinMap = controller.getCoinIdMap();

        for(String coinId : coinMap.keySet()) {
            Coin coin = coinMap.get(coinId);

            BorderPane coinRow = new BorderPane();
            coinRow.setId(getUniqueId("coin", coinId, ""));
            addStyleClass(coinRow, "coinRow");

            Label coinLabel = new Label(coin.getName());
            coinLabel.setId(coin.getName());
            addStyleClass(coinLabel, "coinLabel");

            TextField coinQuantity = new TextField(Integer.toString(coin.getTotalQuantity()));
            coinQuantity.setId(getUniqueId("coin", coinId, "quantity"));
            addStyleClass(coinQuantity, "coinQuantity");

            coinQuantity.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() != KeyCode.ENTER) return;
                    TextField field = (TextField) keyEvent.getSource();
                    try{
                        int newQuantity = Integer.parseInt(field.getText());
                        boolean ret = controller.handleChangeCoinQuantity(coin, newQuantity);
                        if(ret) {
                            createAlert(Alert.AlertType.INFORMATION, "Successful To Change The Number!");
                            coinWarning.setVisible(false);
                        } else {
                            coinWarning.setVisible(true);
                        }
                    } catch (NumberFormatException e){
                        coinWarning.setVisible(true);
                    }
                }
            });

            addTextChangeHandler(coinQuantity, coinWarning);
            coinRow.setLeft(coinLabel);
            coinRow.setRight(coinQuantity);
            coinMenuVBox.getChildren().add(coinRow);
        }
    }

    private void initSlotMenuVBox() {
        Map<String, Slot> slotIdMap = controller.getSlotIdMap();

        for(String slotId: slotIdMap.keySet()){
            Slot slot = slotIdMap.get(slotId);

            BorderPane slotRow = new BorderPane();
            slotRow.setId(getUniqueId("slot", slotId, ""));
            addStyleClass(slotRow, "slotRow");

            Label slotLabel = new Label(slot.getName());
            slotLabel.setId(getUniqueId("slot", slotId, "label"));
            addStyleClass(slotLabel, "slotLabel");

            TextField slotQuantity = new TextField(Integer.toString(slot.getQuantity()));
            slotQuantity.setId(getUniqueId("slot", slotId, "quantity"));
            addStyleClass(slotQuantity, "slotQuantity");

            slotQuantity.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() != KeyCode.ENTER) return;
                    TextField field = (TextField) keyEvent.getSource();

                    try{
                        int newQuantity = Integer.parseInt(field.getText());
                        boolean ret = controller.handleChangeSlotQuantity(slot, newQuantity);
                        if(ret) {
                            createAlert(Alert.AlertType.INFORMATION, "Successful To Change The Number!");
                            drinkWarning.setVisible(false);
                        } else {
                            drinkWarning.setVisible(true);
                        }
                    } catch (NumberFormatException e){
                        drinkWarning.setVisible(true);
                    }
                }
            });

            addTextChangeHandler(slotQuantity, drinkWarning);
            slotRow.setLeft(slotLabel);
            slotRow.setRight(slotQuantity);
            drinkMenuVBox.getChildren().add(slotRow);
        }
    }

    private void addTextChangeHandler(TextField quantityField, Label warning) {
        quantityField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^\\d*$")){
                    quantityField.setText(oldValue);
                    warning.setVisible(true);
                } else {
                    warning.setVisible(false);
                }
            }
        });
    }

    private void initDoorLock() {
        Door door = controller.getDoor();
        boolean isDoorLocked = !door.isOpen();
        if (isDoorLocked) {
            lockPanel();
        } else {
            unlockPanel();
        }
        doorLock.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                controller.handleChangeDoorStatus(!newValue);
            }
        });
    }

    public void lockPanel() {
        doorLock.setSelected(true);

        coinMenuVBox.setDisable(true);
        drinkMenuVBox.setDisable(true);
    }

    public void unlockPanel() {
        doorLock.setSelected(false);

        coinMenuVBox.setDisable(false);
        drinkMenuVBox.setDisable(false);
    }

    public void refreshCoinQuantity(String coinId, int coinQuantity) {
        TextField coinField = (TextField) stage.getScene().lookup(
                "#" + getUniqueId("coin", coinId, "quantity")
        );
        coinField.setText(String.valueOf(coinQuantity));
    }

    public void refreshSlotQuantity(String slotId, int slotQuantity) {
        TextField slotField = (TextField) stage.getScene().lookup(
                "#" + getUniqueId("slot", slotId, "quantity")
        );
        slotField.setText(String.valueOf(slotQuantity));
    }

}
