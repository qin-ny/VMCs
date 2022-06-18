package objects;

public class Coin {
    private final static String SGD = "c";
    private final static String USD = "$";
    private final static String CNY = "y";

    private String name;
    private int weight; //面值 in Singapore cents
    private int quantity; //硬币数量

    public Coin(String name, int weight, int quantity){
        this.name = name;
        this.weight = weight;
        this.quantity = quantity;
    }

    /**
     * calculates the total value of the coin type
     * @return the total value of the coin type
     */
    public int getTotalValue(){
        return weight * quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
