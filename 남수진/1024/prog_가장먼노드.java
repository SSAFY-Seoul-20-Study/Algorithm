import java.util.*;

class Solution {
    public int solution(int n, int[][] edge) {
        int answer = 0;
        
        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[] v = new boolean[n + 1];
        
        q.offer(new int[] {1, 1});
        
        int max = 0;
        while(!q.isEmpty()) {
            int cur = q.peek()[0];
            int depth = q.poll()[1];
            
            if(v[cur]) continue;
            v[cur] = true;
            
            if(max < depth) {
                max = depth;
                answer = 1;
            } else if(max == depth) {
                System.out.println(cur);
                answer++;
            }
            
            for(int i = 0; i < edge.length; i++) {
                if(edge[i][0] == cur) {
                    if(v[edge[i][1]]) continue;
                    q.offer(new int[] {edge[i][1], depth + 1});
                } else if(edge[i][1] == cur) {
                    if(v[edge[i][0]]) continue;
                    q.offer(new int[] {edge[i][0], depth+1});
                } 
            }
            
        }
        
        
        return answer;
    }
}
