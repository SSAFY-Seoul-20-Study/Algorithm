package main;

import java.io.*;
import java.util.*;
public class Main_BJ_17471_게리맨더링 {

	static int N;
	static int[][] connect;
	static int[] a,b;
	static int[] population, area,parent;
	static boolean[] visited;
	static int ans = 1000000000;
	static int chg = 0;
	static boolean[] unionv;
	
	//유니온 파인드를 bfs로 해야한단걸 놓쳐서 시간이 좀 걸렸다...
	//bfs로 순서가 가장 빠른 노드로 유니온을 구분한다
	public static void union(int start) {
		unionv[start] = true;
		ArrayDeque<Integer> arr= new ArrayDeque<>();
		arr.offer(start);
		while(!arr.isEmpty()) {
			int now  = arr.poll();
			for(int i: connect[now]) {
				//같은 area 이어야만 유니온 갱신
				if(unionv[i] == false && area[now] == area[i]) {
					unionv[i] = true;
					arr.offer(i);
					//가장 앞쪽 노드를 부모노드로 갱신
					if(parent[now] < parent[i]) {
						parent[i] = parent[now];
					}
				}
			}
		}
	}
	
	//N에 따른 1부터 N/2까지의 부분집합을 구해 a,b팀을 구분시킨다.
	//부분집합의 특성상 N/2 이후 부터는 어차피 같은 값이라 무시
	public static void subs(int cnt,int start, int size) {
		
		//부분집합이 완성되면, b팀과 나머지 노드들을 anotherteam으로 만든다.
		//그 후 각 지역구들이 연결되었는지 확인 후, 각각 연결되었다면 그 차를 구한다.
		if (cnt == size) {

			int[] anotherteam = new int[N-size];
			int cn = 0;
			//팀 구분. b팀은 1로, a팀은 0으로 표시
			for(int i=0;i<N;i++) {
				if (Arrays.binarySearch(b,i) >= 0) {
					area[i] = 1;
					
				}
				else {
					area[i] = 0;
					anotherteam[cn++] = i;
				}
			}
			
			//유니온 초기화
			for(int i=0;i<N;i++) {
				parent[i] = i;
			}
			unionv = new boolean[N];
			//팀 구분에 따른 유니온화
			for(int i=0;i<N;i++) {
				if(unionv[i] == false) {
					union(i);
				}
			}
			
			int flag  = 0;
			//만약 b팀에서 부모노드가 다른 노드가 있다면(즉 이어져 있지 않다면) 바로 break
			for(int i = 0 ;i<size-1;i++) {
				if(parent[b[i]] != parent[b[i+1]]) {
					flag = 1;
					break;
				}
			}
			//b팀에서 이어져 있지 않은 노드가 있다면(flag ==1)이면 이후는 계산할 필요도 없음
			//만약 b팀은 모두 이어져 있다면 anotherteam이 모두 이어져 있는지 탐색
			if (flag  == 0) {
				for(int i = 0; i<N-size-1;i++) {
					if(parent[anotherteam[i]] != parent[anotherteam[i+1]]) {
						flag = 1;
						break;
					}
				}
			}
			
			//anotherteam,b 팀 모두 이어져 있다면 각 팀의 합을 구하고 그 차가 최소가 되는 ans 값을 갱신
			if(flag == 0) {
				//b팀의 인구수 합
				int pb = 0;
				for(int i :b) {
					pb += population[i];
				}
				int pa = 0;
				for(int i : anotherteam) {
					pa += population[i];
				}
				//한번이라도 최솟값이 갱신된다면(지역구를 나눌 수 있다면) chg를 1로 변경
				chg = 1;
				ans = Math.min(ans, Math.abs(pa-pb));
			}
			
			return;
		}
		for(int i=start;i<N;i++) {
			if(visited[i] == true) continue;
			visited[i] = true;
			b[cnt] = a[i];
			subs(cnt+1,i+1,size);
			visited[i] = false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine().trim());
		population = new int[N];
		area = new int[N];
		parent = new int[N];
		a = new int[N];
		visited = new boolean[N];
		st = new StringTokenizer(br.readLine().trim()," ");
		for(int i=0;i<N;i++) {
			population[i] = Integer.parseInt(st.nextToken());
			a[i]  = i;
			parent[i] = i;
		}

		connect = new int[N][];
	
		for(int i= 0 ; i<N;i++) {
			st = new StringTokenizer(br.readLine().trim()," ");
			int cn = Integer.parseInt(st.nextToken());
			connect[i] = new int[cn];
			for(int j=0;j<cn;j++) {
				connect[i][j] = Integer.parseInt(st.nextToken())-1;
			}
			Arrays.sort(connect[i]);
		}
		for(int i=1;i<=(int)N/2;i++) {
			b = new int[i];
			subs(0,0,i);
		}
		
		//한번도 chg가 변경되지 않았다면(나눌 수 있는 지역구가 아예 없다면) 출력값을 -1로 설정
		if(chg == 0) ans = -1;
		System.out.println(ans);
	}

}
