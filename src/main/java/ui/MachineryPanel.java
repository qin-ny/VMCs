package ui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MachineryPanel extends Panel implements InterfaceMachineryPanel {

    public MachineryPanel() {
        this.name = "Machinery Panel";
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
    public void init() {

    }

    @Override
    public void exit() {

    }
}
