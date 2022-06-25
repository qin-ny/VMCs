package observer;

import java.util.Vector;

public abstract class Observable {
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

    public abstract void notifyObservers(Object arg);

    public synchronized void addObserver(InterfaceObserver inputOb) {
        if (obs == null) obs = new Vector();
        if (!obs.contains(inputOb)) {obs.addElement(inputOb);}
    }
    public synchronized void deleteObserver(InterfaceObserver inputOb) {
        obs.removeElement(inputOb);
    }
}
