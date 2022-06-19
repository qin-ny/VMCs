package handler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Start;
import objects.Coin;
import objects.Slot;

import java.net.URL;
import java.util.ResourceBundle;

public class MachineryPanelController implements Initializable {

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
        coinWarning.setVisible(false);
        drinkWarning.setVisible(false);
        initCoinMenuVBox();
        initDrinkMenuVBox();
        initDoorLock();
    }



    private void initCoinMenuVBox() {
        int index = 0;
        for(Coin coin : Start.jsonMachineConverter.machine.getCoins()) {
            BorderPane coinRow = new BorderPane();
            coinRow.setId(coin.getName());
            coinRow.getStyleClass().add("coinRow");

            Label coinLabel = new Label(coin.getName());
            coinLabel.setId(coin.getName());
            coinLabel.getStyleClass().add("coinLabel");

            TextField coinQuantity = new TextField(Integer.toString(coin.getQuantity()));
            coinQuantity.setId(String.join(";", coin.getName(), String.valueOf(index)));
            coinQuantity.getStyleClass().add("coinQuantity");

            // force the field to be numeric only
            coinQuantity.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue) {
                     if (!newValue.matches("/^[1-9][0-9]*$/")) {
                        coinQuantity.setText(newValue.replaceAll("\\D", ""));
                    }
                     updateCoinQuantity(coin, newValue);
                }
            });

            coinRow.setLeft(coinLabel);
            coinRow.setRight(coinQuantity);
            coinMenuVBox.getChildren().add(coinRow);

            ++index;
        }
    }


    private void initDrinkMenuVBox() {
        int index = 0;
        for(Slot slot: Start.jsonMachineConverter.machine.getSlots()){
            BorderPane slotRow = new BorderPane();
            slotRow.setId(String.join(";", "slot", String.valueOf(index)));
            slotRow.getStyleClass().add("slotRow");

            Label slotLabel = new Label(slot.getDrink().getName());
            slotLabel.setId(slot.getDrink().getName());
            slotLabel.getStyleClass().add("slotLabel");

            TextField slotQuantity = new TextField(Integer.toString(slot.getQuantity()));
            slotQuantity.setId(String.join(";", slot.getDrink().getName(), String.valueOf(index)));
            slotQuantity.getStyleClass().add("slotQuantity");

            slotQuantity.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue) {
                    if (!newValue.matches("/^\\d+$/")) {
                        slotQuantity.setText(newValue.replaceAll("\\D", ""));
                    }
                    updateSlotQuantity(slot, newValue);
                }
            });

            slotRow.setLeft(slotLabel);
            slotRow.setRight(slotQuantity);
            drinkMenuVBox.getChildren().add(slotRow);

            ++index;
        }
    }

    private void initDoorLock() {
        boolean isDoorLocked = !Start.jsonMachineConverter.machine.getDoor().isOpen();
        doorLock.setSelected(isDoorLocked);
        coinMenuVBox.setDisable(isDoorLocked);
        drinkMenuVBox.setDisable(isDoorLocked);
        doorLock.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                Start.jsonMachineConverter.machine.getDoor().setDoorStatus(!newValue);
                coinMenuVBox.setDisable(newValue);
                drinkMenuVBox.setDisable(newValue);
            }
        });
    }


    private void updateCoinQuantity(Coin coin, String newQuantityString) {
        int newQuantity;
        try{
            newQuantity = Integer.parseInt(newQuantityString);
        } catch (NumberFormatException e){
            System.out.println("Invalid integer parsing " + e.getMessage());
            return;
        }
        if(newQuantity >= 0 && newQuantity <= 40){
            coin.setQuantity(newQuantity);
            coinWarning.setVisible(false);
        } else {
            coinWarning.setVisible(true);
        }
    }

    private void updateSlotQuantity(Slot slot, String newQuantityString) {
        int newQuantity;
        try{
            newQuantity = Integer.parseInt(newQuantityString);
        } catch (NumberFormatException e){
            System.out.println("Invalid integer parsing " + e.getMessage());
            return;
        }
        if(newQuantity >= 0 && newQuantity <= 20){
            slot.setQuantity(newQuantity);
            drinkWarning.setVisible(false);
        } else {
            drinkWarning.setVisible(true);
        }
    }




}
