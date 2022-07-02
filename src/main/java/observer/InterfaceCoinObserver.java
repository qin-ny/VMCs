package observer;

public interface InterfaceCoinObserver extends InterfaceObserver {

    public void updateCoin(Observable coin, Object arg);
}
