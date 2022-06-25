package observer;

import java.util.Vector;

public class CoinObservable extends Observable {
    public enum CoinObserverType {
        NAME,
        WEIGHT,
        QUANTITY,
        CURRENT_ENTERED_QUANTITY,
        TOTAL_QUANTITY
    }


    public CoinObservable() {
        super();
    }

    public void notifyObservers(Object arg) {
        Object[] arrLocal;
        synchronized (this) {
            if (!changed)
                return;
            arrLocal = obs.toArray();
            clearChange();
        }
        for (int i = arrLocal.length-1; i>=0; i--)
            ((InterfaceCoinObserver)arrLocal[i]).updateCoin(this, arg);
    }
}
