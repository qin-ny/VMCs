package observer;

import java.util.Vector;

public class AuthorizationObservable {

    private transient boolean changed = false;
    private transient Vector authorizationObs = new Vector();

    public AuthorizationObservable() {
    }

    protected synchronized void setAuthorizationChanged() {
        changed = true;
    }

    protected synchronized void clearAuthorizationChange() {
        changed = false;
    }

    protected synchronized boolean hasAuthorizationChanged() {return changed;}

    public void notifyAuthorizationObservers() {notifyAuthorizationObservers(null);}

    public void notifyAuthorizationObservers(Object arg) {
        Object[] arrLocal;
        synchronized (this) {
            if (!changed)
                return;
            arrLocal = authorizationObs.toArray();
            clearAuthorizationChange();
        }
        for (int i = arrLocal.length-1; i>=0; i--)
            ((InterfaceAuthorizationObserver)arrLocal[i]).updateAuthorization(this, arg);
    }

    public synchronized void addAuthorizationObserver(InterfaceAuthorizationObserver obs) {
        if (authorizationObs == null) authorizationObs = new Vector();
        if (obs == null) throw new NullPointerException();
        if (!authorizationObs.contains(obs)) {authorizationObs.addElement(obs);}
    }
    public synchronized void deleteAuthorizationObserver(InterfaceAuthorizationObserver obs) {
        authorizationObs.removeElement(obs);
    }
}
