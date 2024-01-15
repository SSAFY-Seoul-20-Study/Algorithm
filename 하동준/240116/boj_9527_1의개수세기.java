import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static long[] d = new long[55];
    static long a,b;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        a = Long.parseLong(st.nextToken());
        b = Long.parseLong(st.nextToken());

        // d[i] = 2*d[i-1] + 2^i
        d[0] = 1;
        for (int i = 1; i < 55; i++) {
            d[i] = 2*d[i-1] + (1L<<i);
        }
//        System.out.println(Arrays.toString(d));
        System.out.println(go(b) - go(a-1));

    }
    static long go(long x) {
        long sum = (x & 1);
        for (int i = 54; i > 0; i--) {
            if ( (x&(1L<<i)) > 1 ) {
                sum += d[i-1] + (x-(1L<<i)) + 1;
                x -= (1L<<i);
            }
        }
        return sum;
    }
}
