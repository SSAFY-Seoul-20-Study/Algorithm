package study_algo;

import java.util.*;
import java.io.*;

// 14344KB	124ms
public class Main_boj_17472_다리만들기2 {
	
	static int N, M;
	static int[][] map;
	static boolean[][] v;
	static final int[] dx = {-1, 0, 1, 0};
	static final int[] dy = {0, -1, 0, 1};
	static ArrayList<Node>[] list;
	static PriorityQueue<Mst_Node> pq;
	static int[] parent;
	
	
	public static void main(String[] args) throws Exception {
//		System.setIn(new FileInputStream("res/input_boj_17472.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 입력받기 
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 섬끼리 다른 숫자 저장하기. -> bfs
		list = new ArrayList[7];
		v = new boolean[N][M];
		int num = 1;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(!v[i][j] && map[i][j] == 1) {
					list[num] = new ArrayList<>();
					bfs(i,j, num++);
				}
			}
		}
		
		
		//각 섬에 연결할 수 있는 다리 모두 구해서 pq에 저장.
		pq = new PriorityQueue<>();
		for(int i=1; i<num; i++) { // num : 섬의 개수+1
			for(int j=0; j<list[i].size(); j++) { // 한 섬의 개수(크기)만큼
				Node n = list[i].get(j);
				for(int k=0; k<4; k++) {
					find_bridge(n.x, n.y, i, k, -1);
				}
			}
		}
		
		System.out.println(kruskal(num));
		br.close();
	}
	
	private static int kruskal(int num) {
		parent = new int[num];
		for(int i=1; i<num; i++) {
			parent[i] = i; // 자기 자신을 부모 배열에 초기화.
		}
		boolean[] link = new boolean[num];
		int res = 0;
		int bridge = 0;
		while(!pq. isEmpty()) {
			Mst_Node cur = pq.poll();
			
			int p1 = find(cur.n1);
			int p2 = find(cur.n2);
			
			if(p1 != p2) {
				union(p1, p2);
				link[cur.n1] = true;
				link[cur.n2] = true;
				res += cur.length;
				bridge++;
			}
		}
		if(res == 0) return -1;
		if(bridge != num -2) return -1;
		return res;
	}
	
	private static void union(int a, int b) {
		parent[a] = b;
	}
	
	private static int find(int x) {
		if(parent[x] == x) return x;
		return find(parent[x]);
	}
	
	private static void find_bridge(int x, int y, int num, int dir, int len) {
		if(map[x][y] != 0 && map[x][y] != num) { // 다른 섬일 때
			if(len >= 2) pq.offer(new Mst_Node(num, map[x][y], len)); // 다리 길이가 2이상일 때만 
			return;
		}
		
		int nx = x + dx[dir];
		int ny = y + dy[dir];
		if(0 <= nx && nx < N && 0 <= ny && ny < M && map[nx][ny] != num) {
			find_bridge(nx, ny, num, dir, len+1);
		}
	}

	private static void bfs(int i, int j, int num) {
		Queue<Node> q = new ArrayDeque<>();
		q.offer(new Node(i,j));
		v[i][j] = true;
		
		while(!q.isEmpty()) {
			Node cur = q.poll();
			
			map[cur.x][cur.y] = num;
			list[num].add(new Node(cur.x, cur.y));
			
			for(int d=0; d<4; d++) {
				int nx = cur.x + dx[d];
				int ny = cur.y + dy[d];
				if(0<=nx && nx<N && 0<=ny && ny<M && !v[nx][ny] && map[nx][ny]==1) {
					q.offer(new Node(nx,ny));
					v[nx][ny] = true;
				}
			}
		}
	}
	
	// Mst_Node class 정의
	private static class Mst_Node implements Comparable<Mst_Node> {
		int n1, n2, length;
		
		public Mst_Node(int n1, int n2, int length) {
			this.n1 = n1;
			this.n2 = n2;
			this.length = length;
		}

		@Override
		public int compareTo(Mst_Node o) {
			return this.length - o.length; // 길이로 비교
		}
	}
	
	private static class Node{
		int x, y;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}
