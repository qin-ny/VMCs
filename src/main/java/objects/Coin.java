package objects;

public class Coin {
    private String name;
    private int weight; //面值 in Singapore cents
    private int quantity; //硬币数量
    private int currentEnteredQuantity;

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
    }

    public void enterCoin(int number) {
        this.currentEnteredQuantity += number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getTotalQuantity() {return quantity + currentEnteredQuantity;}

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if(quantity >= 0 && quantity <= 40){
            this.quantity = quantity;
        } else {
            throw new IllegalArgumentException("Coin quantity should between 0 and 40");
        }
    }

    public int getCurrentEnteredQuantity() {
        return currentEnteredQuantity;
    }

    public void setCurrentEnteredQuantity(int quantity) {
        this.currentEnteredQuantity = quantity;
    }
}
