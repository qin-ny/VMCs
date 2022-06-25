package observer;

public interface InterfaceAuthorizationObserver extends InterfaceObserver {
    public void updateAuthorization(AuthorizationObservable authorization, Object arg);
}
