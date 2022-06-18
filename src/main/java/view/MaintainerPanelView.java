package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MaintainerPanelView extends BaseView {

    private boolean isAuthorized;

    public MaintainerPanelView() {
        this.stage = new Stage();
        this.fxml = "maintainer_panel.fxml";
        this.css = "maintainer_panel.css";
        this.title = "VMCs - Maintainer Panel";
        this.isAuthorized = false;
    }

    public boolean getAuthorization() {
        return this.isAuthorized;
    }

    public void setAuthorization(boolean status) {
        this.isAuthorized = status;
    }

//    @Override
//    public void init() throws IOException {
//
//    };
}
