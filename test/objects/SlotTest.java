package objects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SlotTest {

    private Slot colaSlot;
    private Drink cocaCola;

    @Before
    public void setUp() throws Exception {
        cocaCola = new Drink("Coca-Cola", 55);
        colaSlot = new Slot(0, cocaCola, 20);
    }

    @Test
    public void getId() {
        assertEquals(0, colaSlot.getId());
    }

    @Test
    public void getDrink() {
        assertEquals("Coca-Cola", colaSlot.getDrink().getName());
        assertEquals(55, colaSlot.getDrink().getPrice());
    }

    @Test
    public void getName() {
        assertEquals("Coca-Cola", colaSlot.getName());
    }

    @Test
    public void setName() {
        colaSlot.setName("Coca-Colala");
        assertEquals("Coca-Colala", colaSlot.getName());
    }

    @Test
    public void getPrice() {
        assertEquals(55, colaSlot.getPrice());
    }

    @Test
    public void setPrice() {
        colaSlot.setPrice(20);
        assertEquals(20, colaSlot.getPrice());
    }

//    @Test
//    public void setDrink() {
//        Drink newDrink = new Drink("newDrink", 999);
//        colaSlot.setDrink(newDrink);
//        assertEquals("newDrink", colaSlot.getDrink().getName());
//        assertEquals(999, colaSlot.getDrink().getPrice());
//    }

    @Test
    public void getQuantity() {
        assertEquals(20, colaSlot.getQuantity());
    }

    @Test
    public void setQuantityToZero() {
        colaSlot.setQuantity(0);
        assertEquals(0, colaSlot.getQuantity());
    }

//    @Test
//    public void setQuantityLessZero() {
//        try {
//            colaSlot.setQuantity(-1);
//            fail();
//        } catch (IllegalArgumentException e){
//            assertEquals("Slot quantity should between 0 and 20", e.getMessage());
//        }
//    }

//    @Test
//    public void setQuantityGreaterTwenty() {
//        try {
//            colaSlot.setQuantity(21);
//            fail();
//        } catch (IllegalArgumentException e){
//            assertEquals("Slot quantity should between 0 and 20", e.getMessage());
//        }
//    }
}