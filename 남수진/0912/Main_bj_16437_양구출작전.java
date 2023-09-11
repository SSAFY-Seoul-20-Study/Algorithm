package a0912;

import java.util.*;
import java.io.*;

public class Main_bj_16437_양구출작전 {

	static class land{
		char info;
		long cnt;
		land(char info, long cnt) {
			this.info = info;
			this.cnt = cnt;
		}
	}
	
	static int N;
	static List<Integer>[] g;
	static land[] l;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());

		g = new List[N + 1];
		
		for(int i = 0; i < N + 1; i++) g[i] = new ArrayList<>();

		l = new land[N + 1];
		l[1] = new land('S', 0);

		for(int i = 2; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			char info = st.nextToken().charAt(0);
			long cnt = Integer.parseInt(st.nextToken());
			int root = Integer.parseInt(st.nextToken());
		
			g[root].add(i);
			l[i] = new land(info, cnt);
		}
		
		System.out.println(dfs(1));
		
	}
	
	static long dfs(int idx) {
		long sum = 0;
		
		for(int edge : g[idx]) {
			sum += dfs(edge);
		}
		
		if(l[idx].info == 'S') return sum + l[idx].cnt;
		else {
			if(sum < l[idx].cnt) return 0;
			else return sum - l[idx].cnt;
		}
		
		
	}

}
