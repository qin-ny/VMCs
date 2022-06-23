package objects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoinTest {

    private Coin fiveCents, tenCents, twentyCents, fiftyCents, oneDollar, invalidQuantityCoin;
    @Before
    public void setUp() throws Exception {
        fiveCents = new Coin("5c", 5, 0);
        tenCents = new Coin("10c", 10, 7);
        twentyCents = new Coin("20c", 20, 19);
        fiftyCents = new Coin("50c", 50, 30);
        oneDollar = new Coin("$1", 100, 40);
    }

    @Test
    public void getCoinName() {
        assertEquals("$1", oneDollar.getName());
    }

    @Test
    public void setCoinName() {
        oneDollar.setName("$10");
        assertEquals("$10", oneDollar.getName());
    }

    @Test
    public void getWeight() {
        assertEquals(50, fiftyCents.getWeight());
    }

    @Test
    public void setWeight() {
        fiftyCents.setWeight(30000);
        assertEquals(30000, fiftyCents.getWeight());
    }

    @Test
    public void getQuantity() {
        assertEquals(0, fiveCents.getQuantity());
    }

    @Test
    public void setQuantity() {
        fiftyCents.setQuantity(40);
        assertEquals(40, fiftyCents.getQuantity());
    }

//    @Test
//    public void setQuantityOverForty() {
//        try {
//            fiftyCents.setQuantity(41);
//            fail();
//        } catch (IllegalArgumentException e){
//            assertEquals("Coin quantity should between 0 and 40", e.getMessage());
//        }
//
//    }

//    @Test
//    public void setQuantityLessZero() {
//        try {
//            fiftyCents.setQuantity(-1);
//            fail();
//        } catch (IllegalArgumentException e){
//            assertEquals("Coin quantity should between 0 and 40", e.getMessage());
//        }
//    }

    @Test
    public void enterOneFiveCentsCoin() {
        fiveCents.enterCoin(1);
        assertEquals(1, fiveCents.getCurrentEnteredQuantity());
    }


    @Test
    public void getTotalValueWithZeroCoin() {
        assertEquals(0, fiveCents.getTotalValue());
    }

    @Test
    public void getTotalValueAfterOneCoinEntered() {
        fiveCents.enterCoin(1);
        assertEquals(5, fiveCents.getTotalValue());
    }

    @Test
    public void getCurrentEnteredTotalValue1() {
        tenCents.enterCoin(1);
        assertEquals(10, tenCents.getCurrentEnteredTotalValue());
    }

    @Test
    public void getCurrentEnteredTotalValue2() {
        tenCents.enterCoin(2);
        assertEquals(20, tenCents.getCurrentEnteredTotalValue());
    }

    @Test
    public void saveCurrentEnteredValue1() {
        twentyCents.enterCoin(1);
        assertEquals(19, twentyCents.getQuantity());
        assertEquals(1, twentyCents.getCurrentEnteredQuantity());

        twentyCents.saveCurrentEnteredValue();
        assertEquals(20, twentyCents.getQuantity());
        assertEquals(0, twentyCents.getCurrentEnteredQuantity());
    }

    @Test
    public void saveCurrentEnteredValue2() {
        twentyCents.enterCoin(2);
        assertEquals(19, twentyCents.getQuantity());
        assertEquals(2, twentyCents.getCurrentEnteredQuantity());

        twentyCents.saveCurrentEnteredValue();
        assertEquals(21, twentyCents.getQuantity());
        assertEquals(0, twentyCents.getCurrentEnteredQuantity());
    }

    @Test
    public void getCurrentEnteredQuantity1() {
        oneDollar.enterCoin(1);
        assertEquals(1, oneDollar.getCurrentEnteredQuantity());
    }

    @Test
    public void getCurrentEnteredQuantity2() {
        oneDollar.enterCoin(2);
        assertEquals(2, oneDollar.getCurrentEnteredQuantity());
    }

    @Test
    public void setCurrentEnteredQuantity() {
        oneDollar.enterCoin(2);
        assertEquals(2, oneDollar.getCurrentEnteredQuantity());

        oneDollar.setCurrentEnteredQuantity(0);
        assertEquals(0, oneDollar.getCurrentEnteredQuantity());
    }
}