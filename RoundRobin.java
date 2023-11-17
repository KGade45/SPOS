import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Process1{
    int id;
    int arrivalTime;
    int completionTime;
    int turnAroundTime;
    int brustTime;
    int bt;
    int waitingTime;
}

public class RoundRobin {

    Scanner sc= new Scanner(System.in);
    Queue<Process1> q= new LinkedList<>();
    Process1[] processArray;
    int processCount;
    int timeQuantun;

    RoundRobin()
    {
        System.out.print("Enter Count Of Process :");
        processCount=sc.nextInt();
        System.out.println();

        processArray=new Process1[processCount];

        System.out.println("Enter Time Quantum : ");
        timeQuantun=sc.nextInt();
        System.out.println();

        for (int i=0;i<processCount;i++)
        {
            processArray[i]=new Process1();
            processArray[i].id=i+1;
        }

        for (int i=0;i<processCount;i++)
        {
            System.out.print("Enter Arrival Time of P"+i +": ");
            processArray[i].arrivalTime=sc.nextInt();

        }
        for (int i=0;i<processCount;i++)
        {
            System.out.print("Enter Brust Time of P"+i +": ");
            processArray[i].brustTime=sc.nextInt();
            processArray[i].bt=processArray[i].brustTime;
        }
    }

    void sort()
    {

        for (int i=0;i<processCount;i++)
        {
            Process1 temp;
            for (int j=0;j<processCount-(i+1);j++)
            {
                if (processArray[j].arrivalTime>processArray[j+1].arrivalTime)
                {
                    temp=processArray[j];
                    processArray[j]=processArray[j+1];
                    processArray[j+1]=temp;
                }
            }
        }
    }
    void start()
    {
        int time = 0;
        Process1 currentProcess;
        sort();
        q.add(processArray[0]);

        while (stopScheduling())
        {
            currentProcess=q.poll();
            if(currentProcess.brustTime>=timeQuantun)
            {
                time=time+timeQuantun;
                currentProcess.completionTime=time;
            }
            else {
                time += currentProcess.brustTime;
                currentProcess.completionTime = time;
            }

            if (currentProcess.brustTime<timeQuantun)
            {
                currentProcess.brustTime=0;
            }
            else
            {
                currentProcess.brustTime-=timeQuantun;
            }

            for (int i=0;i<processCount;i++)
            {
                if (processArray[i].arrivalTime<=time && processArray[i].brustTime>0)
                {
                    if (!q.contains(processArray[i]) && processArray[i]!=currentProcess)
                    {
                        q.add(processArray[i]);
                    }
                }
            }

            if (currentProcess.brustTime>0 && !q.contains(currentProcess))
                q.add(currentProcess);

        }

        computeTurnAroundTime();
        computeWaitingTime();
        print();




    }

    void print()
    {
        System.out.println("ID\tAT\tBT\tCT\tTAT\tWT");
        for (int i=0;i<processCount;i++)
        {
            int AT=processArray[i].arrivalTime;
            int CT=processArray[i].completionTime;
            int BT=processArray[i].bt;
            int TAT=processArray[i].turnAroundTime;
            int WT=processArray[i].turnAroundTime - processArray[i].bt;
            System.out.println("P"+(i+1)+"\t"+AT+"\t"+BT+"\t"+CT+"\t"+TAT+"\t"+WT);
        }
    }

    void computeTurnAroundTime()
    {
        for (int i=0;i<processCount;i++)
        {
            Process1 process=processArray[i];
            process.turnAroundTime=process.completionTime-process.arrivalTime;
        }
    }

    void computeWaitingTime()
    {
        for (int i=0;i<processCount;i++)
        {
            Process1 process=processArray[i];
            process.waitingTime=process.turnAroundTime-process.brustTime;

        }
    }
    boolean stopScheduling()
    {
        for (int i =0 ;i<processCount;i++)
        {
            if (processArray[i].brustTime>0)
            {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        RoundRobin obj =new RoundRobin();
        obj.start();
    }
}