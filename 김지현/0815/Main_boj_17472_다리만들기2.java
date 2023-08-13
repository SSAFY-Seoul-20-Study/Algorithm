package study_algo;

import java.util.*;
import java.io.*;

public class Main_boj_17472_다리만들기2 {
	
	static int N, M, res=Integer.MAX_VALUE;
	static int[][] map,copy_map;
	static boolean[][] v;
	static final int[] dx = {-1, 0, 1, 0};
	static final int[] dy = {0, -1, 0, 1};
	static List<int[]> bridges = new ArrayList<int[]>();
	static int[] sel;
	
	
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_boj_17472.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		copy_map = new int[N][M];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				int temp = Integer.parseInt(st.nextToken());
				map[i][j] = temp;
			}
		}
		
		// 놓을 수 있는 모든 다리 놓기(다리 2이상)
		FindRowRangeBridge();
		FindColRangeBridge();
		copy_map = copyArr(map);
		for(int[] s: copy_map) System.out.println(Arrays.toString(s));
		
		
		// 하나씩 빼보기(섬에 1개 최소 1개 이상 다리는 있어야함.)
			// 빼면서, 모두 연결되어 있는지 확인. (dfs로 덩어리 개수->1개 여야함)
//		for(int[] b:bridges) System.out.println(Arrays.toString(b));
		
		// 전체 다리 연결 했을 때도, 연결되지 않는 경우 체크.
		if(!isAllConnected()) {
			System.out.println(-1);
			System.exit(0);
		}
		// 전체 연결된 다리 개수 저장
		res = Math.min(res, CountBridge());
		
		// 빼는 개수(1개- ~ 다리 개수(전체 다리 빼)
		for(int sub_cnt=1; sub_cnt<bridges.size(); sub_cnt++) {
			sel = new int[sub_cnt];
			map = copyArr(copy_map);
			comb(sub_cnt, 0, 0);
		}
		
		
		// 연결되어 있으면 최소 다리 개수 갱신.
		if(isAllConnected()) {
			res = Math.min(res, CountBridge());
		}
		System.out.println(res);
		br.close();
	}
	
	private static void removeBridge() {
		for(int i=0; i<sel.length; i++) {
			int[] tmp = bridges.get(sel[i]);
			if(tmp[0] == 0) { // 행인 경우
				for(int y=tmp[2]; y<tmp[3]; y++) {
					map[tmp[1]][y] = 0;
				}
			}else if(tmp[0] == 1) { // 열인 경우
				for(int x=tmp[2]; x<tmp[3]; x++) {
					map[x][tmp[1]] = 7; // 다리 7로 설정.
				}
			}
		}
	}
	
	private static void comb(int length, int cnt, int start) {
		if(cnt == length) {
			removeBridge();
			// 연결되어 있으면 최소 다리 개수 갱신.
			if(isAllConnected()) {
				res = Math.min(res, CountBridge());
//				System.out.println(length+" "+Arrays.toString(sel)+res);
			}
			return;
		}
		for(int i=start; i<bridges.size(); i++) {
			sel[cnt] = i;
			comb(length, cnt+1, i+1);
		}
	}
	
	// 모든 섬이 연결되어 있는 지 확인.
	private static boolean isAllConnected() {
		int cntGroup=0;
		v = new boolean[N][M];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(map[i][j]==1 && !v[i][j]) {
					bfs(i, j);
					cntGroup++;
				}
			}
		}
		if(cntGroup==1) return true; // 덩어리가 1개면 모두 연결
		return false;
	}
	
	private static void bfs(int i, int j) {
		Queue<int[]> q = new ArrayDeque<>();
		q.offer(new int[] {i,j});
		v[i][j] = true;
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			for(int d=0; d<4; d++) {
				int nx = cur[0] + dx[d];
				int ny = cur[1] + dy[d];
				if(0<=nx && nx<N && 0<=ny && ny<M && !v[nx][ny]) {
					q.offer(new int[] {nx,ny});
					v[nx][ny] = true;
				}
			}
		}
	}

	private static int CountBridge() {
		int cnt = 0;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(map[i][j] == 7) cnt++;
			}
		}
		return cnt;
	}
	
	// 행에 다리 놓기(10 -> 01 으로 변하는 지점)
	private static void FindRowRangeBridge() {
		for(int i=0; i<N; i++) {
			int left = -1; // 섬을 계산하기 위한 열의 인덱스
			for(int j=0; j<M-1; j++) {
				if(map[i][j]==1 && map[i][j+1]==0) {
					left = j+1;
				}else if(map[i][j] == 0 && map[i][j+1]==1) {
					if(left != -1 && (j-left)>=1) {
						SetRowBridgeInRange(i, left, j+1);
						left = -1;
					}
				}
				
			}
		}
	}
	// 열에 다리 놓기
	private static void FindColRangeBridge() {
		for(int j=0; j<M; j++) {
			int up = -1; // 섬을 계산하기 위한 행의 인덱스
			for(int i=0; i<N-1; i++) {
				if(map[i][j]==1 && map[i+1][j]==0) {
					up = i+1;
				}else if(map[i][j] == 0 && map[i+1][j]==1) {
					if(up != -1 && (i-up)>=1) {
						SetColBridgeInRange(j, up, i+1);
						up = -1;
					}
				}	
			}
		}
	}
	
	private static void SetRowBridgeInRange(int x, int left, int right) {
		for(int j=left; j<right; j++) {
			map[x][j] = 7; // 다리 7로 설정.
		}
		bridges.add(new int[] {0, x, left, right}); // bridges 리스트에 저장
	}
	private static void SetColBridgeInRange(int y, int up, int down) {
		for(int i=up; i<down; i++) {
			map[i][y] = 7; // 다리 7로 설정.
		}
		bridges.add(new int[] {1, y, up, down}); // bridges 리스트에 저장
	}
	
	private static int[][] copyArr(int[][] maps) {
		int[][] temp = new int[N][M];
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				temp[i][j] = maps[i][j];
			}
		}
		return temp;
	}

}
