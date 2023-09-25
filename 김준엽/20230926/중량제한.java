package a0830;

import java.io.*;
import java.util.*;

public class 중량제한 {
    static int N,M,S,E;
    static ArrayList<int[]> lines[];
    static boolean visited[];
    static int limit;
    static int ans;
    public static void main(String[] args) throws Exception{
        System.setIn(new FileInputStream("C:\\SSAFY\\work_algo\\algo\\input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        lines = new ArrayList[N+1];
        for (int i = 0; i < N+1; i++) {
            lines[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            lines[from].add(new int [] {to, cost});
            lines[to].add(new int [] {from, cost});
            limit = Math.max(cost, limit);
        }
        st = new StringTokenizer(br.readLine());
        S = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        //여기서 이분탐색
        int left = 0;
        int right = limit;
        while(left <= right){
            int mid = (left + right)/2;
//            System.out.println(left + "," + mid + "," + right);
            visited = new boolean[N+1];
            boolean check = bfs(S, mid);
            if(check){
                ans = Math.max(mid, ans);
                left = mid+1;
            }else{
                right = mid-1;
            }
        }
        System.out.println(ans);
    }
    static boolean bfs(int start, int w){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        visited[start] = true;
        q.add(start);
        while(!q.isEmpty()){
            int cur = q.poll();
            if(cur == E) return true;
            for(int []x : lines[cur]){
                int to = x[0];
                int weight = x[1];
                if(!visited[to] && weight >= w){
                    q.add(to);
                    visited[to] = true;
                }
            }
        }
        return false;
    }
}
