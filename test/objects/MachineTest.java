package objects;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class MachineTest {

    private Machine testMachine;
    private List<Slot> slots = new ArrayList<>();
    private List<Coin> coins = new ArrayList<>();
    private String password;

    @Before
    public void setUp() throws Exception {
        Drink fanta = new Drink("Fanta", 50);
        Drink cocacola = new Drink("Coca-Cola", 55);
        Coin fiveCents = new Coin("5c", 5, 0);
        Coin fiftyCents = new Coin("50c", 50, 6);
        password = "testPassword";

        Slot fantaSlot = new Slot(0, fanta, 10);
        Slot colaSlot = new Slot(1, cocacola, 5);
        slots.add(fantaSlot);
        slots.add(colaSlot);
        coins.add(fiveCents);
        coins.add(fiftyCents);

        testMachine = new Machine(slots, coins, password);
    }

    @Test
    public void getMoneyType() {
        assertEquals("c", testMachine.getMoneyType());
    }

    @Test
    public void getAuthorization() {
        assertFalse(testMachine.getAuthorization());
    }

    @Test
    public void setAuthorization() {
        testMachine.setAuthorization(true);
        assertTrue(testMachine.getAuthorization());
    }

    @Test
    public void getCurrentEnteredMoney() {
        assertEquals(0, testMachine.getCurrentEnteredMoney());
    }

    @Test
    public void getCurrentEnteredMoneyEnterOneFiveCents() {
        testMachine.getCoins().get(0).enterCoin(1);
        assertEquals(5, testMachine.getCurrentEnteredMoney());
    }

    @Test
    public void collectALlCash() {
        assertEquals(300, testMachine.collectAllCash());
        testMachine.getCoins().forEach(coin -> {
            assertEquals(0, coin.getQuantity());
            assertEquals(0, coin.getCurrentEnteredQuantity());
        });
    }

    @Test
    public void collectALlCashEnterOneFiveCents() {
        testMachine.getCoins().get(0).enterCoin(1);
        assertEquals(305, testMachine.collectAllCash());
        testMachine.getCoins().forEach(coin -> {
            assertEquals(0, coin.getQuantity());
            assertEquals(0, coin.getCurrentEnteredQuantity());
        });
    }

    @Test
    public void saveCurrentMoneyEnterOneFiveCents() {
        Coin fiveC = testMachine.getCoins().get(0);
        assertEquals(0, fiveC.getQuantity());

        fiveC.enterCoin(1);
        assertEquals(0, fiveC.getQuantity());
        assertEquals(1, fiveC.getCurrentEnteredQuantity());

        testMachine.saveCurrentMoney();
        assertEquals(1, fiveC.getQuantity());
        assertEquals(0, fiveC.getCurrentEnteredQuantity());
    }

    @Test
    public void saveCurrentMoneyEnterTwoFiftyCents() {
        Coin fiftyC = testMachine.getCoins().get(1);
        assertEquals(6, fiftyC.getQuantity());

        fiftyC.enterCoin(2);
        assertEquals(6, fiftyC.getQuantity());
        assertEquals(2, fiftyC.getCurrentEnteredQuantity());

        testMachine.saveCurrentMoney();
        assertEquals(8, fiftyC.getQuantity());
        assertEquals(0, fiftyC.getCurrentEnteredQuantity());
    }

    @Test
    public void getTotalCash() {
        assertEquals(300, testMachine.getTotalCash());
    }

    @Test
    public void getTotalCashEnterTwoFiveCents() {
        testMachine.getCoins().get(0).enterCoin(2);
        assertEquals(310, testMachine.getTotalCash());
    }

//    @Test
//    public void getSlotByIndexInvalid() {
//        assertEquals(Optional.empty(), testMachine.getSlotByIndex(5));
//    }
//
    @Test
    public void getSlotById() {
        Slot firstSlot = testMachine.getSlots().get(0);
        assertEquals(firstSlot, testMachine.getSlotById(0).get());
    }

    @Test
    public void getCoinByNameFiveCents() {
        Optional<Coin> coin = testMachine.getCoinByName("5c");
        if(coin.isPresent()) {
            assertEquals("5c", coin.get().getName());
            assertEquals(0, coin.get().getQuantity());
            assertEquals(5, coin.get().getWeight());
        } else {
            fail();
        }
    }


    @Test
    public void getDoor() {
        assertFalse(testMachine.getDoor().isOpen());
        testMachine.getDoor().setDoorStatus(true);
        assertTrue(testMachine.getDoor().isOpen());
    }

//    @Test
//    public void setDoor() {
//        Door newDoor = new Door(true);
//        testMachine.setDoor(newDoor);
//        assertTrue(testMachine.getDoor().isOpen());
//    }

    @Test
    public void getPassword() {
        assertEquals("testPassword", testMachine.getPassword());
    }

    @Test
    public void setPassword() {
        testMachine.setPassword("testing");
        assertEquals("testing", testMachine.getPassword());
    }

    @Test
    public void getSlots() {
        assertEquals(2, testMachine.getSlots().size());
    }

//    @Test
//    public void setSlots() {
//        List<Slot> emptySlots = new ArrayList<>();
//        testMachine.setSlots(emptySlots);
//        assertEquals(0, testMachine.getSlots().size());
//    }

    @Test
    public void getCoins() {
        assertEquals(2, testMachine.getCoins().size());
    }

//    @Test
//    public void setCoins() {
//        List<Coin> emptyCoinList = new ArrayList<>();
//        testMachine.setCoins(emptyCoinList);
//        assertEquals(0, testMachine.getCoins().size());
//    }
}