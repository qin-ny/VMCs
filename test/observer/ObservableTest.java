package observer;

import org.junit.Before;
import org.junit.Test;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

public class ObservableTest {

    Observable observable;
    String compare = "";

    @Before
    public void setUp() throws Exception {
        observable = new Observable() {
            @Override
            public void notifyObservers(Object arg) {
                compare = arg.toString();
            }
        };
    }

    @Test
    public void notifyObservers() {
        observable.notifyObservers(CoinObservable.CoinObserverType.QUANTITY);
        assertEquals("QUANTITY", compare);
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


}