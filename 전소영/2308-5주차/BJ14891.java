import java.io.*;
import java.util.*;

// 톱니바퀴

public class BJ14891 {
	
	static boolean[][] wheels = new boolean[4][8];
	static int[] dir = new int[4];						// 각 바퀴가 돌아야 할 방향 (0: 돌지 않음, 1: 시계 방향, -1: 반시계 방향)
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for(int i = 0; i < 4; i++) {
			String line = br.readLine();
			for(int j = 0; j < 8; j++) {
				if(line.charAt(j) == '1') wheels[i][j] = true;
			}
		}
		
		int k = Integer.parseInt(br.readLine());
		for(int i = 0; i < k; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int num = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken());
			
			calculateEachDirection(num, d);		// 각 바퀴의 방향을 정해줌
			rotateWheels();						// dir[]에 따라 바퀴들을 돌림
		}
		
		System.out.println(calculateScore());	// 점수를 계산해서 출력
	}
	
	private static void calculateEachDirection(int num, int d) {		// num번 바퀴를 d방향으로 돌리는 경우
		
		Arrays.fill(dir, 0);
		dir[num] = d;
		
		// num의 왼쪽 방향
		int n = num;
		while(--n >= 0) {
			if(wheels[n + 1][6] == wheels[n][2]) break;		// 맞닿은 극이 같다면, 더이상 회전하는 바퀴 없음
			dir[n] = dir[n + 1] == 1 ? -1 : 1;				// 맞닿은 극이 다르다면, 옆 바퀴와 다른 방향으로 지정
		}
		
		// num의 오른쪽 방향
		n = num;
		while(++n < 4) {
			if(wheels[n - 1][2] == wheels[n][6]) break;		// 맞닿은 극이 같다면, 더이상 회전하는 바퀴 없음
			dir[n] = dir[n - 1] == 1 ? -1 : 1;				// 맞닿은 극이 다르다면, 옆 바퀴와 다른 방향으로 지정
		}
	}
	
	private static void rotateWheels() {
		
		for(int i = 0; i < 4; i++) {
			if(dir[i] == 1) {								// 시계 방향인 경우
				boolean tmp = wheels[i][7];
				for(int j = 7; j > 0; j--) {
					wheels[i][j] = wheels[i][j - 1];
				}
				wheels[i][0] = tmp;
			}
			else if(dir[i] == -1) {							// 반시계 방향인 경우
				boolean tmp = wheels[i][0];
				for(int j = 0; j < 7; j++) {
					wheels[i][j] = wheels[i][j + 1];
				}
				wheels[i][7] = tmp;
			}
		}
	}
	
	private static int calculateScore() {
		
		int total = 0;
		for(int i = 0; i < 4; i++) {
			if(wheels[i][0]) total += Math.pow(2, i);
		}
		return total;
	}
	
}
