import java.util.*;
import java.io.*;
public class Main {
    static int N=0;
    static int[][] home;
    static int [][][]DP ;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        home = new int[N][N];
        DP = new int[3][N][N];
        for(int i=0 ; i<N ; i ++){
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            for(int j =0; j<N; j++){
                home[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        DP[0][0][1] = 1;
        for(int i = 2; i < N; i ++){
            if(home[0][i] == 0) DP[0][0][i] = DP[0][0][i-1];
        }

        for(int i = 1; i<N; i++){
            for(int j = 1; j<N; j++){
                if(home[i][j] == 0 && home[i][j-1] == 0 && home[i-1][j] == 0)
                    DP[2][i][j] = DP[0][i - 1][j - 1] + DP[1][i - 1][j - 1] + DP[2][i - 1][j - 1];
                if(home[i][j] == 0){
                    DP[0][i][j] = DP[0][i][j - 1] + DP[2][i][j - 1];
                    DP[1][i][j] = DP[1][i - 1][j] + DP[2][i - 1][j];
                }

            }
        }
//        for(int i = 0 ; i<N; i++){
//            System.out.println(Arrays.toString(home[i]));
//        }
        System.out.println(DP[0][N - 1][N - 1] + DP[1][N - 1][N - 1] + DP[2][N - 1][N - 1]);
    }
}
