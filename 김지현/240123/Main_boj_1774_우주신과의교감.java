import java.io.*;
import java.util.*;

public class Main_boj_1774_우주신과의교감 {
    static int N, M, visitedCnt;
    static int[][] arr;
    static boolean[] v;
    static double[][] adjMatrix;
    static double[] dist;
    static double distSum;

    public static void main(String[] args) throws Exception {
//        System.setIn(new FileInputStream("res/input_boj_1774.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new int[N+1][2];
        v = new boolean[N+1];
        adjMatrix = new double[N+1][N+1];
        dist = new double[N+1];
        distSum = 0;
        visitedCnt = 0;

        Arrays.fill(dist, Double.MAX_VALUE);

        for(int i=1; i<=N; i++){
            st = new StringTokenizer(br.readLine());
            arr[i][0] = Integer.parseInt(st.nextToken());
            arr[i][1] = Integer.parseInt(st.nextToken());
        }

        for(int i=1; i<=N; i++){
            for(int j=i+1; j<=N; j++){
                if(i==j) continue;
                double dist_0 = Math.pow(arr[i][0] - arr[j][0], 2);
                double dist_1 = Math.pow(arr[i][1] - arr[j][1], 2);
                adjMatrix[i][j] = adjMatrix[j][i] = Math.sqrt(dist_0 + dist_1);
            }
        }

        for(int i=1; i<=M; i++){
            st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());
            adjMatrix[left][right] = adjMatrix[right][left] = 0;
        }

        if(N != 0) prim();

        System.out.printf("%.2f", (double) Math.round(distSum * 100) / 100);
    }

    private static void prim(){
        int min_idx = -1;
        double min_dist = Double.MAX_VALUE;

        dist[1] = 0;
        for(int i=2; i<=N; i++){
            dist[i] = Math.min(dist[i], adjMatrix[1][i]);
            if(min_dist > dist[i]){
                min_dist = dist[i];
                min_idx = i;
            }
        }
        v[1] = true;
        visitedCnt++;

        if(!v[min_idx]){
            distSum += min_dist;
            v[min_idx] = true;
            visitedCnt++;
        }
        while(visitedCnt < N){
            v[min_idx] = true;
            int next_idx = min_idx;
            min_dist = Double.MAX_VALUE;
            for(int i=1; i<=N; i++){
                if(i == next_idx) continue;
                if(v[i]) continue;
                dist[i] = Math.min(dist[i], adjMatrix[next_idx][i]);
                if(min_dist > dist[i]){
                    min_dist = dist[i];
                    min_idx = i;
                }
            }
            distSum += min_dist;
            visitedCnt++;
        }

    }
}

