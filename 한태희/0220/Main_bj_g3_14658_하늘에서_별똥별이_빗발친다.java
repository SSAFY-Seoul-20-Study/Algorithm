import java.util.*;
import java.io.*;

public class Main_bj_g3_14658_하늘에서_별똥별이_빗발친다 {
	static int N, M;
	static boolean[][] obst;
	static boolean[][] v;

	static int targetVisit, answer;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int tc =1;
		String input="";

		while((input = br.readLine())!= null){
			StringTokenizer st = new StringTokenizer(input);
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			obst = new boolean[N][M];
			targetVisit = 0;
			
			for (int i = 0; i < N; i++) {
				String s = br.readLine();
				for (int j = 0; j < M; j++) {
					if(s.charAt(j)=='*'){
						obst[i][j] = true;
					}
					if(s.charAt(j)=='.'){
						obst[i][j] = false;
						targetVisit +=1;
					}
				}
			}
			v = new boolean[N][M];
			answer = Integer.MAX_VALUE;

			if(targetVisit ==1){
				answer = 0;
			}
			else{
				for(int i=0; i<N; i++){
					for(int j=0; j<M; j++){
						if(obst[i][j] == false){
							for(int k=0; k<4; k++){
								int ni = i + dr[k];
								int nj = j + dc[k];
								if(isValid(ni, nj) == false){
									continue;
								}
	
								v[i][j] = true;
								dfs(i, j, k, 1, 1);
								v[i][j] = false;
							}
						}
					}
				}
			}

			sb.append("Case ").append(tc++).append(": ");

			if(answer == Integer.MAX_VALUE){
				sb.append(-1);
			}
			else{
				sb.append(answer);
			}

			sb.append("\n");

		}

		System.out.println(sb);

		br.close();
	}

	static void dfs(int r, int c, int dir, int level, int visits){
		if(visits == targetVisit && level < answer){
			answer = level;
			return;
		}

		if(level > answer){
			return;
		}

		int nr = r + dr[dir];
		int nc = c + dc[dir];

		if(isValid(nr, nc)){

			v[nr][nc] = true;
			dfs(nr, nc, dir, level, visits+1);
			v[nr][nc] = false;

		}
		else{

			for(int k=0;k<4;k++){
				if(k==dir) continue;
				nr = r + dr[k];
				nc = c + dc[k];

				if(isValid(nr, nc)){
					v[nr][nc] = true;
					dfs(nr, nc, k, level+1, visits+1);
					v[nr][nc] = false;
				}
			}

		}

	}

	static boolean isValid(int r, int c){
		if(0<=r && r<N && 0<=c && c<M && obst[r][c] == false && v[r][c] == false){
			return true;
		}else{
			return false;
		}
	}
}