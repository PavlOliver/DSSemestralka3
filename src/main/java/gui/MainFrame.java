package gui;

import OSPABA.SimState;
import OSPABA.Simulation;
import OSPAnimator.AnimTextItem;
import OSPDataStruct.SimQueue;
import gui.furniture.FurnitureQueuePanel;
import gui.furniture.FurnitureTablePanel;
import gui.worker.WorkersPanel;
import gui.workingplace.WorkingPlacesPanel;
import simulation.MySimulation;
import simulation.TimeFunctions;
import worker.WorkerType;
import worker.Workers;
import workingplace.WorkingPlaces;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.SwingUtilities.invokeLater;

public class MainFrame extends JFrame implements OSPABA.ISimDelegate {
    private MySimulation simulation;
    private FurnitureTablePanel furnitureTablePanel;
    private FurnitureQueuePanel storageTablePanel;
    private WorkingPlacesPanel workingPlacesPanel;
    private WorkersPanel workersAPanel;
    private WorkersPanel workersBPanel;
    private WorkersPanel workersCPanel;

    //speed
    private double simInterval = 1000d;
    private double simDuration = 0.001d;


    //labels
    private JLabel simulationTimeLabel;

    private JPanel centerPanel;

    private JLabel sizeOfQueueMorenia;
    private JLabel sizeOfQueueStavania;
    private JLabel sizeOfQueueKovania;

    private boolean animation = false;
    private boolean stopFlag = false;


    public MainFrame() {
        setTitle("Simulation");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        this.init();

        this.setVisible(true);
    }

    private void initCenter() {
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        this.add(centerPanel, BorderLayout.CENTER);

        this.furnitureTablePanel = new FurnitureTablePanel(new ArrayList<>());
        centerPanel.add(furnitureTablePanel);
        //this.add(furnitureTablePanel, BorderLayout.CENTER);

        this.storageTablePanel = new FurnitureQueuePanel(new SimQueue<>());
        centerPanel.add(storageTablePanel);

        this.workingPlacesPanel = new WorkingPlacesPanel(new WorkingPlaces(0, simulation));
        centerPanel.add(workingPlacesPanel);

        this.workersAPanel = new WorkersPanel(new Workers(0, WorkerType.A, simulation));
        this.workersBPanel = new WorkersPanel(new Workers(0, WorkerType.B, simulation));
        this.workersCPanel = new WorkersPanel(new Workers(0, WorkerType.C, simulation));

        JPanel workersPanel = new JPanel();
        workersPanel.setLayout(new BoxLayout(workersPanel, BoxLayout.Y_AXIS));
        workersPanel.setBorder(BorderFactory.createTitledBorder("Workers"));
        workersPanel.add(workersAPanel);
        workersPanel.add(workersBPanel);
        workersPanel.add(workersCPanel);

        centerPanel.add(workersPanel);
    }

    private void init() {
        this.simulationTimeLabel = new JLabel("Simulation time: 0.0");
        this.add(simulationTimeLabel, BorderLayout.NORTH);

        centerPanel = new JPanel();
        this.initCenter();

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Control"));
        add(controlPanel, BorderLayout.SOUTH);

        JTextField replicationCountField = new JTextField("1");
        replicationCountField.setMaximumSize(new Dimension(50, 25));
        controlPanel.add(replicationCountField);

        this.sizeOfQueueMorenia = new JLabel("Size of queue Morenia: 0");
        this.sizeOfQueueStavania = new JLabel("Size of queue Stavania: 0");
        this.sizeOfQueueKovania = new JLabel("Size of queue Kovania: 0");
        controlPanel.add(sizeOfQueueMorenia);
        controlPanel.add(sizeOfQueueStavania);
        controlPanel.add(sizeOfQueueKovania);


        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");

        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            stopFlag = false;

            //animation = false;
            this.startSimulation();
            stopButton.setEnabled(true);
        });

        stopButton.addActionListener(e -> {
            stopFlag = true;
            simulation.stopSimulation();
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
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

        JSlider durationSlider = new JSlider(1, 4, 1);
        durationSlider.setMaximumSize(new Dimension(200, 100));
        durationSlider.setMajorTickSpacing(1);
        durationSlider.setPaintTicks(true);
        durationSlider.setSnapToTicks(true);
        durationSlider.setPaintLabels(true);
        durationSlider.addChangeListener(e -> {
            simDuration = 1 / Math.pow(10, durationSlider.getValue() - 1);
            if (simulation != null) {
                simulation.setSimSpeed(simInterval, simDuration);
            }
        });

        JSlider speedSlider = new JSlider(1, 5, 1);
        speedSlider.setMaximumSize(new Dimension(200, 100));
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setSnapToTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(e -> {
            simInterval = Math.pow(10, speedSlider.getValue() - 1) * 1000;
            if (simulation != null) {
                simulation.setSimSpeed(simInterval, simDuration);
            }
        });

        JButton startAnimationButton = new JButton("Start Animation");
        startAnimationButton.addActionListener(e -> {
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    if (!animation) {
                        animation = true;
                        startAnimationButton.setText("Stop Animation");

                        startAnimation();
                    } else {
                        animation = false;
                        startAnimationButton.setText("Start Animation");
                        centerPanel.removeAll();
                        if (simulation.animatorExists()) {
                            simulation.removeAnimator();
                        }
                        initCenter();
                        centerPanel.revalidate();
                        centerPanel.repaint();


                    }

                    return null;
                }

                @Override
                protected void done() {
                    startAnimationButton.setEnabled(true);
                }
            };
            worker.execute();
        });

        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(pauseButton);

        controlPanel.add(durationSlider);
        controlPanel.add(speedSlider);

        controlPanel.add(startAnimationButton);
    }

    @Override
    public void simStateChanged(Simulation simulation, SimState simState) {
        System.out.println("Simulation state changed: " + simState);
        if (simState == SimState.stopped && !stopFlag) {
            this.startSimulation();
        }
    }

    @Override
    public void refresh(Simulation simulation) {
        invokeLater(() -> {
            this.simulationTimeLabel.setText("Simulation time: " + TimeFunctions.toHumanTime(simulation.currentTime()));
            this.furnitureTablePanel.setFurnitureList(((MySimulation) simulation).getFurnitures());
            this.storageTablePanel.setStorage(((MySimulation) simulation).getStorage());
            this.workingPlacesPanel.setWorkingPlaces(((MySimulation) simulation).getWorkingPlaces());
            this.workersAPanel.setWorkers(((MySimulation) simulation).getWorkersA());
            this.workersBPanel.setWorkers(((MySimulation) simulation).getWorkersB());
            this.workersCPanel.setWorkers(((MySimulation) simulation).getWorkersC());

            this.sizeOfQueueMorenia.setText("Size of queue Morenia: " + ((MySimulation) simulation).agentC().getQueueMoreniaPriority().size());
            this.sizeOfQueueStavania.setText("Size of queue Stavania: " + ((MySimulation) simulation).agentB().getQueueSkladaniaPriority().size());
            this.sizeOfQueueKovania.setText("Size of queue Kovania: " + ((MySimulation) simulation).agentVyroby().getQueueKovaniaPriority().size());
        });
        //this.simulationTimeLabel.setText("Simulation time: " + Mc.toHumanTime(simulation.currentTime()));
    }

    private void startSimulation() {
        simulation = new MySimulation();
        centerPanel.removeAll();
        if (simulation.animatorExists()) {
            simulation.removeAnimator();
        }
        if (simulation != null) {
            simulation.stopSimulation();
        }

        initCenter();
        centerPanel.revalidate();
        centerPanel.repaint();
        simulation.registerDelegate(MainFrame.this);
        simulation.setSimSpeed(simInterval, simDuration);
        //simulation.simulate(Integer.parseInt(replicationCountField.getText()), (double) 249 * 8 * 60 * 60 * 1000);
        simulation.simulateAsync(1, (double) 249 * 8 * 60 * 60 * 1000);

        if (animation) {
            startAnimation();
        }
    }

    private void startAnimation() {
        centerPanel.removeAll();

        //simulation = new MySimulation();
        simulation.createAnimator();
        simulation.startAnimation();

        AnimTextItem simulationTimeItem = new AnimTextItem("Simulation time: 0.0");
        simulationTimeItem.setPosition(new Point(100, 500));
        simulation.animator().register(simulationTimeItem);

        centerPanel.add(simulation.animator().canvas());
        //this.setLayout(null);
        simulation.animator().canvas().setBounds(0, 0, MainFrame.this.getWidth(), MainFrame.this.getHeight());
        simulation.animator().canvas().setVisible(true);
        //simulation.animator().setSimSpeed(1, 0.1);
        simulation.setSimSpeed(simInterval, simDuration);
        simulation.animator().setSynchronizedTime(true);
    }
}
