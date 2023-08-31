import java.io.*;
import java.util.*;

// 맥주 마시면서 걸어가기

public class BJ9205 {

    static int t;
    static int n;
    static String ans;
    static int[][] pos;
    static boolean[][] matrix;

    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        t = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        for(int tc = 0; tc < t; tc++) {
            
            n = Integer.parseInt(br.readLine());
            pos = new int[n + 2][2];
            matrix = new boolean[n + 2][n + 2];

            st = new StringTokenizer(br.readLine());
            pos[0][0] = Integer.parseInt(st.nextToken());
            pos[0][1] = Integer.parseInt(st.nextToken());
            
            for(int i = 1; i <= n + 1; i++) {
                st = new StringTokenizer(br.readLine());
                pos[i][0] = Integer.parseInt(st.nextToken());
                pos[i][1] = Integer.parseInt(st.nextToken());
            }

            if(calculateDist(pos[0][0], pos[0][1], pos[n + 1][0], pos[n + 1][1]) <= 1000) {
                ans = "happy";
            }
            else {
                solution();
                if(matrix[0][n + 1]) ans = "happy";
                else ans = "sad";
            }
            sb.append(ans + "\n");
        }

        System.out.println(sb.toString());
    }

    private static void solution() {

        for(int i = 0; i <= n + 1; i++) {
            for(int j = 0; j <= n + 1; j++) {
                if(calculateDist(pos[i][0], pos[i][1], pos[j][0], pos[j][1]) <= 1000) {
                    matrix[i][j] = true;
                }
            }
        }

        for(int k = 0; k <= n + 1; k++) {
            for(int i = 0; i <= n + 1; i++) {
                for(int j = 0; j <= n + 1; j++) {
                    if(matrix[i][k] && matrix[k][j]) {
                        matrix[i][j] = true;
                    }
                }
            }
        }
    }

    private static int calculateDist(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}
