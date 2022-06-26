package observer;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

public class SlotObservableTest {

    SlotObservable slotObservable;
    InterfaceSlotObserver observer;
    @Before
    public void setUp() throws Exception {
        slotObservable = new SlotObservable();
        observer = mock(InterfaceSlotObserver.class);
        slotObservable.addObserver(observer);
    }

    @Test
    public void notifyObservers() {
        observer.updateSlot(slotObservable, SlotObservable.SlotObserverType.PRICE);
        AtomicReference<String> compare = new AtomicReference<>();
        expectLastCall().andAnswer(() -> {
            String msg= "Updated " + getCurrentArgument(0).getClass().toString()
                    + " with attribute: " + getCurrentArgument(1).toString();
            compare.set(msg);
            return null;
        });
        replay(observer);

        slotObservable.setChanged();
        slotObservable.notifyObservers(SlotObservable.SlotObserverType.PRICE);
        assertEquals(new AtomicReference<>("Updated class observer.SlotObservable with attribute: PRICE").toString(), compare.toString());
        verify(observer);

        assertFalse(slotObservable.hasChanged());

    }
}