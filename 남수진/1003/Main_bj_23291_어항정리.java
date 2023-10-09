package a1003;

import java.util.*;
import java.io.*;

public class Main_bj_23291_어항정리 {
	
	// h 높이 start 물고기가 처음 있는 위
	static int N, K, h, start;
	static int[][] fish;
	static int[] dx = {1, 0};
	static int[] dy = {0, 1};
	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		fish = new int[N][N];
		
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			fish[0][i] = Integer.parseInt(st.nextToken());
		}
		
		int num = 0;
		while(true) {
			num++;
			findMin();
			rotate();
			diff();
			
			line();
			rotate2();
			diff();
			line();

			if(isFinish()) break;
		}
		
		System.out.println(num);
	}
	
	// 가장 적은 어항에 물고기 넣기
	static void findMin() {	
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < N; i++) {
			if(min > fish[0][i]) {
				min = fish[0][i];
			}
		}
		for(int i = 0; i < N; i++) {
			if(fish[0][i] == min) {
				fish[0][i] += 1;
			}
		}
	}
	
	// 첫번째 공중부양과 회전
	static void rotate() {
		
		// 첫번째 어항을 두번째 어항 위로 쌓음
		fish[1][1] = fish[0][0];
		fish[0][0] = 0;
		start = 1;
		h = 2;
		
		// 공중부양이 가능할 때 까지 반복 
		while(true) {
			ArrayList<Integer> list = new ArrayList<>();
			int num = 0;
			
			for(int k = start; k < N; k++) {
				// 아직 안 쌓았으면 break
				if(fish[1][k] == 0) {
					num = k;
					break;
				}
				// 이전에 쌓았던 어항들을 리스트에 담음 
				for(int i = 0; i < h; i++) {
					list.add(fish[i][k]);
					fish[i][k] = 0;
				}
			}
			
			// 90도 회전한 방향대로
			// 이전에 쌓인 어항 높이 만큼 옆으로 쌓음
			for(int i = num - start; i >= 1; i--) {
				for(int j = num; j <= num + h - 1; j++) {
					fish[i][j] = list.remove(0);
				}
			}
			
			h = num - start + 1; //높이 재계산

			// 더이상 공중부양을 못하면 break
			if(h > N - (2 * num - start)) {
				start = num;
				break;
			}
			
			// 시작점 재계산
			start = num;
		}
	}
	
	// 인접한 두 어항에 대하여 물고기 수 차이 계산
	static void diff() {
		
		int[][] copy = new int[N][N];
		for(int i = 0; i < h; i++) {
			for(int j = start; j < N; j++) {
				copy[i][j] = fish[i][j];
			}
		}
		
		for(int i = 0; i < h; i++) {
			for(int j = start; j < N; j++) {
				if(fish[i][j] == 0) continue;
				
				for(int d = 0; d < 2; d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];

					if(nx < 0 || nx >= h || ny < start || ny >= N) continue;
					if(fish[nx][ny] == 0) continue;
					int res = Math.abs(fish[nx][ny] - fish[i][j]) / 5;
					if(res > 0) {
						if(fish[nx][ny] > fish[i][j]) {
							copy[nx][ny] -= res;
							copy[i][j] += res;
						}
						else if(fish[nx][ny] < fish[i][j]){
							copy[nx][ny] += res;
							copy[i][j] -= res;
						}
					}
				}
				
			}
		}
		
		for(int i = 0; i < h; i++) {
			for(int j = start; j < N; j++) {
				fish[i][j] = copy[i][j];
			}
		}
	}
	
	// 다시 일렬로 놓기
	static void line() {
		
		ArrayList<Integer> list = new ArrayList<>();
		
		for(int i = start; i < N; i++) {
			for(int j = 0; j < h; j++) {
				if(fish[j][i] == 0) continue;
				list.add(fish[j][i]);
				fish[j][i] = 0;
			}
		}
		for(int i = 0; i < N; i++) {
			fish[0][i] = list.remove(0);
		}
		h = 1;
		start = 0;
	}
	
	// 공중부양2 
	static void rotate2() {
		
		// 가운데 중심 공중 부양
		for(int i = 0; i < N/2; i++) {
			fish[1][N - i - 1] = fish[0][i];
			fish[0][i] = 0;
		}
		start = N / 2;
		h = 2;
		
		// 다시 가운데를 중심으로 공중 부양
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < N / 4; j++) {
				fish[3 - i][N - j - 1] = fish[i][j + N / 2];
				fish[i][j + N / 2] = 0;
			}
		}
		
		h = 4;
		start = 3 * N / 4;
	}
	
	// 최댓값과 최솟값 차이
	static boolean isFinish() {
		int max = 0;
		int min = 10_000;
		for(int i = 0; i < N; i++) {
			max = Integer.max(max, fish[0][i]);
			min = Integer.min(min, fish[0][i]);
		}
		
		
		if(max - min <= K) return true;
		else return false;
	}
}
