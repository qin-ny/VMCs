package observer;

public interface InterfaceAuthorizationObserver extends InterfaceObserver {
    public void updateAuthorization(Observable authorization, Object arg);
}
