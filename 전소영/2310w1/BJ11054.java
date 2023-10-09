import java.io.*;
import java.util.*;

// 가장 긴 바이토닉 부분 수열

public class BJ11054_2 {
    
    static int n;
    static int[] arr;
    static int[][] dp;

    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        arr = new int[n];
        dp = new int[2][n];
        for(int i = 0; i < 2; i++) {
            Arrays.fill(dp[i], 1);
        }
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        System.out.println(solution());
    }

    private static int solution() {
        int maxLength = 0;
        // i번째 수를 기준으로 앞쪽은 감소, 뒷쪽은 증가하는 경우의 최장 길이 구하기
        for(int i = 0; i < n; i++) {
            getDecreaseLength(i);
            getIncreaseLength(i);
            maxLength = Integer.max(maxLength, dp[0][i] + dp[1][i] - 1);
        }
        return maxLength;
    }
    
    private static void getDecreaseLength(int x) {
        for(int i = 1; i <= x; i++) {
            for(int j = i - 1; j >= 0; j--) {
                if(arr[i] > arr[j]) dp[0][i] = Integer.max(dp[0][i], dp[0][j] + 1);
            }
        }
    }

    private static void getIncreaseLength(int x) {
        for(int i = n - 1; i >= x; i--) {
            for(int j = i + 1; j < n; j++) {
                if(arr[j] < arr[i]) dp[1][i] = Integer.max(dp[1][i], dp[1][j] + 1);
            }
        }
    }
}

