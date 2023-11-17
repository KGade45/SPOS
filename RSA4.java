import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class RSA4 {
    public static boolean isprime(int p, int q) {
        for (int i = 2; i < p; i++) {
            if (p % i == 0) {
                System.out.println("p is not prime");
                return false;
            } else {
                System.out.println("p is prime");
                break;
            }
        }

        for (int i = 2; i < q; i++) {
            if (q % i == 0) {
                System.out.println("q not prime");
                return false;
            } else {
                System.out.println("q is prime");
                break;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n, z, e, d = 0;
        System.out.println("Enter original msg:-");
        int msg = sc.nextInt();
        System.out.println("Enter first prime number:-");
        int p = sc.nextInt();
        System.out.println("Enter second prime number:-");
        int q = sc.nextInt();

        if (isprime(p, q)) {
            n = p * q;
            z = (p - 1) * (q - 1);
            System.out.println("Value of z:-" + z);

            for (e = 2; e < z; e++) {
                if (gcd(e, z) == 1) {
                    break;
                }
            }


            for (int i = 0; i <= 9; i++) {
                int x = 1 + (i * z);
                if (x % e == 0) {
                    d = x / e;
                    break;
                }

            }
            double c = (Math.pow(msg, e)) % n;
            System.out.println("Encryption:-" + c);
            BigInteger N = BigInteger.valueOf(n);
            BigInteger C = BigDecimal.valueOf(c).toBigInteger();
            BigInteger msgback = (C.pow(d)).mod(N);
            System.out.println("Decrtion:-" + msgback);

        }
    }

    static int gcd(int e, int z) {
        if (e == 0) {
            return z;
        } else {
            return gcd(z % e, e);
        }
    }
}