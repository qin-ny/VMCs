package observer;

import controller.BaseController;

public interface InterfaceObserver {
    void update(Observable observable, BaseController.ObserverType observerType, Object arg);
}
