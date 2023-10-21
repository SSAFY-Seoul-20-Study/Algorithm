package a1017;

import java.util.*;
import java.io.*;

class Solution_pro_49189_가장먼노드 {

	static List<Integer>[] adj;
    static int[] dist;
    
    public int solution(int n, int[][] edge) throws Exception {
        int answer = 0;
        adj = new List[n+1];
        
        for(int i=1; i<=n; i++){
            adj[i] = new ArrayList<Integer>();
        }
        
        for(int[] e : edge){
            adj[e[1]].add(e[0]);
            adj[e[0]].add(e[1]);
        }
        
        Queue<int[]> q = new ArrayDeque<>();
        dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        
        q.offer(new int[]{1, 0});
        dist[1] = 0;
        int dist_max = 0;
        
        while(!q.isEmpty()){
            int[] cur = q.poll();
            for(int ver : adj[cur[0]]){
                if(dist[ver] == Integer.MAX_VALUE){
                    dist[ver] = cur[1] + 1;
                    q.offer(new int[]{ver, cur[1]+1});
                    if(dist[ver] > dist_max) dist_max = dist[ver];
                }
            }
        }
        
        for(int i=2; i<=n; i++){
            if(dist[i] == dist_max) answer++;
        }

                    
        return answer;
    }
}


