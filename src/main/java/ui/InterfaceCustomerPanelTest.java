package ui;

import objects.Coin;
import java.util.Set;

public interface InterfaceCustomerPanelTest {
    String getBrand();

    Set<Coin> getCoins();

    void ShowAwailability(boolean available);   //

    void accept();

    void reject();

    void dispensedrink();

    void terminate();
}
