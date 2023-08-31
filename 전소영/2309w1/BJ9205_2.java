import java.io.*;
import java.util.*;

// 맥주 마시면서 걸어가기

public class BJ9205_2 {

    static int t;
    static int n;
    static boolean flag;
    static int[][] pos;
    static boolean[] visited;
    static ArrayDeque<int[]> queue = new ArrayDeque<>();

    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        t = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        for(int tc = 0; tc < t; tc++) {
            
            flag = false;
            n = Integer.parseInt(br.readLine());
            pos = new int[n + 2][2];
            visited = new boolean[n + 2];

            st = new StringTokenizer(br.readLine());
            pos[0][0] = Integer.parseInt(st.nextToken());
            pos[0][1] = Integer.parseInt(st.nextToken());
            
            for(int i = 1; i <= n + 1; i++) {
                st = new StringTokenizer(br.readLine());
                pos[i][0] = Integer.parseInt(st.nextToken());
                pos[i][1] = Integer.parseInt(st.nextToken());
            }

            if(calculateDist(pos[0][0], pos[0][1], pos[n + 1][0], pos[n + 1][1]) <= 1000) flag = true;
            else solution();
            sb.append(flag ? "happy\n" : "sad\n");
        }

        System.out.println(sb.toString());
    }

    private static void solution() {

        queue.clear();
        queue.add(new int[] {pos[0][0], pos[0][1]});

        while(!queue.isEmpty()) {
            int[] poll = queue.poll();

            for(int i = 1; i <= n + 1; i++) {
                if(visited[i] || calculateDist(poll[0], poll[1], pos[i][0], pos[i][1]) > 1000) continue;
                if(i == n + 1) {
                    flag = true;
                    return;
                }
                visited[i] = true;
                queue.add(new int[] {pos[i][0], pos[i][1]});
            }
        }
    }

    private static int calculateDist(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}
