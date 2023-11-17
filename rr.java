import java.util.*;
public class rr {
    class rb{
        Scanner sc = new Scanner(System.in);
        int[]id;
        int[]at;
        int[]tat;
        int[]bt;
        int[]ct;
        int process;

        Queue<Integer> q = new LinkedList<>();

        void input(){
            System.out.println("Enter no of precess: ");
            process = sc.nextInt();
            at = new int[process];
            tat =new int[process];
            bt = new int[process];
            ct = new int[process];
            id = new int[process];

            System.out.println("Enter arrival time: ");
            for (int i=0;i<process;i++){
                at[i] = sc.nextInt();
                id[i] = i+1;
            }

            System.out.println("Enter Burst time: ");
            for (int i=0;i<process;i++)
                bt[i] = sc.nextInt();

            for(int i =0;i<process;i++){
                for(int j = 0;j<process-(i+1);j++){
                    if (at[j]>at[j+1]){
                        int temp = at[j];
                        at[j]=at[j+1];
                        at[j]=temp;
                        temp = bt[j];
                        bt[j]=bt[j+1];
                        bt[j]=temp;
                        temp = id[j];
                        id[j]=id[j+1];
                        id[j]=temp;
                    }
                }
            }




        }



    }

}
