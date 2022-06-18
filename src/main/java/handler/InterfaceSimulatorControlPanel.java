package handler;

public interface InterfaceSimulatorControlPanel {

    public void beginSimulation();

    public void endSimulation();

    public boolean isSimulated();

    public void activePanel(Panel panel);
}
