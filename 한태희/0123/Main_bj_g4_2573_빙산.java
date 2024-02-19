import java.util.*;
import java.io.*;

public class Main_bj_g4_2573_빙산 {
	static int N, M;
	static short[][] arr;
	static int[] dr = { 1, 0, -1, 0 };
	static int[] dc = { 0, 1, 0, -1 };
	static int current;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		arr = new short[N][M];

		current = 0;

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				arr[i][j] = Short.parseShort(st.nextToken());
				if (arr[i][j] != 0) {
					current++;
				}
			}
		}

		int year = 0;
		boolean answerExist = false;

		while (true) {

			melt();
			year++;

			v = new boolean[N][M];
			gszie = 0;

			grouping: for(int i=0;i<N;i++){
				for(int j=0;j<M;j++){
					if(arr[i][j]==0) continue;
					getGroupSize(i,j);
					break grouping;
				}
			}

			if(gszie!= current){
				answerExist = true;
				break;
			}
			if(current == 0){
				answerExist = false;
				break;
			}
		}

		if(!answerExist){
			year = 0;
		}

		System.out.println(year);

		br.close();
	}

	static void melt(){
		short[][] next = new short[N][M];

		for(int i=0;i<N;i++){
			for(int j=0;j<M;j++){

				if(arr[i][j]==0) continue;

				int cnt=0;
		
				for(int d=0;d<4;d++){
					int nr=i+dr[d];
					int nc=j+dc[d];
					if(!isValid(nr,nc) || arr[nr][nc]==0) cnt++;
				}

				next[i][j]=(short) Math.max((arr[i][j]-cnt), 0);
				if(next[i][j]==0) current--;
			}
		}

		arr = next;
	}

	static boolean v[][];
	static int gszie;

	static void getGroupSize(int r, int c){
		v[r][c] = true;
		gszie++;

		for(int d=0;d<4;d++){
			int nr = r+dr[d];
			int nc = c+dc[d];
			if(!isValid(nr,nc) || arr[nr][nc]==0 || v[nr][nc]) continue;
			getGroupSize(nr,nc);
		}
	}

	static boolean isValid(int r, int c) {
		if (0 <= r && r < N && 0 <= c && c < M)
			return true;
		else
			return false;
	}

}