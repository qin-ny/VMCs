package observer;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

public class AuthorizationObservableTest {

    AuthorizationObservable authorizationObservable;
    InterfaceAuthorizationObserver observer;
    @Before
    public void setUp() throws Exception {
        authorizationObservable = new AuthorizationObservable();
        observer = mock(InterfaceAuthorizationObserver.class);
        authorizationObservable.addObserver(observer);
    }

    @Test
    public void notifyObservers() {
        observer.updateAuthorization(authorizationObservable, null);
        AtomicReference<String> compare = new AtomicReference<>();
        expectLastCall().andAnswer(() -> {
            String msg= "Updated " + authorizationObservable.getClass().toString();
            compare.set(msg);
            return null;
        });
        replay(observer);

        authorizationObservable.setChanged();
        authorizationObservable.notifyObservers(null);
        assertEquals(new AtomicReference<>("Updated class observer.AuthorizationObservable").toString(), compare.toString());
        verify(observer);

        assertFalse(authorizationObservable.hasChanged());
    }
}