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
import view.MaintainerPanelView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MaintainerPanelController extends BaseController
        implements Initializable, InterfaceSlotObserver, InterfaceCoinObserver {

    @FXML
    public PasswordField maintainerPasswdField;
    @FXML
    public Label validPasswdLabel;
    @FXML
    public Label invalidPasswdLabel;
    @FXML
    public VBox coinContentVBox;
    @FXML
    public VBox slotContentVBox;
    @FXML
    public Label availableCoinNumLabel;
    @FXML
    public Label availableSlotNumLabel;
    @FXML
    public Button collectAllCashButton;
    @FXML
    public TextField slotPriceTField;
    @FXML
    public Label showTotalCashLabel;
    @FXML
    public Label collectCashLabel;
    @FXML
    public Button logoutButton;
    @FXML
    public VBox centerVBox;
    @FXML
    public VBox bottomVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MaintainerPanelView view = (MaintainerPanelView) Start.getView(Start.ViewType.MAINTAINER_PANEL_VIEW);
        initCoinContent(view);
        initSlotContent(view);
        if (!Start.getMachine().getAuthorization()) {
            lockPanel();
        }
    }

    private void initCoinContent(MaintainerPanelView view) {
        ToggleGroup toggleGroup = view.getCoinToggleGroup();
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oValue, Toggle nValue) {
                if (Start.getMachine().getAuthorization() | nValue == null) {
                    handleSelectCoin((RadioButton) nValue);
                    return;
                }
                createLoginAlert(view);
            }
        });
        List<Coin> coins = Start.getMachine().getCoins();
        for (Coin coin: coins) {
            RadioButton coinButton = new RadioButton(coin.getName());
            setRadioButton(coinButton, toggleGroup);
            addStyleClass(coinButton, "radioButton");
            coinContentVBox.getChildren().add(coinButton);

            registerObserver(coin, this);
        }
    }

    private void initSlotContent(MaintainerPanelView view) {
        ToggleGroup toggleGroup = view.getSlotToggleGroup();
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oValue, Toggle nValue) {
                if (Start.getMachine().getAuthorization() | nValue == null) {
                    handleSelectSlot((RadioButton) nValue);
                    return;
                }
                createLoginAlert(view);
            }
        });
        List<Slot> slots = Start.getMachine().getSlots();
        for (Slot slot: slots) {
            RadioButton slotButton = new RadioButton(slot.getName());
            slotButton.setId(getUniqueId("slot", slot.getId(), "button"));
            setRadioButton(slotButton, toggleGroup);
            addStyleClass(slotButton, "radioButton");
            slotContentVBox.getChildren().add(slotButton);

            registerObserver(slot, this);
        }
    }

    private void lockPanel() {
        centerVBox.setDisable(true);
        bottomVBox.setDisable(true);
    }

    private void unlockPanel() {
        centerVBox.setDisable(false);
        bottomVBox.setDisable(false);
    }

    private void handleShowTotalCash(MaintainerPanelView view, Button button) {
        showTotalCashLabel.setText(
                Start.getMachine().getTotalCash() +
                        " " +
                        Start.getMachine().getMoneyType()
        );
    }

    private void handleCollectAllCash(MaintainerPanelView view, Button button) {
        collectCashLabel.setText(
                Start.getMachine().collectALlCash() +
                        " " +
                        Start.getMachine().getMoneyType()
        );
//        handleShowTotalCash(view, button);
    }

    private void handleLogout(MaintainerPanelView view, Button button) {
        if (!Start.getMachine().getAuthorization()) {
            lockPanel();
            return;
        };
        if (!Start.getMachine().getDoor().isOpen()) {
            Start.getMachine().setAuthorization(false);
            setDefaultLabel(validPasswdLabel);
            setDefaultLabel(invalidPasswdLabel);
            maintainerPasswdField.clear();
            if (view.getSlotToggleGroup().getSelectedToggle() != null) {
                view.getSlotToggleGroup().getSelectedToggle().setSelected(false);
            }
            if (view.getCoinToggleGroup().getSelectedToggle() != null) {
                view.getCoinToggleGroup().getSelectedToggle().setSelected(false);
            }
            lockPanel();
        } else {
            view.createAlert(Alert.AlertType.WARNING, "You Haven't Locked The Door!");
        }
    }

    private void handleLogin(MaintainerPanelView view, PasswordField field) {
        if (Start.getMachine().getAuthorization()) {
            view.createAlert(Alert.AlertType.INFORMATION, "You Have Already Logged In!");
            unlockPanel();
            return;
        }

        String passwd = field.getText();
        if (!passwd.matches("^[0-9a-zA-Z]{1,6}$")) {
            view.createAlert(Alert.AlertType.WARNING, "Incorrect Password Format!");
            return;
        }
        if (passwd.equals(Start.getMachine().getPassword())) {
            Start.getMachine().setAuthorization(true);
            unlockPanel();
            Start.getMachine().getDoor().setDoorStatus(true);
            setSuccessLabel(validPasswdLabel);
            setDefaultLabel(invalidPasswdLabel);
        } else {
            setFailureLabel(invalidPasswdLabel);
            setDefaultLabel(validPasswdLabel);
        }
        field.clear();
    }

    private void createLoginAlert(MaintainerPanelView view) {
        view.createAlert(Alert.AlertType.WARNING, "You Haven't Logged In Yet!");
    }

    private void handleSlotPriceChange(MaintainerPanelView view, TextField field) {
        if (!Start.getMachine().getAuthorization()) {
            createLoginAlert(view);
            return;
        }

        String value = field.getText();
        if (!value.matches("^\\d+$")) {
            view.createAlert(Alert.AlertType.WARNING, "Incorrect Quantity Format!");
            return;
        }

        Toggle selectedToggle = view.getSlotToggleGroup().getSelectedToggle();
        if (selectedToggle == null) {
            view.createAlert(Alert.AlertType.WARNING, "You Haven't Selected A Slot Yet!");
            return;
        }

        RadioButton slotButton = (RadioButton) selectedToggle;
        Start.getMachine().getSlotById(getSlotIdByUniqueId(slotButton.getId())).get()
                .setPrice(Integer.parseInt(value));
        view.createAlert(Alert.AlertType.INFORMATION, "Successful to Change The Price of " + slotButton.getText() + "!");
//        field.setText(value + " " + Start.getMachine().getMoneyType());
    }

    private void handleSelectCoin(RadioButton radioButton) {
        if (radioButton == null) {
            availableCoinNumLabel.setText("");
            return;
        }
        availableCoinNumLabel.setText(
            String.valueOf(
                Start.getMachine().getCoinByName(radioButton.getText()).get().getTotalQuantity()
            )
        );
    }

    private void handleSelectSlot(RadioButton radioButton) {
        if (radioButton == null) {
            availableSlotNumLabel.setText("");
            slotPriceTField.setText("");
            return;
        }
        Slot slot = Start.getMachine().getSlotById(getSlotIdByUniqueId(radioButton.getId())).get();
        availableSlotNumLabel.setText(String.valueOf(slot.getQuantity()));
        slotPriceTField.setText(slot.getPrice() + " " + Start.getMachine().getMoneyType());
    }

    @FXML
    public void handleFieldAction(KeyEvent keyEvent) {
        if (keyEvent.getCode() != KeyCode.ENTER) return;

        MaintainerPanelView view = (MaintainerPanelView) Start.getView(Start.ViewType.MAINTAINER_PANEL_VIEW);
        TextField field = (TextField) keyEvent.getSource();
        switch (field.getId()) {
            case "maintainerPasswdField":
                handleLogin(view, (PasswordField)field);
                break;
            case "slotPriceTField":
                handleSlotPriceChange(view, field);
                break;
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent actionEvent) {
        MaintainerPanelView view = (MaintainerPanelView) Start.getView(Start.ViewType.MAINTAINER_PANEL_VIEW);
        Button button = (Button)actionEvent.getSource();
        if (!Start.getMachine().getAuthorization()) {
            createLoginAlert(view);
            return;
        }
        switch (button.getId()) {
            case "showTotalCashButton":
                handleShowTotalCash(view, button);
                break;
            case "collectAllCashButton":
                handleCollectAllCash(view, button);
                break;
            case "logoutButton":
                handleLogout(view, button);
                break;
        }
    }

    private void refreshCoinQuantity(Coin coin) {
        MaintainerPanelView view = (MaintainerPanelView) Start.getView(Start.ViewType.MAINTAINER_PANEL_VIEW);
        Toggle toggle = view.getCoinToggleGroup().getSelectedToggle();
        if (!Start.getMachine().getAuthorization() | toggle == null) return;
        RadioButton coinButton = (RadioButton) toggle;

        if (showTotalCashLabel.getText() != "") {
            showTotalCashLabel.setText(Start.getMachine().getTotalCash() + " " + Start.getMachine().getMoneyType());
        }

        if (coinButton == null | !coinButton.getText().equals(coin.getName())) return;
        availableCoinNumLabel.setText(String.valueOf(coin.getTotalQuantity()));
    }

    private void refreshSlotPrice(Slot slot) {
        MaintainerPanelView view = (MaintainerPanelView) Start.getView(Start.ViewType.MAINTAINER_PANEL_VIEW);
        Toggle toggle = view.getSlotToggleGroup().getSelectedToggle();
        if (!Start.getMachine().getAuthorization() | toggle == null) return;
        RadioButton slotButton = (RadioButton) toggle;
        if (getSlotIdByUniqueId(slotButton.getId()) != slot.getId()) return;
        slotPriceTField.setText(slot.getPrice() + " " + Start.getMachine().getMoneyType());
    }

    private void refreshSlotQuantity(Slot slot) {
        MaintainerPanelView view = (MaintainerPanelView) Start.getView(Start.ViewType.MAINTAINER_PANEL_VIEW);
        Toggle toggle = view.getSlotToggleGroup().getSelectedToggle();
        if (!Start.getMachine().getAuthorization() | toggle == null) return;
        RadioButton slotButton = (RadioButton) toggle;
        if (getSlotIdByUniqueId(slotButton.getId()) != slot.getId()) return;
        availableSlotNumLabel.setText(String.valueOf(slot.getQuantity()));
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
    public void updateSlot(SlotObservable slot, Object arg) {
        switch ((SlotObservable.SlotObserverType) arg) {
            case PRICE:
                refreshSlotPrice((Slot) slot);
                break;
            case QUANTITY:
                refreshSlotQuantity((Slot) slot);
                break;
        }
    }
}
