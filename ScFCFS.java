import java.util.*;
public class ScFCFS {
    Scanner sc = new Scanner(System.in);
    int numProc;
    int AvgWait = 0;
    int AvgTat = 0;
    int[] arrTime;
    int[] burTime;
    int[] compTime;
    int[] taTime;
    int[] waitTime;
    int[] proID;

    void function(){
        input();
        calcComptime();
        calcTatAndWait();
        print();

    }
    void input(){
        System.out.println("Enter no. of process: ");
        numProc = sc.nextInt();
        arrTime = new int[numProc];
        burTime = new int[numProc];
        waitTime = new int[numProc];
        taTime = new int[numProc];
        compTime = new int[numProc];
        proID = new int[numProc];
        System.out.println("Enter Arrival Time: ");
        for (int i = 0;i<numProc;i++){
            arrTime[i] = sc.nextInt();
            proID[i]=i+1;
        }
        System.out.println("Enter Burst Time: ");
        for (int i = 0;i<numProc;i++)
            burTime[i] = sc.nextInt();

        for (int i =0;i<numProc;i++){
            for (int j = 0;j<numProc-(i+1);j++){
                if (arrTime[j]>arrTime[j+1]){
                    int temp = arrTime[j];
                    arrTime[j] = arrTime[j+1];
                    arrTime[j+1] = temp;
                    temp = burTime[j];
                    burTime[j] = burTime[j+1];
                    burTime[j+1] = temp;
                }
            }
        }
    }

    void calcComptime(){
        compTime[0] = burTime[0];
        for (int i = 1; i<numProc;i++)
        {
            int k =0;
            if (arrTime[i]-compTime[i-1]>0)
                k=arrTime[i]-compTime[i-1];
            compTime[i]=compTime[i-1]+burTime[i]+k;
        }
    }

    void calcTatAndWait(){
        for (int i =0;i<numProc;i++){
            taTime[i]=compTime[i]-arrTime[i];
            waitTime[i] = taTime[i]-burTime[i];
            AvgTat+=taTime[i];
            AvgWait +=waitTime[i];
        }
    }

    void print(){
        System.out.println("ID\tAt\tBt\tCt\tTat\tWt");
        for (int i=0;i<numProc;i++)
            System.out.println(proID[i]+"\t"+arrTime[i]+"\t"+burTime[i]+"\t"+compTime[i]+"\t"+taTime[i]+"\t"+waitTime[i]);
        System.out.println("Average waiting time: "+AvgWait);
        System.out.println("Average Turn around time: "+AvgTat);

    }


    public static void main(String[] args) {
        ScFCFS fs = new ScFCFS();
        fs.function();
    }
}
