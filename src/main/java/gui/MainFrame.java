package gui;

import OSPABA.SimState;
import OSPABA.Simulation;
import OSPDataStruct.SimQueue;
import gui.furniture.FurnitureTablePanel;
import gui.furniture.FurnituresTablePanel;
import simulation.Mc;
import simulation.MySimulation;
import simulation.TimeFunctions;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.SwingUtilities.invokeLater;

public class MainFrame extends JFrame implements OSPABA.ISimDelegate {
    private MySimulation simulation;
    private FurnitureTablePanel furnitureTablePanel;
    private FurnituresTablePanel storageTablePanel;

    //labels
    private JLabel simulationTimeLabel;

    public MainFrame() {
        setTitle("Simulation");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        this.init();

        this.setVisible(true);


    }

    private void init() {
        this.simulationTimeLabel = new JLabel("Simulation time: 0.0");
        this.add(simulationTimeLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        this.add(centerPanel, BorderLayout.CENTER);

        this.furnitureTablePanel = new FurnitureTablePanel(new ArrayList<>());
        centerPanel.add(furnitureTablePanel);
        //this.add(furnitureTablePanel, BorderLayout.CENTER);

        this.storageTablePanel = new FurnituresTablePanel(new SimQueue<>());
        centerPanel.add(storageTablePanel);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Control"));
        add(controlPanel, BorderLayout.SOUTH);

        JTextField replicationCountField = new JTextField("1000");
        replicationCountField.setMaximumSize(new Dimension(50, 25));
        controlPanel.add(replicationCountField);


        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    simulation = new MySimulation();
                    simulation.registerDelegate(MainFrame.this);
                    simulation.setSimSpeed(10000d, 0.001d);
                    simulation.simulate(Integer.parseInt(replicationCountField.getText()));
                    return null;
                }

                @Override
                protected void done() {
                    startButton.setEnabled(true);
                }
            };
            worker.execute();
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
            simulation.stopSimulation();
            startButton.setEnabled(true);
        });

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> {
            if (simulation.isPaused()) {
                simulation.resumeSimulation();
                pauseButton.setText("Pause");
            } else {
                simulation.pauseSimulation();
                pauseButton.setText("Resume");
            }
        });

        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(pauseButton);


    }

    @Override
    public void simStateChanged(Simulation simulation, SimState simState) {
        System.out.println("Simulation state changed: " + simState);
    }

    @Override
    public void refresh(Simulation simulation) {
        invokeLater(() -> {
            this.simulationTimeLabel.setText("Simulation time: " + TimeFunctions.toHumanTime(simulation.currentTime()));
            this.furnitureTablePanel.setFurnitureList(((MySimulation) simulation).getFurnitures());
            this.storageTablePanel.setQueue(((MySimulation) simulation).getStorage());
        });
        //this.simulationTimeLabel.setText("Simulation time: " + Mc.toHumanTime(simulation.currentTime()));
    }
}
