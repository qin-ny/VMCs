package observer;

import java.util.Vector;

public class SlotObservable {
    public enum SlotObserverType {
        PRICE,
        NAME,
        QUANTITY
    }

    private transient boolean changed = false;
    private transient Vector slotObs;

    public SlotObservable() {
        slotObs = new Vector();
    }

    protected synchronized void setSlotChanged() {
        changed = true;
    }

    protected synchronized void clearSlotChange() {
        changed = false;
    }

    protected synchronized boolean hasSlotChanged() {return changed;}

    public void notifySlotObservers() {notifySlotObservers(null);}

    public void notifySlotObservers(Object arg) {
        Object[] arrLocal;
        synchronized (this) {
            if (!changed)
                return;
            arrLocal = slotObs.toArray();
            clearSlotChange();
        }
        for (int i = arrLocal.length-1; i>=0; i--)
            ((InterfaceSlotObserver)arrLocal[i]).updateSlot(this, arg);
    }

    public synchronized void addSlotObserver(InterfaceSlotObserver obs) {
        if (slotObs == null) slotObs = new Vector();
        if (obs == null) throw new NullPointerException();
        if (!slotObs.contains(obs)) {slotObs.addElement(obs);}
    }
    public synchronized void deleteSlotObserver(InterfaceSlotObserver obs) {
        slotObs.removeElement(obs);
    }

}
