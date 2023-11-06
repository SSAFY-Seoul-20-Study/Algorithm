import java.util.*;
class Solution {
    static int [] gInfo;
    static int [][]gEdges;
    static int maxSheepCnt = 0;
    public int solution(int[] info, int[][] edges) {
        gInfo = info;
        gEdges = edges;
        boolean[] visited = new boolean[info.length];
        dfs(0, visited, 0,0);
        return maxSheepCnt;
    }
    static void dfs(int idx, boolean[] visited, int sheep, int wolf){
        visited[idx] = true;
        if(gInfo[idx] == 0){
            sheep++;
            if(sheep > maxSheepCnt) maxSheepCnt = sheep;
        }else{
            wolf++;
        }
        if(sheep <= wolf) return;
        
        for(int edge[] : gEdges){
            if(visited[edge[0]] && !visited[edge[1]]){
                boolean[] nextVisted = new boolean[visited.length];
                for(int i =0; i<visited.length; i++){
                    nextVisted[i] = visited[i];
                }
                dfs(edge[1], nextVisted, sheep, wolf);
            }
        }
    }
    
}