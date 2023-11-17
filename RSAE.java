import java.math.BigInteger;
import java.util.Scanner;

public class RSAE {
    public static boolean isprime(int n){
        for (int i=2;i<Math.sqrt(n);i++)
        {
            if(n%i==0) {
//                System.out.println(n + " is not prime");
                return false;
            }
        }
        return  true;
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int n,z,e,d=0;
        System.out.println("Enter original msg:-");
        int msg=sc.nextInt();
        System.out.println("Enter first prime number:-");
        int p=sc.nextInt();
        System.out.println("Enter second prime number:-");
        int q= sc.nextInt();
        if(isprime(p) && isprime(q)){
            n=p*q;
            z=(p-1)*(q-1);
            System.out.println("Value of z:-"+z);
            for (e=2;e<z;e++)
            {
                if(gcd(e,z)==1){
                    break;
                }
            }
            for(int k=0;k<=z;k++ ){
                int x= 1+ (k*z);
                if(x%e==0){
                    d= x/e;
                    break;
                }
            }
            BigInteger N=BigInteger.valueOf(n);
            BigInteger C =(BigInteger.valueOf(msg).pow(e)).mod(N);
            System.out.println("Encryption:-"+C);
            BigInteger msgback=(C.pow(d)).mod(N);
            System.out.println("Decrtion:-"+msgback);
        }
    }
    static int gcd(int e,int z){
        if(e==0)
        {
            return z;
        }
        else {
            return gcd(z%e, e);
        }
    }
}