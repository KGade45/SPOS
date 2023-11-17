import java.util.*;

class process{
    int id;
    int ct,bt,at,tat,wt,priority;
    boolean flag;
}

public class ScPriority {
    int processcount;
    Scanner sc = new Scanner(System.in);
    process[] processarray;
    ArrayList<process> ktemp = new ArrayList<>();
    Queue<process>q = new LinkedList<>();
    ScPriority(){
        System.out.println("enter no of process ");
        processcount = sc.nextInt();

        processarray = new process[processcount];

        System.out.println("enter arrival time process ");
        for(int i =0;i<processcount;i++){
            processarray[i].at = sc.nextInt();
        }

        System.out.println("enter burst time process ");
        for(int i =0;i<processcount;i++){
            processarray[i].bt = sc.nextInt();
            processarray[i].flag = false;
        }
        System.out.println("enter priority process ");
        for(int i =0;i<processcount;i++){
            processarray[i].priority = sc.nextInt();
            processarray[i].id = i+1;
        }

    }

    void sort(){
        for (int i=0;i<processcount;i++){
            process temp;
            for (int j=0;j<processcount-(i+1);j++){
                if (processarray[j].at>processarray[j+1].at){
                    temp = processarray[j];
                    processarray[j] = processarray[j+1];
                    processarray[j+1] = temp;
                }
            }
        }
    }
    boolean Stop(){
            for (int i =0 ;i<processcount;i++)
            {
                if (processarray[i].bt>0)
                {
                    return true;
                }
            }
            return false;
        }

    void start(){
        process currentProcess;
        int time = 0;
        sort();
        q.add(processarray[0]);

        while(Stop()){
            currentProcess = q.poll();
            currentProcess.flag=true;
            time += currentProcess.bt;


            for (int i=0;i<processcount;i++){
                if (processarray[i].at<=time && processarray[i].flag==false){
                    ktemp.add(processarray[i]);
                }
            }

            process mm;

            for (int i=0;i<processcount;i++)
            {



                    if (!q.contains(processarray[i]) && processarray[i]!=currentProcess)
                    {
                        q.add(processarray[i]);
                        processarray[i].flag=true;
                    }
                }
            }


        }

    public static void main(String[] args) {
        ScPriority sk = new ScPriority();
        sk.start();
    }
}
