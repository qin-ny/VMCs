package objects;

import observer.Observable;

public class Door extends Observable {

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
