package controller;

import main.Start;
import objects.Coin;
import objects.Drink;
import objects.Machine;
import handler.InterfaceCustomerPanelTest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.util.Set;

public class CustomerController extends BaseController implements InterfaceCustomerController, ActionListener {
    private Machine machine;

    private InterfaceCustomerPanelTest customerPanel;

    String brand;
    Set<Coin> customerInput;

    public CustomerController(Start startObj) {
        super(startObj);
    }



    @Override
    public void generateView() {
        // customerPanel.makevisible();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "brand":
                String brand = customerPanel.getBrand();
//                Optional<Drink> t = machine.getDrinkByName(brand);
//                Optional<Drink> t = Optional.of(new Drink("", 1, 1));
//                boolean avl;
//                if (t.isPresent() && t.get().isAvailable()) {
//                    avl = true;
//                    this.brand = brand;
//                } else avl = false;
//                customerPanel.ShowAwailability(avl);
                break;
            case "entercoins":
                this.customerInput = customerPanel.getCoins();
                if (checkValidation()) {
                    customerPanel.accept();
                    customerPanel.dispensedrink();
                    // 找钱（待实现）
                } else customerPanel.reject();
            case "terminated":
                customerPanel.terminate();

        }
    }

    boolean checkValidation() { // 判断输入的面额是否大于等于drink的价格
        int totalInput = 0;
        for(Coin coin: customerInput){
            totalInput += coin.getTotalValue();
        }

        return false;
//        return totalInput >= machine.getDrinkByName(brand).get().getPrice();
    }
}
