package study;

import java.io.*;
import java.util.*;

public class 게리맨더링 {
	static int N;
	static int [] humans, dummy;
	static HashMap<Integer, Boolean> [] map;
	static int min = Integer.MAX_VALUE;
	static boolean [] visited, teamCheck;
	public static void main(String args[]) throws Exception
	{
		System.setIn(new FileInputStream("study/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		humans = new int[N];
		map = new HashMap[N];
		for(int i = 0; i<N; i++) {
			humans[i] = Integer.parseInt(st.nextToken());
		}
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			int M = Integer.parseInt(st.nextToken());
			map[i] = new HashMap<>();
			for(int j=0; j<M; j++) {
				map[i].put(Integer.parseInt(st.nextToken())-1, true);
			}
		}
		//구현
		for(int i=1; i<N; i++) {
			dummy = new int[i];
			comb(0, 0,i);
		}
		if(min == Integer.MAX_VALUE) min = -1;
		System.out.println(min);
		
		br.close();
	}
	static void comb(int depth, int start ,int target) {
		if(depth == target) {
			teamCheck = new boolean [N];
			for(int t : dummy) teamCheck[t] = true;
			int start1=0, start2=0	;
			for(int i =0; i<N;i++) {
				if(teamCheck[i]) start1 = i;
				else start2 = i;
			}
			if(checkSector(start1, true) && checkSector(start2, false)) {
				int left = 0, right =0;
				for(int i =0; i<N;i++) {
					if(teamCheck[i]) left += humans[i];
					else right += humans[i];
				}
				min = Math.min(min, Math.abs(left-right));
			}
			return;
		}
		for(int i = start; i<N; i++) {
			dummy[depth] = i;
			comb(depth+1, i+1, target);
		}
	}
	static boolean checkSector(int start ,boolean flag) {
		int ans = 0;
		for(int i=0; i<N;i++) {
			if(teamCheck[i] == flag) ans++;
		}
		ArrayDeque<Integer> q = new ArrayDeque<>();
		visited = new boolean[N];
		visited[start] = true;
		q.offer(start);
		int count = 1;
		while(!q.isEmpty()) {
			int cur = q.poll();
			for(int k : map[cur].keySet()) {
				if(teamCheck[k] == flag && !visited[k]) {
					q.offer(k);
					visited[k] = true;
					count++;
				}
			}
		}
		if(count==ans) return true;
		else return false;
	}
	
}
