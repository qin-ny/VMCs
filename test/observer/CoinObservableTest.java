package observer;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicReference;
import static org.easymock.EasyMock.*;

public class CoinObservableTest {

    CoinObservable coinObservable;
    InterfaceCoinObserver observer;
    @Before
    public void setUp() throws Exception {
        coinObservable = new CoinObservable();
        observer = mock(InterfaceCoinObserver.class);
        coinObservable.addObserver(observer);
    }

    @Test
    public void notifyObservers() {
        observer.updateCoin(coinObservable, CoinObservable.CoinObserverType.QUANTITY);
        AtomicReference<String> compare = new AtomicReference<>();
        expectLastCall().andAnswer(() -> {
            String msg= "Updated " + getCurrentArgument(0).getClass().toString()
                    + " with attribute: " + getCurrentArgument(1).toString();
            compare.set(msg);
            return null;
        });
        replay(observer);

        coinObservable.setChanged();
        coinObservable.notifyObservers(CoinObservable.CoinObserverType.QUANTITY);
        assertEquals(new AtomicReference<>("Updated class observer.CoinObservable with attribute: QUANTITY").toString(), compare.toString());
        verify(observer);

        assertFalse(coinObservable.hasChanged());

    }
}