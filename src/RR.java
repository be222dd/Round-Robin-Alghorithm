/**
 * Created by beysimeryalmaz on 2017-11-12.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author beysimeryalmaz
 */
public class RR{

    // The list of processes to be scheduled
    public ArrayList<Process> processes;
    public ArrayList<Process>  tempList;
    public GantChart chart=new GantChart();

    // the quantum time - which indicates the maximum allowable time a process can run once it is scheduled
    int tq;

    // keeps track of which process should be executed next
    public Queue<Process> schedulingQueue;
    public int time;
    public  Process lastExecutedProcess;
    public boolean hasLeftFlag=true;

    // Class constructor
    public RR(ArrayList<Process> processes, int tq) {
        schedulingQueue = new LinkedList<Process>();
        lastExecutedProcess=null;

        this.processes = processes;

        this.tq = tq;
        this.processes=processes;
        tempList=new ArrayList<>(processes);
        tempList.sort((p1, p2) -> p1.getArrivalTime() - p2.getArrivalTime());

        this.time=0;
    }

    public void run() {
		/*
		 * This is where you put your code
		 * hints:
		 * 1. do not forget to sort the processes by arrival time
		 * 2. think about CPU idle times
		 */

        while(!tempList.isEmpty() || !schedulingQueue.isEmpty() ||hasLeftFlag ){
            //selects the processes according to their arrival time and put them into the queue
            putToScheduleQ();
            //removes them from temproray list
            removeFromList();
            //checking if last executed process finished otherwise adds it to the queue again
            if(lastExecutedProcess!=null){
                if(!lastExecutedProcess.isScheduled()){
                    schedulingQueue.add(lastExecutedProcess);
                }
            }
            //checks if there is a process to execute or cpu has to wait for a process to come
            if(schedulingQueue.isEmpty()){
                time=tempList.get(0).getArrivalTime();//idle time
                
            }else{
                executeTheProcess();
            }

        }
        //sorting for the test case
        processes.sort((p1,p2)->p1.getArrivalTime()-p2.getArrivalTime());
        printProcesses();
        printGanttChart();
    }

    public void printProcesses(){

        System.out.println("Table FORM");
        for(Process process:processes){
            System.out.println("Id "+process.getProcessId()+" CT "+process.getCompletedTime()+" TAT "+process.getTurnaroundTime()+" WT "+process.getWaitingTime());
        }
    }

    public void printGanttChart(){
        // TODO Print the demonstration of the scheduling algorithm using Gantt Chart
        System.out.println("\nGantt Chart");
        chart.printIds();
        System.out.println("");
        chart.printExTimes();
        System.out.println("");
    }


    public void putToScheduleQ(){
        for(Process process:tempList){
            if(process.getArrivalTime()<=time && process.getBurstTime()>=process.getRemainingBurstTime()){
                schedulingQueue.add(process);
            }
        }


    }

    public void removeFromList(){

        tempList.removeIf(process->process.getArrivalTime()<=time);
    }

    public void executeTheProcess(){

        Process process=schedulingQueue.remove();
        if(process.getRemainingBurstTime()>tq){
            process.setRemainingBurstTime(process.getRemainingBurstTime()-tq);
            time+=tq;
            chart.processIds.add(process.getProcessId());
            chart.exeTimes.add(time);

        }else{
            if(process.getRemainingBurstTime()<tq){
                time+=process.getRemainingBurstTime();
            }else{
                time+=tq;
            }
            chart.processIds.add(process.getProcessId());
            chart.exeTimes.add(time);
            process.setRemainingBurstTime(0);

            process.setCompletedTime(time);
            process.setTurnaroundTime(time-process.getArrivalTime());
            process.setWaitingTime(process.getTurnaroundTime()-process.getBurstTime());


        }

        lastExecutedProcess=process;
        hasLeftFlag=!process.isScheduled();
    }

}