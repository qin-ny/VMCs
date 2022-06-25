package observer;

import java.util.Vector;

public class DoorObservable extends Observable {

    public DoorObservable() {
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
            ((InterfaceDoorObserver)arrLocal[i]).updateDoor(this, arg);
    }

}
