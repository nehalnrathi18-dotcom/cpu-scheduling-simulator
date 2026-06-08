package cpu_scheduling;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Process> processes = new ArrayList<>();

        processes.add(new Process("P1", 2, 5, 2));
        processes.add(new Process("P2", 3, 3, 1));
        processes.add(new Process("P3", 4, 8, 3));
        processes.add(new Process("P4", 5, 2, 2));

        /*System.out.println("Processes:");

        for(Process p : processes) {
            System.out.println(p);
        }

        List<GanttBlock> chart = new ArrayList<>();

        chart.add(new GanttBlock("P1", 0, 5));
        chart.add(new GanttBlock("P2", 5, 8));

        System.out.println("\nGantt Blocks:");

        for(GanttBlock block : chart) {
            System.out.println(block);
        }*/
        
        //FCFS fcfs=new FCFS();
        //fcfs.schedule(processes);
        
        //SJF sjf=new SJF();
       // sjf.schedule(processes);
        
        //SRTF srtf = new SRTF();
        //srtf.schedule(processes);
        
        //RR rr = new RR();
        //rr.schedule(processes, 2);
        
        priorityscheduling ps=new priorityscheduling();
        ps.schedule(processes);
        
        System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");

        for(Process p : processes) {

            System.out.println(
                p.getPid() + "\t" +
                p.getArrivalTime() + "\t" +
                p.getBurstTime() + "\t" +
                p.getCompletionTime() + "\t" +
                p.getTurnaroundTime() + "\t" +
                p.getWaitingTime()
            );
        }
        
        MetricCalculate m=new MetricCalculate();
        double TAT=m.AvgTAT(processes);
        double WT=m.AvgWaitingTime(processes);
        System.out.println("Avg Turnaround Time is : "+ TAT);
        System.out.println("Avg Waiting Time is: "+WT);
        
        //GanttChartPrinter.print(fcfs.getGanttChart());
        
        GanttChartPrinter.print(ps.getGanttChart());
        
    }
}