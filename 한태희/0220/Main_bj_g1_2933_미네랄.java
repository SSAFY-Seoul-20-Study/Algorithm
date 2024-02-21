import java.util.*;
import java.io.*;

public class Main_bj_g1_2933_미네랄 {
	static int N, M;
	static int[] dr = { 1, 0, -1, 0 };
	static int[] dc = { 0, 1, 0, -1 };
	static boolean arr[][];
	static int g[][];
	static int gFall[];

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		arr = new boolean[N][M];
		for(int i=0;i<N;i++) {
			String s = br.readLine();
			for(int j=0;j<M;j++) {
				if(s.charAt(j) == 'x') {
					arr[i][j] = true;
				}
				if(s.charAt(j) == '.') {
					arr[i][j] = false;
				}
			}
		}

		int totalTurns = Integer.parseInt(br.readLine());

		st = new StringTokenizer(br.readLine());

		boolean shotLeft = true; //true: 왼쪽에서 오른쪽으로, false: 오른쪽에서 왼쪽으로

		for(int turn=0;turn<totalTurns;turn++) {
			int level = Integer.parseInt(st.nextToken());
			shot(level, shotLeft);
			shotLeft = !shotLeft;
			moveClusters();
		}

		printArr();

		br.close();
	}

	static void shot(int level, boolean shotLeft){
		int col = shotLeft ? 0 : M-1;
		int dc = shotLeft ? 1 : -1;
		int row = N - level;

		while(0 <= col && col < M) {
			//System.out.println(row + " " + col);
			//System.out.println(arr[row][col]);
			if(arr[row][col]) {
				arr[row][col] = false;
				break;
			}
			col += dc;
		}
	}

	static void moveClusters(){
		while(true){
			int groupCnt = initGroup();
			initGroupFall(groupCnt);
			if(isFallNeeded(groupCnt)){
				fall();
			}
			else{
				break;
			}
		}
	}

	static int initGroup(){
		int groupCnt = 1;
		g = new int[N][M];
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(arr[i][j] && g[i][j] == 0) {
					dfsGrouping(i, j, groupCnt++);
				}
			}
		}

		return groupCnt;
	}

	static void dfsGrouping(int r, int c, int groupCnt){
		g[r][c] = groupCnt;
		for(int k=0;k<4;k++) {
			int nr = r + dr[k];
			int nc = c + dc[k];
			if(isValid(nr, nc) && arr[nr][nc] && g[nr][nc] == 0) {
				dfsGrouping(nr, nc, groupCnt);
			}
		}
	}

	static void initGroupFall(int groupCnt){
		gFall = new int[groupCnt];
		for(int i=0;i<groupCnt;i++){
			gFall[i] = Integer.MAX_VALUE;
		}
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(gFall[g[i][j]] ==0) continue;
				int r = i, c = j, fallLength = 0;
				while(true){
					r +=1;
					if(isValid(r, c)==false) break;
					if(g[i][j] == g[r][c]){
						fallLength = Integer.MAX_VALUE;
						break;
					}
					if(g[r][c]!=0 && g[i][j] != g[r][c]) break;

					fallLength+=1;
				}

				gFall[g[i][j]] = Math.min(gFall[g[i][j]], fallLength);
			}
		}
	}

	static boolean isFallNeeded(int groupCnt){
		for(int i=0;i<groupCnt;i++){
			if(gFall[i] != 0) return true;
		}
		return false;
	}

	static void fall(){
		boolean[][] newArr = new boolean[N][M];
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(g[i][j] == 0) continue;
				int r = i + gFall[g[i][j]];
				int c = j;
				newArr[r][c] = true;
				
			}
		}

		arr = newArr;

	}

	static void printArr(){
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				System.out.print(arr[i][j] ? 'x' : '.');
			}
			System.out.println();
		}
	}

	static boolean isValid(int r, int c){
		if(0<=r && r<N && 0<=c && c<M) return true;
		else return false;
	}

}