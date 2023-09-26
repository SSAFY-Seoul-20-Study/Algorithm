package BaekJoon;

import java.io.*;
import java.util.*;

public class Main_bj_1113_수영장만들기 {
	static int N,M;
	static int[][] pool;
	static int[][] bound;
	
	static boolean[][] visited;
	static int answer = 0;
	
	//상우하좌
	static int[] di = {-1,0,1,0};
	static int[] dj = {0,1,0,-1};
	
	public static void printpool(int[][] pp) {
		for(int i=0;i<N;i++) {
			System.out.println(Arrays.toString(pp[i]));
		}
	}
	
	public static boolean bfs(int si, int sj,int floor) {

		ArrayDeque<int[]> temp = new ArrayDeque<>();
		ArrayList<int[]> tempool = new ArrayList<>();
		temp.offer(new int[]{si,sj});
		tempool.add(new int[] {si,sj});
		
		
		boolean verify = true;
		
		int boundary = Integer.MAX_VALUE;
		
		while(!temp.isEmpty()) {
			int[] now = temp.poll();
			if(now[0] == 0 || now[0] == N-1 || now[1] ==0 || now[1] == M-1) {
				verify = false;
			}
			for(int i=0;i<4;i++) {
				int ni = now[0]+di[i];
				int nj = now[1]+dj[i];
				if(ni>=0 && ni<N && nj>=0 && nj<M ) {
					if(visited[ni][nj] == false && pool[ni][nj] == floor) {
						visited[ni][nj] = true;
						temp.offer(new int[] {ni,nj});
						tempool.add(new int[] {ni,nj});
					}
					if(pool[ni][nj] < floor) {
						verify = false;
					}
					if(pool[ni][nj]> floor) {
						boundary = Math.min(boundary, pool[ni][nj]);
					}
				}
			}
		}
		if(verify == true) {
			answer += tempool.size() * (boundary - floor);
			for(int i=0;i<tempool.size();i++) {
				int[] refresh = tempool.get(i);
				pool[refresh[0]][refresh[1]] = boundary;
				visited[refresh[0]][refresh[1]] = false;
			}
		}
		return verify;
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		pool = new int[N][M];
		visited = new boolean[N][M];
		for(int i=0;i<N;i++) {
			st= new StringTokenizer(br.readLine().trim());
			String sr = st.nextToken();
			for(int j=0;j<M;j++) {
				pool[i][j] = sr.charAt(j) -'0';
			}
		}

		for(int k=1;k<=9;k++) {
			//printpool(pool);
			for(int i=0;i<N;i++) {
				for(int j=0;j<M;j++) {
					if(pool[i][j] == k && visited[i][j] == false) {
						visited[i][j] = true;
						bfs(i,j,k);
					}
				}
			}
			//System.out.println("--------------------");
		}
		//printpool(pool);
		System.out.println(answer);
	}
}
