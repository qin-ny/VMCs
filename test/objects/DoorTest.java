package objects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DoorTest {

    private Door lockedDoor, unlockedDoor;
    @Before
    public void setUp() throws Exception {
        lockedDoor = new Door(false);
        unlockedDoor = new Door(true);
    }

    @Test
    public void isOpen() {
        assertTrue(unlockedDoor.isOpen());
        assertFalse(lockedDoor.isOpen());
    }

    @Test
    public void setDoorStatus() {
        assertTrue(unlockedDoor.isOpen());
        unlockedDoor.setDoorStatus(false);
        assertFalse(unlockedDoor.isOpen());

        assertFalse(lockedDoor.isOpen());
        lockedDoor.setDoorStatus(true);
        assertTrue(lockedDoor.isOpen());
    }
}