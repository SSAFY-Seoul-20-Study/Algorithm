import java.util.*;

class Solution {

    private class Node {
        int num;
        Node next;

        Node(int num, Node next) {
            this.num = num;
            this.next = next;
        }
    }
    public int solution(int n, int[][] edge) {

        int max = 0;
        int maxCnt = 0;
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        boolean[] visited = new boolean[n + 1];
        Node[] adj = new Node[n + 1];
        ArrayDeque<int[]> queue = new ArrayDeque<>();

        for(int i = 0; i < edge.length; i++) {
            int u = edge[i][0];
            int v = edge[i][1];
            adj[u] = new Node(v, adj[u]);
            adj[v] = new Node(u, adj[v]);
        }

        visited[1] = true;
        queue.add(new int[] {1, 0});
        while(!queue.isEmpty()) {

            int[] p = queue.poll();

            dist[p[0]] = p[1];
            if(max < p[1]) {
                max = p[1];
                maxCnt = 1;
            }
            else if(max == p[1]) {
                maxCnt++;
            }

            for(Node node = adj[p[0]]; node != null; node = node.next) {
                if(visited[node.num]) continue;
                visited[node.num] = true;
                queue.add(new int[] {node.num, p[1] + 1});
            }
        }

        return maxCnt;
    }
}
