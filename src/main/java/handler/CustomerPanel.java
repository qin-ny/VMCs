package handler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import objects.Coin;
import objects.Drink;

import java.util.ArrayList;
import java.util.List;

public class CustomerPanel extends Panel implements InterfaceCustomerPanel {

    private int totalInsertedMoney;
    private List<Button> coinButtons;
    private List<Drink> drinks;
    private Drink selectedDrink;
    private boolean isSelected;
    private Label totalMoneyLabel;

    public CustomerPanel() {
        this.name = "Customer Panel";
        this.title = "VMCs - " + name;
        this.caption.setText("Soft Drink Dispenser");
        this.totalInsertedMoney = 0;
        this.coinButtons = new ArrayList<Button>();
        this.drinks = new ArrayList<Drink>();
        this.isSelected = false;
    }

    protected BorderPane getBorderPane() {
        BorderPane bPane = new BorderPane();
        bPane.setPadding(new Insets(10));
//        bPane.setPrefWidth(400);
//        bPane.setPrefHeight(800);

        BorderPane bPaneTop = getTopPane();
        bPane.setTop(bPaneTop);

        HBox hb = getCenterHBox();
        VBox vb_super = (VBox)hb.getChildren().get(0);

        bPane.getChildren().add(hb);
        bPane.setCenter(vb_super);

        BorderPane bPaneBottom = getBottomBPane();
        bPane.setBottom(bPaneBottom);
        return bPane;
    }

    private BorderPane getBottomBPane() {
        BorderPane bottomBPane = new BorderPane();

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        BorderPane collectCoinBPane = new BorderPane();
        collectCoinBPane.setMaxWidth(200);
        Label label = new Label("Collect Coins: ");
        Label value = new Label(0 + " " + visibleCoinType);
        value.setMinWidth(30);
        value.setAlignment(Pos.CENTER);
        setStyle(value, coinLabelColor, 0, normalFontFamily, 16);
        collectCoinBPane.setLeft(label);
        collectCoinBPane.setRight(value);
        vBox.getChildren().add(collectCoinBPane);

        BorderPane collectCanBPane = new BorderPane();
        collectCanBPane.setMaxWidth(200);
        Label canLabel = new Label("Collect Can Here: ");
        Label canValue = new Label("No Can");
        canValue.setMinWidth(30);
        canValue.setAlignment(Pos.CENTER);
        setStyle(canValue, coinLabelColor, 0, normalFontFamily, 16);
        collectCanBPane.setLeft(canLabel);
        collectCanBPane.setRight(canValue);
        vBox.getChildren().add(collectCanBPane);

        bottomBPane.setCenter(vBox);

        return bottomBPane;
    }

    private HBox getTotalMoneyHBox() {
        HBox totalMoneyHBox = new HBox();
        totalMoneyHBox.setAlignment(Pos.CENTER);
        Label label = new Label("Total Money Inserted: ");
        totalMoneyHBox.getChildren().add(label);

        totalMoneyLabel = new Label(String.valueOf(totalInsertedMoney + " " + visibleCoinType));
        totalMoneyLabel.setMinWidth(30);
        totalMoneyLabel.setAlignment(Pos.CENTER);
        setStyle(totalMoneyLabel, coinLabelColor, 0, normalFontFamily, 16);

        totalMoneyHBox.getChildren().add(totalMoneyLabel);

        totalMoneyHBox.setSpacing(5);
        return totalMoneyHBox;
    }

    private Button getCoinButton(String name) {
        Button button = new Button(name);
        setButtonStyle(button, 0,  80, 30);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().name().equals(MouseButton.PRIMARY.name())){
                    if (isSelected) {
                        button.getText();
//                        totalInsertedMoney += button.getText();
                    }
                }
            }
        });
        return button;
    }

    private VBox getCoinVBox() {
        VBox coinVBox = new VBox();
        coinVBox.setAlignment(Pos.CENTER);

        Label label = new Label("Enter Coins Here");
        coinVBox.getChildren().add(label);

        List<String> coins = getSupportedCoins();
        coins.add("Invalid");
//        ListView<Button> listView = new ListView<Button>();
//        listView.setOrientation(Orientation.HORIZONTAL);

        HBox hBox = new HBox();
        hBox.setSpacing(2);
        hBox.setAlignment(Pos.CENTER);
        ScrollPane sPane = getScrollPane();
        sPane.setMaxWidth(250);
        for (String coin: coins) {
            Button button = getCoinButton(coin);
//            setButtonStyle(button, 0,  80, 30);
//            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
//                @Override
//                public void handle(MouseEvent mouseEvent) {
//                    if (mouseEvent.getButton().name().equals(MouseButton.PRIMARY)){
//                        if (isSelected) {
//                            if
//                        }
//                    }
//                }
//            });
            hBox.getChildren().add(button);
            this.coinButtons.add(button);
//            listView.getItems().add(button);
        }
        sPane.setContent(hBox);
        coinVBox.getChildren().add(sPane);

        Button invalidButton = new Button("Invalid Coin");
        setButtonStyle(invalidButton, successColor, successColor, successColor, 0, 150, 30, false);
        coinVBox.getChildren().add(invalidButton);

        HBox totalMoneyHBox = getTotalMoneyHBox();
        coinVBox.getChildren().add(totalMoneyHBox);

        coinVBox.setSpacing(10);
//        coinVBox.setStyle("-fx-background-color: #000000");
        return coinVBox;
    }

    private VBox getDrinkBox() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        ScrollPane sPane = getScrollPane();
        sPane.setMaxWidth(320);
//        sPane.setStyle("-fx-background-color: #ff0000");

        VBox contentVBox = new VBox();
        contentVBox.setAlignment(Pos.CENTER);
        contentVBox.setSpacing(10);
//        contentVBox.setStyle("-fx-background-color: #ff00ff");
        List<Drink> drinks = getDrinks();
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton selectedButton = (RadioButton) t1;
                for (Drink drink: drinks) {
                    if (drink.getName().equals(selectedButton.getText())) {
                        selectedDrink = drink;
                        isSelected = true;
                        break;
                    }
                }

            }
        });
        for (Drink drink: drinks) {
            BorderPane bPane = new BorderPane();
//            bPane.setStyle("-fx-background-color: #ff0000");
            bPane.setMinWidth(300);

            RadioButton radioButton = getRadioButton(drink.getName());
            radioButton.setPrefWidth(100);
            radioButton.setPrefHeight(30);
//            setButtonStyle(radioButton, 0, 100, 30);
            bPane.setLeft(radioButton);
            radioButton.setToggleGroup(toggleGroup);
//            radioButton.setLayoutX(0);

            Label priceLabel = new Label(drink.getPrice() + " " + visibleCoinType);
            priceLabel.setMinWidth(30);
            priceLabel.setAlignment(Pos.CENTER);
            setStyle(priceLabel, coinLabelColor, 0, normalFontFamily, 16);
            bPane.setCenter(priceLabel);
//            priceLabel.setLayoutX(60);

            Button stockButton = new Button();
            if (drink.getPrice() <= 0) {
                stockButton.setText("Not In Stock");
                setButtonStyle(stockButton, failureColor, failureColor, failureColor, 0, 150, 30, false);
            } else {
                stockButton.setText("In Stock");
                setButtonStyle(stockButton, successColor, successColor, successColor, 0, 150, 30, false);
            }
            bPane.setRight(stockButton);
            contentVBox.getChildren().add(bPane);
        }
        sPane.setContent(contentVBox);

        vBox.getChildren().add(sPane);
        return vBox;
    }

    @Override
    protected HBox getCenterHBox() {
        HBox hBox = new HBox();
        VBox superVBox = new VBox();
        superVBox.setSpacing(20);

        superVBox.setAlignment(Pos.CENTER);

        VBox coinVBox = getCoinVBox();
        superVBox.getChildren().add(coinVBox);

        VBox drinkBox = getDrinkBox();
        superVBox.getChildren().add(drinkBox);

        Button highlightButton = new Button("No Change Available");
        setButtonStyle(highlightButton, successColor, successColor, successColor, 0, 200, 0, false);
        superVBox.getChildren().add(highlightButton);

        Button terminateButton = new Button("Terminate And Return Cash");
        setButtonStyle(terminateButton, 0, 250, 0);
        superVBox.getChildren().add(terminateButton);

        hBox.getChildren().add(superVBox);
        return hBox;
    }

    @Override
    public void exit() {

    }



    @Override
    public List<String> getSupportedCoins() {
        List<String> coins = new ArrayList<String>();
        coins.add("1 c");
        coins.add("5 c");
        coins.add("10 c");
        return coins;
    }

    @Override
    public List<Drink> getDrinks() {
        return drinks;
    }

    @Override
    public Coin getCoinByName(String name) {
//        start_obj.getCustomerController()
        return null;
    }

}
