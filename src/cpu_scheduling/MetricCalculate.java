package cpu_scheduling;
import java.util.*;
public class MetricCalculate {
	
	public double AvgWaitingTime(List<Process> processes) {
		
		double totalwaitingTime=0;
		for(Process p: processes) {
		totalwaitingTime=totalwaitingTime+ p.getWaitingTime();
		}
		double avgWT=totalwaitingTime/processes.size();
		return avgWT;
	}
	
	public double AvgTAT(List<Process> processes) {
		double totalTAT=0;
		for(Process p: processes) {
			totalTAT=totalTAT+p.getTurnaroundTime();
		}
		double avgTAT=totalTAT/processes.size();
		return avgTAT;
	}

}
