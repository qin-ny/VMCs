package observer;

import java.util.Vector;

public class AuthorizationObservable extends Observable {

    public AuthorizationObservable() {
        super();
    }

    public void notifyObservers(Object arg) {
        Object[] arrLocal;
        synchronized (this) {
            if (!changed)
                return;
            arrLocal = obs.toArray();
            clearChange();
        }
        for (int i = arrLocal.length-1; i>=0; i--)
            ((InterfaceAuthorizationObserver)arrLocal[i]).updateAuthorization(this, arg);
    }
}
