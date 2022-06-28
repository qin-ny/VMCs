package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import main.Start;
import objects.Coin;
import objects.Slot;
import observer.CoinObservable;
import observer.InterfaceCoinObserver;
import observer.InterfaceSlotObserver;
import observer.SlotObservable;
import view.CustomerPanelView;
import view.MaintainerPanelView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MaintainerPanelController extends BaseController
        implements InterfaceSlotObserver, InterfaceCoinObserver {

    private MaintainerPanelView view;

    public MaintainerPanelController() {
        this.view = (MaintainerPanelView) Start.getView(Start.ViewType.MAINTAINER_PANEL_VIEW);

        for(Coin coin: Start.getMachine().getCoins()) {
            registerObserver(coin, this);
        }

        for(Slot slot: Start.getMachine().getSlots()) {
            registerObserver(slot, this);
        }
    }

    public boolean isAuthorization() {
        return Start.getMachine().getAuthorization();
    }

    public void handleShowTotalCash() {
        view.getHandler().displayTotalCash(
                Start.getMachine().getTotalCash() +
                " " +
                Start.getMachine().getMoneyType()
        );
    }

    public void handleCollectAllCash() {
        view.getHandler().collectCash(
                Start.getMachine().collectAllCash() +
                " " +
                Start.getMachine().getMoneyType()
        );
    }

    public boolean handleLogout() {
        if (!Start.getMachine().getAuthorization()) {
            return false;
        };
        if (!Start.getMachine().getDoor().isOpen()) {
            Start.getMachine().setAuthorization(false);
            return true;
        } else {
            view.getHandler().createAlert(Alert.AlertType.WARNING, "You Haven't Locked The Door!");
            return false;
        }
    }

    public boolean handleLogin(String passwd) {
        if (passwd.equals(Start.getMachine().getPassword())) {
            Start.getMachine().setAuthorization(true);
            view.getHandler().unlockPanel();
            Start.getMachine().getDoor().setDoorStatus(true);
            return true;
        }
        return false;
    }

    public void handleSlotPriceChange(String slotId, int nPrice) {
        Slot slot = Start.getMachine().getSlotById(Integer.parseInt(slotId)).get();
        slot.setPrice(nPrice);
        view.getHandler().createAlert(Alert.AlertType.INFORMATION, "Successful to Change The Price of " + slot.getName() + "!");
    }

    public void handleSelectCoin(String coinId) {
        view.getHandler().displayCoinInfo(Start.getMachine().getCoinByName(coinId).get().getTotalQuantity());
    }

    public void handleSelectSlot(String slotId) {
        Slot slot = Start.getMachine().getSlotById(Integer.parseInt(slotId)).get();
        view.getHandler().displaySlotInfo(
                slot.getQuantity(), slot.getPrice() + " " + Start.getMachine().getMoneyType());

    }

    @Override
    public void updateCoin(CoinObservable coinObservable, Object arg) {
        if (view.getHandler() == null) return;
        switch (((CoinObservable.CoinObserverType) arg)) {
            case CURRENT_ENTERED_QUANTITY:
            case QUANTITY:
            case TOTAL_QUANTITY:
                if (!Start.getMachine().getAuthorization()) return;
                String totalCash = Start.getMachine().getTotalCash() + " " + Start.getMachine().getMoneyType();
                Coin coin = (Coin) coinObservable;
                view.getHandler().refreshCoinQuantity(coin.getName(), totalCash, coin.getTotalQuantity());
                break;
        }
    }

    @Override
    public void updateSlot(SlotObservable slotObservable, Object arg) {
        Slot slot = (Slot) slotObservable;
        if (view.getHandler() == null) return;
        switch ((SlotObservable.SlotObserverType) arg) {
            case PRICE:
                view.getHandler().refreshSlotPrice(
                        String.valueOf(slot.getId()), slot.getPrice() + " " + Start.getMachine().getMoneyType());
                break;
            case QUANTITY:
                view.getHandler().refreshSlotQuantity(String.valueOf(slot.getId()), slot.getQuantity());
                break;
        }
    }
}
