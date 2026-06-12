# CPU Scheduling Simulator

A JavaFX-based desktop application that simulates and compares CPU scheduling algorithms with graphical visualization and performance metrics.

---

## Table of Contents
- [Algorithms Implemented](#algorithms-implemented)
- [Algorithm Descriptions](#algorithm-descriptions)
- [Features](#features)
- [Screenshots](#screenshots)
- [DSA Concepts Used](#dsa-concepts-used)
- [Tech Stack](#tech-stack)
- [How to Run](#how-to-run)
- [Project Structure](#project-structure)
- [Author](#author)

---

## Algorithms Implemented

| Algorithm | Type | Data Structure Used |
| FCFS (First Come First Serve) | Non-Preemptive | ArrayList + Sorting |
| SJF (Shortest Job First) | Non-Preemptive | Priority Queue (Min Heap) |
| SRTF (Shortest Remaining Time First) | Preemptive | Priority Queue (Min Heap) |
| Priority Scheduling | Non-Preemptive | Priority Queue (Min Heap) |
| Round Robin | Preemptive | Queue |

---

## Algorithm Descriptions

### FCFS (First Come First Serve)
The simplest CPU scheduling algorithm. The process that arrives first is executed first. It is non-preemptive — once a process starts executing, it runs until completion. ArrayList with arrival time sorting is used. Easy to implement but can lead to the **Convoy Effect** where shorter processes wait behind longer ones.

### SJF (Shortest Job First)
Selects the process with the shortest burst time from all currently available processes. It is non-preemptive — once started, the process runs to completion. A **Min-Heap Priority Queue** is used to efficiently select the minimum burst time process in O(log n). Minimizes average waiting time but can cause **starvation** for longer processes.

### SRTF (Shortest Remaining Time First)
Preemptive version of SJF. At every time unit, the algorithm checks if any newly arrived process has a shorter remaining burst time than the currently running process. If yes, the current process is preempted. **Min-Heap Priority Queue** on remaining time is used. Gives optimal average waiting time among all algorithms.

### Priority Scheduling
Each process is assigned a priority value. The process with the highest priority (lowest priority number) is executed first. It is non-preemptive. **Min-Heap Priority Queue** on priority value is used. Can cause **starvation** for low priority processes — solved using Aging technique.

### Round Robin
Each process gets a fixed time slice called **quantum**. After the quantum expires, the process is moved to the back of the queue and the next process gets CPU time. A **Queue** is used for FIFO order of process re-entry. Fairest algorithm — no starvation. Performance depends heavily on the quantum value chosen.

---

## Features

- Add, delete and clear processes dynamically
- Select any scheduling algorithm from dropdown
- **Gantt Chart** visualization with color-coded process blocks
- Per-process statistics — Completion Time (CT), Turnaround Time (TAT), Waiting Time (WT)
- **Compare All** — runs all 5 algorithms on same input simultaneously
- Algorithm comparison table (Avg TAT, Avg WT)
- Bar chart comparison of average waiting time across all algorithms
- **Best Algorithm Detection** — recommends algorithm with lowest Avg WT
- Idle CPU time shown in Gantt Chart
- Round Robin supports custom time quantum input
- Priority Scheduling supports custom priority input

---

## Screenshots

### Main Interface
![Main Interface](screenshots/main.png)

### Gantt Chart Visualization
![Gantt Chart](screenshots/gantt.png)

### Algorithm Comparison Table
![Comparison Table](screenshots/comparison.png)

### Bar Chart Comparison
![Bar Chart](screenshots/barchart.png)

---

## DSA Concepts Used

| Concept | Where Used | Why |
|---|---|---|
| Queue | FCFS, Round Robin | FIFO process order |
| Priority Queue (Min Heap) | SJF, SRTF, Priority Scheduling | O(log n) minimum selection |
| ArrayList | Process storage, Result management | Dynamic size, index access |
| Sorting | Arrival time ordering | Correct execution sequence |
| OOP | All algorithms | Modular, reusable design |

---

## Tech Stack

- Java
- JavaFX
- Object-Oriented Programming
- Data Structures and Algorithms

---

## How to Run

1. Clone the repository
```bash
git clone https://github.com/nehalnrathi18-dotcom/cpu-scheduling-simulator
```

2. Open project in **Eclipse IDE**

3. Add JavaFX SDK to build path
   - Right click project → Build Path → Configure Build Path
   - Add External JARs → select all JARs from JavaFX lib folder

4. Add VM arguments
```
--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
```

5. Run `JavaFXTest.java`

---

---

## Author

**Nehal Rathi**  
Third Year B.Tech Student  
[GitHub](https://github.com/nehalnrathi18-dotcom)
