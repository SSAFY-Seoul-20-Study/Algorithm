import java.util.*;
import java.io.*;

public class Main_bj_g3_1774_우주신과의_교감 {
	static class QInfo implements Comparable<QInfo> {
		int n1, n2;
		double d;
		public QInfo(int x, int y, double d) {
			this.n1 = x;
			this.n2 = y;
			this.d = d;
		}
		public String toString() {
			return n1 + " " + n2 + " " + d;
		}
		public int compareTo(QInfo o) {
			return Double.compare(this.d, o.d);
		}
	}

	static int N, M;
	static int[][] a;
	static PriorityQueue<QInfo> pq;
	static int[] p;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		pq = new PriorityQueue<>();

		a = new int[N + 1][2];
		for(int i=1;i<=N;i++) {
			st = new StringTokenizer(br.readLine());
			a[i][0] = Integer.parseInt(st.nextToken());
			a[i][1] = Integer.parseInt(st.nextToken());
		}

		p = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			p[i] = i;
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int n1 = Integer.parseInt(st.nextToken());
			int n2 = Integer.parseInt(st.nextToken());
			union(n1, n2);
		}

		for(int i=1;i<=N;i++) {
			for(int j=i+1;j<=N;j++) {

				double l1 = (double) Math.pow((double)a[i][0] - a[j][0], 2);
				double l2 = (double) Math.pow((double)a[i][1] - a[j][1], 2);

				double dist = (double)Math.sqrt(l1 + l2);

				pq.add(new QInfo(i, j, dist));
			}
		}

		double ans = 0;

		while (!pq.isEmpty()) {
		    QInfo info = pq.poll();
		    int root1 = find(info.n1);
		    int root2 = find(info.n2);

			if(root1 != root2){
				ans += info.d;
			}
			union(info.n1, info.n2);
		}

		System.out.println(String.format("%.2f", ans));

		br.close();
	}

	public static int find(int x) {
		if (p[x] == x) return x;
		return p[x] = find(p[x]);
	}

	public static void union(int x, int y) {
		x = find(x);
		y = find(y);
		if (x != y) p[y] = x;
	}

}