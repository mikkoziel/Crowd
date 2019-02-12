package entity;

import android.app.Application;

public class GlobalClass extends Application {
    private AppContent appContent;

    public AppContent getAppContent() {
        return appContent;
    }

    public void setAppContent(AppContent appContent) {
        this.appContent = appContent;
    }
}
