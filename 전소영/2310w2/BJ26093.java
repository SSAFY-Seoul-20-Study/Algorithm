import java.io.*;
import java.util.*;

// 고양이 목에 리본 달기

public class BJ26093 {

    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[][] dp = new int[n + 1][k];
        int max = 0, preMax = 0;

        for(int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < k; j++) {
                dp[i][j] = Integer.parseInt(st.nextToken());
                if(i == 1) {
                    if(dp[i][j] >= max) {
                        preMax = max;
                        max = dp[i][j];
                    }
                    else if(dp[i][j] > preMax) {
                        preMax = dp[i][j];
                    }
                }
            }
        }

        for(int i = 2; i <= n; i++) {
            int newMax = 0, newPreMax = 0;
            for(int j = 0; j < k; j++) {
                dp[i][j] += (max == dp[i - 1][j]) ? preMax : max; 
                if(dp[i][j] >= newMax) {
                    newPreMax = newMax;
                    newMax = dp[i][j];
                }
                else if(dp[i][j] > newPreMax) {
                    newPreMax = dp[i][j];
                }
            }
            max = newMax;
            preMax = newPreMax;
        }

        Arrays.sort(dp[n]);
        System.out.println(dp[n][k - 1]);
    }
}
