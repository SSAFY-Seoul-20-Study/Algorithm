import java.util.*;

class Solution {
    public static ArrayList<Integer>[] al;
    public static int N;
    public static boolean[] visited;
    public static int[] distance;
    public static int ans=0,longest=0;
    
    public void dfs(int now,int dist){
        for(int i=0;i<al[now].size();i++){
            int next = al[now].get(i);   
            if(next!=1){
                if(distance[next] == 0 || distance[next] > dist+1){
                    distance[next] = dist+1;
                    dfs(next,dist+1);
                    visited[next] = false;
                }               
            }         
        }
    }
    
    public int solution(int n, int[][] edge) {
        int answer = 0;
        N = n;
        al = new ArrayList[N+1];
        visited = new boolean[N+1];
        distance = new int[N+1];
        for(int i=0;i<N+1;i++){
            al[i] = new ArrayList<>();
        }
        for(int[] ver : edge){
            al[ver[0]].add(ver[1]);
            al[ver[1]].add(ver[0]);
        }

        visited[1] = true;
        dfs(1,0);
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for(int i=1;i<=N;i++){
            pq.offer(distance[i]);
        }
        ans =  1;
        longest = pq.poll();
        while(!pq.isEmpty()){
            if(longest != pq.poll()){
                break;
            }
            else{
                ans+=1;
            }
        }
        answer = ans;
        return answer;
    }
}