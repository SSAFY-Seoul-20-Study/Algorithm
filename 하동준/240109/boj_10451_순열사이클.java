package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class boj_10451_순열사이클 {
    static int n;
    static int[] p;
    static int[] g;
    static boolean[] v;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());
        for (int i = 0; i < t; i++) {
            n = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            g = new int[n+1];
            v = new boolean[n+1];
            for (int j = 1; j <= n; j++) {
                int to = Integer.parseInt(st.nextToken());
                g[j] = to;
            }
            int cnt = 0;
            make();
            for (int j = 1; j <= n; j++) {
//                System.out.printf("for문"+j+"입니다.\n");

                // j에서 to
                int from = j;
                if (v[from]) continue;
                int to = g[j];
                v[from] = true;
                while (true) {
                    // 사이클 미발생 -> to 방문체크, to를 from으로 교체
                    if (union(from,to)) {
//                        System.out.printf("from: %d to: %d\n",from,to);
//                        System.out.println(Arrays.toString(p));
                        v[to] = true;
                        from = to;
                        to = g[from];
                    } else {
                        // 사이클 발생 -> cnt++, break
//                        System.out.println("사이클 발생");
//                        System.out.printf("from: %d to: %d\n",from,to);
//                        System.out.println(Arrays.toString(p));
                        cnt++;
                        break;
                    }
//                    if (v[to]) break;
                }
            }
//            System.out.println(cnt);
            sb.append(cnt).append("\n");
//            break;
        }
        System.out.println(sb.toString());
    }
    static boolean union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);
        if (aRoot==bRoot) return false;
        p[a] = bRoot;
        return true;
    }
    static int find(int a) {
        if (p[a]==a) return a;
        return p[a]=find(p[a]);
    }
    static void make() {
        p = new int[n+1];
        for (int i = 1; i <= n; i++) {
            p[i] = i;
        }
    }

}
