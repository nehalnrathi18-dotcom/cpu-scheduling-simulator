package cpu_scheduling;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Application;
import javafx.scene.control.TextArea;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


public class JavaFXTest extends Application {

    private List<Process> processes = new ArrayList<>();
    private int pidCounter = 1;
    private String[] colors = {
    	    "#FF9999",
    	    "#99CCFF",
    	    "#99FF99",
    	    "#FFD699",
    	    "#D9B3FF",
    	    "#FFB366",
    	    "#66CCCC",
    	    "#FF66B2"
    	};
    
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
        title.setStyle(
        	    "-fx-font-size:24px;" +
        	    "-fx-font-weight:bold;"
        	);

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
        
        algorithmBox.setOnAction(e -> {

            String algo = algorithmBox.getValue();

            if(algo.equals("Priority Scheduling")) {

            	prCol.setVisible(true);
                priorityRow.setVisible(true);
                priorityRow.setManaged(true);

                quantumRow.setVisible(false);
                quantumRow.setManaged(false);
            }

            else if(algo.equals("Round Robin")) {
            	prCol.setVisible(false);

                priorityRow.setVisible(false);
                priorityRow.setManaged(false);

                quantumRow.setVisible(true);
                quantumRow.setManaged(true);
            }

            else {

            	prCol.setVisible(false);
                priorityRow.setVisible(false);
                priorityRow.setManaged(false);

                quantumRow.setVisible(false);
                quantumRow.setManaged(false);
            }
        });
        
        table.setPrefHeight(300);
        prCol.setVisible(false);
   
        
        Button addButton =
                new Button("Add Process");
        
        Button runButton =
                new Button("Run Algorithm");
        Button clearButton =
                new Button("Clear All");
        Button deleteButton =
                new Button("Delete Selected");
        
        Button clearInputButton =
                new Button("Clear Inputs");
        Button compareButton =
                new Button("Compare All");
        HBox buttonRow1 = new HBox(10);

        buttonRow1.getChildren().addAll(
            addButton,
            clearInputButton,
            runButton
        );

        HBox buttonRow2 = new HBox(10);

        buttonRow2.getChildren().addAll(
            compareButton,
            clearButton,
            deleteButton
        );
        Label resultLabel =
                new Label();
        
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        
        TableView<ComparisonResult> compareTable =
                new TableView<>();

        TableColumn<ComparisonResult, String> algoCol =
                new TableColumn<>("Algorithm");

        algoCol.setCellValueFactory(
                new PropertyValueFactory<>("algorithm")
        );

        TableColumn<ComparisonResult, Double> tatCompareCol =
                new TableColumn<>("Avg TAT");

        tatCompareCol.setCellValueFactory(
                new PropertyValueFactory<>("avgTAT")
        );

        TableColumn<ComparisonResult, Double> wtCompareCol =
                new TableColumn<>("Avg WT");

        wtCompareCol.setCellValueFactory(
                new PropertyValueFactory<>("avgWT")
        );

        compareTable.getColumns().addAll(
                algoCol,
                tatCompareCol,
                wtCompareCol
        );
        algoCol.setPrefWidth(300);
        tatCompareCol.setPrefWidth(250);
        wtCompareCol.setPrefWidth(250);

        compareTable.setPrefHeight(220);
        compareTable.setColumnResizePolicy(
        	    TableView.CONSTRAINED_RESIZE_POLICY
        	);

        BarChart<String, Number> barChart =
                new BarChart<>(xAxis, yAxis);

        barChart.setTitle("Algorithm Comparison on basis of waiting time");

        xAxis.setLabel("Algorithm");
        yAxis.setLabel("Average Waiting Time");

        barChart.setPrefHeight(200);
        barChart.setLegendVisible(false);
        barChart.setAnimated(true);
        barChart.setCategoryGap(20);
        barChart.setBarGap(3);

                resultLabel.setStyle(
        	    "-fx-font-size:18px; -fx-font-weight:bold;"
        	);
        HBox ganttBox = new HBox(5);
        ScrollPane chartScroll =
                new ScrollPane(ganttBox);

        chartScroll.setFitToHeight(true);
        chartScroll.setPrefHeight(180);

        Label ganttLabel =
                new Label("Gantt Chart:");
    
        clearButton.setOnAction(e -> {

            processes.clear();

            table.getItems().clear();

            ganttBox.getChildren().clear();

            resultLabel.setText("");

            pidCounter = 1;
        });
        
        deleteButton.setOnAction(e -> {

            Process selected =
                    table.getSelectionModel()
                         .getSelectedItem();

            if(selected != null) {

                processes.remove(selected);

                table.getItems().remove(selected);

                resultLabel.setText(
                    "Process Deleted"
                );
            }
        });
        clearInputButton.setOnAction(e -> {

            arrivalField.clear();

            burstField.clear();

            priorityField.clear();

            quantumField.clear();

            resultLabel.setText("");
        });
        
        compareButton.setOnAction(e -> {

        	compareTable.getItems().clear();
        	barChart.getData().clear();

        	XYChart.Series<String, Number> series =
        	        new XYChart.Series<>();
        	
        	MetricCalculate m = new MetricCalculate();

        	double bestWT = Double.MAX_VALUE;
        	final String[] bestAlgo = {""};

        	// FCFS
        	List<Process> fcfsList = copyProcesses();

        	FCFS fcfs = new FCFS();
        	fcfs.schedule(fcfsList);

        	double fcfsWT = m.AvgWaitingTime(fcfsList);
        	series.getData().add(
        		    new XYChart.Data<>("FCFS", fcfsWT)
        		);

        	compareTable.getItems().add(
        		    new ComparisonResult(
        		        "FCFS",
        		        m.AvgTAT(fcfsList),
        		        fcfsWT
        		    )
        		);
        	if(fcfsWT < bestWT) {
        	    bestWT = fcfsWT;
        	    bestAlgo[0] = "FCFS";
        	}

        	// SJF
        	List<Process> sjfList = copyProcesses();

        	SJF sjf = new SJF();
        	sjf.schedule(sjfList);

        	double sjfWT = m.AvgWaitingTime(sjfList);
        	series.getData().add(
        		    new XYChart.Data<>("SJF", sjfWT)
        		);

        	compareTable.getItems().add(
        		    new ComparisonResult(
        		        "SJF",
        		        m.AvgTAT(sjfList),
        		        sjfWT
        		    )
        		);
        	if(sjfWT < bestWT) {
        	    bestWT = sjfWT;
        	    bestAlgo[0] = "SJF";
        	}

        	// SRTF
        	List<Process> srtfList = copyProcesses();

        	SRTF srtf = new SRTF();
        	srtf.schedule(srtfList);

        	double srtfWT = m.AvgWaitingTime(srtfList);
        	series.getData().add(
        		    new XYChart.Data<>("SRTF", srtfWT)
        		);

        	compareTable.getItems().add(
        		    new ComparisonResult(
        		        "SRTF",
        		        m.AvgTAT(srtfList),
        		        srtfWT
        		    )
        		);

        	if(srtfWT < bestWT) {
        	    bestWT = srtfWT;
        	    bestAlgo[0] = "SRTF";
        	}

        	// Priority Scheduling
        	List<Process> priorityList = copyProcesses();

        	priorityscheduling ps =
        	        new priorityscheduling();

        	ps.schedule(priorityList);

        	double priorityWT =m.AvgWaitingTime(priorityList);
        	
        	series.getData().add(
        		    new XYChart.Data<>("Priority", priorityWT)
        		);

        	compareTable.getItems().add(
        		    new ComparisonResult(
        		        "Priority",
        		        m.AvgTAT(priorityList),
        		        priorityWT
        		    )
        		);

        	if(priorityWT < bestWT) {
        	    bestWT = priorityWT;
        	    bestAlgo[0] = "Priority";
        	}

        	// Round Robin
        	List<Process> rrList = copyProcesses();

        	RR rr = new RR();

        	int quantum = 2;
        	rr.schedule(rrList, quantum);

        	double rrWT =m.AvgWaitingTime(rrList);
        	series.getData().add(
        		    new XYChart.Data<>("Round Robin", rrWT)
        		);

        	compareTable.getItems().add(
        		    new ComparisonResult(
        		        "Round Robin",
        		        m.AvgTAT(rrList),
        		        rrWT
        		    )
        		);

        	if(rrWT < bestWT) {
        	    bestWT = rrWT;
        	    bestAlgo[0] = "Round Robin";
        	}

        	resultLabel.setText(
        		    "Best Algorithm: " + bestAlgo[0] +
        		    "    Lowest Average WT: " + bestWT
        		);
        	
        	barChart.getData().add(series);
        	barChart.applyCss();
        	
        	for (int i = 0; i < series.getData().size(); i++) {

        	    XYChart.Data<String, Number> data =
        	            series.getData().get(i);

        	    if (data.getNode() != null) {

        	        switch(i) {

        	            case 0:
        	                data.getNode().setStyle("-fx-bar-fill:#FF9999;");
        	                break;

        	            case 1:
        	                data.getNode().setStyle("-fx-bar-fill:#99CCFF;");
        	                break;

        	            case 2:
        	                data.getNode().setStyle("-fx-bar-fill:#99FF99;");
        	                break;

        	            case 3:
        	                data.getNode().setStyle("-fx-bar-fill:#FFD699;");
        	                break;

        	            case 4:
        	                data.getNode().setStyle("-fx-bar-fill:#D9B3FF;");
        	                break;
        	        }
        	    }
        	}
        });
        
        runButton.setOnAction(e -> {

            List<Process> temp =
                    copyProcesses();

            String algo =algorithmBox.getValue();
            List<GanttBlock> chart = null;
            ganttBox.getChildren().clear();

            if(algo.equals("FCFS")) {

                FCFS fcfs = new FCFS();
                fcfs.schedule(temp);
                chart = fcfs.getGanttChart();
            }

            else if(algo.equals("SJF")) {

                SJF sjf = new SJF();
                sjf.schedule(temp);
                
                chart = sjf.getGanttChart();
            }

            else if(algo.equals("SRTF")) {

                SRTF srtf = new SRTF();
                srtf.schedule(temp);
                
                chart = srtf.getGanttChart();
            }

            else if(algo.equals("Priority Scheduling")) {
       
                priorityscheduling ps =new priorityscheduling();
               

                ps.schedule(temp);
                chart = ps.getGanttChart();
            }

            else if(algo.equals("Round Robin")) {

                try {

                    if(quantumField.getText().isEmpty()) {

                        resultLabel.setText(
                            "Enter Quantum"
                        );

                        return;
                    }

                    int quantum = Integer.parseInt(
                                quantumField.getText()
                            );

                    if(quantum <= 0) {

                        resultLabel.setText(
                            "Quantum must be > 0"
                        );

                        return;
                    }

                    RR rr = new RR();

                    rr.schedule(temp, quantum);

                    chart = rr.getGanttChart();

                } catch(Exception ex) {

                    resultLabel.setText(
                        "Quantum must be a number"
                    );

                    return;
                }
            }            
            if(chart != null) {

                for(GanttBlock g : chart) {

                	Label block = new Label(
                	        g.getPid()
                	        + "\n"
                	        + g.getStartTime()
                	        + "->"
                	        + g.getEndTime()
                	        
                	);
                
                	String color;

                	if(g.getPid().equals("Idle")) {

                	    color = "#D3D3D3";
                	}
                	else {

                	    int index =
                	        Integer.parseInt(
                	            g.getPid().replace("P", "")
                	        ) - 1;

                	    color = colors[index % colors.length];
                	}
                	
                	block.setStyle(
                	    "-fx-border-color:black;" +
                	    "-fx-background-color:" + color + ";" +
                	    "-fx-padding:5;" +
                	    "-fx-font-weight:bold;"
                	);            
                	ganttBox.getChildren().add(block);
                }
                
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
                if(at < 0 || bt <= 0) {

                    resultLabel.setText(
                        "AT >= 0 and BT > 0"
                    );

                    return;
                }
                int pr = 0;

                if(algorithmBox.getValue().equals("Priority Scheduling")) {

                    pr = Integer.parseInt(
                            priorityField.getText()
                    );
                    if(pr < 0) {

                        resultLabel.setText(
                            "Priority cannot be negative"
                        );

                        return;
                    }
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
                resultLabel.setText("");

                pidCounter++;

                arrivalField.clear();
                burstField.clear();
                priorityField.clear();
                quantumField.clear();

            } catch (Exception ex) {
                resultLabel.setText( "Please enter valid numbers" );
            }      
            });

        root.getChildren().addAll(
                title,
                algoRow,
                arrivalRow,
                burstRow,
                priorityRow,
                quantumRow,
                buttonRow1,
                buttonRow2,
                table,
                compareTable,
                barChart,
                resultLabel,
                ganttLabel,
                chartScroll
        );

        Scene scene =
                new Scene(root, 900, 550);
       
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