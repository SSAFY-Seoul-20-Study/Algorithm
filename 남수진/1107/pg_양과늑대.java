import java.util.*;
class Solution {
    
    static List<Integer>[] graph;
    static int result = 0;
    public int solution(int[] info, int[][] edges) {
        int answer =0;
        
        graph = new List[info.length];
        
        for(int i = 0; i < info.length; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for(int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }
        
        boolean[] v = new boolean[info.length];
        
        dfs(0, 0, 0, v, info);
        
        return result;
    }
    
    public static void dfs(int idx, int sheep, int wolf, boolean[] v, int[] info) {
        
        if(info[idx] == 0) sheep++;
        else wolf++;
        
        if(wolf >= sheep) return;
        
        boolean[] copy = new boolean[v.length];
        
        for(int i = 0; i < copy.length; i++) {
            copy[i] = v[i];
        }
        copy[idx] = true;
        
        result = Math.max(result, sheep);
        
        for(int i = 0; i < copy.length; i++) {
            if(!copy[i]) continue;
            
            for(int edge : graph[i]) {
                if(!copy[edge]) {
                    dfs(edge, sheep, wolf, copy, info);
                }
            }
        }
        
    }
}
