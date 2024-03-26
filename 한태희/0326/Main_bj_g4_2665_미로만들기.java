import java.util.*;
import java.io.*;


public class Main_bj_g4_2665_미로만들기 {
	static int N;
	static int[][] a, dp;

	static int[] dr = { 1, -1, 0, 0 };
	static int[] dc = { 0, 0, 1, -1 };

	static ArrayList<int[]> list, nextList;

    public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());

		a = new int[N][N];

		for(int i=0;i<N;i++){
			String str = br.readLine();
			for(int j=0;j<N;j++){
				a[i][j] = str.charAt(j) - '0';
			}
		}

		dp = new int[N][N];
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				dp[i][j] = -1;
			}
		}

		list = new ArrayList<>();
		nextList = new ArrayList<>();
		list.add(new int[]{0, 0});

		int level = 0;

		while(true){
			if(dp[N-1][N-1] != -1) break;
			
			for(int[] cord: list){
				int r = cord[0];
				int c = cord[1];

				fill(r, c, level);
			}
			
			level = level + 1;
			list = nextList;
			nextList = new ArrayList<>();
		}

		System.out.println(dp[N-1][N-1]);
		
	}

	static void fill(int r, int c, int l){
		dp[r][c] = l;
		for(int k=0;k<4;k++){
			int nr = r + dr[k];
			int nc = c + dc[k];

			if(isValid(nr, nc) && a[nr][nc]==0){
				nextList.add(new int[]{nr, nc});
			}

			if(isValid(nr, nc) && a[nr][nc]==1 && dp[nr][nc]==-1){
				fill(nr, nc, l);
			}


		}
	}

	static boolean isValid(int r, int c){
		if(0<=r && r<N && 0<=c && c<N) return true;
		else return false;
	}

}