import java.io.*;
import java.util.*;

// 양 구출 작전

public class BJ16437 {

    static class Node {
        int id;
        Node next;

        Node(int id, Node next) {
            this.id = id;
            this.next = next;
        }
    }
    static class Island {
        boolean isWolf;
        int num;
        
        Island(boolean isWolf, int num) {
            this.isWolf = isWolf;
            this.num = num;
        }
    }
    static int n;
    static Island[] islands;
    static Node[] adj;

    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        
        islands = new Island[n + 1];
        islands[1] = new Island(false, 0);
        adj = new Node[n + 1];
        
        StringTokenizer st;
        for(int i = 2; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            boolean isWolf = st.nextToken().charAt(0) == 'W' ? true : false;
            int num = Integer.parseInt(st.nextToken());
            int idx = Integer.parseInt(st.nextToken());
            
            islands[i] = new Island(isWolf, num);
            adj[idx] = new Node(i, adj[idx]);
        }
        
        System.out.println(dfs(1));
    }

    private static long dfs(int id) {

        long total = 0;
        for(Node node = adj[id]; node != null; node = node.next) {
            total += dfs(node.id);
        }

        // 늑대라면, (1) 양의 수가 더 많다면 잡아먹히고 남은 양의 수를 넘겨줌 (2) 늑대의 수가 더 많거나 같다면 0마리를 넘겨줌
        if(islands[id].isWolf) return (total - islands[id].num) > 0 ? total - islands[id].num : 0;            
        // 양이라면, 양의 수 만큼 더해서 넘겨줌
        return islands[id].num + total;                 
    }
    
}
