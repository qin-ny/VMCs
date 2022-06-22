package objects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DrinkTest {

    private Drink fanta, cocacola;

    @Before
    public void setUp() throws Exception {
        fanta = new Drink("Fanta", 50);
        cocacola = new Drink("Coca-Cola", 55);
    }

    @Test
    public void getName() {
        assertEquals("Fanta", fanta.getName());
        assertEquals("Coca-Cola", cocacola.getName());
    }

    @Test
    public void setName() {
        fanta.setName("Fantata");
        cocacola.setName("Colacola");
        assertEquals("Fantata", fanta.getName());
        assertEquals("Colacola", cocacola.getName());
    }

    @Test
    public void getPrice() {
        assertEquals(50, fanta.getPrice());
        assertEquals(55, cocacola.getPrice());
    }

    @Test
    public void setPrice() {
        fanta.setPrice(1000);
        assertEquals(1000, fanta.getPrice());
        cocacola.setPrice(99);
        assertEquals(99, cocacola.getPrice());
    }
}