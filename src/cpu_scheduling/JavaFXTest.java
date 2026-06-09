package cpu_scheduling;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXTest extends Application {

    private List<Process> processes = new ArrayList<>();
    private int pidCounter = 1;
    
    private List<Process> copyProcesses() {

        List<Process> copy = new ArrayList<>();

        for(Process p : processes) {

            copy.add(
                new Process(
                    p.getPid(),
                    p.getArrivalTime(),
                    p.getBurstTime(),
                    p.getPriority()
                )
            );
        }

        return copy;
    }

    @Override
    public void start(Stage stage) {

        VBox root = new VBox(10);

        Label title = new Label("CPU Scheduling Simulator");

        ComboBox<String> algorithmBox = new ComboBox<>();

        algorithmBox.getItems().addAll(
                "FCFS",
                "SJF",
                "SRTF",
                "Round Robin",
                "Priority Scheduling"
        );

        algorithmBox.setValue("FCFS");
     

        HBox algoRow = new HBox(10);
        algoRow.getChildren().addAll(
                new Label("Algorithm:"),
                algorithmBox
        );

        TextField arrivalField = new TextField();

        HBox arrivalRow = new HBox(10);
        arrivalRow.getChildren().addAll(
                new Label("Arrival Time:"),
                arrivalField
        );

        TextField burstField = new TextField();

        HBox burstRow = new HBox(10);
        burstRow.getChildren().addAll(
                new Label("Burst Time:"),
                burstField
        );

        TextField priorityField = new TextField();

        HBox priorityRow = new HBox(10);
        priorityRow.getChildren().addAll(
                new Label("Priority:"),
                priorityField
        );
        
        TextField quantumField = new TextField();

        HBox quantumRow = new HBox(10);
        quantumRow.getChildren().addAll(
                new Label("Quantum:"),
                quantumField
        );
        quantumRow.setVisible(false);
        quantumRow.setManaged(false);

        priorityRow.setVisible(false);
        priorityRow.setManaged(false);

        algorithmBox.setOnAction(e -> {

            String algo = algorithmBox.getValue();

            if(algo.equals("Priority Scheduling")) {

                priorityRow.setVisible(true);
                priorityRow.setManaged(true);

                quantumRow.setVisible(false);
                quantumRow.setManaged(false);
            }

            else if(algo.equals("Round Robin")) {

                priorityRow.setVisible(false);
                priorityRow.setManaged(false);

                quantumRow.setVisible(true);
                quantumRow.setManaged(true);
            }

            else {

                priorityRow.setVisible(false);
                priorityRow.setManaged(false);

                quantumRow.setVisible(false);
                quantumRow.setManaged(false);
            }
        });
        
        TableView<Process> table =
                new TableView<>();

        TableColumn<Process,String> pidCol =
                new TableColumn<>("PID");

        pidCol.setCellValueFactory(
                new PropertyValueFactory<>("pid")
        );

        TableColumn<Process,Integer> atCol =
                new TableColumn<>("AT");

        atCol.setCellValueFactory(
                new PropertyValueFactory<>("arrivalTime")
        );

        TableColumn<Process,Integer> btCol =
                new TableColumn<>("BT");

        btCol.setCellValueFactory(
                new PropertyValueFactory<>("burstTime")
        );

        TableColumn<Process,Integer> prCol =
                new TableColumn<>("Priority");

        prCol.setCellValueFactory(
                new PropertyValueFactory<>("priority")
        );
        
        TableColumn<Process,Integer> ctCol =
                new TableColumn<>("CT");

        ctCol.setCellValueFactory(
                new PropertyValueFactory<>("completionTime")
        );
        
        TableColumn<Process,Integer> tatCol =
                new TableColumn<>("TAT");

        tatCol.setCellValueFactory(
                new PropertyValueFactory<>("turnaroundTime")
        );
        
        TableColumn<Process,Integer> wtCol =
                new TableColumn<>("WT");

        wtCol.setCellValueFactory(
                new PropertyValueFactory<>("waitingTime")
        );

        table.getColumns().addAll(
                pidCol,
                atCol,
                btCol,
                prCol,
                ctCol,
                tatCol,
                wtCol
        );
        
        table.setPrefHeight(200);
        
        
        Button addButton =
                new Button("Add Process");
        
        Button runButton =
                new Button("Run Algorithm");

        Label resultLabel =
                new Label();
        
        HBox ganttBox = new HBox();

        Label ganttTitle =
                new Label("Gantt Chart:");
        
        runButton.setOnAction(e -> {

            List<Process> temp =
                    copyProcesses();

            String algo =
                    algorithmBox.getValue();

            if(algo.equals("FCFS")) {

                FCFS fcfs = new FCFS();
                fcfs.schedule(temp);
            }

            else if(algo.equals("SJF")) {

                SJF sjf = new SJF();
                sjf.schedule(temp);
            }

            else if(algo.equals("SRTF")) {

                SRTF srtf = new SRTF();
                srtf.schedule(temp);
            }

            else if(algo.equals("Priority Scheduling")) {

                priorityscheduling ps =
                        new priorityscheduling();

                ps.schedule(temp);
            }

            else if(algo.equals("Round Robin")) {

                int quantum =
                        Integer.parseInt(
                            quantumField.getText()
                        );

                RR rr = new RR();

                rr.schedule(
                        temp,
                        quantum
                );
            }

            table.getItems().clear();

            table.getItems().addAll(temp);

            MetricCalculate m =
                    new MetricCalculate();

            resultLabel.setText(
                "Average TAT = "
                + m.AvgTAT(temp)
                +
                "    Average WT = "
                + m.AvgWaitingTime(temp)
            );
        });

       
        addButton.setOnAction(e -> {

            try {

                int at =
                        Integer.parseInt(
                                arrivalField.getText()
                        );

                int bt =
                        Integer.parseInt(
                                burstField.getText()
                        );

                int pr = 0;

                if(algorithmBox.getValue().equals("Priority Scheduling")) {

                    pr = Integer.parseInt(
                            priorityField.getText()
                    );
                }
                Process p =
                        new Process(
                                "P" + pidCounter,
                                at,
                                bt,
                                pr
                        );

                processes.add(p);
                table.getItems().add(p);

                pidCounter++;

                arrivalField.clear();
                burstField.clear();
                priorityField.clear();
                quantumField.clear();

            } catch (Exception ex) {
            	System.out.println("Invalid Input");
            }
        });

        root.getChildren().addAll(
                title,
                algoRow,
                arrivalRow,
                burstRow,
                priorityRow,
                quantumRow,
                addButton,
                table,
                runButton,
                resultLabel,
                ganttTitle,
                ganttBox
        );

        Scene scene =
                new Scene(root, 500, 450);

        stage.setTitle(
                "CPU Scheduling Simulator"
        );

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}