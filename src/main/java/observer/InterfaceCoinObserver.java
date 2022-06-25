package observer;

public interface InterfaceCoinObserver extends InterfaceObserver {

    public void updateCoin(CoinObservable coin, Object arg);
}
