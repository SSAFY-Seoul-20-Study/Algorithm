import java.util.*;
import java.io.*;

public class Main_bj_g2_19236_청소년_상어 {

	static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
	static int[] dc = {0, -1, -1, -1, 0, 1, 1, 1};

	static int max_point;

	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[][] a = new int[4][4]; int[][] b = new int[4][4];
		int[][] f = new int[16+1][2];
		f[0][0] = -1; //f에서 row가 -1이라면 존재하지 않음으로 취급

		for(int i=0;i<4;i++){
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0;j<4;j++){
				a[i][j] = Integer.parseInt(st.nextToken());
				b[i][j] = Integer.parseInt(st.nextToken()) -1;
				f[a[i][j]][0] = i;
				f[a[i][j]][1] = j;
			}
		}

		max_point = 0;
		moveShark(0, 0, a, b, f, 0);
		System.out.println(max_point);
		br.close();
	}

	static void moveShark(int r, int c, int[][] Ba, int[][]Bb, int[][]Bf, int cumul){
		int[][] a = deepcopy2D(Ba);
		int[][] b = deepcopy2D(Bb);
		int[][] f = deepcopy2D(Bf);
		//상어가 먹이를 먹는다.
		int eat = a[r][c];
		int sharkDir = b[r][c];
		f[eat][0] = -1;
		a[r][c] = 0;
		b[r][c] = -1;

		//물고기들이 이동한다.
		for(int k=1;k<=16;k++){
			if(f[k][0] == -1) continue;
			int my_i=f[k][0], my_j=f[k][1];
			int dir = b[my_i][my_j];
			if(dir==-1) continue;
			if(dir==-1) System.out.println(my_i +" "+ my_j + " " + dir);
			for(int w=0;w<8;w++){
				int ot_i = dr[(dir+w)%8] + my_i;
				int ot_j = dc[(dir+w)%8] + my_j;
				if(ot_i==r && ot_j==c) continue;
				else if(!isValid(ot_i, ot_j)) continue;
				int my_eat = k;
				int ot_eat = a[ot_i][ot_j];
				int tempi = f[my_eat][0], tempj=f[my_eat][1];
				f[my_eat][0] = f[ot_eat][0]; f[my_eat][1] = f[ot_eat][1];
				f[ot_eat][0] = tempi; f[ot_eat][1] = tempj;

				a[my_i][my_j] = ot_eat;
				a[ot_i][ot_j] = my_eat;
				b[my_i][my_j] = b[ot_i][ot_j];
				b[ot_i][ot_j] = (dir+w)%8;
				break;
			}
		}

		//상어가 가능한 구간으로 모두 이동해본다.
		while(isValid(r, c)){
			if(a[r][c] != 0){
				moveShark(r, c, a, b, f, cumul+eat);
			}
			r += dr[sharkDir];
			c += dc[sharkDir];
		}

		max_point = Math.max(max_point, cumul+eat);

	}

	static boolean isValid(int r, int c){
		if(0<=r && r<4 && 0<=c && c<4) return true;
		else return false;
	}

	static int[][] deepcopy2D(int arr[][]){
		int N=arr.length, M=arr[0].length;
		int[][] ret = new int[N][M];
		for(int i=0;i<N;i++){
			for(int j=0;j<M;j++){
				ret[i][j] = arr[i][j];
			}
		}
		return ret;
	}
}