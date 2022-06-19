package handler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Start;
import objects.Coin;
import objects.Drink;
import objects.Slot;
import view.CustomerPanelView;
import view.SimulatorControlPanelView;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class CustomerPanelController implements Initializable {
    @FXML
    public HBox coinMenuHBox;
    @FXML
    public Label enterStatusLabel;
    @FXML
    public VBox drinkMenuVBox;
    @FXML
    public Label noChangeAvaLabel;
    @FXML
    public Button terminateButton;
    @FXML
    public Label collectCanLabel;
    @FXML
    public Label collectCoinLabel;
    @FXML
    public Label totalMoneyLabel;
    @FXML
    public Button invalidCoin;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCoinMenuHBox();
        initDrinkMenuVBox();
    }

    private void initCoinMenuHBox() {

        for(Coin coin :Start.jsonMachineConverter.machine.getCoins()) {
            Button coinButton = new Button(coin.getName());
            coinButton.setId(coin.getName());
            coinButton.getStyleClass().add("coinButton");
            coinButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent actionEvent) {
                    handleButtonAction(actionEvent);
                }

            });
            coinMenuHBox.getChildren().add(0, coinButton);

        }
    }

    private void initDrinkMenuVBox() {
        List<Slot> slots = Start.jsonMachineConverter.machine.getSlots();
        String moneyType = Start.jsonMachineConverter.machine.getMoneyType();
        ToggleGroup toggleGroup = ((CustomerPanelView) Start.views.get("customerPanelView")).getDrinkToggleGroup();
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oValue, Toggle nValue) {
                CustomerPanelView view = (CustomerPanelView) Start.views.get("customerPanelView");
                purchaseDrink(view, (RadioButton) nValue);
//                System.out.println(nValue.toString());
//                handleToggleSelection(observableValue, oValue, nValue, slots);
            }
        });
        int index = 0;
        for (Slot slot: slots) {
            BorderPane drinkBPane = new BorderPane();
            drinkBPane.setId(String.join(";", slot.getDrink().getName(), String.valueOf(index)));
            drinkBPane.getStyleClass().add("drinkBPane");

            RadioButton radioButton = new RadioButton(slot.getDrink().getName());
            radioButton.setId(String.join(";", slot.getDrink().getName(), String.valueOf(index), "button"));
            radioButton.getStyleClass().remove("radio-button");
            radioButton.getStyleClass().add("toggle-button");
            drinkBPane.setLeft(radioButton);
            radioButton.setToggleGroup(toggleGroup);

            Label priceLabel = new Label(slot.getDrink().getPrice() + " " + moneyType);
            priceLabel.setId(String.join(";", slot.getDrink().getName(), String.valueOf(index), "price"));
            priceLabel.getStyleClass().add("drinkPriceLabel");
            drinkBPane.setCenter(priceLabel);

            Label stockLabel = new Label();
            stockLabel.setId(String.join(";", slot.getDrink().getName(), String.valueOf(index), "stock"));
            if (slot.getQuantity() <= 0) {
                disableSlot(radioButton, stockLabel);
            } else {
                activateSlot(radioButton, stockLabel);
            }
            drinkBPane.setRight(stockLabel);
            drinkMenuVBox.getChildren().add(drinkBPane);

            index++;
        }

        ((CustomerPanelView) Start.views.get("customerPanelView")).setDrinkToggleGroup(toggleGroup);
    }

    private void setSuccessLabel(Label label) {
        label.getStyleClass().add("successLabel");
        label.getStyleClass().remove("failureLabel");
    }

    public void setFailureLabel(Label label) {
        label.getStyleClass().add("failureLabel");
        label.getStyleClass().remove("successLabel");
    }

    // return the final quantity of specified slot
    private int popDrink(Slot slot) {
//        System.out.println(slot.getQuantity());
        slot.setQuantity(slot.getQuantity() - 1);
        collectCanLabel.setText(slot.getDrink().getName());
        return  slot.getQuantity();
//        System.out.println(Start.jsonMachineConverter.machine.getSlotByName(slot.getDrink().getName()).get().getQuantity());
    }

    private void handleInvalidCoin(CustomerPanelView view, Button button) {
        view.setFailureLabel(enterStatusLabel);
    }

    private void refundMoney(int requiredMoney, String moneyType, List<Coin> coins) {
        int refundMoney = 0;
        for (Coin coin: coins) {
//            System.out.println(coin.getWeight());
            int currentTotalCoinMoney = coin.getTotalValue();
            if (currentTotalCoinMoney <= requiredMoney) {
                refundMoney += currentTotalCoinMoney;
                requiredMoney -= currentTotalCoinMoney;
                coin.setQuantity(0);
            } else {
                int coinWeight = coin.getWeight();
                int coinNum = coin.getQuantity();
                int currentRefundCoinNum = requiredMoney / coinWeight;
                int currentRefundMoney = currentRefundCoinNum * coinWeight;
                refundMoney += currentRefundMoney;
                requiredMoney -= currentRefundMoney;
                coin.setQuantity(coinNum - currentRefundCoinNum);
            }
        }
        if (requiredMoney > 0) {
            setFailureLabel(noChangeAvaLabel);
        }
        collectCoinLabel.setText(refundMoney + " " + moneyType);
    }

    private void purchaseDrink(CustomerPanelView view, RadioButton selectedSlotButton) {
        String moneyType = Start.jsonMachineConverter.machine.getMoneyType();
        String[] valueList = selectedSlotButton.getId().split(";");
        Slot selectedSlot = Start.jsonMachineConverter.machine.getSlotByIndex(valueList[1]).get();
        int currentEnteredMoney = Start.jsonMachineConverter.machine.getCurrentEnteredMoney();
        int drinkPrice = selectedSlot.getDrink().getPrice();

//        Label testStockLabel = (Label) view.getStage().getScene().lookup(
//                "#" + String.join(
//                        ";",
//                        selectedSlot.getDrink().getName(),
//                        valueList[1],
//                        "stock")
//        );
//        System.out.println(testStockLabel);

        if (currentEnteredMoney >= drinkPrice) {
            int slotFinalQuantity = popDrink(selectedSlot);
            Start.jsonMachineConverter.machine.saveCurrentMoney();
            currentEnteredMoney -= drinkPrice;

            if (slotFinalQuantity <= 0) {
                Label slotStockLabel = (Label) view.getStage().getScene().lookup(
                        "#" + String.join(
                                ";",
                                selectedSlot.getDrink().getName(),
                                valueList[1],
                                "stock")
                );
                disableSlot(selectedSlotButton, slotStockLabel);
            }

            refundMoney(currentEnteredMoney, moneyType, Start.jsonMachineConverter.machine.getCoins());
            totalMoneyLabel.setText("0 " + moneyType);
        } else {
            totalMoneyLabel.setText(currentEnteredMoney + " " + moneyType);
        }
    }

    // valueList = [slotName, index, suffix]
    private void handleNormalCoin(CustomerPanelView view, Button button) {
        Toggle selectedToggle = view.getDrinkToggleGroup().getSelectedToggle();
        if (selectedToggle == null) {
            return;
        }
        RadioButton selectedSlotButton = (RadioButton) selectedToggle;

        Coin coin = Start.jsonMachineConverter.machine.getCoinByName(button.getText()).get();
        Start.jsonMachineConverter.machine.addCurrentMoney(coin);
        purchaseDrink(view, selectedSlotButton);
    }

    private void handleTerminateTransaction(CustomerPanelView view, Button button) {
        int currentEnteredMoney = Start.jsonMachineConverter.machine.getCurrentEnteredMoney();
        String moneyType = Start.jsonMachineConverter.machine.getMoneyType();
        totalMoneyLabel.setText("0 " + moneyType);
        collectCoinLabel.setText(currentEnteredMoney + " " + moneyType);
    }

    private void disableSlot(RadioButton radioButton, Label stockLabel) {
        radioButton.setDisable(true);
        radioButton.getStyleClass().add("unFocusRadioButton");

        stockLabel.setText("Not In Stock");
        setFailureLabel(stockLabel);
        stockLabel.getStyleClass().add("failureLabel");
    }

    private void activateSlot(RadioButton radioButton, Label stockLabel) {
        radioButton.getStyleClass().add("drinkRadioButton");

        stockLabel.setText("In Stock");
        setSuccessLabel(stockLabel);
    }

    public void handleButtonAction(ActionEvent actionEvent) {
        CustomerPanelView view = (CustomerPanelView) Start.views.get("customerPanelView");
        Button button = (Button)actionEvent.getSource();

        switch (button.getId()) {
            case "invalidCoin":
                handleInvalidCoin(view, button);
            case "terminateButton":
                handleTerminateTransaction(view, button);
                break;
            default:
                handleNormalCoin(view, button);

        }

    }

//    public void handleToggleSelection(ObservableValue<? extends Toggle> observableValue, Toggle oValue, Toggle nValue, List<Slot> slots) {
//        RadioButton selectedButton = (RadioButton) nValue;
//        for (Slot slot: slots) {
//            if (slot.getDrink().getName().equals(selectedButton.getText())) {
//                CustomerPanelView view = (CustomerPanelView) Start.views.get("customerPanelView");
//                view.selectSlot(slot);
//                break;
//            }
//        }
//
//    }
}