import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static int line[][];
    static boolean visited[];
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());
        for(int tc = 1; tc<=T; tc++){
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            line = new int[N+2][2];
            visited = new boolean[N+2];
            for(int i =0; i<N+2; i++){
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < 2; j++) {
                    line[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            bfs();
            if(visited[N+1]) System.out.println("happy");
            else System.out.println("sad");

        }
    }
    static void bfs(){
        ArrayDeque<int []> q = new ArrayDeque<>();
        visited[0] = true;
        q.add(new int [] {line[0][0], line[0][1]});
        while(!q.isEmpty()){
            int cur[] = q.poll();
            int x = cur[0];
            int y = cur[1];
            for (int i = 0; i < N+2; i++) {
                int tmp = Math.abs(line[i][0] - x) + Math.abs(line[i][1] - y);
                if(!visited[i] && tmp <= 1000){
                    q.add(new int [] {line[i][0], line[i][1]});
                    visited[i] = true;
                }
            }
        }
    }
}