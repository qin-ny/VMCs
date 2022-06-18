package controller;

import main.Start;

import java.io.IOException;

public class OperatorController extends BaseController implements InterfaceOperatorController {

    public OperatorController(Start startObj) {
        super(startObj);
    }


    @Override
    public void beginSimulation() {

    }

    @Override
    public void endSimulation() {

    }

    @Override
    public void activePanel(String panelName) {
        try {
            Start.panels.get(panelName).init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
