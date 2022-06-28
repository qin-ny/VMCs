package view;

import controller.CustomerPanelController;
import controller.MaintainerPanelController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Start;
import objects.Coin;
import objects.Slot;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class MaintainerPanelView extends BaseView implements Initializable {

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

    private ToggleGroup slotToggleGroup;
    private ToggleGroup coinToggleGroup;
    private MaintainerPanelController controller;

    public MaintainerPanelView() {
        this.stage = new Stage();
        this.fxml = "maintainer_panel.fxml";
        this.css = "maintainer_panel.css";
        this.title = "VMCs - Maintainer Panel";
        this.slotToggleGroup = new ToggleGroup();
        this.coinToggleGroup = new ToggleGroup();
        this.controller = (MaintainerPanelController) Start.getController(Start.ControllerType.MAINTAINER_PANEL_CONTROLLER);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCoinContent();
        initSlotContent();
        if (!controller.isAuthorization()) {
            lockPanel();
        }
    }

    public MaintainerPanelView getHandler() {
        return (MaintainerPanelView) viewHandler;
    }

    private void initCoinContent() {
        Map<String, Coin> coinIdMap = controller.getCoinIdMap();

        coinToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oValue, Toggle nValue) {
                if (controller.isAuthorization()) {
                    if (nValue == null) {
                        availableCoinNumLabel.setText("");
                        return;
                    }
                    controller.handleSelectCoin(((RadioButton) nValue).getId());
                }
            }
        });

        for (String coinId: coinIdMap.keySet()) {
            Coin coin = coinIdMap.get(coinId);
            RadioButton coinButton = new RadioButton(coin.getName());
            coinButton.setId(coinId);
            setRadioButton(coinButton, coinToggleGroup);
            addStyleClass(coinButton, "radioButton");
            coinContentVBox.getChildren().add(coinButton);
        }
    }

    private void initSlotContent() {
        Map<String, Slot> slotIdMap = controller.getSlotIdMap();

        slotToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oValue, Toggle nValue) {
                if (Start.getMachine().getAuthorization()) {
                    if (nValue == null) {
                        availableSlotNumLabel.setText("");
                        slotPriceTField.setText("");
                        return;
                    }
                    controller.handleSelectSlot(fetchIdByUniqueId(((RadioButton) nValue).getId()));
                }
            }
        });

        for (String slotId: slotIdMap.keySet()) {
            Slot slot = slotIdMap.get(slotId);

            RadioButton slotButton = new RadioButton(slot.getName());
            slotButton.setId(getUniqueId("slot", slotId, "button"));
            setRadioButton(slotButton, slotToggleGroup);
            addStyleClass(slotButton, "radioButton");
            slotContentVBox.getChildren().add(slotButton);
        }
    }

    public void displayCoinInfo(int quantity) {
        availableCoinNumLabel.setText(String.valueOf(quantity));
    }

    public void displaySlotInfo(int quantity, String price) {
        availableSlotNumLabel.setText(String.valueOf(quantity));
        slotPriceTField.setText(price);
    }

    public void displayTotalCash(String totalCash) {
        showTotalCashLabel.setText(totalCash);
    }

    public void collectCash(String collectCash) {
        collectCashLabel.setText(collectCash);
    }

    private void createLoginAlert() {
        createAlert(Alert.AlertType.WARNING, "You Haven't Logged In Yet!");
    }

    public void lockPanel() {
        setDefaultLabel(validPasswdLabel);
        setDefaultLabel(invalidPasswdLabel);
        maintainerPasswdField.clear();

        if (slotToggleGroup.getSelectedToggle() != null) {
            slotToggleGroup.getSelectedToggle().setSelected(false);
        }
        if (coinToggleGroup.getSelectedToggle() != null) {
            coinToggleGroup.getSelectedToggle().setSelected(false);
        }

        availableCoinNumLabel.setText("");
        availableSlotNumLabel.setText("");

        slotPriceTField.clear();

        showTotalCashLabel.setText("");

        centerVBox.setDisable(true);
        bottomVBox.setDisable(true);
    }

    public void unlockPanel() {
        centerVBox.setDisable(false);
        bottomVBox.setDisable(false);
    }

    @FXML
    public void handleFieldAction(KeyEvent keyEvent) {
        if (keyEvent.getCode() != KeyCode.ENTER) return;

        TextField field = (TextField) keyEvent.getSource();
        switch (field.getId()) {
            case "maintainerPasswdField":
                if (controller.isAuthorization()) {
                    createAlert(Alert.AlertType.INFORMATION, "You Have Already Logged In!");
                    unlockPanel();
                    return;
                }

                String passwd = field.getText();
                if (!passwd.matches("^[0-9a-zA-Z]{1,6}$")) {
                    createAlert(Alert.AlertType.WARNING, "Incorrect Password Format!");
                    return;
                }

                boolean isLogin = controller.handleLogin(passwd);
                if (isLogin) {
                    setSuccessLabel(validPasswdLabel);
                    setDefaultLabel(invalidPasswdLabel);
                } else {
                    setFailureLabel(invalidPasswdLabel);
                    setDefaultLabel(validPasswdLabel);
                }
                field.clear();
                break;
            case "slotPriceTField":
                if (!Start.getMachine().getAuthorization()) {
                    createLoginAlert();
                    return;
                }

                String price = field.getText();
                if (!price.matches("^\\d+$")) {
                    createAlert(Alert.AlertType.WARNING, "Incorrect Quantity Format!");
                    return;
                }

                Toggle selectedToggle = slotToggleGroup.getSelectedToggle();
                if (selectedToggle == null) {
                    createAlert(Alert.AlertType.WARNING, "You Haven't Selected A Slot Yet!");
                    return;
                }
                RadioButton slotButton = (RadioButton) selectedToggle;
                controller.handleSlotPriceChange(fetchIdByUniqueId(slotButton.getId()), Integer.parseInt(price));
                break;
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent actionEvent) {
        Button button = (Button)actionEvent.getSource();
        if (!controller.isAuthorization()) {
            createLoginAlert();
            return;
        }
        switch (button.getId()) {
            case "showTotalCashButton":
                controller.handleShowTotalCash();
                break;
            case "collectAllCashButton":
                controller.handleCollectAllCash();
                break;
            case "logoutButton":
                boolean ret = controller.handleLogout();
                if (ret) lockPanel();
                break;
        }
    }

    public void refreshCoinQuantity(String coinId, String totalCash, int coinQuantity) {
        Toggle toggle = coinToggleGroup.getSelectedToggle();
        RadioButton coinButton = (RadioButton) toggle;

        if (!Objects.equals(showTotalCashLabel.getText(), "")) {
            showTotalCashLabel.setText(totalCash);
        }

        if (coinButton == null) return;
        if (!coinButton.getId().equals(coinId)) return;
        availableCoinNumLabel.setText(String.valueOf(coinQuantity));
    }

    private boolean isSelectSlot(String slotId) {
        Toggle toggle = slotToggleGroup.getSelectedToggle();
        if (!Start.getMachine().getAuthorization() | toggle == null) return false;
        RadioButton slotButton = (RadioButton) toggle;
        return Objects.equals(fetchIdByUniqueId(slotButton.getId()), slotId);
    }

    public void refreshSlotPrice(String slotId, String price) {
        if (isSelectSlot(slotId)) slotPriceTField.setText(price);
    }

    public void refreshSlotQuantity(String slotId, int quantity) {
        if (isSelectSlot(slotId)) availableSlotNumLabel.setText(String.valueOf(quantity));
    }

}
