package a0109;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_10451_순열사이클 {

    static int N;
    static int[] parents;
    static boolean[] v;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        for(int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());

            parents = new int[N + 1];
            StringTokenizer st = new StringTokenizer(br.readLine());

            for(int i = 1; i <= N; i++){
                parents[i] = Integer.parseInt(st.nextToken());
            }

            v = new boolean[N + 1];
            int cnt = 0;
            for(int i = 1; i <= N; i++) {
                if(v[i]) continue;
                int tmp = find(i, i);
                parents[tmp] = tmp;
                cnt++;
            }
            System.out.println(cnt);
        }
    }

    static int find(int a, int start){
        v[a] = true;
        if(start == parents[a]) return a;
        return parents[a] = find(parents[a], start);
    }

}
