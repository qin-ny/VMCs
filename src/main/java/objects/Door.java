package objects;

public class Door {

    private boolean isOpen;

    public Door(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setDoorStatus(boolean open) {
        isOpen = open;
    }


}
