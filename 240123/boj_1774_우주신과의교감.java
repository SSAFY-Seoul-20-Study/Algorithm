package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class boj_1774_우주신과의교감 {
    static int n,m;
    static long[][] gods;
    static int[] p;
    static boolean[][] v;
    static class Edge implements Comparable<Edge> {
        int to;
        int from;
        double l;

        public Edge(int to, int from, double l) {
            this.from = from;
            this.to = to;
            this.l = l;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "to=" + to +
                    ", from=" + from +
                    ", l=" + l +
                    '}';
        }



        @Override
        public int compareTo(Edge edge) {
            if (this.l < edge.l) {
                return -1;
            }
            return 1;
        }
    }
    static PriorityQueue<Edge> edges = new PriorityQueue<>();
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        gods = new long[n+1][2];
        p = new int[n+1];
        v = new boolean[n+1][n+1];
        for (int i = 1; i <= n; i++) {
            p[i] = i;
        }
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            long a = Long.parseLong(st.nextToken());
            long b = Long.parseLong(st.nextToken());
            gods[i] = new long[]{a,b};
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if (v[a][b] || v[b][a]) continue;
            v[a][b] = true;
            v[b][a] = true;
            // 여기서는 이미 연결 되어있기 때문에 무조건 방문체크를 해야해.
            // 사이클 생겨도 노상관
            int rootA = find(a);
            int rootB = find(b);
            if (rootA!=rootB) {
                p[rootA] = rootB;
            }
        }

        // 나머지 선 정리
        for (int i = 1; i <= n; i++) {
            for (int j = i+1; j <= n; j++) {
                if (v[i][j] || v[j][i]) continue;
                double content = Math.pow((gods[i][0]-gods[j][0]),2) + Math.pow((gods[i][1]-gods[j][1]),2);
                content = Math.sqrt(content);
                edges.add(new Edge(i,j,content));
            }
        }

        // 이으려는 두점이 이미 같은 부모를 가지면 못 넣어.
        double res = 0;
        while (true) {
            if (edges.isEmpty()) break;
            Edge e = edges.poll();
            if (union(e.from, e.to)) {
                res += e.l;
            }
        }
        System.out.printf("%.2f",res);


    }
    static int find(int a) {
        if (p[a]==a) return a;
        return p[a]=find(p[a]);
    }
    static boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA==rootB) return false;
        p[rootA] = rootB;
        return true;
    }
}
