package gui;

import OSPABA.SimState;
import OSPABA.Simulation;
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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    //fields
    private JTextField numberOfAField;
    private JTextField numberOfBField;
    private JTextField numberOfCField;
    private JTextField numberOfWPField;

    private JPanel centerPanel;

    //private JLabel sizeOfQueueMorenia;
    //private JLabel sizeOfQueueStavania;
    //private JLabel sizeOfQueueKovania;

    private boolean animation = false;
    private boolean stopFlag = false;

    private XYSeries series;
    private XYSeries seriesUp;
    private XYSeries seriesDown;

    private DoubleTablePanel resultsTablePanel;


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
        furnitureTablePanel.setBorder(BorderFactory.createTitledBorder("Furniture in system"));
        centerPanel.add(furnitureTablePanel);
        //this.add(furnitureTablePanel, BorderLayout.CENTER);

        this.storageTablePanel = new FurnitureQueuePanel(new SimQueue<>());
        storageTablePanel.setBorder(BorderFactory.createTitledBorder("Storage"));
        centerPanel.add(storageTablePanel);

        this.workingPlacesPanel = new WorkingPlacesPanel(new WorkingPlaces(0, simulation));
        workingPlacesPanel.setBorder(BorderFactory.createTitledBorder("Working places"));
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
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        this.simulationTimeLabel = new JLabel("Simulation time: 0.0");
        topPanel.add(simulationTimeLabel);

        this.add(topPanel, BorderLayout.NORTH);

        centerPanel = new JPanel();
        this.initCenter();

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        this.resultsTablePanel = new DoubleTablePanel();
        leftPanel.add(this.resultsTablePanel);

        this.add(leftPanel, BorderLayout.WEST);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Control"));
        add(controlPanel, BorderLayout.SOUTH);

        JTextField replicationCountField = new JTextField("500");
        replicationCountField.setMaximumSize(new Dimension(50, 25));
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(new JLabel("replications:"));
        controlPanel.add(Box.createHorizontalStrut(5));
        controlPanel.add(replicationCountField);
        controlPanel.add(Box.createHorizontalStrut(10));

        numberOfAField = new JTextField("6");
        numberOfAField.setMaximumSize(new Dimension(50, 25));
        controlPanel.add(new JLabel("A:"));
        controlPanel.add(Box.createHorizontalStrut(5));
        controlPanel.add(numberOfAField);
        controlPanel.add(Box.createHorizontalStrut(10));

        numberOfBField = new JTextField("6");
        numberOfBField.setMaximumSize(new Dimension(50, 25));
        controlPanel.add(new JLabel("B:"));
        controlPanel.add(Box.createHorizontalStrut(5));
        controlPanel.add(numberOfBField);
        controlPanel.add(Box.createHorizontalStrut(10));

        numberOfCField = new JTextField("39");
        numberOfCField.setMaximumSize(new Dimension(50, 25));
        controlPanel.add(new JLabel("C:"));
        controlPanel.add(Box.createHorizontalStrut(5));
        controlPanel.add(numberOfCField);
        controlPanel.add(Box.createHorizontalStrut(10));

        numberOfWPField = new JTextField("51");
        numberOfWPField.setMaximumSize(new Dimension(50, 25));
        controlPanel.add(new JLabel("WP:"));
        controlPanel.add(Box.createHorizontalStrut(5));
        controlPanel.add(numberOfWPField);
        controlPanel.add(Box.createHorizontalStrut(10));

        JButton startSim = new JButton("Start Simulation");
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton startAnimationButton = new JButton("Start Animation");
        startAnimationButton.setEnabled(false);


        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            stopFlag = false;

            this.startSimulation();
            stopButton.setEnabled(true);
            startSim.setEnabled(false);
            startAnimationButton.setEnabled(true);

        });

        stopButton.addActionListener(e -> {
            stopFlag = true;
            simulation.stopSimulation();
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            startSim.setEnabled(true);
            startAnimationButton.setEnabled(false);
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

        JSlider durationSlider = new JSlider(1, 4, 4);
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

        startSim.addActionListener(e -> {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            startSim.setEnabled(false);
            startAnimationButton.setEnabled(false);
            centerPanel.removeAll();
            if (simulation != null) {
                simulation.stopSimulation();
                if (simulation.animatorExists()) {
                    simulation.removeAnimator();
                }
            }

            simulation = new MySimulation();
            simulation.settings(
                    Integer.parseInt(numberOfAField.getText()),
                    Integer.parseInt(numberOfBField.getText()),
                    Integer.parseInt(numberOfCField.getText()),
                    Integer.parseInt(numberOfWPField.getText())
            );

            series = new XYSeries("time in system");
            seriesUp = new XYSeries("Upper bound");
            seriesDown = new XYSeries("Lower bound");
            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(series);
            dataset.addSeries(seriesUp);
            dataset.addSeries(seriesDown);

            JFreeChart chart = ChartFactory.createXYLineChart(
                    "Furniture",
                    "Time",
                    "time in system (h)",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            XYPlot plot = chart.getXYPlot();
            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setAutoRange(true);
            rangeAxis.setAutoRangeIncludesZero(false);

            ChartPanel chartPanel = new ChartPanel(chart);
            centerPanel.add(chartPanel);
            centerPanel.revalidate();
            centerPanel.repaint();

            simulation.onReplicationDidFinish(s -> {
                System.out.println("Replication finished: " + simulation.currentReplication());

                series.add(simulation.currentReplication(), simulation.getPriemernaDobaObjednavkyVSystemeStat().mean() / 3600000);
                if (simulation.currentReplication() > 2) {
                    double[] confidenceInterval = simulation.getPriemernaDobaObjednavkyVSystemeStat().confidenceInterval_95();
                    seriesUp.add(simulation.currentReplication(), confidenceInterval[0] / 3600000);
                    seriesDown.add(simulation.currentReplication(), confidenceInterval[1] / 3600000);

                    this.resultsTablePanel.setData(List.of(
                            new Object[]{
                                    "Average time in system",
                                    simulation.getPriemernaDobaObjednavkyVSystemeStat().mean() / 3600000,
                                    confidenceInterval[0] / 3600000,
                                    confidenceInterval[1] / 3600000
                            },
                            new Object[]{
                                    "Average Storage size",
                                    simulation.getAvgSizeOfStorageStat().mean(),
                                    simulation.getAvgSizeOfStorageStat().confidenceInterval_95()[0],
                                    simulation.getAvgSizeOfStorageStat().confidenceInterval_95()[1]
                            },
                            new Object[]{
                                    "Average Pickling Queue size",
                                    simulation.getAvgSizeOfPickingStat().mean(),
                                    simulation.getAvgSizeOfPickingStat().confidenceInterval_95()[0],
                                    simulation.getAvgSizeOfPickingStat().confidenceInterval_95()[1]
                            },
                            new Object[]{
                                    "Average Building Queue size",
                                    simulation.getAvgSizeOfBuildingStat().mean(),
                                    simulation.getAvgSizeOfBuildingStat().confidenceInterval_95()[0],
                                    simulation.getAvgSizeOfBuildingStat().confidenceInterval_95()[1]
                            },
                            new Object[]{
                                    "Average Forging Queue size",
                                    simulation.getAvgSizeOfForgingStat().mean(),
                                    simulation.getAvgSizeOfForgingStat().confidenceInterval_95()[0],
                                    simulation.getAvgSizeOfForgingStat().confidenceInterval_95()[1]
                            },
                            new Object[]{
                                    "Workers A workload",
                                    simulation.getWorkloadA().mean(),
                                    simulation.getWorkloadA().confidenceInterval_95()[0],
                                    simulation.getWorkloadA().confidenceInterval_95()[1]
                            },
                            new Object[]{
                                    "Workers B workload",
                                    simulation.getWorkloadB().mean(),
                                    simulation.getWorkloadB().confidenceInterval_95()[0],
                                    simulation.getWorkloadB().confidenceInterval_95()[1]
                            },
                            new Object[]{
                                    "Workers C workload",
                                    simulation.getWorkloadC().mean(),
                                    simulation.getWorkloadC().confidenceInterval_95()[0],
                                    simulation.getWorkloadC().confidenceInterval_95()[1]
                            },
                            new Object[]{
                                    "Unstarted orders",
                                    simulation.getUnstartedOrdersStat().mean(),
                                    simulation.getUnstartedOrdersStat().confidenceInterval_95()[0],
                                    simulation.getUnstartedOrdersStat().confidenceInterval_95()[1]
                            }
                    ));


                }
            });

            simulation.onSimulationDidFinish(s -> {
                startSim.setEnabled(true);
                this.simulationTimeLabel.setText("Order time in system: " + (simulation.getPriemernaDobaObjednavkyVSystemeStat().mean() / 3600000) + " h");
            });

            simulation.simulateAsync(Integer.parseInt(replicationCountField.getText()), (double) 249 * 8 * 60 * 60 * 1000);

        });

        controlPanel.add(startSim);
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(startButton);
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(stopButton);
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(pauseButton);
        controlPanel.add(Box.createHorizontalStrut(10));

        controlPanel.add(new JLabel("duration:"));
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(durationSlider);
        controlPanel.add(new JLabel("speed:"));
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(speedSlider);
        controlPanel.add(Box.createHorizontalStrut(10));

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
            try {
                this.furnitureTablePanel.setFurnitureList(((MySimulation) simulation).getFurnitures());
                this.storageTablePanel.setStorage(((MySimulation) simulation).getStorage());
                this.workingPlacesPanel.setWorkingPlaces(((MySimulation) simulation).getWorkingPlaces());
                this.workersAPanel.setWorkers(((MySimulation) simulation).getWorkersA());
                this.workersBPanel.setWorkers(((MySimulation) simulation).getWorkersB());
                this.workersCPanel.setWorkers(((MySimulation) simulation).getWorkersC());
            } catch (Exception _) {}

            resultsTablePanel.setData(List.of(
                    new Object[]{
                            "Time in system",
                            ((MySimulation) simulation).agentVyroby().getOrderTimeInSystemStat().mean() / 3600000,
                            ((MySimulation) simulation).agentVyroby().getOrderTimeInSystemStat().min() / 3600000,
                            ((MySimulation) simulation).agentVyroby().getOrderTimeInSystemStat().max() / 3600000
                    },
                    new Object[]{
                            "Storage size",
                            ((MySimulation) simulation).getStorage().lengthStatistic().mean(),
                            ((MySimulation) simulation).getStorage().lengthStatistic().min(),
                            ((MySimulation) simulation).getStorage().lengthStatistic().max()
                    },
                    new Object[]{
                            "Pickling Queue size",
                            ((MySimulation) simulation).getDlzkaMoreniaStat().mean(),
                            ((MySimulation) simulation).getDlzkaMoreniaStat().min(),
                            ((MySimulation) simulation).getDlzkaMoreniaStat().max()
                    },
                    new Object[]{
                            "Building Queue size",
                            ((MySimulation) simulation).getDlzkaSkladaniaStat().mean(),
                            ((MySimulation) simulation).getDlzkaSkladaniaStat().min(),
                            ((MySimulation) simulation).getDlzkaSkladaniaStat().max()
                    },
                    new Object[]{
                            "Forging Queue size",
                            ((MySimulation) simulation).getDlzkaKovaniaStat().mean(),
                            ((MySimulation) simulation).getDlzkaKovaniaStat().min(),
                            ((MySimulation) simulation).getDlzkaKovaniaStat().max()
                    },
                    new Object[]{
                            "Workers A workload",
                            ((MySimulation) simulation).agentVyroby().getWorkersA().getAverageUtilization(),
                            ((MySimulation) simulation).agentVyroby().getWorkersA().getMinUtilization(),
                            ((MySimulation) simulation).agentVyroby().getWorkersA().getMaxUtilization()
                    },
                    new Object[]{
                            "Workers B workload",
                            ((MySimulation) simulation).agentVyroby().getWorkersB().getAverageUtilization(),
                            ((MySimulation) simulation).agentVyroby().getWorkersB().getMinUtilization(),
                            ((MySimulation) simulation).agentVyroby().getWorkersB().getMaxUtilization()
                    },
                    new Object[]{
                            "Workers C workload",
                            ((MySimulation) simulation).agentVyroby().getWorkersC().getAverageUtilization(),
                            ((MySimulation) simulation).agentVyroby().getWorkersC().getMinUtilization(),
                            ((MySimulation) simulation).agentVyroby().getWorkersC().getMaxUtilization()
                    }
            ));
        });
    }

    private void startSimulation() {
        simulation = new MySimulation();
        simulation.settings(
                Integer.parseInt(numberOfAField.getText()),
                Integer.parseInt(numberOfBField.getText()),
                Integer.parseInt(numberOfCField.getText()),
                Integer.parseInt(numberOfWPField.getText())
        );
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
        simulation.simulateAsync(1, (double) 249 * 8 * 60 * 60 * 1000);

        if (animation) {
            startAnimation();
        }
    }

    private void startAnimation() {
        centerPanel.removeAll();

        simulation.createAnimator();
        simulation.startAnimation();

        centerPanel.add(simulation.animator().canvas());
        simulation.animator().canvas().setBounds(0, 0, MainFrame.this.getWidth(), MainFrame.this.getHeight());
        simulation.animator().canvas().setVisible(true);
        simulation.setSimSpeed(simInterval, simDuration);
        simulation.animator().setSynchronizedTime(true);
    }
}
