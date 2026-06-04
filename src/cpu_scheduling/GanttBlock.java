package cpu_scheduling;

public class GanttBlock {
	    private String pid;
	    private int startTime;
	    private int endTime;
		public GanttBlock(String pid, int startTime, int endTime) {
			super();
			this.pid = pid;
			this.startTime = startTime;
			this.endTime = endTime;
		}
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		public int getStartTime() {
			return startTime;
		}
		public void setStartTime(int startTime) {
			this.startTime = startTime;
		}
		public int getEndTime() {
			return endTime;
		}
		public void setEndTime(int endTime) {
			this.endTime = endTime;
		}
		@Override
		public String toString() {
			return "GanttBlock [pid=" + pid + ", startTime=" + startTime + ", endTime=" + endTime + "]";
		}

}
