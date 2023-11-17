import java.util.*;

class Process3{
    int id;
    int arrivalTime;
    int completionTime;
    int turnAroundTime;
    int brustTime;
    boolean isCompleted=false;
    int waitingTime;
    int priority;
}

public class Priority1 {
    Scanner sc = new Scanner(System.in);
    Process3[] processArray;
    Queue<Process3> readyQueue = new LinkedList<>();
    int processCount;

    Priority1() {

        System.out.print("Enter to count of process :-");
        processCount = sc.nextInt();
        System.out.println();

        processArray = new Process3[processCount];

        for (int i = 0; i < processCount; i++) {
            processArray[i] = new Process3();
            processArray[i].id=i+1;
        }

        for (int i = 0; i < processCount; i++) {
            System.out.print("Enter Priority of P" + (i + 1) + ":");
            processArray[i].priority = sc.nextInt();
            System.out.println();

        }

        for (int i = 0; i < processCount; i++) {
            System.out.print("Enter Arrival of P" + (i + 1) + ":");
            processArray[i].arrivalTime = sc.nextInt();
            System.out.println();
        }

        for (int i = 0; i < processCount; i++) {
            System.out.print("Enter Brust Time of P" + (i + 1) + ":");
            processArray[i].brustTime = sc.nextInt();
            System.out.println();
        }


    }

    void start() {

        sort();

        int min = Integer.MAX_VALUE;
        Process3 c = null;

        for (int i = 0; i < processCount; i++) {
            if (processArray[i].arrivalTime < min) {
                min = processArray[i].arrivalTime;
                c = processArray[i];
            }
        }
        readyQueue.add(c);

        int time = 0;
        while (!stopScheduling()) {

            Process3 currentProcess = getCurrentProcess();
            time = time+currentProcess.brustTime;

            currentProcess.completionTime = time;
            currentProcess.isCompleted = true;

            addToReadyQueue(time);


        }

        print();

    }

    void print()
    {
        System.out.println("ID\tAT\tBT\tCT\tTAT\tWT");
        for (int i=0;i<processCount;i++)
        {
            int id=processArray[i].id;
            int AT=processArray[i].arrivalTime;
            int CT=processArray[i].completionTime;
            int BT=processArray[i].brustTime;
            int TAT=processArray[i].completionTime-processArray[i].arrivalTime;
            processArray[i].turnAroundTime=TAT;
            int WT=processArray[i].turnAroundTime - processArray[i].brustTime;
            System.out.println("P"+id+"\t"+AT+"\t"+BT+"\t"+CT+"\t"+TAT+"\t"+WT);
        }
    }

    boolean stopScheduling() {
        for (int i = 0; i < processCount; i++) {
            if (!processArray[i].isCompleted) {
                return false;
            }
        }
        return true;
    }

    void addToReadyQueue(int time) {

        for (int i = 0; i < processCount; i++) {
            if (processArray[i].arrivalTime <= time && !processArray[i].isCompleted && !readyQueue.contains(processArray[i])) {
                readyQueue.add(processArray[i]);
            }
        }
    }

    Process3 getCurrentProcess() {

        int min = Integer.MAX_VALUE;
        Process3 processToReturn = null;

        Iterator<Process3> itr=readyQueue.iterator();

        while (itr.hasNext())
        {
            Process3 process=itr.next();
            if (process.priority < min && !process.isCompleted) {
                min = process.priority;
                processToReturn = process;
            }
        }
        return processToReturn;
    }

    void sort() {
        Process3 temp;
        for (int i = 0; i < processCount; i++) {
            for (int j = 0; j < processCount - (i + 1); j++) {
                if (processArray[j].priority > processArray[j + 1].priority) {
                    temp = processArray[j];
                    processArray[j] = processArray[j + 1];
                    processArray[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        Priority1 obj=new Priority1();

        obj.start();
    }
}