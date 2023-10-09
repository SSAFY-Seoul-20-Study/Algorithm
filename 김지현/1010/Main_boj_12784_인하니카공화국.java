import java.io.*;
import java.util.*;

public class Main_boj_12784_인하니카공화국 {

    static int INF = Integer.MAX_VALUE;
    static int N, M;
    static List<int[]>[] adj;

    public static void main(String[] args) throws Exception {
//        System.setIn(new FileInputStream("res/input_boj_12784.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();


        int T = Integer.parseInt(br.readLine());
        for(int tc=1; tc<=T; tc++){
            // 입력받기
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            // 초기화
            adj = new List[N+1];
            for(int i=1; i<=N; i++){
                adj[i] = new ArrayList<>();
            }

            // 인접리스트 값 넣기
            for(int m=0; m<M; m++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());

                adj[a].add(new int[]{b,c});
                adj[b].add(new int[]{a,c});
            }

            int result = dfs(1, -1, INF);

            sb.append((result==INF ? 0 : result) + "\n"); // 1만 입력으로 들어온 경우 예외 처리
        }
        System.out.println(sb.toString());
        br.close();
    }

    private static int dfs(int now, int past, int bomb){
        int res = 0;
        for(int[] nxt : adj[now]){ // 자식 노드를 돌면서
            if(past != nxt[0]){ // 사이클이 돌지 않으면(바로 위와 같지 않으면)
                res += dfs(nxt[0], now, nxt[1]); // res에 더해주기
            }
        }
        if(res == 0) res = bomb; // 리프 노드인 경우 해당 다이너마이트 개수를 그대로 넣기
        return Math.min(res, bomb); // 내 다이너마이트 개수와 아래 자식 개수를 비교해서, 작은거 반환
    }
}