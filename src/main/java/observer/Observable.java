package observer;

import controller.BaseController;

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
                        ((InterfaceObserver)arrLocal[i]).update(this, BaseController.ObserverType.AUTHORIZATION, arg);
                        break;
                    case "Coin":
                        ((InterfaceObserver)arrLocal[i]).update(this, BaseController.ObserverType.COIN, arg);
                        break;
                    case "Door":
                        ((InterfaceObserver)arrLocal[i]).update(this, BaseController.ObserverType.DOOR, arg);
                        break;
                    case "Slot":
                        ((InterfaceObserver)arrLocal[i]).update(this, BaseController.ObserverType.SLOT, arg);
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
