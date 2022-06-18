package handler;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MaintainerPanel extends Panel {


    public MaintainerPanel() {
        this.name = "Maintainer Panel";
        this.title = "VMCs - " + name;
        this.caption.setText(name);
    }

    @Override
    protected BorderPane getBorderPane() {
        BorderPane bPane = new BorderPane();
        return bPane;
    }

    @Override
    protected HBox getCenterHBox() {
        return null;
    }

    @Override
    public void exit() {

    }
}
