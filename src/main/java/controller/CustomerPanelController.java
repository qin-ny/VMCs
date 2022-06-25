package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Start;
import objects.Coin;
import objects.Machine;
import objects.Slot;
import observer.*;
import view.CustomerPanelView;
import view.MaintainerPanelView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerPanelController extends BaseController
        implements Initializable, InterfaceCoinObserver, InterfaceSlotObserver, InterfaceAuthorizationObserver {
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
    @FXML
    public HBox centerVBox;
    @FXML
    public BorderPane bottomBPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CustomerPanelView view = (CustomerPanelView) Start.getView(Start.ViewType.CUSTOMER_PANEL_VIEW);

        initCoinMenuHBox();
        initDrinkMenuVBox(view);
        registerObserver(Start.getMachine(), this);
    }

    private void initCoinMenuHBox() {

        for(Coin coin :Start.getMachine().getCoins()) {
            Button coinButton = new Button(coin.getName());
            coinButton.setId(coin.getName());
            addStyleClass(coinButton, "coinButton");
            coinButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent actionEvent) {
                    handleButtonAction(actionEvent);
                }

            });
            coinMenuHBox.getChildren().add(0, coinButton);

            registerObserver(coin, this);
        }
    }

    private void initDrinkMenuVBox(CustomerPanelView view) {
        List<Slot> slots = Start.getMachine().getSlots();
        String moneyType = Start.getMachine().getMoneyType();
        ToggleGroup toggleGroup = view.getDrinkToggleGroup();
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oValue, Toggle nValue) {
                if (nValue == null) return;
                purchaseDrink(view, (RadioButton) nValue);
            }
        });
        for (Slot slot: slots) {
            BorderPane drinkBPane = new BorderPane();
            addStyleClass(drinkBPane, "drinkBPane");

            RadioButton radioButton = new RadioButton(slot.getName());
            int slotId = slot.getId();
            radioButton.setId(getUniqueId("slot", slotId, "button"));
            setRadioButton(radioButton, toggleGroup);
            drinkBPane.setLeft(radioButton);

            Label priceLabel = new Label(slot.getPrice() + " " + moneyType);
            priceLabel.setId(getUniqueId("slot", slotId, "price"));
            addStyleClass(priceLabel, "drinkPriceLabel");
            drinkBPane.setCenter(priceLabel);

            Label stockLabel = new Label();
            stockLabel.setId(getUniqueId("slot", slotId, "stock"));
            if (slot.getQuantity() <= 0) {
                disableSlot(radioButton, stockLabel);
            } else {
                activateSlot(radioButton, stockLabel);
            }
            drinkBPane.setRight(stockLabel);
            drinkMenuVBox.getChildren().add(drinkBPane);

            registerObserver(slot, this);
        }
//        view.setDrinkToggleGroup(toggleGroup);
//        ((CustomerPanelView) Start.views.get("customerPanelView")).setDrinkToggleGroup(toggleGroup);
    }

    // return the final quantity of specified slot
    private int dispenseDrink(Slot slot) {
//        System.out.println(slot.getQuantity());
        slot.setQuantity(slot.getQuantity() - 1);
        collectCanLabel.setText(slot.getName());
        return  slot.getQuantity();
//        System.out.println(Start.jsonMachineConverter.machine.getSlotByName(slot.getDrink().getName()).get().getQuantity());
    }

    private void refundCoin(String coinString) {
        collectCoinLabel.setText(coinString);
    }

    private void handleInvalidCoin(CustomerPanelView view, Button button) {
        Toggle selectedToggle = view.getDrinkToggleGroup().getSelectedToggle();
        if (selectedToggle == null) {
            return;
        }
        setFailureLabel(enterStatusLabel);
        refundCoin("Invalid Coin");
    }

    private void computeRefundCoin(int requiredMoney, String moneyType, List<Coin> coins) {
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

        refundCoin(refundMoney + " " + moneyType);
    }

    private void unFocusSlot(CustomerPanelView view, RadioButton selectedSlotButton) {
        selectedSlotButton.setSelected(false);
    }

    private void purchaseDrink(CustomerPanelView view, RadioButton selectedSlotButton) {
        String moneyType = Start.getMachine().getMoneyType();
        int slotIndex = getSlotIdByUniqueId(selectedSlotButton.getId());
        Slot selectedSlot = Start.getMachine().getSlotById(slotIndex).get();

        int currentEnteredMoney = Start.getMachine().getCurrentEnteredMoney();
        int drinkPrice = selectedSlot.getPrice();

        if (currentEnteredMoney >= drinkPrice) {
            dispenseDrink(selectedSlot);

            unFocusSlot(view, selectedSlotButton);

            Start.getMachine().saveCurrentMoney();
            currentEnteredMoney -= drinkPrice;

            computeRefundCoin(currentEnteredMoney, moneyType, Start.getMachine().getCoins());
        }
    }

    // valueList = [slotName, index, suffix]
    private void handleNormalCoin(CustomerPanelView view, Button button) {
        Toggle selectedToggle = view.getDrinkToggleGroup().getSelectedToggle();
        if (selectedToggle == null) {
            return;
        }

        setSuccessLabel(enterStatusLabel);

        RadioButton selectedSlotButton = (RadioButton) selectedToggle;

        Coin coin = Start.getMachine().getCoinByName(button.getText()).get();
        coin.enterCoin(1);
//        Start.jsonMachineConverter.machine.addCurrentMoney(coin);
        purchaseDrink(view, selectedSlotButton);
    }

    private void handleTerminateTransaction(CustomerPanelView view) {
        Toggle toggle = view.getDrinkToggleGroup().getSelectedToggle();
        if (toggle != null) {
            toggle.setSelected(false);
        }
        int currentEnteredMoney = Start.getMachine().collectCurrentEnteredCash();
        String moneyType = Start.getMachine().getMoneyType();
//        totalMoneyLabel.setText("0 " + moneyType);
        collectCoinLabel.setText(currentEnteredMoney + " " + moneyType);
    }

    private void disableSlot(RadioButton radioButton, Label stockLabel) {
        radioButton.setDisable(true);
        radioButton.getStyleClass().remove("radioButton");
        addStyleClass(radioButton, "unFocusRadioButton");

        stockLabel.setText("Not In Stock");
        setFailureLabel(stockLabel);
    }

    private void activateSlot(RadioButton radioButton, Label stockLabel) {
        radioButton.setDisable(false);
        radioButton.getStyleClass().remove("unFocusRadioButton");
        addStyleClass(radioButton, "radioButton");

        stockLabel.setText("In Stock");
        setSuccessLabel(stockLabel);
    }

    public void handleButtonAction(ActionEvent actionEvent) {
        CustomerPanelView view = (CustomerPanelView) Start.getView(Start.ViewType.CUSTOMER_PANEL_VIEW);
        Button button = (Button)actionEvent.getSource();

        switch (button.getId()) {
            case "invalidCoin":
                handleInvalidCoin(view, button);
                break;
            case "terminateButton":
                handleTerminateTransaction(view);
                break;
            default:
                handleNormalCoin(view, button);
                break;
        }
    }

    private void refreshCurrentEnteredCash(Coin coin) {
        String moneyType = Start.getMachine().getMoneyType();
        totalMoneyLabel.setText(coin.getCurrentEnteredTotalValue() + moneyType);
    }

    private void refreshCash(Coin coin) {
        return;
    }

    private void refreshTotalCash(Coin coin) {
        refreshCash(coin);
        refreshCurrentEnteredCash(coin);
    }

    private void refreshSlotPrice(Slot slot) {
        CustomerPanelView view = (CustomerPanelView) Start.getView(Start.ViewType.CUSTOMER_PANEL_VIEW);
        Label slotPriceLabel = (Label) view.getStage().getScene().lookup(
                "#" + getUniqueId("slot", slot.getId(), "price")
        );
        slotPriceLabel.setText(String.valueOf(slot.getPrice()));
    }

    private void refreshSlotQuantity(Slot slot, CustomerPanelView view) {
        Label slotStockLabel = (Label) view.getStage().getScene().lookup(
                "#" + getUniqueId("slot", slot.getId(), "stock")
        );
        RadioButton slotButton = (RadioButton)  view.getStage().getScene().lookup(
                "#" + getUniqueId("slot", slot.getId(), "button")
        );

        if (slot.getQuantity() <= 0) {
            slotButton.setSelected(false);
            disableSlot(slotButton, slotStockLabel);
        } else {
            activateSlot(slotButton, slotStockLabel);
        }
    }

    private void lockPanel(CustomerPanelView view) {
        handleTerminateTransaction(view);

        centerVBox.setDisable(true);
        bottomBPane.setDisable(true);
    }

    private void unlockPanel(CustomerPanelView view) {
        centerVBox.setDisable(false);
        bottomBPane.setDisable(false);
    }

    @Override
    public void updateCoin(CoinObservable coin, Object arg) {
        switch (((CoinObservable.CoinObserverType) arg)) {
            case CURRENT_ENTERED_QUANTITY:
                refreshCurrentEnteredCash((Coin) coin);
                break;
            case QUANTITY:
//                refreshCash((Coin) coin);
                break;
            case TOTAL_QUANTITY:
                refreshTotalCash((Coin) coin);
                break;
            case WEIGHT:
                break;
        }
    }

    @Override
    public void updateSlot(SlotObservable slot, Object arg) {
        CustomerPanelView view = (CustomerPanelView) Start.getView(Start.ViewType.CUSTOMER_PANEL_VIEW);
        switch ((SlotObservable.SlotObserverType) arg) {
            case PRICE:
                refreshSlotPrice((Slot) slot);
                break;
            case QUANTITY:
                refreshSlotQuantity((Slot) slot, view);
                break;
        }
    }

    @Override
    public void updateAuthorization(AuthorizationObservable authorization, Object arg) {
        CustomerPanelView view = (CustomerPanelView) Start.getView(Start.ViewType.CUSTOMER_PANEL_VIEW);
        Machine machine =  (Machine) authorization;
        if (machine.getAuthorization()) {
            lockPanel(view);
        } else {
            unlockPanel(view);
        }
    }
}
