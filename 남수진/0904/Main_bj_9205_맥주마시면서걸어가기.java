package a0903;

import java.util.*;
import java.io.*;

public class Main_bj_9205_맥주마시면서걸어가기 {

	static int n;
	static ArrayList<int[]> g;
	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc<=T;tc++) {
			n = Integer.parseInt(br.readLine());
			
			g = new ArrayList<>();
			
			for(int i = 0; i < n + 2; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				
				g.add(new int[] {x, y});
			}
			
			sb.append(bfs(g.get(0)[0], g.get(0)[1])).append("\n");
		}
		System.out.println(sb.toString());
		
		br.close();
	}

	static String bfs(int x, int y) {
		
		ArrayDeque<int []> q = new ArrayDeque<>();
		
		boolean[] v= new boolean[n + 2];
		
		
		q.offer(new int[] {x, y});
		v[0] = true;
		
		while(!q.isEmpty()) {
			int px = q.peek()[0];
			int py = q.poll()[1];
			
			for(int i = 1; i < n + 2; i++) {
				int nx = g.get(i)[0];
				int ny = g.get(i)[1];
				
				// 맥주 20병 -> 50 * 20 = 1000 까지 이동 가능
				if(Math.abs(nx - px) + Math.abs(ny - py) <= 1000 && !v[i]) {
					q.offer(new int[] {nx, ny});
					v[i] = true;
					if(i == n + 1) return "happy";
				}
				
			}
		}
		return "sad";
		
	}
}
