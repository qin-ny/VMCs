package ui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MaintainerPanel extends Panel {


    public MaintainerPanel() {
        this.name = "Maintainer Panel";
        this.title = "VMCs - " + name;
        this.caption.setText(name);
        this.stage.setTitle(title);
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
