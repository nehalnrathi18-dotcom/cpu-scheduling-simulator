package cpu_scheduling;
import java.util.*;
public class GanttChartPrinter {
	 public static void print(List<GanttBlock> chart) {

	        System.out.println("\nGantt Chart:");

	        for(GanttBlock block : chart) {
	            System.out.print("| " + block.getPid() + " ");
	        }

	        System.out.println("|");

	        System.out.print(chart.get(0).getStartTime());

	        for(GanttBlock block : chart) {
	            System.out.print("    " + block.getEndTime());
	        }

	        System.out.println();
	    }
}
