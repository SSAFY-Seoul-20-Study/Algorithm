package a1005;

import java.io.*;
import java.util.*;

public class Main_인하니카공화국 {
    static int N,M;
    static ArrayList<int []> lines[];
    static int islands[], INF = Integer.MAX_VALUE;
    static boolean visited[];
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());
        for (int tc = 0; tc < T; tc++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            islands = new int[N+1];
            lines =new ArrayList[N+1];
            visited = new boolean[N+1];
            for (int i = 1; i < N+1; i++) {
                islands[i] = INF;
                lines[i] = new ArrayList<>();
            }
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());
                lines[from].add(new int[] {to, cost});
                lines[to].add(new int [] {from, cost});
            }
            for (int i = 2; i < N+1; i++) {
                if(lines[i].size() == 1) islands[i] = lines[i].get(0)[1];
            }
            dfs(1);
            if(islands[1] == INF) System.out.println(0);
            else System.out.println(islands[1]);
        }
    }
    //노드에 적힌건 자식중 트리를 자르는 다이너 마이트의 수의 합이다
    //단 리프노드는 무조건 잘라야 하기에 리프노드에는 다이너 마이트의 수로 초기화 한다.
    //즉 내가 갈 다음 노드에 적힌 수와 간선 크기만 비교하면 된다.
    // 하지만 현재 노드가 INF 값이라면 아직 최초의 간선값이 초기화가 안된것이므로 더하지 말고 대입해준다.
    static int dfs(int now){
        visited[now] = true;
        for(int next[]:lines[now]){
            if(!visited[next[0]]){
                int nextEdge = dfs(next[0]);
                if(islands[now] == INF){
                    islands[now] = Math.min(nextEdge, next[1]);
                }
                else{
                    islands[now] += Math.min(nextEdge, next[1]);
                }
            }
        }
        return islands[now];
    }
}
