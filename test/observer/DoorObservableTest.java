package observer;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

public class DoorObservableTest {

    DoorObservable doorObservable;
    InterfaceDoorObserver observer;
    @Before
    public void setUp() throws Exception {
        doorObservable = new DoorObservable();
        observer = mock(InterfaceDoorObserver.class);
        doorObservable.addObserver(observer);
    }

    @Test
    public void notifyObservers() {
        observer.updateDoor(doorObservable, null);
        AtomicReference<String> compare = new AtomicReference<>();
        expectLastCall().andAnswer(() -> {
            String msg= "Updated " + getCurrentArgument(0).getClass().toString();
            compare.set(msg);
            return null;
        });
        replay(observer);

        doorObservable.setChanged();
        doorObservable.notifyObservers(null);
        assertEquals(new AtomicReference<>("Updated class observer.DoorObservable").toString(), compare.toString());
        verify(observer);

        assertFalse(doorObservable.hasChanged());
    }

}