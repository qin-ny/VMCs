package ui;

import objects.Coin;
import objects.Drink;

import java.util.List;

public interface InterfaceCustomerPanel {

    public List<String> getSupportedCoins();

    public List<Drink> getDrinks();

}
