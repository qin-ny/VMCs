package observer;

import controller.BaseController;
import objects.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

public class ObservableTest {

    Observable observable;
    InterfaceObserver observer;

    @Before
    public void setUp() throws Exception {
        observable = new Observable();
        observer = mock(InterfaceObserver.class);
    }

    @Test
    public void addNullObserver() {
        observable.addObserver(null);
        assertEquals(1, observable.obs.size());
        assertNull(observable.obs.get(0));
    }

    @Test
    public void addObserver() {
        InterfaceObserver observer = mock(InterfaceObserver.class);
        observable.addObserver(observer);
        assertEquals(1, observable.obs.size());
        assertEquals(observer, observable.obs.get(0));
    }

    @Test
    public void deleteObserver() {
        InterfaceObserver observer = mock(InterfaceObserver.class);
        observable.addObserver(observer);
        assertEquals(1, observable.obs.size());
        observable.deleteObserver(observer);
        assertEquals(0, observable.obs.size());
    }

    @Test
    public void hasChanged() {
        assertFalse(observable.hasChanged());
    }

    @Test
    public void setChanged() {
        assertFalse(observable.hasChanged());
        observable.setChanged();
        assertTrue(observable.hasChanged());
    }

    @Test
    public void clearChange() {
        assertFalse(observable.hasChanged());
        observable.setChanged();
        assertTrue(observable.hasChanged());
        observable.clearChange();
        assertFalse(observable.hasChanged());
    }

    @Test
    public void notifyCoinObservers() {
        Coin coinObservable = new Coin("5c", 5, 0);
        coinObservable.addObserver(observer);

        observer.update(coinObservable, BaseController.ObserverType.COIN, Coin.CoinObserverType.QUANTITY);
        AtomicReference<String> compare = new AtomicReference<>();
        expectLastCall().andAnswer(() -> {
            String msg= "Updated " + getCurrentArgument(1).toString()
                    + " with attribute: " + getCurrentArgument(2).toString();
            compare.set(msg);
            return null;
        });
        replay(observer);

        coinObservable.setChanged();
        coinObservable.notifyObservers(Coin.CoinObserverType.QUANTITY);
        assertEquals("Updated COIN with attribute: QUANTITY", compare.toString());

        verify(observer);

        assertFalse(coinObservable.hasChanged());
    }

    @Test
    public void notifySlotObservers() {
        Drink cocaCola = new Drink("Coca-Cola", 55);
        Slot slotObservable = new Slot(0, cocaCola, 20);
        slotObservable.addObserver(observer);

        observer.update(slotObservable, BaseController.ObserverType.SLOT, Slot.SlotObserverType.PRICE);
        AtomicReference<String> compare = new AtomicReference<>();
        expectLastCall().andAnswer(() -> {
            String msg= "Updated " + getCurrentArgument(1).toString()
                    + " with attribute: " + getCurrentArgument(2).toString();
            compare.set(msg);
            return null;
        });
        replay(observer);

        slotObservable.setChanged();
        slotObservable.notifyObservers(Slot.SlotObserverType.PRICE);
        assertEquals("Updated SLOT with attribute: PRICE", compare.toString());

        verify(observer);

        assertFalse(slotObservable.hasChanged());
    }

    @Test
    public void notifyDoorObservers() {
        Door doorObservable = new Door(false);
        doorObservable.addObserver(observer);

        observer.update(doorObservable, BaseController.ObserverType.DOOR, null);
        AtomicReference<String> compare = new AtomicReference<>();
        expectLastCall().andAnswer(() -> {
            String msg= "Updated " + getCurrentArgument(1).toString();
            compare.set(msg);
            return null;
        });
        replay(observer);

        doorObservable.setChanged();
        doorObservable.notifyObservers(null);
        assertEquals("Updated DOOR", compare.toString());

        verify(observer);

        assertFalse(doorObservable.hasChanged());
    }

    @Test
    public void notifyAuthorizationObservers() {
        Drink fanta = new Drink("Fanta", 50);
        Coin fiveCents = new Coin("5c", 5, 0);
        String password = "testPassword";
        Slot fantaSlot = new Slot(0, fanta, 10);
        List<Slot> slots = new ArrayList<>();
        List<Coin> coins = new ArrayList<>();
        slots.add(fantaSlot);
        coins.add(fiveCents);

        Machine authorizationObservable = new Machine(slots, coins, password);
        authorizationObservable.addObserver(observer);

        observer.update(authorizationObservable, BaseController.ObserverType.AUTHORIZATION, null);
        AtomicReference<String> compare = new AtomicReference<>();
        expectLastCall().andAnswer(() -> {
            String msg= "Updated " + getCurrentArgument(1).toString();
            compare.set(msg);
            return null;
        });
        replay(observer);

        authorizationObservable.setChanged();
        authorizationObservable.notifyObservers(null);
        assertEquals("Updated AUTHORIZATION", compare.toString());

        verify(observer);

        assertFalse(authorizationObservable.hasChanged());
    }


}