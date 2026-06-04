package cpu_scheduling;
import java.util.*;

public class FCFS {

	private List<GanttBlock> ganttChart = new ArrayList<>();
	
	public void schedule(List<Process> processes) {
		Queue <Process> readyqueue=new LinkedList<>();
		
		for(Process p: processes) {
			readyqueue.add(p);
		}
		
		int currTime=0;
		while(!readyqueue.isEmpty()) {
			
			Process current=readyqueue.poll();
			
			if(currTime<current.getArrivalTime()) {
				ganttChart.add(new GanttBlock("Idle",currTime,current.getArrivalTime()));
				currTime=current.getArrivalTime();
			}
			int startTime = currTime;
			
			
			currTime=currTime+current.getBurstTime();
			current.setCompletionTime(currTime);
			int endTime = currTime;
			
			ganttChart.add(new GanttBlock(current.getPid(),startTime,endTime ));
			
			int tat=current.getCompletionTime()-current.getArrivalTime();
			current.setTurnaroundTime(tat);
			
			int wt=current.getTurnaroundTime()-current.getBurstTime();
			current.setWaitingTime(wt);
			
		}
	}
	public List<GanttBlock> getGanttChart() {
	    return ganttChart;
	}
}
