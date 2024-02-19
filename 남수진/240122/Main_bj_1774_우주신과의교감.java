package a0123;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_1774_우주신과의교감 {

    static class Node{
        int num;
        double x;
        double y;

        Node(int num, double x, double y) {
            this.num = num;
            this.x = x;
            this.y = y;
        }
    }
    static int[] p;
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        Node[] nodes = new Node[N + 1];


        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());

            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());

            nodes[i] = new Node(i, x, y);
        }
        p = new int[N + 1];
        for (int i = 1; i <= N; i++) p[i] = i;

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            union(start, end);
        }

        PriorityQueue<double[]> pq = new PriorityQueue<>((o1, o2) -> Double.compare(o1[2], o2[2]));
        for (int i = 1; i < N; i++) {
            for (int j = i + 1; j <= N; j++) {
                double dist = distance(nodes[i], nodes[j]);
                pq.offer(new double[]{i, j, dist});
            }
        }

        double result = 0;

        while (!pq.isEmpty()) {
            double[] cur = pq.poll();

            if (union((int)cur[0], (int)cur[1])) {
                result += cur[2];
            }
        }
        System.out.println(String.format("%.2f", result));
    }

    static int find(int a) {
        if(p[a] == a) return a;
        return p[a] = find(p[a]);
    }

    static boolean union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);

        if(aRoot == bRoot) return false;
        else {
            p[bRoot] = aRoot;
            return true;
        }
    }

    static double distance(Node n1, Node n2) {
        return Math.sqrt(Math.pow(n1.x - n2.x, 2) + Math.pow(n1.y-n2.y,2));
    }
}
