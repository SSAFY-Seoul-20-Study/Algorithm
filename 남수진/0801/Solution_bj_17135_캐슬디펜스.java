package a0801;

import java.util.*;
import java.io.*;

public class Solution_bj_17135_캐슬디펜스 {

	static int N, M, D;
	static int[][] board;
	static int[][] temp;
	static int[] archer;
	static int answer = 0;
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		temp = new int[N][M];
		archer = new int[3];
		
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<M; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 3명의 궁수 조합 찾기
		searchArchor(0, 0);
		
		System.out.println(answer);
	
	}
	
	// 3명의 궁수 조합 찾기
	public static void searchArchor(int r, int cnt) {
		if(cnt == 3) {
			answer = Math.max(answer, attack()); // 조합이 결정되면 공격
			return;
		}
		
		for(int i = r; i < M; i++) {
			archer[cnt] = i; // 궁수의 열 위치 저장
			searchArchor(i + 1, cnt + 1);
		}
	}
	
	public static int attack() {
		for(int i = 0; i<N; i++) { // copy map
			temp[i] = Arrays.copyOf(board[i], M);
		}
		
		Queue<int []> q = new LinkedList<>();
		
		int turn = 0; // 적이 아래로 이동한 거리 -> 1턴이 지난 후 1
		int kill = 0;
		
		
		
		while(turn < N) { 
			for(int k = 0; k < 3; k++) {
				int x = N - turn ; // 궁수의 행위치 - 적이 아래로 한칸 이동 -> 궁수가 뒤로 한 칸 이동
				int y = archer[k]; // 궁수의 열위치 

				int min = Integer.MAX_VALUE;
				int minX = -1;
				int minY = -1;
				
				for(int i = N - 1 - turn; i>=0; i--) { 
					for(int j = 0; j<M; j++) {
						if(temp[i][j] == 1) { // 적의 위치인 경우
							int dist = Math.abs(i - x) + Math.abs(j - y); // 적과 궁수와의 거리
							
							// 영역을 벗어난 경우
							if(dist > D) continue;
							
							// 가장 가깝고, 가장 왼쪽에 있는 적 공격해야 함
							if(dist < min) { // 거리가 더 가까운 경우
								min = dist;
								minX = i;
								minY = j;
							} else if (dist == min) { // 가장 왼쪽에 있는 적 공
								if( j < minY) {
									minX = i;
									minY = j;
								}
							}
						}
					}
					
				}
				if(minX == -1 || minY == -1) continue; // 적을 못 찾았을 경우
				
				q.offer(new int[]{minX, minY}); // 공격할 적을 queue에 담음
			}
			while(!q.isEmpty()) { // poll하면서 적 공
				int x = q.peek()[0];
				int y = q.poll()[1];
				
				if(temp[x][y] == 1) {
					temp[x][y] = 0;
					kill++;
				}
			}
			turn++;
		}
		return kill;
	}

}
