package main;

import java.io.*;
import java.util.*;

public class Main_bj_17472_다리만들기2 {
	
	static int N,M;
	static int[][] imap;
	static boolean[][] visited;
	static int[] di = {-1,0,1,0};	//상우하좌
	static int[] dj = {0,1,0,-1};
	static int[][] bridgelong;
	static boolean[] islandvisit;
	static int ans = -1;
	static int[] parent;
	static int[][] connect;
	static int[] ip;
	static boolean[] uv;

	//모든 노드가 이어져 있는지 확인하기 위해 전체 노드를 유니온화
	public static void makeparent(int start) {
		uv[start] = true;
		ArrayDeque<Integer> arr= new ArrayDeque<>();
		arr.offer(start);
		while(!arr.isEmpty()) {
			int now  = arr.poll();
			for(int i: connect[now]) {
				//같은 area 이어야만 유니온 갱신
				if(uv[i] == false && i>0) {
					uv[i] = true;
					arr.offer(i);
					//가장 앞쪽 노드를 부모노드로 갱신
					if(ip[now] < ip[i]) {
						ip[i] = ip[now];
					}
				}
			}
		}
	}
	
	//크루스칼 알고리즘 적용을 위한 유니온화
	public static void union(int x, int y) {
		x = find(x);
		y = find(y);
		if(x<y) parent[y] = x;
		else parent[x] = y;
	}
	
	public static int find(int x) {
		if (parent[x] == x) return x;
		else return find(parent[x]);
	}
	
	//크루스칼. 다리 길이에 따른 정렬법을 모르겠어서 PirorityQueue로 구현.
	//진짜진짜진짜 파이썬 lambda sorting 마렵다.
	public static void kruskal(int inum) {
		parent = new int[inum+1];
		PriorityQueue<int[]> kr = new PriorityQueue<>(new Comparator<int []>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				// TODO Auto-generated method stub
				if (o1[0]==o2[0]) {
					if(o1[1]==o2[1]) return o1[2]-o2[2];
					return o1[1]-o2[1];
				}
				return o1[0]-o2[0];
			}
		 });
		for(int i=1;i<=inum;i++) {
			for(int j= i;j<=inum;j++) {
				if(bridgelong[i][j] < 1000) {
					kr.offer(new int[] {bridgelong[i][j], i, j});
				}
			}
			parent[i] = i;
		}
		int cost = 0;
		while(!kr.isEmpty()) {
			int[] now = kr.poll();
			//System.out.println(Arrays.toString(now));
			if(find(now[1])!= find(now[2])) {
				cost += now[0];
				union(now[1],now[2]);
			}
		}
		if(cost ==0) cost = -1;
		ans = cost;
		
	}
	
	//bfs로 섬 별로 구분. 섬 갯수 측정 위함.
	public static void makemap(int r, int c, int island) {
		visited[r][c] = true;
		imap[r][c] = island;
		ArrayDeque<int[]> arr = new ArrayDeque<>();
		arr.offer(new int[] {r,c});
		while(!arr.isEmpty()) {
			int[] ij = arr.poll();
			int i = ij[0];
			int j = ij[1];
			for(int d = 0; d<4;d++) {
				int ni = i+di[d];
				int nj = j+dj[d];
				if(ni>=0 && ni<N && nj>=0 && nj<M && visited[ni][nj] == false && imap[ni][nj]!=0) {
					visited[ni][nj] = true;
					imap[ni][nj] = island;
					arr.offer(new int[] {ni,nj});
				}
			}
		}
	}
	
	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine().trim()," ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		imap = new int[N][M];
		visited =  new boolean[N][M]; 
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine().trim()," ");
			for(int j=0;j<M;j++) {
				imap[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int inum = 1;
		for(int i = 0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(visited[i][j] == false && imap[i][j] !=0) {
					makemap(i,j,inum++);
				}
			}
		}
		
		//섬이 나올때 마다 inum을 1씩 증가시키고 마지막에서 ++로 1 더 증가하니, 1만큼 빼주면 섬 갯수 측정 가능
		inum = inum-1;
		int prev = 0;
		int bridge;
		bridgelong = new int[inum+1][inum+1];
		for(int i=0;i<=inum;i++) {
			Arrays.fill(bridgelong[i],1000);
		}


		//가로 다리 체크
		//그냥 무식하게 앞뒤에 섬인지 바다인지 측정하면 된다.
		//다만 같은 섬 내에서 바다가 있는경우, 섬간 거리가 1 이하인 경우를 따로 생각해야한다.
		//2차원 배열로 섬 간 다리 길이의 최소를 저장한다.
		for(int i=0;i<N;i++) {
			int pi = -1;
			int ni = -1;
			bridge = 0;
			for(int j = 1;j<M;j++) {
				prev = imap[i][j-1];
				if(prev != imap[i][j]) {
					if(imap[i][j] ==0) {
						pi = prev;
					}
					else {
						if(pi !=-1) {
							if(bridge >1) {
								ni = imap[i][j];
								if(pi != ni) {
									//System.out.println(pi+ " "+ni+ " "+bridge);
									bridgelong[pi][ni] = Math.min(bridgelong[pi][ni], bridge);
									bridgelong[ni][pi] = Math.min(bridgelong[ni][pi], bridge);
								}
								
							}
							
							bridge = 0;
						}
					}
				}
				if(imap[i][j] ==0 && pi!=-1) {
					bridge ++;
				}
			}
		}
		
		//세로 다리 체크
		for(int j=0;j<M;j++) {
			int pj = -1;
			int nj = -1;
			bridge = 0;
			for(int i = 1;i<N;i++) {
				prev = imap[i-1][j];
				if(prev != imap[i][j]) {
					if(imap[i][j] ==0) {
						pj = prev;
					}
					else {
						if(pj !=-1) {
							if(bridge>1) {
								nj = imap[i][j];
								if(pj!=nj) {
									//System.out.println(pi+ " "+ni+ " "+bridge);
									bridgelong[pj][nj] = Math.min(bridgelong[pj][nj], bridge);
									bridgelong[nj][pj] = Math.min(bridgelong[nj][pj], bridge);
								}
								
							}
							bridge = 0;
						}
					}
				}
				if(imap[i][j] ==0 && pj!=-1) {
					bridge ++;
				}
			}
		}
		
		islandvisit = new boolean[inum+1];
		
		//노드간 연결노드를 저장한다. 어차피 모든 노드(섬)는 1부터 시작하므로 고정된 길이의 2차원 배열로 생성이 가능
		connect = new int[inum+1][inum+1];
		for(int i =1;i<=inum;i++) {
			int tmp =0;
			for(int j=1;j<=inum;j++) {
				if(bridgelong[i][j]<1000) {
					connect[i][tmp++] = j;
				}
			}
		}


		//ip = island parent: 섬들이 이어진 경우가 아예 불가능한 경우를 고려해줘야한다!!!!
		//그걸 위해서 크루스칼 적용 전 이 그래프가 모든 노드가 이어진 그래프인지 부터 판별해야한다.
		ip = new int[inum+1];
		for(int i = 1;i<=inum;i++) {
			ip[i] = i;
		}
		uv = new boolean[inum+1];
		
		//makeparent로 각 노드들의 조상노드를 정리한다.
		//모든 노드의 조상노드가 같다면 연결되어있는것, 아니면 끊긴 그래프이다.
		for(int i = 1;i<=inum;i++) {
			if(uv[i] == false) {
				makeparent(i);
			}
		}
		//만약 끊긴 그래프라면 크루스칼 돌릴 필요도 없이(애초에 돌리면 값이 틀려진다) -1을 출력하고 코드 종료.
		for(int i = 1;i<inum;i++) {
			if (ip[i] != ip[i+1]) {
				System.out.println("-1");
				return;
			}
		}
		kruskal(inum);
		System.out.println(ans);
		
	}

}
