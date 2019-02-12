package entity;

public class GlobalClass {
    private static GlobalClass instance = new GlobalClass();
    private AppContent _appContent;

    private GlobalClass() {}

    public static GlobalClass getInstance() {
        return instance;
    }

    public void setAppContent(AppContent appContent) {
        this._appContent = appContent;
    }

    public AppContent getAppContent() {
        return _appContent;
    }
}
