package observer;

public interface InterfaceDoorObserver extends InterfaceObserver {

    public void updateDoor(DoorObservable door, Object arg);
}
