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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class CustomerPanelController extends BaseController implements InterfaceObserver {

    private CustomerPanelView view;

    public CustomerPanelController() {
        this.view = (CustomerPanelView) Start.getView(Start.ViewType.CUSTOMER_PANEL_VIEW);

        registerObserver(Start.getMachine(), this);

        for(Coin coin: Start.getMachine().getCoins()) {
            registerObserver(coin, this);
        }

        for(Slot slot: Start.getMachine().getSlots()) {
            registerObserver(slot, this);
        }
    }

    public void handleInvalidCoin() {
        view.getHandler().highlightEnterStatusLabel();
        view.getHandler().refundCoin("Invalid Coin");
    }

    private int computeRefundCoin(int requiredMoney, List<Coin> coins) {
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
        return refundMoney;
    }

    public boolean purchaseDrink(String slotId) {
        Slot selectedSlot = Start.getMachine().getSlotById(Integer.parseInt(slotId)).get();
        String moneyType = Start.getMachine().getMoneyType();

        int currentEnteredMoney = Start.getMachine().getCurrentEnteredMoney();
        int drinkPrice = selectedSlot.getPrice();

        if (currentEnteredMoney >= drinkPrice) {
            // save money
            Start.getMachine().saveCurrentMoney();

            // dispense drink
            selectedSlot.setQuantity(selectedSlot.getQuantity() - 1);
            view.getHandler().dispenseDrink(selectedSlot.getName());

            // refund money
            int requiredMoney = currentEnteredMoney - drinkPrice;
            int refundMoney = computeRefundCoin(
                    requiredMoney, Start.getMachine().getCoins());
            view.getHandler().refundCoin(refundMoney + " " + moneyType);
            if (refundMoney < requiredMoney) view.getHandler().highlightNoChangeAvaLabel();

            return true;
        }
        return false;
    }

    public void handleNormalCoin(String coinId) {
        Coin coin = Start.getMachine().getCoinByName(coinId).get();
        coin.enterCoin(1);
    }

    public void handleTerminateTransaction() {
        int currentEnteredMoney = Start.getMachine().collectCurrentEnteredCash();
        String moneyType = Start.getMachine().getMoneyType();
        view.getHandler().refundCoin(currentEnteredMoney + " " + moneyType);
    }

    public void updateCoin(Observable coinObservable, Object arg) {
        String moneyType = Start.getMachine().getMoneyType();
        if (view.getHandler() == null) return;
        switch (((Coin.CoinObserverType) arg)) {
            case CURRENT_ENTERED_QUANTITY:
            case TOTAL_QUANTITY:
                view.getHandler().refreshCurrentEnteredCash(Start.getMachine().getCurrentEnteredMoney() + " " + moneyType);
                break;
            case QUANTITY:
//                refreshCash((Coin) coin);
                break;
            case WEIGHT:
                break;
        }
    }

    public void updateSlot(Observable slotObservable, Object arg) {
        String moneyType = Start.getMachine().getMoneyType();
        Slot slot = (Slot) slotObservable;
        if (view.getHandler() == null) return;
        switch ((Slot.SlotObserverType) arg) {
            case PRICE:
                view.getHandler().refreshSlotPrice(slot.getId(), slot.getPrice() + " " + moneyType);
                break;
            case QUANTITY:
                view.getHandler().refreshSlotQuantity(slot.getId(), slot.getQuantity());
                break;
        }
    }

    public void updateAuthorization(Observable authorizationObservable, Object arg) {
        Machine machine =  (Machine) authorizationObservable;
        if (view.getHandler() == null) return;
        if (machine.getAuthorization()) {
            handleTerminateTransaction();
            view.getHandler().lockPanel();
        } else {
            view.getHandler().unlockPanel();
        }
    }

    @Override
    public void update(Observable observable, ObserverType observerType, Object arg) {
        switch (observerType) {
            case COIN:
                updateCoin(observable, arg);
                break;
            case SLOT:
                updateSlot(observable, arg);
                break;
            case AUTHORIZATION:
                updateAuthorization(observable, arg);
                break;
        }
    }
}
