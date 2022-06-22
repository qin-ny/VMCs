package observer;

import java.util.Vector;

public class CoinObservable {
    public enum CoinObserverType {
        NAME,
        WEIGHT,
        QUANTITY,
        CURRENT_ENTERED_QUANTITY,
        TOTAL_QUANTITY
    }

    private transient boolean changed = false;
    private transient Vector coinObs = new Vector();

    public CoinObservable() {
    }

    protected synchronized void setCoinChanged() {
        changed = true;
    }

    protected synchronized void clearCoinChange() {
        changed = false;
    }

    protected synchronized boolean hasCoinChanged() {return changed;}

    public void notifyCoinObservers() {notifyCoinObservers(null);}

    public void notifyCoinObservers(Object arg) {
        Object[] arrLocal;
        synchronized (this) {
            if (!changed)
                return;
            arrLocal = coinObs.toArray();
            clearCoinChange();
        }
        for (int i = arrLocal.length-1; i>=0; i--)
            ((InterfaceCoinObserver)arrLocal[i]).updateCoin(this, arg);
    }

    public synchronized void addCoinObserver(InterfaceCoinObserver obs) {
        if (coinObs == null) coinObs = new Vector();
        if (obs == null) throw new NullPointerException();
        if (!coinObs.contains(obs)) {coinObs.addElement(obs);}
    }
    public synchronized void deleteCoinObserver(InterfaceCoinObserver obs) {
        coinObs.removeElement(obs);
    }
}
