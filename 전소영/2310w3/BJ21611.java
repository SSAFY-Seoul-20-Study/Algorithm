import java.io.*;
import java.util.*;

// 마법사 상어와 블리자드

public class BJ21611 {

	static int n, m;
	static int sx, sy;
	static int[] count = new int[4];
	static int[][] map;
	static ArrayDeque<Integer> queue = new ArrayDeque<>();
	static int[] dx = {0, -1, 1, 0, 0};
	static int[] dy = {0, 0, 0, -1, 1};
	static int[] dx2 = {1, 0, -1, 0};
	static int[] dy2 = {0, 1, 0, -1};
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		sx = n / 2;
		sy = n / 2;
		map = new int[n][n];
		
		for(int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			solution(d, s);
		}
		
		int ans = 0;
		for(int i = 1; i <= 3; i++) {
			ans += count[i] * i;
		}
		System.out.println(ans);
	}
	
	private static void solution(int d, int s) {
		
		magic(d, s);
		move();
		bomb();
		insert();
	}
	
	private static void magic(int d, int s) {
		
		int x = sx;
		int y = sy;
		while(s-- > 0) {
			x += dx[d];
			y += dy[d];
			map[x][y] = 0;
		}
	}
	
	private static void move() {		// queue에 빈 칸 제외 구슬 넣기
		
		queue.clear();
		int x = sx;
		int y = sy - 1;
		if(map[x][y] != 0) {
			queue.add(map[x][y]);
			map[x][y] = 0;
		}
		x += dx2[0];
		y += dy2[0];
		
		int d = 1;
		int time = 2;
		boolean flag = false;
		while(true) {
			if(x == 0 && y == -1) break;
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < time; j++) {
					if(map[x][y] != 0) {
						queue.add(map[x][y]);
						map[x][y] = 0;
					}
					x += dx2[d];
					y += dy2[d];
					if(x == 0 && y == -1) {
						flag = true;
						break;
					}
				}
				if(flag) break;
				d = (++d) % 4;
			}
			if(flag) break;
			time++;
		}
	}
	
	private static void bomb() {
		
		boolean flag = false;
		while(true) {
			flag = false;
			
			int size = queue.size();
			if(size == 0) break;
			int pre = queue.poll();
			size--;
			
			int cnt = 1;
			for(int i = 0; i < size; i++) {
				
				int poll = queue.poll();
				if(poll == pre) {
					cnt++;
					if(i == size - 1 && cnt >= 4) count[pre] += cnt;
				}
				else {
					if(cnt < 4) {
						for(int j = 0; j < cnt; j++) queue.add(pre);
					}
					else {
						count[pre] += cnt;
						flag = true;	
					}
					pre = poll;
					cnt = 1;
				}
				
				if(i == size - 1) {
					for(int j = 0; j < cnt; j++) queue.add(pre);
				}
			}
			
			if(!flag) break;
		}
	}
	
	private static void insert() {
		
		ArrayDeque<Integer> tmp = new ArrayDeque<>();
		if(queue.isEmpty()) return;
		int pre = queue.poll();
		int cnt = 1;
		while(true) {
			
			int size = queue.size();
			if(size == 0) {
				tmp.add(cnt);
				tmp.add(pre);
				break;
			}
			int poll = queue.poll();
			size--;
			if(poll == pre) cnt++;
			else {
				tmp.add(cnt);
				tmp.add(pre);
				pre = poll;
				cnt = 1;
			}
		}
		
		// tmp에서 꺼내서 배열에 넣기
		int size = tmp.size();
		
		while(true) {
			
			int x = sx;
			int y = sy - 1;
			map[x][y] = tmp.poll();
			if(--size == 0) return;
			x += dx2[0];
			y += dy2[0];
			
			int d = 1;
			int time = 2;
			while(true) {
				if(x == 0 && y == -1) break;
				for(int i = 0; i < 2; i++) {
					for(int j = 0; j < time; j++) {
						map[x][y] = tmp.poll();
						if(--size == 0) return;
						x += dx2[d];
						y += dy2[d];
						if(x == 0 && y == -1) return;
					}
					d = (++d) % 4;
				}
				time++;
			}
		}
	}
}
