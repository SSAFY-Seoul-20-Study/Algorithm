import java.io.*;
import java.util.*;

public class W2_boj_17135 {
	static int n, m, d, res;
	static int[][] map;
	static int[] archer = new int[3];
	static int[][] copyMap;

	public static void main(String[] args) throws Exception {
//		System.setIn(new FileInputStream("res/input_boj_17135.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// n, m, d 입력받기
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());

		map = new int[n][m];
		copyMap = new int[n][m];
		res = 0;

		// 배열 입력받기
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				copyMap[i][j] = map[i][j];
			}
		}

//		// 배열 출력
//		for (int[] nums : map) {
//			System.out.println(Arrays.toString(nums));
//		}

		// 궁수 선택
		selectArcher(0, 0);
		System.out.println(res);

		br.close();
	}

	// M(0~M-1) 중 3개 선택 : 궁수 위치
	public static void selectArcher(int cnt, int start) {
		if (cnt == 3) {
			calMaxRes();
			return;
		}
		for (int i = start; i < m; i++) {
			archer[cnt] = i;
			selectArcher(cnt + 1, i + 1);
		}
	}

	// 완탐(map 계산)
	// 맨 아래 행부터 체크(d행씩 한번에 체크)
	public static void calMaxRes() {
		// map 초기화
		for (int i = 0; i < n; i++)
			map[i] = Arrays.copyOf(copyMap[i], m);

		int res_temp = 0;
		int[][] chooseEnemy = new int[3][2]; // 각 궁수가 선택한 적

		for (int game = 0; game < n; game++) {
			for (int a = 0; a < 3; a++) {
				chooseEnemy[a] = attack(archer[a]);
			}			
			for (int a = 0; a < 3; a++) {
				if (chooseEnemy[a] != null && map[chooseEnemy[a][0]][chooseEnemy[a][1]] == 1) { // 궁수들이 동시에 같은 적 공격했는지 확인
					map[chooseEnemy[a][0]][chooseEnemy[a][1]] = 0;
					res_temp++;
				}
			}
			downEnemy();
		}
		res = Math.max(res, res_temp);
	}

	// 궁수마다, 공격할 적 선택하기.
	public static int[] attack(int y) {
//		for (int i = n - 1; i >= 0; i--) { // 맨 아래 행부터 체크
//			for (int j = 0; j < m; j++) {
//				if (map[i][j] == 1 && isInDist(n, y, i, j)) {
//					return new int[] { i, j };
//				}
//			}
//		}
		for (int dist=1; dist<=d; dist++) {
			for(int j= -dist; j<=dist; j++) {
				int ny = y + j;
				int nx = n - dist + Math.abs(j);
				if(0 <= nx && nx < n && 0 <= ny && ny < m && map[nx][ny] == 1) {
					return new int[] {nx, ny};
				}
			}
		}
		return null;
	}

	// 적 아래로 한 칸씩 이동
	public static void downEnemy() {
		for (int i = n - 1; i > 0; i--) {
			map[i] = map[i-1];
			}
		map[0] = new int[m];
	}

	// 거리 계산해서 범위에 있는지 확인 (True, False)
	public static Boolean isInDist(int r1, int c1, int r2, int c2) {
		if ((Math.abs(r1 - r2) + Math.abs(c1 - c2)) <= d) {
			return true;
		}
		return false;
	}
}
/*
 * 17135(G3) 16008KB 156ms
 * 풀이 순서. 
 * 1. M열 중 3개 선택하여, 궁수 뽑기(조합) 
 * 2. 뽑힌 궁수와, 맨 아래 행 적들과 거리 계산해서, 공격 -> 공격하면 0으로 변경 
 * 3. 공격이 끝나면, 적을 한 줄씩 아래로 내리기. 
 * 4. n만큼 반복
 * 
 * 틀린 이유: 
 * 궁수 1명당, 가장 가까운 거리의 적 1명을 처치하는 것 구현 안함. 거리 내에 있으면, 모두 공격해버림. 
 * d개 행씩 확인하고, 한번 공격 끝나면 map을 아래로 내려서 그대로 진행하니, n의 인덱스는 넘어가버리는데, 배열은 하나씩 밀려, 확인하지 않는 행이 생김. 
 * map 리스트를 처음 궁수 조합에서 원본을 수정해서, 다음 조합부터, 제대로 동작안함 -> 복사 필요!
 * 
 * 추가
 * 거리를 기준으로 공격할 적 선택하도록 해야함! (마지막 행부터 확인 할 경우,거리가 같은 윗 열이 있을 수 있음)
 * (1,2,4)일 때, 1 궁수 -> (5,2)를 선택함.
 */