package a0811.study;

import java.util.*;
import java.io.*;

//크루스칼 알고리즘: 그래프 내의 모든 정점들을 가장 적은 비용으로 연결
// 가중치를 오름차순으로 정렬 -> 사이클을 형성하지 않는 선에서 정렬된 순서대로 간선 선택
class Bridge implements Comparable<Bridge>{
	int start;
	int end;
	int len;
	
	public Bridge(int start, int end, int len) {
		this.start = start;
		this.end = end;
		this.len = len;
	}
	// 가중치를 오름차순 정렬
	public int compareTo(Bridge b) {
		return Integer.compare(this.len, b.len);
	}
	
}

public class Main_bj_17472_다리만들기2 {

	static int N, M, cntBridge = 0, cntIsle = 0;
	static int[][] map;
	static boolean[][] v;
	static int[] connect;
	static PriorityQueue<Bridge> pq= new PriorityQueue<>(); 
	static final int[] dx = {1, 0, -1, 0};
	static final int[] dy = {0, 1, 0, -1};
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		
		v = new boolean[N][M];
		// 섬 구분
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(map[i][j] == 1 && !v[i][j]) {
					cntIsle++;
					countLoad(i, j);
				}
			}
		}
		// 다리만들기
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(map[i][j] != 0) {
					makeBridge(i, j); // 각 좌표에서 만들 수 있는 다리를 모두 제작
				}
			}
		}

		// 크루스칼 알고리즘
		connect = new int[cntIsle + 1]; // 각 정점의 앞서 건너온 섬 표현
		
		for(int i = 0; i < connect.length; i++) {
			connect[i] = i;
		}
		
		int result = 0;
		int size  = pq.size();
		// 다리길이가 짧은 순서대로 연결 
		for(int i = 0; i < size; i++) {
			Bridge b = pq.poll();
			
			int s = find(b.start);
			int e = find(b.end);
			
			if(s == e) continue; // 이미 연결되었으면 continue;
			union(b.start, b.end);
			result += b.len;
			cntBridge++;
			
		}
		
		if(result == 0 || cntBridge != cntIsle - 1) {
			System.out.println(-1);
		} else {
			System.out.println(result);
		}
		
	}
	
	// 사이클 판단 함수
	// 값을 찾으면 return
	static int find(int a) {
		if(a==connect[a]) return a; // root node가 자기 자신인 경우
		connect[a] = find(connect[a]); // root node의 root node 찾기
		// 가장 위의 root node를 연결함 
		return connect[a];
	}
	
	// 섬을 연결하기 위해 root node를 찾음
	// root node 가 같은 경우,cycle이 형성되기 때문
	static void union(int start, int end) {
		int aroot = find(start); // 연결할 두 섬의 root Node 
		int broot = find(end); 
		
		if(aroot != broot) { // root Node 가 다른 경
			connect[aroot] = end; //길이가 작은 섬이 root node;
		} else return; //root Node가 같으면 리턴
	}
	
	// 각 좌표에서 만들 수 있는 최대의 다리 만들기
	static void makeBridge(int x, int y) {
		int nx = x;
		int ny = y;
		
		int len = 0;

		// 다 섬이 나올 때 까지 반복
		for(int i = 0; i < 4; i++) {
			while(true) {
				nx += dx[i];
				ny += dy[i];
				
				if(isRange(nx,ny)) {
					if(map[nx][ny] == map[x][y]) {
						// 동일한 섬이므로 초기화
						len = 0;
						nx = x;
						ny = y;
						break;
					}
					// 바다는 다리 설치
					else if(map[nx][ny] == 0) {
						len++;
					} else {
						// 다리설치가 가능하면 pq에 추가 후 break;
						if(len > 1) {
							pq.offer(new Bridge(map[x][y], map[nx][ny], len));
						}
						len = 0;
						nx = x;
						ny = y;
						break;
					}
				} else {
					len = 0;
					nx = x;
					ny = y;
					break;
				}
			}
		}
		
	}
	
	// 섬을 구분하기 위해 numbering
	static void countLoad(int x, int y) {
		ArrayDeque<int []> q = new ArrayDeque<>();
		
		q.offer(new int[] {x, y});
		v[x][y] = true;
		map[x][y] = cntIsle;
		
		while(!q.isEmpty()) {
			int px = q.peek()[0];
			int py = q.poll()[1];
			
			for(int i = 0; i < 4; i++) {
				int nx = px + dx[i];
				int ny = py + dy[i];
				
				if(isRange(nx, ny) && map[nx][ny] == 1 && !v[nx][ny]) {
					v[nx][ny] = true;
					map[nx][ny] = cntIsle;
					q.offer(new int[] {nx, ny});
				}
			}
		}
		
	}
	
	static boolean isRange(int x, int y) {
		if(x < 0 || x >= N || y < 0 || y >= M) return false;
		return true;
	}
}
