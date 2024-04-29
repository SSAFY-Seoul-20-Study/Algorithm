import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main_bj_g3_2616_소형기관차 {
    static int[] passengers; 
    static Integer[][] dp;
    static int[] sum;
    static int maxCarry;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        passengers = new int[n+1];
        dp = new Integer[n+1][4];
        sum = new int[n+1];

        String[] input = br.readLine().split(" ");
        for (int i = 1; i <= n; i++) {
            passengers[i] = Integer.parseInt(input[i-1]);
            sum[i] = sum[i-1] + passengers[i];
        }

        maxCarry = Integer.parseInt(br.readLine());

        System.out.println(findMaxPassenger(0, 3));
    }

    public static int findMaxPassenger(int start, int trainCount) {
        if (trainCount == 0) return 0;

        if (dp[start][trainCount] == null) {
            dp[start][trainCount] = 0;

            for (int i = start + 1; i <= passengers.length - maxCarry * trainCount; i++) {
                dp[start][trainCount] = Math.max(dp[start][trainCount], findMaxPassenger(i + maxCarry - 1, trainCount - 1) + (sum[i + maxCarry - 1] - sum[start]));
            }
        }

        return dp[start][trainCount];
    }
}