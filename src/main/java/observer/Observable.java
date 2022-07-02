package observer;

import java.util.Vector;

public class Observable {
    protected transient boolean changed = false;
    protected transient Vector obs;

    public Observable() {
        obs = new Vector();
    }

    protected synchronized void setChanged() {
        changed = true;
    }

    protected synchronized void clearChange() {
        changed = false;
    }

    protected synchronized boolean hasChanged() {return changed;}

    public void notifyObservers() {notifyObservers(null);}

    public void notifyObservers(Object arg) {
            Object[] arrLocal;
            synchronized (this) {
                if (!changed)
                    return;
                arrLocal = obs.toArray();
                clearChange();
            }
            String[] slice = this.getClass().getName().split("\\.");
//            System.out.println(slice[slice.length-1]);
            for (int i = arrLocal.length-1; i>=0; i--)
                switch (slice[slice.length-1]) {
                    case "Machine":
                        ((InterfaceAuthorizationObserver)arrLocal[i]).updateAuthorization(this, arg);
                        break;
                    case "Coin":
                        ((InterfaceCoinObserver)arrLocal[i]).updateCoin(this, arg);
                        break;
                    case "Door":
                        ((InterfaceDoorObserver)arrLocal[i]).updateDoor(this, arg);
                        break;
                    case "Slot":
                        ((InterfaceSlotObserver)arrLocal[i]).updateSlot(this, arg);
                        break;
                }
    };

    public synchronized void addObserver(InterfaceObserver inputOb) {
        if (obs == null) obs = new Vector();
        if (!obs.contains(inputOb)) {obs.addElement(inputOb);}
    }
    public synchronized void deleteObserver(InterfaceObserver inputOb) {
        obs.removeElement(inputOb);
    }
}
