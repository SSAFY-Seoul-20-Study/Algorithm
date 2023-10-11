package a1011;

import java.io.*;
import java.util.*;

// 	29588KB	232ms
public class Main_boj_21609_상어중학교 {
	
	static int N, M, result;
	static int[][] map;
	static final int[] dx = {-1, 0, 1, 0};
	static final int[] dy = {0, -1, 0, 1};
	static List<int[]> group; // 블록그룹
	static boolean[][] v;
	
	public static void main(String[] args) throws Exception {
//		System.setIn(new FileInputStream("res/input_boj_21609.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		// while (블록그룹 있을때까지)
		while(true) {
			// 블록 그룹 찾기
			group = new ArrayList<int[]>();
			v = new boolean[N][N];
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					if(map[i][j] > 0 && !v[i][j]) find_block(i, j);
				}
			}
			
			if(group.size() < 2) break; // 종료 조건
			
			// 크기가 가장 큰 블록 그룹 찾기
//			for(int[] g : group) System.out.println(Arrays.toString(g));
			
			// 블록 제거 -> 점수 획득
//			for(int[] g: group) System.out.println("그룹 : " + Arrays.toString(g));
			result += (group.size()*group.size());
//			System.out.println("result : "+result);
			
			remove_block();
//			System.out.println("블록 제거 후");
//			for(int[] m: map)System.out.println(Arrays.toString(m)); System.out.println();
			
			// 중력 작용
			gravity();
//			System.out.println("중력 작용 후_1");
//			for(int[] m: map)System.out.println(Arrays.toString(m)); System.out.println();
			
			// 90도 반시계 방향 회전
			map = rotation();
//			System.out.println("90도 반시계 회전 후");
//			for(int[] m: map)System.out.println(Arrays.toString(m)); System.out.println();
			
			// 중력 작용
			gravity();
//			System.out.println("중력 작용 후_2");
//			for(int[] m: map)System.out.println(Arrays.toString(m)); System.out.println();
			
		}
		System.out.println(result);
		br.close();
	}
	
	// 90도 반시계 회전
	private static int[][] rotation() {
		int[][] tmp_map = new int[N][N];
		ArrayDeque<Integer> stack;
		for(int i=0; i<N; i++) {
			stack = new ArrayDeque<Integer>();
			for(int j=0; j<N; j++) {
				stack.push(map[i][j]);
			}
			for(int k=0; k<N; k++) {
				tmp_map[k][i] = stack.pop();
			}
		}
		return tmp_map;
	}
	
	// 중력 작용
	private static void gravity() {
		for(int j=0; j<N; j++) {
			Queue<Integer> q = new ArrayDeque<Integer>();
			Queue<Integer> down_q = new ArrayDeque<Integer>();
			for(int i=N-1; i>=0; i--) {
				// 내릴 공간이 있다면
				if(map[i][j] == -2) {
					down_q.offer(i);
				} else if(map[i][j] == -1) {
					if(!down_q.isEmpty() && !q.isEmpty()) {
						// 내리기
						int start = down_q.poll(); // 내릴 가장 아래 행 인덱스
						int idx = 0;
						while(!q.isEmpty()) {
							int cur = q.poll();
							map[start-idx][j] = map[cur][j];
							map[cur][j] = -2;
							idx++;
						}
						down_q.clear();
					} else {
						down_q.clear();
						continue;
					}
				} else {
					if(!down_q.isEmpty()) q.offer(i);
				}
			}
			
			// 내릴 공간이 있고, 큐에 내릴 것이 있다면
			if(!down_q.isEmpty() && !q.isEmpty()) {
				// 내리기
				int start = down_q.poll(); // 내릴 가장 아래 행 인덱스
				int idx = 0;
				while(!q.isEmpty()) {
					int cur = q.poll();
					map[start-idx][j] = map[cur][j];
					map[cur][j] = -2;
					idx++;
				}
				down_q.clear();
			}
		}
	}
	
	// 블록 지우기
	private static void remove_block() {
		for(int[] g : group) {
			map[g[0]][g[1]] = -2; // 임의로 -2로 저장하기.
		}
	}
	
	// 블록 그룹 찾기 + 크기가 큰 블록 갱신
	private static void find_block(int x, int y) {
		Queue<int[]> q = new ArrayDeque<int[]>();
		List<int[]> tmp_group = new ArrayList<int[]>();
		boolean[][] v_rainbow = new boolean[N][N];
		
		int target = map[x][y];
		tmp_group.add(new int[] {x, y});
		
		q.offer(new int[] {x, y});
		v[x][y] = true;
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			for(int d=0; d<4; d++) {
				int nx = cur[0] + dx[d];
				int ny = cur[1] + dy[d];
				if(isRange(nx, ny)) {
					// target 일때
					if(map[nx][ny] == target && !v[nx][ny]) {
						q.offer(new int[] {nx, ny});
						v[nx][ny] = true;
						tmp_group.add(new int[] {nx,ny});
					} else if(map[nx][ny] == 0 && !v_rainbow[nx][ny]) { // 무지개 일때
						q.offer(new int[] {nx, ny});
						v_rainbow[nx][ny] = true;
						tmp_group.add(new int[] {nx, ny});
					}
				}
			}
		}
		// 블록 그룹 비교
		if(tmp_group.size() > group.size()) {
			group = tmp_group;
		} else if (tmp_group.size() == group.size()) {
			// 무지개 블록의 수가 많은 그룹
			int group_rainbow = 0;
			int tmp_group_rainbow = 0;
			for(int[] g :group) {
				if(map[g[0]][g[1]] == 0) group_rainbow++;
			}
			for(int[] g : tmp_group) {
				if(map[g[0]][g[1]] == 0) tmp_group_rainbow++;
			}
			
			if(group_rainbow < tmp_group_rainbow) {
				group = tmp_group;
				
			} else if (group_rainbow == tmp_group_rainbow) {
				// 기준 블록 찾기
				int[] tmp_stand = find_stand(tmp_group);
				int[] stand = find_stand(group);
				if(tmp_stand[0] > stand[0]) group = tmp_group;
				else if(tmp_stand[0] == stand[0]) {
					if(tmp_stand[1] > stand[1]) group = tmp_group;
				}				
			}
		}
	}
	
	// 기준 블록 찾기
	private static int[] find_stand(List<int[]> g) {
		int x=21, y=21; // 임의로 최대값 넣기(N <= 20)
		for(int[] gg : g) {
			if(map[gg[0]][gg[1]] > 0) {
				if(x > gg[0]) {
					x = gg[0]; y = gg[1];
				} else if(x == gg[0]) {
					y = Math.min(y, gg[1]);
				}				
			}
		}
		return new int[] {x, y};
	}
	
	private static boolean isRange(int x, int y) {
		if(0 <= x && x < N && 0 <= y && y < N) return true;
		return false;
	}
}