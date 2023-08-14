package study;

import java.io.*;
import java.util.*;

/*
	 1. BFS로 각 섬의 번호를 매긴다
	 2. 섬일 경우에 BFS를 통하여 다른섬을 만나면 간선 리스트에 (대신 방향은 일정해야한다) 저장한다. 그 외의 경우에는 무시해도 상관없을듯? 단 count는 2 이상
	 3. 간선을 저장하면 크루스칼 알고리즘으로 사이클 여부 체킹 한다.
 */
public class 다리만들기2 {
	static int N, M;
	static int [][] board;
	static int [] dx = {-1,1,0,0}, dy = {0,0,-1,1};
	static ArrayList<int []> line = new ArrayList<>();
	static boolean [][] visited, check;
	static int [] parents;
	static int cost= 0;
	public static void main(String args[]) throws Exception
	{
		System.setIn(new FileInputStream("study/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		board = new int[N][M];
		check = new boolean[N][M];
		visited = new boolean[N][M];
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int flag = 1;
		for(int i =0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(board[i][j] != 0 && !visited[i][j]) {
					bfs(i,j,flag++);
				}
			}
		}
		parents = new int[flag];
		for(int i =1; i<flag; i++) {
			parents[i] = i;
		}
		visited = new boolean[N][M];
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(board[i][j] != 0 && !visited[i][j]) {
					makeLine(i,j,board[i][j]);
				}
			}
		}
		line.sort((o1, o2) -> Integer.compare(o1[2], o2[2]));
		int lineCount = 0;
		//크루스칼 진행
		for(int i=0; i< line.size(); i++) {
			if(find(line.get(i)[0]) != find(line.get(i)[1])) {
				union(line.get(i)[0], line.get(i)[1]);
				cost += line.get(i)[2];
				lineCount++;
				continue;
			}
		}
		if(lineCount +2 != flag) cost = -1;
		System.out.println(cost);
		br.close();
	}
	static void union(int a, int b) {
		a = find(a);
		b = find(b);
		if(a>b) {
			parents[a] = b;
		}else {
			parents[b] = a;
		}
	}
	static int find(int x) {
		if(parents[x] == x) {
			return x;
		}else {
			return find(parents[x]);
		}
	}
	static void makeLine(int x, int y, int island) {
		visited = new boolean[N][M];
		ArrayDeque<int []> q= new ArrayDeque<>();
		visited[x][y] = true;
        //초기 4방향으로 이동해보기
		q.offer(new int[] {x,y,0,0});
		q.offer(new int[] {x,y,1,0});
		q.offer(new int[] {x,y,2,0});
		q.offer(new int[] {x,y,3,0});
		while(!q.isEmpty()) {
			int [] cur = q.poll();
			x = cur[0]; y = cur[1];
			int count = cur[3], d = cur[2];
			int nx = x + dx[d];
			int ny = y + dy[d];
			if(0 <= nx && nx < N && 0<=ny && ny <M) {
				if(!visited[nx][ny] && board[nx][ny] == 0) {
					q.offer(new int[] {nx,ny, d, count+1});
					visited[nx][ny] = true;
				}
				if(!visited[nx][ny] && board[nx][ny] != 0 && board[nx][ny] != island && count >= 2) {
					line.add(new int [] {island, board[nx][ny], count});
				}
			}
		}
	}
	static void bfs(int x, int y, int flag) {
		ArrayDeque<int []> q = new ArrayDeque<>();
		q.offer(new int[] {x,y});
		visited[x][y] = true;
		board[x][y] = flag;
		while(!q.isEmpty()) {
			int [] cur = q.poll();
			x = cur[0]; y = cur[1];
			for(int d=0; d<4; d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				if(0 <= nx && nx < N && 0<=ny && ny <M && !visited[nx][ny] && board[nx][ny] != 0) {
					q.offer(new int[] {nx,ny});
					board[nx][ny] = flag;
					visited[nx][ny] = true;
				}
			}
		}
		
	}
}
