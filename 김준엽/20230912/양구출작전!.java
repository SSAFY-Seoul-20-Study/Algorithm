import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static long islands[];
    static boolean visited[];
    static ArrayList<Integer>[] lines;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        islands = new long[N+1];
        lines = new ArrayList[N+1];
        visited = new boolean[N+1];
        for (int i = 0; i < N+1; i++) {
            lines[i] = new ArrayList<>();
        }
        for (int i = 2; i < N-1+2; i++) {
            st = new StringTokenizer(br.readLine());
            String flag = st.nextToken();
            int count = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            //주의! 간선을 양쪽으로 연결할 경우 리프노드 판단을 조심해야함!
//            lines[i].add(to);
            lines[to].add(i);
            if(flag.equals("S")) islands[i] = count;
            else islands[i] = -count;
        }

        System.out.println(dfs(1));
    }
    static long dfs(int now){
        //만약 리프노드가 사이즈가 1인경우로 체크하면 오류 생김
        if(lines[now].size() == 0){
            if(islands[now] > 0) return islands[now];
            else return 0;
        }
        long sum = 0;
        sum += islands[now];
        for(int next:lines[now]){
            sum += dfs(next);
        }
        if(sum < 0 )return 0 ;
        return sum;
    }
}