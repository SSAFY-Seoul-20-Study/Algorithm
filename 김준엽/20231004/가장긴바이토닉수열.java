
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 가장긴바이토닉부분수열 {
    static int N;
    static int arr[], dp1[], dp2[];
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        dp1 = new int[N];
        dp2 = new int[N];
        arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            dp1[i] = 1;
            dp2[i] = 1;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if(arr[i] > arr[j]){
                    dp1[i] = Math.max(dp1[i], dp1[j] + 1);
                }
            }
        }
        for (int i = N-1; i >= 0; i--) {
            for (int j = N-1; j > i; j--) {
                if(arr[i] > arr[j]){
                    dp2[i] = Math.max(dp2[i], dp2[j] + 1);
                }
            }
        }
        int max = 0;
        for (int i = 0; i < N; i++) {
            if(dp1[i] + dp2[i] > max){
                max = dp1[i] + dp2[i];
            }
        }
        System.out.println(max-1);
    }
}
