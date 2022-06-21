package handler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.Start;
import objects.Coin;
import objects.Slot;
import view.CustomerPanelView;
import view.MaintainerPanelView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MaintainerPanelView view = (MaintainerPanelView) Start.views.get("maintainerPanelView");
        initCoinContent(view);
        initSlotContent(view);
    }

    private void initCoinContent(MaintainerPanelView view) {
        ToggleGroup toggleGroup = view.getCoinToggleGroup();
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oValue, Toggle nValue) {
                selectCoin((RadioButton) nValue);
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
                selectSlot((RadioButton) nValue);
            }
        });
        List<Slot> slots = Start.getMachine().getSlots();
        int index = 0;
        for (Slot slot: slots) {
            RadioButton slotButton = new RadioButton(slot.getDrink().getName());
            slotButton.setId(String.join(";", slot.getDrink().getName(), String.valueOf(index), "button"));
            setRadioButton(slotButton, toggleGroup);
            slotButton.getStyleClass().add("radioButton");
//            coinButton.getStyleClass().add("");
            slotContentVBox.getChildren().add(slotButton);

            index++;
        }
    }

    private void selectCoin(RadioButton radioButton) {

    }

    private void selectSlot(RadioButton radioButton) {
        List<Slot> slots = Start.getMachine().getSlots();
        String[] valueList = radioButton.getId().split(";");

        availableSlotNumLabel.setText(String.valueOf(slots.get(Integer.parseInt(valueList[1])).getQuantity()));
    }

    @Override
    public void handleButtonAction(ActionEvent actionEvent) {

    }
}
