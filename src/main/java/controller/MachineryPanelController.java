package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import main.Start;
import objects.Coin;
import objects.Door;
import objects.Slot;
import observer.*;
import view.MachineryPanelView;

import java.net.URL;
import java.util.ResourceBundle;

public class MachineryPanelController extends BaseController
        implements Initializable, InterfaceCoinObserver, InterfaceSlotObserver, InterfaceDoorObserver {

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MachineryPanelView view = (MachineryPanelView) Start.getView(Start.ViewType.MACHINERY_PANEL_VIEW);
        coinWarning.setVisible(false);
        drinkWarning.setVisible(false);
        initCoinMenuVBox(view);
        initDrinkMenuVBox(view);
        initDoorLock();
    }



    private void initCoinMenuVBox(MachineryPanelView view) {
        for(Coin coin : Start.getMachine().getCoins()) {
            BorderPane coinRow = new BorderPane();
            coinRow.setId(getUniqueId("coin", coin.getName(), ""));
            addStyleClass(coinRow, "coinRow");

            Label coinLabel = new Label(coin.getName());
            coinLabel.setId(coin.getName());
            addStyleClass(coinLabel, "coinLabel");

            TextField coinQuantity = new TextField(Integer.toString(coin.getTotalQuantity()));
            coinQuantity.setId(getUniqueId("coin", coin.getName(), "quantity"));
            addStyleClass(coinQuantity, "coinQuantity");

            coinQuantity.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() != KeyCode.ENTER) return;
                    TextField field = (TextField) keyEvent.getSource();
                    updateCoinQuantity(view, coin, field.getText());
                }
            });

            addTextChangeHandler(coinQuantity, coinWarning);
            coinRow.setLeft(coinLabel);
            coinRow.setRight(coinQuantity);
            coinMenuVBox.getChildren().add(coinRow);

            registerObserver(coin, this);
        }
    }


    private void initDrinkMenuVBox(MachineryPanelView view) {
        for(Slot slot: Start.getMachine().getSlots()){
            int slotId = slot.getId();

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
                    updateSlotQuantity(view, slot, field.getText());
                }
            });

            addTextChangeHandler(slotQuantity, drinkWarning);
            slotRow.setLeft(slotLabel);
            slotRow.setRight(slotQuantity);
            drinkMenuVBox.getChildren().add(slotRow);

            registerObserver(slot, this);
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
        Door door = Start.getMachine().getDoor();
        boolean isDoorLocked = !door.isOpen();
        doorLock.setSelected(isDoorLocked);
        if (isDoorLocked) {
            lockPanel();
        } else {
            unlockPanel();
        }
        doorLock.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                Start.getMachine().getDoor().setDoorStatus(!newValue);
//                if (newValue) {
//                    lockPanel();
//                } else {
//                    unlockPanel();
//                }
            }
        });

        registerObserver(door, this);
    }

    private void lockPanel() {
        coinMenuVBox.setDisable(true);
        drinkMenuVBox.setDisable(true);
    }

    private void unlockPanel() {
        coinMenuVBox.setDisable(false);
        drinkMenuVBox.setDisable(false);
    }

    private void updateCoinQuantity(MachineryPanelView view, Coin coin, String newQuantityString) {
        try{
            coin.saveCurrentEnteredValue();
            int newQuantity = Integer.parseInt(newQuantityString);
            if(newQuantity >= 0 && newQuantity <= 40) {
                coin.setQuantity(newQuantity);
                view.createAlert(Alert.AlertType.INFORMATION, "Successful To Change The Number!");
            } else {
                coinWarning.setVisible(true);
                return;
            }
        } catch (NumberFormatException e){
            coinWarning.setVisible(true);
            return;
        }
        coinWarning.setVisible(false);
    }

    private void updateSlotQuantity(MachineryPanelView view, Slot slot, String newQuantityString) {
        try{
            int newQuantity = Integer.parseInt(newQuantityString);
            if(newQuantity >= 0 && newQuantity <= 20) {
                slot.setQuantity(newQuantity);
                view.createAlert(Alert.AlertType.INFORMATION, "Successful To Change The Number!");
            } else {
                drinkWarning.setVisible(true);
                return;
            }
        } catch (NumberFormatException e){
            drinkWarning.setVisible(true);
            return;
        }
        drinkWarning.setVisible(false);
    }

    private void refreshCoinQuantity(Coin coin) {
        MachineryPanelView view = (MachineryPanelView) Start.getView(Start.ViewType.MACHINERY_PANEL_VIEW);
        TextField coinField = (TextField) view.getStage().getScene().lookup(
                "#" + getUniqueId("coin", coin.getName(), "quantity")
        );
        coinField.setText(String.valueOf(coin.getTotalQuantity()));
    }

    private void refreshSlotQuantity(Slot slot) {
        MachineryPanelView view = (MachineryPanelView) Start.getView(Start.ViewType.MACHINERY_PANEL_VIEW);
        TextField slotField = (TextField) view.getStage().getScene().lookup(
                "#" + getUniqueId("slot", slot.getId(), "quantity")
        );
        slotField.setText(String.valueOf(slot.getQuantity()));
    }

    @Override
    public void updateCoin(CoinObservable coin, Object arg) {
        switch (((CoinObservable.CoinObserverType) arg)) {
            case CURRENT_ENTERED_QUANTITY:
            case QUANTITY:
            case TOTAL_QUANTITY:
                refreshCoinQuantity((Coin) coin);
                break;
        }
    }

    @Override
    public void updateDoor(DoorObservable door, Object arg) {
        if (((Door) door).isOpen()) {
            unlockPanel();
            doorLock.setSelected(false);
        } else {
            lockPanel();
            doorLock.setSelected(true);
        }
    }

    @Override
    public void updateSlot(SlotObservable slot, Object arg) {
        switch ((SlotObservable.SlotObserverType) arg) {
            case QUANTITY:
                refreshSlotQuantity((Slot) slot);
                break;
        }
    }
}
