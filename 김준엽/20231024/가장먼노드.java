import java.util.*;
class Solution {
    static int visited[];
    static int lines[][];
    public int solution(int n, int[][] edge) {
        int answer = 1;
        int max = 0;
        visited = new int[n+1];
        lines = edge;
        bfs();
        for(int i=1; i<n+1; i++){
            if(visited[i] > max){
                answer = 1;
                max = visited[i];
            }
            else if(visited[i] == max){
                answer++;
            }
        }
        System.out.println(Arrays.toString(visited));
        return answer;
    }
    static void bfs(){
        ArrayDeque<int []> q = new ArrayDeque<>();
        visited[1] = 1;
        q.add(new int []{1,1});
        while(!q.isEmpty()){
            int cur[] = q.poll();
            int node = cur[0];
            int count = cur[1];
            for(int x[] : lines){
                if(x[0] == node && visited[x[1]] == 0){
                    visited[x[1]] = count+1;
                    q.add(new int[] {x[1], count+1});
                }
                if(x[1] == node && visited[x[0]] == 0){
                    visited[x[0]] = count+1;
                    q.add(new int[] {x[0], count+1});
                }
            }
        }
    }
}