package cpu_scheduling;

import java.util.*;

public class RR {

    private List<GanttBlock> ganttChart = new ArrayList<>();
    public void schedule(List<Process> processes, int quantum) {

        processes.sort(Comparator.comparingInt( Process::getArrivalTime ) );

        Queue<Process> readyQueue =new LinkedList<>();

        int currTime = 0;
        int index = 0;
        int completed = 0;
        int n = processes.size();

        while (completed < n) {

            while (index < n &&
                    processes.get(index).getArrivalTime() <= currTime) {
                readyQueue.add(processes.get(index));
                index++;
            }

            if (readyQueue.isEmpty()) {
                ganttChart.add( new GanttBlock("Idle",currTime,processes.get(index).getArrivalTime()));

                currTime =processes.get(index).getArrivalTime();
                continue;
            }

            Process current =readyQueue.poll();

            int startTime = currTime;

            int executionTime =
                    Math.min(quantum,current.getRemainingTime());

            currTime += executionTime;

            current.setRemainingTime(current.getRemainingTime()- executionTime);
            int endTime = currTime;

            ganttChart.add(new GanttBlock(current.getPid(), startTime,endTime ) );

            while (index < n &&
                    processes.get(index).getArrivalTime() <= currTime) {

                readyQueue.add(processes.get(index));
                index++;
            }

            if (current.getRemainingTime() == 0) {

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

                readyQueue.add(current);
            }
        }
    }

    public List<GanttBlock> getGanttChart() {
        return ganttChart;
    }
}