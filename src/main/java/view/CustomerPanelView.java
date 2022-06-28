package view;

import controller.CustomerPanelController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Start;
import objects.Coin;
import objects.Drink;
import objects.Slot;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class CustomerPanelView extends BaseView implements Initializable {
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

    private ToggleGroup slotToggleGroup;
    private CustomerPanelController controller;

    public CustomerPanelView() {
        this.stage = new Stage();
        this.fxml = "customer_panel.fxml";
        this.css = "customer_panel.css";
        this.title = "VMCs - Customer Panel";
        this.slotToggleGroup = new ToggleGroup();
        this.controller = (CustomerPanelController) Start.getController(Start.ControllerType.CUSTOMER_PANEL_CONTROLLER);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCoinMenuHBox();
        initSlotMenuVBox();
    }

    public CustomerPanelView getHandler() {
        return (CustomerPanelView) viewHandler;
    }

    private void initCoinMenuHBox() {
        Map<String, Coin> coinIdMap = controller.getCoinIdMap();
        for(String id : coinIdMap.keySet()) {
            Coin coin = coinIdMap.get(id);
            Button coinButton = new Button(coin.getName());
            coinButton.setId(id);

            addStyleClass(coinButton, "coinButton");
            coinButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent actionEvent) {
                    handleButtonAction(actionEvent);
                }

            });
            coinMenuHBox.getChildren().add(0, coinButton);
        }
    }

    private void initSlotMenuVBox() {
        Map<String, Slot> slotIdMap = controller.getSlotIdMap();
        String moneyType = controller.getMoneyType();

        slotToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oValue, Toggle nValue) {
                if (nValue == null) return;
                RadioButton selectedSlotButton = (RadioButton) nValue;
                boolean ret = controller.purchaseDrink(fetchIdByUniqueId(selectedSlotButton.getId()));
                if (ret) selectedSlotButton.setSelected(false);
            }
        });

        for (String uniqueId: slotIdMap.keySet()) {
            Slot slot = slotIdMap.get(uniqueId);
            BorderPane drinkBPane = new BorderPane();
            addStyleClass(drinkBPane, "drinkBPane");

            RadioButton radioButton = new RadioButton(slot.getName());
            radioButton.setId(getUniqueId("slot", uniqueId, "button"));
            setRadioButton(radioButton, slotToggleGroup);
            drinkBPane.setLeft(radioButton);

            Label priceLabel = new Label(slot.getPrice() + " " + moneyType);
            priceLabel.setId(getUniqueId("slot", uniqueId, "price"));
            addStyleClass(priceLabel, "drinkPriceLabel");
            drinkBPane.setCenter(priceLabel);

            Label stockLabel = new Label();
            stockLabel.setId(getUniqueId("slot", uniqueId, "stock"));
            if (slot.getQuantity() <= 0) {
                disableSlot(radioButton, stockLabel);
            } else {
                activateSlot(radioButton, stockLabel);
            }
            drinkBPane.setRight(stockLabel);
            drinkMenuVBox.getChildren().add(drinkBPane);
        }
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

    public void refundCoin(String coinString) {
        collectCoinLabel.setText(coinString);
    }

    public void highlightEnterStatusLabel() {
        setFailureLabel(enterStatusLabel);
    }

    public void highlightNoChangeAvaLabel() {
        setFailureLabel(noChangeAvaLabel);
    }

    public void dispenseDrink(String drinkName) {
        collectCanLabel.setText(drinkName);
    }

    public void handleButtonAction(ActionEvent actionEvent) {
        Button button = (Button)actionEvent.getSource();

        switch (button.getId()) {
            case "invalidCoin":
                if (slotToggleGroup.getSelectedToggle() == null) return;
                controller.handleInvalidCoin();
                break;
            case "terminateButton":
                controller.handleTerminateTransaction();
                break;
            default:
                Toggle selectedToggle = slotToggleGroup.getSelectedToggle();
                if (selectedToggle == null) return;
                setSuccessLabel(enterStatusLabel);

                controller.handleNormalCoin(button.getId());
                boolean ret = controller.purchaseDrink(fetchIdByUniqueId(((RadioButton) selectedToggle).getId()));
                if (ret) selectedToggle.setSelected(false);
                break;
        }
    }

    @FXML
    public void refreshCurrentEnteredCash(String cash) {
        totalMoneyLabel.setText(cash);
    }

    public void refreshSlotPrice(int slotId, int slotPrice) {
        Label slotPriceLabel = (Label) stage.getScene().lookup(
                "#" + getUniqueId("slot", slotId, "price")
        );
        slotPriceLabel.setText(String.valueOf(slotPrice));
    }

    public void refreshSlotQuantity(int slotId, int slotQuantity) {
        Label slotStockLabel = (Label) stage.getScene().lookup(
                "#" + getUniqueId("slot", slotId, "stock")
        );
        RadioButton slotButton = (RadioButton)  stage.getScene().lookup(
                "#" + getUniqueId("slot", slotId, "button")
        );

        if (slotQuantity <= 0) {
            slotButton.setSelected(false);
            disableSlot(slotButton, slotStockLabel);
        } else {
            activateSlot(slotButton, slotStockLabel);
        }
    }

    public void lockPanel() {
        centerVBox.setDisable(true);
        bottomBPane.setDisable(true);
    }

    public void unlockPanel() {
        centerVBox.setDisable(false);
        bottomBPane.setDisable(false);
    }
}
