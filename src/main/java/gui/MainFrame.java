package gui;

import OSPABA.SimState;
import OSPABA.Simulation;
import OSPAnimator.AnimImageItem;
import OSPAnimator.AnimTextItem;
import OSPDataStruct.SimQueue;
import gui.furniture.FurnitureTablePanel;
import gui.furniture.FurnituresTablePanel;
import gui.worker.WorkersPanel;
import gui.workingplace.WorkingPlacesPanel;
import simulation.MySimulation;
import simulation.TimeFunctions;
import worker.Workers;
import workingplace.WorkingPlaces;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.SwingUtilities.invokeLater;

public class MainFrame extends JFrame implements OSPABA.ISimDelegate {
    private MySimulation simulation;
    private FurnitureTablePanel furnitureTablePanel;
    private FurnituresTablePanel storageTablePanel;
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

    public MainFrame() {
        setTitle("Simulation");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        this.init();

        this.setVisible(true);
    }

    private void initCenter(){
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        this.add(centerPanel, BorderLayout.CENTER);

        this.furnitureTablePanel = new FurnitureTablePanel(new ArrayList<>());
        centerPanel.add(furnitureTablePanel);
        //this.add(furnitureTablePanel, BorderLayout.CENTER);

        this.storageTablePanel = new FurnituresTablePanel(new SimQueue<>());
        centerPanel.add(storageTablePanel);

        this.workingPlacesPanel = new WorkingPlacesPanel(new WorkingPlaces(0, simulation));
        centerPanel.add(workingPlacesPanel);

        this.workersAPanel = new WorkersPanel(new Workers(0, 'A', simulation));
        this.workersBPanel = new WorkersPanel(new Workers(0, 'B', simulation));
        this.workersCPanel = new WorkersPanel(new Workers(0, 'C', simulation));

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


        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    simulation = new MySimulation();
                    centerPanel.removeAll();
                    if(simulation.animatorExists()) {
                        simulation.removeAnimator();
                    }
                    initCenter();
                    centerPanel.revalidate();
                    centerPanel.repaint();
                    simulation.registerDelegate(MainFrame.this);
                    simulation.setSimSpeed(simInterval, simDuration);
                    simulation.simulate(Integer.parseInt(replicationCountField.getText()), (double) 249 * 8 * 60 * 60 * 1000);
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
                    centerPanel.removeAll();

                    simulation = new MySimulation();
                    simulation.createAnimator();
                    AnimImageItem animItem = new AnimImageItem("src/main/java/workerA.png", 40, 40);
                    animItem.setPosition(new Point(100, 100));
                    simulation.animator().register(animItem);

                    centerPanel.add(simulation.animator().canvas());
                    //this.setLayout(null);
                    simulation.animator().canvas().setBounds(0, 0, MainFrame.this.getWidth(), MainFrame.this.getHeight());
                    simulation.animator().canvas().setVisible(true);
                    simulation.animator().setSimSpeed(simInterval, simDuration);
                    simulation.animator().setSynchronizedTime(true);
//                    try {
//                        simulation.animator().setBackgroundImage(ImageIO.read(new File("src/main/java/workerA.png")));
//                    } catch (IOException ex) {
//                        throw new RuntimeException(ex);
//                    }
                    //centerPanel.revalidate();
                    simulation.simulate(1);
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
    }

    @Override
    public void refresh(Simulation simulation) {
        invokeLater(() -> {
            this.simulationTimeLabel.setText("Simulation time: " + TimeFunctions.toHumanTime(simulation.currentTime()));
            this.furnitureTablePanel.setFurnitureList(((MySimulation) simulation).getFurnitures());
            this.storageTablePanel.setQueue(((MySimulation) simulation).getStorage());
            this.workingPlacesPanel.setWorkingPlaces(((MySimulation) simulation).getWorkingPlaces());
            this.workersAPanel.setWorkers(((MySimulation) simulation).getWorkersA());
            this.workersBPanel.setWorkers(((MySimulation) simulation).getWorkersB());
            this.workersCPanel.setWorkers(((MySimulation) simulation).getWorkersC());
        });
        //this.simulationTimeLabel.setText("Simulation time: " + Mc.toHumanTime(simulation.currentTime()));
    }
}
