package objects;

import observer.CoinObservable;

public class Coin extends CoinObservable {
    private String name;
    private int weight; //面值 in Singapore cents
    private int quantity; //硬币数量
    private transient int currentEnteredQuantity;

    public Coin(String name, int weight, int quantity){
        this.name = name;
        this.weight = weight;
        this.quantity = quantity;
        this.currentEnteredQuantity = 0;
    }

    /**
     * calculates the total value of the coin type
     * @return the total value of the coin type
     */
    public int getTotalValue(){
        return weight * (quantity + currentEnteredQuantity);
    }

    public int getCurrentEnteredTotalValue() {
        return weight * currentEnteredQuantity;
    }

    public void saveCurrentEnteredValue() {
        this.quantity += this.currentEnteredQuantity;
        this.currentEnteredQuantity = 0;
        setCoinChanged();
        notifyCoinObservers(CoinObserverType.TOTAL_QUANTITY);
    }

    public void enterCoin(int number) {
        this.currentEnteredQuantity += number;
        setCoinChanged();
        notifyCoinObservers(CoinObserverType.CURRENT_ENTERED_QUANTITY);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setCoinChanged();
        notifyCoinObservers(CoinObserverType.NAME);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
        setCoinChanged();
        notifyCoinObservers(CoinObserverType.WEIGHT);
    }

    public int getTotalQuantity() {return quantity + currentEnteredQuantity;}

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        setCoinChanged();
        notifyCoinObservers(CoinObserverType.QUANTITY);
    }

    public int getCurrentEnteredQuantity() {
        return currentEnteredQuantity;
    }

    public void setCurrentEnteredQuantity(int quantity) {
        this.currentEnteredQuantity = quantity;
        setCoinChanged();
        notifyCoinObservers(CoinObserverType.CURRENT_ENTERED_QUANTITY);
    }
}
