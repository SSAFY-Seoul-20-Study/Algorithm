import java.io.*;
import java.util.*;

// 상어 중학교

public class BJ21609 {

	static class Group implements Comparable<Group> {
		int id;
		int x;
		int y;
		int cnt;
		int cntRB;
		
		Group(int id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}
		
		@Override
		public int compareTo(Group o) {
			if(this.cnt == o.cnt) {
				if(this.cntRB == o.cntRB) {
					if(this.x == o.x) return o.y - this.y;
					return o.x - this.x;
				}
				return o.cntRB - this.cntRB;
			}
			return o.cnt - this.cnt;
		}
	}
	static int n, m;
	static int ans;
	static PriorityQueue<Group> groups = new PriorityQueue<>();
	static ArrayDeque<int[]> queue = new ArrayDeque<>();
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		int[][] map = new int[n][n];
		for(int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		solution(map);
		System.out.println(ans);
	}
	
	private static void solution(int[][] map) {
		
		while(true) {
			if(makeGroups(map) == 0) break;
			deleteBlocks(map);
			doGravity(map);
			rotate(map);
			doGravity(map);
		}
	}
	
	private static int makeGroups(int[][] map) {
		
		groups.clear();
		boolean[][] visited = new boolean[n][n];		// makeGroups() 내 방문처리 배열 (무지개 블록 미포함)
		
		int idCnt = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(!visited[i][j] && 1 <= map[i][j] && map[i][j] <= m) bfs(++idCnt, i, j, map, visited);
			}
		}
		return groups.size();
	}
	
	private static void bfs(int id, int x, int y, int[][] map, boolean[][] visited) {
		
		boolean[][] visited2 = new boolean[n][n];		// bfs() 내 방문처리 배열 (무지개 블록 포함)
		Group newGroup = new Group(id, x, y);
		int cnt = 1;
		int cntRB = 0;
		visited[x][y] = true;
		visited2[x][y] = true;
		queue.add(new int[] {x, y});
		
		while(!queue.isEmpty()) {
			int[] xy = queue.poll();
			
			for(int d = 0; d < 4; d++) {
				int xx = xy[0] + dx[d];
				int yy = xy[1] + dy[d];
				if(xx < 0 || xx >= n || yy < 0 || yy >= n || visited2[xx][yy]) continue;
				
				if(map[xx][yy] == map[x][y] || map[xx][yy] == 0) {
					
					visited2[xx][yy] = true;									// 일반, 무지개 블록 모두 방문처리
					cnt++;
					queue.add(new int[] {xx, yy});
				
					if(map[xx][yy] == map[x][y]) visited[xx][yy] = true;		// 일반 블록만 방문처리
					else if(map[xx][yy] == 0) cntRB++;
				}
			}
		}
		
		if(cnt >= 2) {
			newGroup.cnt = cnt;
			newGroup.cntRB = cntRB;
			groups.add(newGroup);
		}
	}
	
	private static void deleteBlocks(int[][] map) {		// bfs 탐색을 통해 블록 제거하기
		
		Group group = groups.poll();
		ans += Math.pow(group.cnt, 2);
		int num = map[group.x][group.y];				// 타겟 번호
		
		map[group.x][group.y] = 9;						// 제거한 블록은 9로 표시
		queue.add(new int[] {group.x, group.y});
		
		while(!queue.isEmpty()) {
			int[] xy = queue.poll();
			
			for(int d = 0; d < 4; d++) {
				int xx = xy[0] + dx[d];
				int yy = xy[1] + dy[d];
				if(xx < 0 || xx >= n || yy < 0 || yy >= n) continue;
				
				if(map[xx][yy] == num || map[xx][yy] == 0) {		// 타겟 번호이거나 무지개 블록인 경우
					map[xx][yy] = 9;
					queue.add(new int[] {xx, yy});
				}
			}
		}
	}
	
	private static void doGravity(int[][] map) {
		
		for(int j = 0; j < n; j++) {
			for(int i = n - 1; i > 0; i--) {
				if(map[i][j] == 9 && map[i - 1][j] != -1) {			// 아래가 빈 칸, 위가 검은색 블록이 아닌 경우라면
					
					int r = i;
					int c = j;
					while(true) {									// 계속해서 아래, 위를 교환하기
						int tmp = map[r][c];
						map[r][c] = map[r - 1][c];
						map[r - 1][c] = tmp;
						r++;
						if(r == n || map[r][c] != 9) break;			// 마지막 행이거나 빈 칸이 아니라면 교환 종료
					}
				}
			}
		}
	}
	
	private static void rotate(int[][] map) {
		
		int[][] tmp = new int[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				tmp[j][i] = map[i][n - j - 1];
			}
		}
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				map[i][j] = tmp[i][j];
			}
		}
	}
	
}
