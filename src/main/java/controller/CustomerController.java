package controller;

import objects.Coin;
import objects.Drink;
import objects.Machine;
import ui.InterfaceCustomerPanel;
import ui.InterfaceCustomerPanelTest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.util.Set;

public class CustomerController implements InterfaceCustomerController, ActionListener {
    private Machine machine;

    private InterfaceCustomerPanelTest customerPanel;

    String brand;
    Set<Coin> customerInput;

    public CustomerController(Machine machine, InterfaceCustomerPanelTest customerPanel) {
        this.machine = machine;
        this.customerPanel = customerPanel;
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
                Optional<Drink> t = machine.getDrinkByName(brand);
                boolean avl;
                if (t.isPresent() && t.get().isAvailable()) {
                    avl = true;
                    this.brand = brand;
                } else avl = false;
                customerPanel.ShowAwailability(avl);
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

        return totalInput >= machine.getDrinkByName(brand).get().getPrice();
    }
}
