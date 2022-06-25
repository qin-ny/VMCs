package observer;

import java.util.Vector;

public class SlotObservable extends Observable {
    public enum SlotObserverType {
        PRICE,
        NAME,
        QUANTITY
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
            ((InterfaceSlotObserver)arrLocal[i]).updateSlot(this, arg);
    }
}
