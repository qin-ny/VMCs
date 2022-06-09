package controller;

import javafx.scene.control.Button;
import objects.Coin;
import objects.Drink;
import objects.Machine;
import ui.InterfaceMaintainerPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

/**
 * This is implementation of InterfaceMaintainerController, which contains the method generateView for displaying
 * the maintainer panel, corresponding actions from its maintainerPanel will be listened and triggered by this
 * controller class. This class manipulates Machine and InterfaceMaintainerPanel as a controller.
 */
public class MaintainerController implements InterfaceMaintainerController, ActionListener {

    private Machine machine;
    private InterfaceMaintainerPanel maintainerPanel;

    public MaintainerController(Machine machine, InterfaceMaintainerPanel maintainerPanel){
        this.machine = machine;
        this.maintainerPanel = maintainerPanel;
    }


    @Override
    public void generateView() {
//        this.maintainerPanel.makeVisible();
    }

    /**
     * This method performs certain actions based on the ActionEvent.
     * Actions include:
     * "login": if the input password is correct, activate all the functions (e.g. buttons) other than the password input.
     * "coinPressed": based on the pressed coin button name, find the quantity of the coin type and display it on panel.
     * "drinkPressed": based on he pressed drink button name, find the quantity and price of the drink type and display them on panel.
     * "drinkPriceChange": when the input price changed, update the price of corresponding drink.
     * "totalCashPressed": display total cash in the machine on panel.
     * "collectCoinsPressed": display total cash collected and total cash remaining in the machine, which is zero.
     * "logout": if the door of the machine is locked, logout the user and deactivate  all the functions (e.g. buttons) other than the password input.
     *
     * @param e an action event binds with certain elements from maintainerPanel.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            //TODO 以下注释方法需要在MaintainerPanel中开发
            case "login":
//                maintainerPanel.login(machine.isValidPassword(maintainerPanel.getPassword())); //判断密码是否正确，解锁面板功能
            case "coinPressed":
                String coinName = ((Button)e.getSource()).getText();
                Coin coin = getCoin(coinName);
//                maintainerPanel.displayCoinQuantity(coin.getQuantity()); //展示对应硬币数量
            case "drinkPressed":
                String drinkName = ((Button)e.getSource()).getText();
//                maintainerPanel.setSelectedDrink(drinkName); //记录已选择的饮料名
                Drink drink = getDrink(drinkName);
//                maintainerPanel.displayDrinkPrice(drink.getPrice()); //展示饮料价格
//                maintainerPanel.displayDrinkQuantity(drink.getQuantity()); //展示饮料数量
            case "drinkPriceChanged":
//                String selectedDrinkName = maintainerPanel.getSelectedDrink(); //获得已选择的饮料名
//                int newPrice = maintainerPanel.getDrinkPrice();  //获得（新）饮料价格
//                Drink selectedDrink = getDrink(selectedDrinkName);
//                selectedDrink.setPrice(newPrice);
            case "totalCashPressed":
                int totalCash = machine.getTotalCash();
//                maintainerPanel.displayTotalCash(totalCash); //展示硬币总金额
            case "collectCoinsPressed":
                int totalCashCollected = machine.collectAllCoins();
//                maintainerPanel.displayCollectedCash(totalCashCollected); //展示获取到的硬币总金额
//                maintainerPanel.displayTotalCash(0); //展示硬币总金额 0
            case "logout":
//                if(!machine.getDoor().isOpen()) maintainerPanel.logout(); //判断门是否已锁，锁定面板功能

        }
    }

    /**
     * check if the machine contains the coin type and get corresponding Coin.
     * @param coinName a name of the coin (e.g. 5c, $1)
     * @return the Coin corresponding to the coin name
     * @throws IllegalArgumentException if the coin name does not exist in the machine
     */
    private Coin getCoin(String coinName) throws IllegalArgumentException{
        Optional<Coin> validateCoin = machine.getCoinByName(coinName);
        if(validateCoin.isPresent()) return validateCoin.get();
        throw new IllegalArgumentException("coin type not exist");
    }

    /**
     * check if the machine contains the drink type and get corresponding Drink.
     * @param drinkName a name of the drink (e.g. Fanta, Sprite)
     * @return the Drink corresponding to the drink name
     * @throws IllegalArgumentException if the drink name does not exist in the machine
     */
    private Drink getDrink(String drinkName) throws IllegalArgumentException{
        Optional<Drink> validateDrink = machine.getDrinkByName(drinkName);
        if(validateDrink.isPresent()) return validateDrink.get();
        throw new IllegalArgumentException("drink type not exist");
    }

}
