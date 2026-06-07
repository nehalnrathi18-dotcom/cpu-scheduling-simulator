package cpu_scheduling;
import java.util.*;

public class SRTF {
	
	private List<GanttBlock> ganttChart=new ArrayList<>();
	
	public void schedule(List<Process> processes) {
		//sort process according to arrival time
		processes.sort(Comparator.comparingInt(Process::getArrivalTime));
		//sort process on basis of burst time
		PriorityQueue<Process> readyq=new PriorityQueue<>((p1,p2)-> p1.getRemainingTime() -p2.getRemainingTime());
		
		int currTime=0;
		int index=0;
		int completed=0;
		int n=processes.size();
		
		String lastProcess = "";
		
		while(completed<n) {
			while( index<n && processes.get(index).getArrivalTime()<=currTime) {
				readyq.add(processes.get(index));
				index++;
			}
			if(readyq.isEmpty()) {
				ganttChart.add(new GanttBlock( "Idle",currTime, processes.get(index).getArrivalTime()));
				currTime=processes.get(index).getArrivalTime();
				continue;	
			}
			
			Process current=readyq.poll();
			int startTime=currTime;
			
			current.setRemainingTime(current.getRemainingTime() - 1);
            currTime++;
			
			int endTime=currTime;
			
			if (ganttChart.isEmpty()
                    || !current.getPid().equals(lastProcess)) {
			
			ganttChart.add(new GanttBlock(current.getPid(),startTime,endTime));
			lastProcess = current.getPid();
            }
            else {
                GanttBlock last = ganttChart.get(ganttChart.size() - 1);
                last.setEndTime(endTime);
            }
			
			if(current.getRemainingTime() == 0) {
			
			current.setCompletionTime(currTime);

			int tat =
			current.getCompletionTime()
			- current.getArrivalTime();

			current.setTurnaroundTime(tat);

			int wt =
			current.getTurnaroundTime()
			- current.getBurstTime();

			current.setWaitingTime(wt);
			
			completed++;
			}
			else {
				readyq.add(current);
			}
		}
	}
	 public List<GanttBlock> getGanttChart() {
	        return ganttChart;
	    }

}
