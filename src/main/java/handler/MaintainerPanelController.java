package handler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import main.Start;
import objects.Coin;
import objects.Slot;
import view.CustomerPanelView;
import view.MaintainerPanelView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class MaintainerPanelController extends BaseController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MaintainerPanelView view = (MaintainerPanelView) Start.getView(Start.ViewType.MAINTAINER_PANEL_VIEW);
        initCoinContent(view);
        initSlotContent(view);
    }

    private void initCoinContent(MaintainerPanelView view) {
        ToggleGroup toggleGroup = view.getCoinToggleGroup();
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oValue, Toggle nValue) {
                if (!view.getAuthorization()) {
                    view.createAlert(Alert.AlertType.WARNING, "you haven't logged in yet!");
                    return;
                }
                handleSelectCoin((RadioButton) nValue);
            }
        });
        List<Coin> coins = Start.getMachine().getCoins();
        for (Coin coin: coins) {
            RadioButton coinButton = new RadioButton(coin.getName());
            setRadioButton(coinButton, toggleGroup);
            coinButton.getStyleClass().add("radioButton");
//            coinButton.getStyleClass().add("");
            coinContentVBox.getChildren().add(coinButton);
        }
    }

    private void initSlotContent(MaintainerPanelView view) {
        ToggleGroup toggleGroup = view.getSlotToggleGroup();
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oValue, Toggle nValue) {
                if (!view.getAuthorization()) {
                    view.createAlert(Alert.AlertType.WARNING, "you haven't logged in yet!");
                    return;
                }
                handleSelectSlot((RadioButton) nValue);
            }
        });
        List<Slot> slots = Start.getMachine().getSlots();
        int index = 0;
        for (Slot slot: slots) {
            RadioButton slotButton = new RadioButton(slot.getDrink().getName());
            slotButton.setId(getUniqueId("slot", index, "button"));
            setRadioButton(slotButton, toggleGroup);
            slotButton.getStyleClass().add("radioButton");
//            coinButton.getStyleClass().add("");
            slotContentVBox.getChildren().add(slotButton);

            index++;
        }
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
        handleShowTotalCash(view, button);
    }

    private void handleLogout(MaintainerPanelView view, Button button) {
        view.setAuthorization(false);
        setDefaultLabel(validPasswdLabel);
        setFailureLabel(invalidPasswdLabel);
        maintainerPasswdField.clear();
    }

    private void handleLogin(MaintainerPanelView view, PasswordField field) {
        if (view.getAuthorization()) {
            view.createAlert(Alert.AlertType.INFORMATION, "You Have Already Logged In!");
            return;
        }

        String passwd = field.getText();
        if (!passwd.matches("^[0-9a-zA-Z]{1,6}$")) {
            view.createAlert(Alert.AlertType.WARNING, "Incorrect Password Format!");
            return;
        }
        if (passwd.equals(Start.getMachine().getPassword())) {
            view.setAuthorization(true);
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
        if (!view.getAuthorization()) {
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
        Start.getMachine().getSlotByIndex(getSlotIndexByFXId(slotButton.getId())).get()
                .getDrink().setPrice(Integer.parseInt(value));
        view.createAlert(Alert.AlertType.INFORMATION, "Successful to Change The Quantity of " + slotButton.getText() + "!");
        field.setText(value + " " + Start.getMachine().getMoneyType());
    }

    private void handleSelectCoin(RadioButton radioButton) {
        availableCoinNumLabel.setText(
            String.valueOf(
                Start.getMachine().getCoinByName(radioButton.getText()).get().getTotalQuantity()
            )
        );
    }

    private void handleSelectSlot(RadioButton radioButton) {
        List<Slot> slots = Start.getMachine().getSlots();
        Slot slot = slots.get(getSlotIndexByFXId(radioButton.getId()));
        availableSlotNumLabel.setText(String.valueOf(slot.getQuantity()));
        slotPriceTField.setText(slot.getDrink().getPrice() + Start.getMachine().getMoneyType());
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
        if (!view.getAuthorization()) {
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

}
