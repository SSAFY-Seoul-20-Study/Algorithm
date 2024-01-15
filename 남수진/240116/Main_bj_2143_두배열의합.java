import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main_bj_2143_두배열의합 {

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        long T = Long.parseLong(br.readLine());

        int n = Integer.parseInt(br.readLine());
        int[] A = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i = 0; i< n; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        int m = Integer.parseInt(br.readLine());
        int[] B = new int[m];

        st = new StringTokenizer(br.readLine());

        for(int i = 0; i< m; i++) {
            B[i] = Integer.parseInt(st.nextToken());
        }

        int alen = n * (n + 1) / 2;
        int blen = m * (m + 1) / 2;

        long[] sumA = new long[alen];
        long[] sumB = new long[blen];

        sumA[0] = A[0];
        sumB[0] = B[0];

        sum(A, sumA, n);
        sum(B, sumB, m);

        Arrays.sort(sumA);
        Arrays.sort(sumB);

        int left = 0, right = blen - 1;

        long cnt = 0;

        while(left < alen && right >= 0) {
            long leftA = sumA[left], rightB = sumB[right];
            long tmpSum = leftA + rightB;

            if(tmpSum == T) {
                long ac = 0, bc = 0;
                while(left < alen && sumA[left] == leftA) {
                    left++;
                    ac++;
                }
                while(right >= 0 && sumB[right] == rightB) {
                    right--;
                    bc++;
                }
                cnt += ac * bc;
            }

            if(tmpSum > T) right--;
            else if(tmpSum < T) left++;
        }
        System.out.println(cnt);
    }

    static void sum(int[] arr, long[] sum, int size) {
        int idx = 0;
        for(int i = 0; i < size; i++) {
            long tmp = 0;
            for(int j = i; j < size; j++) {
                tmp += arr[j];
                sum[idx++] = tmp;
            }
        }
    }
}
