import java.util.*;

class processs{
    int at,bt,ct,wt,tat,id,bt2;
}

public class heap {
    Scanner sc = new Scanner(System.in);
    processs[] prarray;
    int n ;
    int quantom;
    Queue<processs>q = new LinkedList<>();
    heap(){
        System.out.println("Enter number of process");
        n = sc.nextInt();
        System.out.println("Enter quantom");
        quantom = sc.nextInt();

        prarray = new processs[n];

        for (int i =0;i<n;i++){
            prarray[i] = new processs();
            prarray[i].id = i+1;
        }

        for (int i =0;i<n;i++){
            System.out.println("Enter arrival of "+(i+1)+" process ");
            prarray[i].at= sc.nextInt();

        }

        for (int i =0;i<n;i++){
            System.out.println("Enter burst of "+(i+1)+" process ");
            prarray[i].bt= sc.nextInt();
            prarray[i].bt2 = prarray[i].bt;
        }
    }

    void sort(){
        for(int i= 0;i<n;i++){
            processs temp;
            for(int j= 0;j<n-(i+1);j++){
                if (prarray[j].at>prarray[j+1].at) {
                    temp = prarray[j];
                    prarray[j] = prarray[j + 1];
                    prarray[j + 1] = temp;
                }

            }
        }
    }

    boolean stop(){
        for (int i=0;i<n;i++){
            if (prarray[i].bt>0)
                return true;
        }
        return false;
    }

    void start(){
        processs current;
        q.add(prarray[0]);
        sort();
        int time=0;
        while(stop()){
            current = q.poll();
            if(current.bt>=quantom){
                time+=quantom;
                current.ct=time;
            }
            else{
                time+=current.bt;
                current.ct=time;
            }

            if(current.bt<quantom){
                current.bt=0;
            }
            else{
                current.bt-=quantom;
            }

            for (int i = 0;i<n;i++){
                if((prarray[i].at<=time) && (prarray[i]!=current)){
                    if(!q.contains(prarray[i]) && prarray[i].bt>0){
                        q.add(prarray[i]);
                    }
                }
            }
            if(current.bt>0)
                q.add(current);
        }
        turttime();
        wtime();
        printm();

    }

    void turttime(){
        for (int i =0;i<n;i++){
            prarray[i].tat = prarray[i].ct-prarray[i].at;
        }
    }

    void wtime(){
        for (int i =0;i<n;i++){
            prarray[i].tat = prarray[i].tat-prarray[i].bt2;
        }
    }

    void printm(){
        System.out.println("ID\tAT\tBT\tCT\tTAT\tWT");
        for (int i=0;i<n;i++)
        {
            int AT=prarray[i].at;
            int CT=prarray[i].ct;
            int BT=prarray[i].bt;
            int TAT=prarray[i].tat;
            int WT=prarray[i].tat - prarray[i].bt;
            System.out.println("P"+(i+1)+"\t"+AT+"\t"+BT+"\t"+CT+"\t"+TAT+"\t"+WT);
        }
    }

    public static void main(String[] args){
        heap p2 = new heap();
        p2.start();
    }
}
