package bj0803;
import java.io.*;
import java.util.*;

public class Main_bj_17281_야구공{

	// N: 이닝 수, maxScore: 최대 득점, outCnt: 아웃횟수
	static int N, maxScore = 0, outCnt = 0; 
	static int[][] balls; // 이닝마다 타자 결과
	static int[] home, orderP; // home: 루 진출 orderP: 타자 순서
	static boolean[] v; // 선수 순서 지정 여부
	
	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		balls = new int[N + 1][10];
		home = new int[5];
		v = new boolean[10];
		orderP = new int[10];
		
		for(int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 1; j <= 9; j++) {
				balls[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		// 1번 선수를 4번 타자로 지정
		v[1] = true;
		orderP[4] = 1;
		
		playerCase(1);
		
		System.out.println(maxScore);
		
	}
	
	// 타자의 순서가 정해질 때 까지 재귀
	static void playerCase(int cnt) {
		if(cnt == 10) { // 순서가 정해지면 score 계산
			calcScore();
			maxScore = Math.max(maxScore, home[4]);
			home[4] = 0;
			
			return;
		}
		
		if(cnt != 4) {
			for(int i = 1; i<= 9; i++) {
				if(v[i]) continue; // 선수 순서 지정 여부
				v[i] = true;
				orderP[cnt] = i; // 순서 저장 - cnt번째 타수로 i번째 선수 지정
				playerCase(cnt + 1);
				v[i] = false;
			}
		} else {
			playerCase(cnt + 1);	// 4번 타자의 경우 이미 정해졌으므로 제외
		}
		
	}
	
	
	static void calcScore() {
		
		int inning = 1;
		int num = 1;
		
		while(inning <= N){ // 이닝 수
			for(int i = 1; i<4; i++) { // 이닝 전 초기화
				home[i] = 0;
			}
			while(outCnt < 3) { // 3 아웃 까지
				run(balls[inning][orderP[num]]);
				num++;
				if(num == 10) { // 9번 타자까지 끝나면 1부터 다시 시작
					num = 1;
				}
			}
			inning++;
			outCnt = 0;
		}
	}
	
	// 결과에 따른 루의 결과
	static void run(int ball) {
		switch(ball) {
		case 1:
			if(home[3] > 0) home[4]++;
			for(int i = 3; i >= 1; i--) {
				home[i] = home[i - 1];
				home[i - 1] = 0;
			}
			home[1] = 1; 
			
			break;
		case 2:
			for(int i = 3; i >= 2; i--) {
				if(home[i] > 0) {
					home[i] = 0;
					home[4]++;
				}
			}
			home[3] = home[1];
			home[1] = 0;
			home[2] = 1;
			break;
		case 3:
			for(int i = 3; i >= 1; i--) {
				if(home[i] > 0) {
					home[i] = 0;
					home[4]++;
				}
			}
			home[3] = 1;
			break;
		case 4:
			for(int i = 3; i >= 1; i--) {
				if(home[i] > 0) {
					home[4]++;
					home[i] = 0;
				}
			}
			home[4]++;
			break;
		case 0:
			outCnt++;
			break;
		}
	}
}