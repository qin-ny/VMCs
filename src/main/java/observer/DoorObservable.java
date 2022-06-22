package observer;

import java.util.Vector;

public class DoorObservable {
    private boolean changed = false;
    private Vector doorObs = new Vector();

    public DoorObservable() {
    }

    protected synchronized void setDoorChanged() {
        changed = true;
    }

    protected synchronized void clearDoorChange() {
        changed = false;
    }

    protected synchronized boolean hasDoorChanged() {return changed;}

    public void notifyDoorObservers() {notifyDoorObservers(null);}

    public void notifyDoorObservers(Object arg) {
        Object[] arrLocal;
        synchronized (this) {
            if (!changed)
                return;
            arrLocal = doorObs.toArray();
            clearDoorChange();
        }
        for (int i = arrLocal.length-1; i>=0; i--)
            ((InterfaceDoorObserver)arrLocal[i]).updateDoor(this, arg);
    }

    public synchronized void addDoorObserver(InterfaceDoorObserver obs) {
        if (doorObs == null) doorObs = new Vector();
        if (obs == null) throw new NullPointerException();
        if (!doorObs.contains(obs)) {doorObs.addElement(obs);}
    }
    public synchronized void deleteDoorObserver(InterfaceDoorObserver obs) {
        doorObs.removeElement(obs);
    }
}
