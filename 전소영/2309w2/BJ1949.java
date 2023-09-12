import java.io.*;
import java.util.*;

// 우수 마을

public class BJ1949 {
    static class Node {
        int id;
        Node next;

        Node(int id, Node next) {
            this.id = id;
            this.next = next;
        }
    }
    static int n;
    static int ans;
    static int[] population;
    static int[] visited;
    static int[][] dp;
    static Node[] adj;
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        population = new int[n + 1];
        visited = new int[n + 1];
        dp = new int[2][n + 1];
        adj = new Node[n + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= n; i++) {
            population[i] = Integer.parseInt(st.nextToken());
        }

        for(int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj[u] = new Node(v, adj[u]);
            adj[v] = new Node(u, adj[v]);
        }

        visited[1]++;
        dfs(1);
        System.out.println(Integer.max(dp[0][1], dp[1][1]));
    }

    private static void dfs(int id) {

        boolean flag = false;
        for(Node node = adj[id]; node != null; node = node.next) {
            if(visited[node.id] == 0) {     // 아직 방문하지 않은 노드(자식 노드)들에 대하여
                visited[node.id] = visited[id] + 1;
                dfs(node.id);
                flag = true;
            }
        }

        if(flag) {      // 리프 노드가 아닌 경우
            for(Node node = adj[id]; node != null; node = node.next) {
                if(visited[node.id] < visited[id]) continue;                // 부모 노드는 패쓰
                dp[0][id] += Integer.max(dp[0][node.id], dp[1][node.id]);   // id번 마을을 선택하지 않는다면, 자식 노드들에 대하여 (선택하지 않은 경우, 선택한 경우) 중 더 큰 값의 경우를 더해줌
                dp[1][id] += dp[0][node.id];                                // id번 마을을 선택한다면, 자식 노드들에 대하여 (선택한 경우)를 더해줌
            }
            dp[1][id] += population[id];                                    // id번 마을을 선택했으므로, id번 마을의 인구수를 더해줌
        }
        else {          // 리프 노드인 경우
            dp[0][id] = 0;                  // id번 마을을 선택하지 않은 경우
            dp[1][id] = population[id];     // id번 마을을 선택한 경우
        }
    }
}
