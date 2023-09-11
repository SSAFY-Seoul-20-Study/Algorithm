import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static int citizens[], dp[][];
    static ArrayList<Integer> trees = new ArrayList<>();
    static boolean visited[];
    static ArrayList<Integer>[] lines;
    public static void main(String[] args) throws Exception{
        System.setIn(new FileInputStream("C:\\Users\\top15\\Dev\\algorithmn\\input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        citizens = new int[N+1];
        lines = new ArrayList[N+1];
        visited = new boolean[N+1];
        dp = new int[N+1][2];
        for (int i = 0; i < N+1; i++) {
            lines[i] = new ArrayList<>();
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < N+1; i++) {
            citizens[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i < N-1; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            lines[from].add(to);
            lines[to].add(from);
        }
        dfs(1);
        System.out.println(Math.max(dp[1][0], dp[1][1]));
    }
    static void dfs(int now){
        visited[now] = true;
        //DFS로 탐색하면서 DP에 값을 넣으면서 진행 (더 빠름)
        dp[now][0] = 0;
        dp[now][1] = citizens[now];
        for(int next : lines[now]){
            if(!visited[next]){
                dfs(next);
                //현재 위치를 우수마을로 지정
                dp[now][1] += dp[next][0];
                //현재 마을을 우수마을로 지정 X
                dp[now][0] += Math.max(dp[next][0], dp[next][1]);
            }
        }
    }
}