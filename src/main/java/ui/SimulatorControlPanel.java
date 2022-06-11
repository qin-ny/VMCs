package ui;

import controller.OperatorController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Start;

import java.util.ArrayList;
import java.util.List;

public class SimulatorControlPanel extends Panel implements InterfaceSimulatorControlPanel {

    private Start start_obj;

    public SimulatorControlPanel(Start start_obj) {
        this.name = "Simulator Control Panel";
        title = "VMCs - " + name;
        this.caption.setText(name);
        this.stage.setTitle(title);
        this.start_obj = start_obj;
    }

    private List<Button> getActiveButtons() {
        List<Button> buttons = new ArrayList<>();
//        List<Panel> activatePanels = start_obj.getActivatingPanels();
//        for (Panel panel: activatePanels) {
//            Button button = new Button("Activate " + panel.name);
//            setButtonStyle(button, 200, 0);

//            button.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent actionEvent) {
//                    Button bu = (Button) actionEvent.getSource();
//                }
//            });
//            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
//                @Override
//                public void handle(MouseEvent mouseEvent) {
////                mouseEvent.getClickCount() == 1 &&
//                    if (mouseEvent.getButton().name().equals(MouseButton.PRIMARY.name())){
//                        if (isSimulated()) {
//                            activePanel(panel);
//                        } else {
//                            createAlert(Alert.AlertType.WARNING, "System hasn't begun the simulation yet!");
//
//                        }
//                    }
//                }
//            });
//
//            buttons.add(button);
//        }
        return buttons;
    }

    private Button getBeginButton() {
        Button button = new Button("Begin Simulation");
        setButtonStyle(button, 200, 0);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().name().equals(MouseButton.PRIMARY)){
                    beginSimulation();
                }
            }
        });
        return button;
    }

    private Button getEndButton() {
        Button button = new Button("End Simulation");
        setButtonStyle(button, 200, 0);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().name().equals(MouseButton.PRIMARY)){
                    endSimulation();
                }
            }
        });
        return button;
    }


    private TextField getTextField() {
        TextField text_field = new TextField();
        Tooltip tt = new Tooltip("this is the reminding");
        text_field.setTooltip(tt);
        text_field.setPromptText("this is the prompt text");

        text_field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.length() > 7){
                    text_field.setText(s);
                }
            }
        });
        return  text_field;
    }


    protected BorderPane getBorderPane() {
        BorderPane bPane = new BorderPane();
        bPane.setPadding(new Insets(10));
//        bPane.setPrefWidth(400);
//        bPane.setPrefHeight(800);

        BorderPane bPaneTop = getTopPane();
        bPane.setTop(bPaneTop);

        HBox hb = getCenterHBox();
        VBox vb_super = (VBox)hb.getChildren().get(0);

        bPane.getChildren().add(hb);
        bPane.setCenter(vb_super);
        return bPane;
    }

    protected HBox getCenterHBox() {
        HBox hb = new HBox();

        VBox vb_top = new VBox();
        VBox vb_bottom = new VBox();
        VBox vb_super = new VBox();

        vb_top.setAlignment(Pos.CENTER);
        vb_bottom.setAlignment(Pos.CENTER);
        vb_super.setAlignment(Pos.CENTER);
        hb.setAlignment(Pos.CENTER);

        Button beginButton = getBeginButton();
        Button endButton = getEndButton();
        vb_top.getChildren().add(beginButton);
        vb_top.getChildren().add(endButton);
        vb_top.setSpacing(10);

        List<Button> activeButtons = getActiveButtons();
        vb_bottom.getChildren().addAll(activeButtons);
        vb_bottom.setSpacing(10);

        vb_super.getChildren().add(vb_top);
        vb_super.getChildren().add(vb_bottom);
        vb_super.setSpacing(20);

        hb.getChildren().add(vb_super);
        return hb;
    }

    public void exit() {

    }

    @Override
    public void beginSimulation() {

    }

    @Override
    public void endSimulation() {

    }

    @Override
    public boolean isSimulated() {
        return true;
    }

    @Override
    public void activePanel(Panel panel) {
        panel.init();
    }
}
