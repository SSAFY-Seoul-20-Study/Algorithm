package BaekJoon;
import java.util.*;
import java.io.*;

public class Main_bj_1939_중량제한{

	static int N,M;
	static int start,end;
	
	static ArrayList<int[]>[] path;
	static boolean[] visited;
	
	static int answer = 0;
	static int[] dp;
	static PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>(){
		@Override
		public int compare(int[] o1, int[] o2) {
			return -Integer.compare(o1[2], o2[2]);
		}
	});
	
	
	public static void bfs() {
		path[end].sort(new Comparator<int[]>() {
			public int compare(int[] o1, int[] o2) {
				if(o1[0] == o2[0]) {
					return -Integer.compare(o1[1], o2[1]);
				}else {
					return Integer.compare(o1[0], o2[0]);
				}
			}
		});
		
		for(int i=0;i<path[end].size();i++) {
			
			int[] tmp = path[end].get(i);
			if(tmp[1]>dp[tmp[0]]) {
				dp[path[end].get(i)[0]] = path[end].get(i)[1];
				pq.add(new int[] {end,tmp[0],tmp[1]});
			}
		}
		while(!pq.isEmpty()) {
			int[] most = pq.poll();

			if(most[1] == start) {
				return;
			}
			
			for(int i=0;i<path[most[1]].size();i++) {
				int[] next = new int[]{most[1], path[most[1]].get(i)[0], path[most[1]].get(i)[1]};
				if(visited[next[1]] != true && (Math.min(next[2], dp[next[0]]) > dp[next[1]])) {
					dp[next[1]] = Math.min(next[2], dp[next[0]]);
					pq.offer(next);
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine().trim());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		path = new ArrayList[N+1];
		visited = new boolean[N+1];
		dp = new int[N+1];
		for(int i=0;i<N+1;i++) {
			path[i] = new ArrayList<int[]>();
		}
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine().trim());
			int[] tmp = new int[3];
			for(int t=0;t<3;t++) {
				tmp[t] = Integer.parseInt(st.nextToken());
			}

			
			path[tmp[0]].add(new int[] {tmp[1],tmp[2]});
			path[tmp[1]].add(new int[] {tmp[0],tmp[2]});	
		}
		
		
		st = new StringTokenizer(br.readLine().trim());
		start = Integer.parseInt(st.nextToken());
		end = Integer.parseInt(st.nextToken());
		
		visited[end] = true;
		bfs();

		System.out.println(dp[start]);

	}
	
}
