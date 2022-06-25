package objects;

import observer.DoorObservable;

public class Door extends DoorObservable {

    private boolean isOpen;

    public Door(boolean isOpen) {
        super();
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setDoorStatus(boolean open) {
        isOpen = open;
        setChanged();
        notifyObservers();
    }


}
