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
import view.MaintainerPanelView;

import java.net.URL;
import java.util.ResourceBundle;

public class MachineryPanelController extends BaseController
        implements InterfaceCoinObserver, InterfaceSlotObserver, InterfaceDoorObserver {

    private MachineryPanelView view;

    public MachineryPanelController() {
        this.view = (MachineryPanelView) Start.getView(Start.ViewType.MACHINERY_PANEL_VIEW);

        for(Coin coin: Start.getMachine().getCoins()) {
            registerObserver(coin, this);
        }

        for(Slot slot: Start.getMachine().getSlots()) {
            registerObserver(slot, this);
        }

        Door door = Start.getMachine().getDoor();
        registerObserver(door, this);
    }


    public boolean handleChangeCoinQuantity(Coin coin, int newQuantity) {
        if(newQuantity >= 0 && newQuantity <= 40) {
            coin.saveCurrentEnteredValue();
            coin.setQuantity(newQuantity);
            return true;
        }
        return false;
    }

    public boolean handleChangeSlotQuantity(Slot slot, int newQuantity) {
        if(newQuantity >= 0 && newQuantity <= 20) {
            slot.setQuantity(newQuantity);
            return true;
        }
        return false;
    }

    public void handleChangeDoorStatus(boolean isOpen) {
        Start.getMachine().getDoor().setDoorStatus(isOpen);
    }

    @Override
    public void updateCoin(Observable coinObservable, Object arg) {
        Coin coin = (Coin) coinObservable;
        if (view.getHandler() == null) return;
        switch (((Coin.CoinObserverType) arg)) {
            case CURRENT_ENTERED_QUANTITY:
            case QUANTITY:
            case TOTAL_QUANTITY:
                view.getHandler().refreshCoinQuantity(coin.getName(), coin.getTotalQuantity());
                break;
        }
    }

    @Override
    public void updateDoor(Observable door, Object arg) {
        if (view.getHandler() == null) return;
        if (((Door) door).isOpen()) {
            view.getHandler().unlockPanel();
        } else {
            view.getHandler().lockPanel();
        }
    }

    @Override
    public void updateSlot(Observable slotObservable, Object arg) {
        Slot slot = (Slot) slotObservable;
        if (view.getHandler() == null) return;
        switch ((Slot.SlotObserverType) arg) {
            case QUANTITY:
                view.getHandler().refreshSlotQuantity(String.valueOf(slot.getId()), slot.getQuantity());
                break;
        }
    }
}
