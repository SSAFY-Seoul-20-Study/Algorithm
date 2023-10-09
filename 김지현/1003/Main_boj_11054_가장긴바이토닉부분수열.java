import java.util.*;
import java.io.*;

// 14880KB	160ms
public class Main_boj_11054_가장긴바이토닉부분수열 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 입력받기
        int N = Integer.parseInt(br.readLine());
        int[] list = new int[N];

        st = new StringTokenizer(br.readLine());
        for(int i=0; i<N; i++){
            list[i] = Integer.parseInt(st.nextToken());
        }

        // 왼쪽 -> 오른쪽 오름차순 dp 구하기(LIS 알고리즘)
        int[] dp = new int[N];
        for(int i=0; i<N; i++){
            dp[i] = 1;
            for(int j=0; j<i; j++){
                if(list[i] > list[j]) dp[i] = Math.max(dp[j]+1, dp[i]);

            }
        }

        // 오른쪽 -> 왼쪽 오름차순 dp 구하기
        int[] dp_2 = new int[N];
        for(int i=N-1; i>=0; i--){
            dp_2[i] = 1;
            for(int j=N-1; j>i; j--){
                if(list[i] > list[j]) dp_2[i] = Math.max(dp_2[j]+1, dp_2[i]);
            }
        }
//        System.out.println(Arrays.toString(dp));
//        System.out.println(Arrays.toString(dp_2));

        // 두 배열의 합의 최댓값 -1
        int res = 0;
        for(int i=0; i<N; i++){
            res = Math.max(res, dp[i]+dp_2[i]);
        }

        System.out.println(res-1);
        br.close();

    }
}
