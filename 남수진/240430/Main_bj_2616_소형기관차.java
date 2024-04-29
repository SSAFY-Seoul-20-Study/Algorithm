import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        int[] train = new int[N + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i = 1; i <= N; i++){
            int input = Integer.parseInt(st.nextToken());
            // i번째 승객 수 + i번째 전까지의 총 승객수
            train[i] += input + train[i - 1];
        }

        int M = Integer.parseInt(br.readLine()); // 최대 객차 수
        // i번째 기관차가 객차 j번째 까지 보았을 때 최대로 운송할 수 있는 승객수
        int[][] dp = new int[4][N + 1];

        for(int i = 1; i <= 3; i++) {
            for(int j = i * M; j <= N; j++) {
                // 1. j번째 객차를 선택하지 않았을 경우, 이전 객차까지의 최대 승객 수
                // 2. j번째 객차를 선택했을 경우, i - 1 번째 기관차가 객차 j-M 까지 최대 승객수 + 객차 j - M ~ j 까지의 승객 합
                dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j - M] + train[j] - train[j - M]);
            }
        }

        System.out.println(dp[3][N]);

    }

}
