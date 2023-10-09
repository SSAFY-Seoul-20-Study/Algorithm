import java.io.*;
import java.util.*;

// 인하니카 공화국

public class BJ12784 {

    static int n;
    static ArrayList<int[]>[] adj;
    static boolean[] visited;
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());
        
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for(int t = 0; t < tc; t++) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            adj = new ArrayList[n + 1];
            for(int i = 1; i <= n; i++) {
                adj[i] = new ArrayList<>();
            }
            visited = new boolean[n + 1];
            
            for(int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());
                adj[u].add(new int[] {v, d});
                adj[v].add(new int[] {u, d});
            }
            
            visited[1] = true;
            int ans = dfs(1, Integer.MAX_VALUE);
            ans = (ans == Integer.MAX_VALUE) ? 0 : ans;
            sb.append(ans + "\n");
        }

        System.out.println(sb.toString());
    }
    
    private static int dfs(int x, int weight) {     // weight은 x번 섬으로 갈 때의 다이너마이트 수
        
        boolean flag = false;
        int total = 0;
        for(int i = 0; i < adj[x].size(); i++) {
            int n = adj[x].get(i)[0];
            int d = adj[x].get(i)[1];
            
            if(visited[n]) continue;
            visited[n] = true;
            total += dfs(n, d);
            flag = true;
        }
        
        if(flag) return Integer.min(weight, total);
        return weight;
    }
        
}
