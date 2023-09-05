package BaekJoon;

import java.io.*;
import java.util.*;

public class Main_bj_9205_맥주마시면서걸어가기 {

	public static int[] startpoint = new int[2];
	public static int[] endpoint = new int[2];
	public static int[][] cs;
	public static boolean[] visited;
	public static int n;
	
	//맨허튼 거리가 뭔지 알아보는 시간...
	public static int getdistance(int[] from, int[] to) {
		int distance = (int)(Math.abs(from[0]-to[0]) + Math.abs(from[1]-to[1]));
		return distance;
	}
	
	//사실 단순 dfs 사용하면 풀린다.
	//처음엔 출발지 기준으로 정렬이 필요한가...? 했지만
	//생각해보면 어차피 연결된 점(이동 가능한 편의점)들은 서로 다 연결될 것이기에 정렬도 필요 없다.
	public static boolean dfs(int cnt, int[] now) {
		//도착점에 거리에 들어온다면 true로 종료
		if(getdistance(now,endpoint) <= 1000) {
			return true;
		}
		//만약 편의점 다 돌았는데도 도착 못하면? false
		if(cnt == n) {
			return false;
		}
		for(int i = 0;i<n;i++) {
			if(visited[i] == true) continue;
			if(getdistance(now,cs[i]) > 1000) continue;
			visited[i] = true;
			if(dfs(cnt+1,cs[i])) return true;
		}
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine().trim());
		for(int tc = 0;tc<T;tc++) {
			n = Integer.parseInt(br.readLine().trim());
			//편의점 위치들 저장
			cs = new int[n][2];
			visited = new boolean[n];
			st = new StringTokenizer(br.readLine().trim()," ");
			startpoint[0] = Integer.parseInt(st.nextToken());
			startpoint[1] = Integer.parseInt(st.nextToken());
			for(int i=0;i<n;i++) {
				st = new StringTokenizer(br.readLine().trim()," ");
				cs[i][0] = Integer.parseInt(st.nextToken());
				cs[i][1] = Integer.parseInt(st.nextToken());
			}
			st = new StringTokenizer(br.readLine().trim()," ");
			endpoint[0] = Integer.parseInt(st.nextToken());
			endpoint[1] = Integer.parseInt(st.nextToken());
		
			//dfs로 돌리기
			if(dfs(0,startpoint)) System.out.println("happy");
			else System.out.println("sad");
		}
	}
}
