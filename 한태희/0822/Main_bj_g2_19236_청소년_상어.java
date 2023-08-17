import java.util.*;
import java.io.*;

public class Main_bj_g2_19236_청소년_상어 {
	static int[][] a;
	static int[][] b;
	static int[][] f;

	static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
	static int[] dc = {0, -1, -1, -1, 0, 1, 1, 1};

	static int max_point;
	static int point;

	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		a = new int[4][4]; b = new int[4][4];
		f = new int[16+1][2];
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
		point = 0;
		moveShark(0, 0);
		for(int[] t:a) System.out.println(Arrays.toString(t));
		
		br.close();
	}

	static void moveShark(int r, int c){
		//상어가 먹이를 먹는다.
		int eat = a[r][c];
		point += eat;
		f[eat][0] = -1;
		a[r][c] = 0;

		//물고기들이 이동한다.
		for(int k=1;k<=16;k++){
			if(f[k][0] == -1) continue;
			int my_i=f[k][0], my_j=f[k][1];
			int dir = b[my_i][my_j];
			System.out.println(k + " " + my_i +" "+ my_j +" " + dir);
			// for(int w=0;w>=-7;w--){
			// 	int ot_i = dr[(dir+w+8)%8] + my_i;
			// 	int ot_j = dc[(dir+w+8)%8] + my_j;
			// 	if(ot_i==r && ot_j==c) continue;
			// 	else if(!isValid(ot_i, ot_j)) continue;
			// 	int my_eat = a[my_i][my_j];
			// 	if(my_eat != k) System.out.println("망했대요 ㅋㅋㅋ");
			// 	int ot_eat = a[ot_i][ot_j];
			// 	int tempi = f[my_eat][0], tempj=f[my_eat][1];
			// 	f[my_eat][0] = f[ot_eat][0]; f[my_eat][1] = f[ot_eat][1];
			// 	f[ot_eat][0] = tempi; f[ot_eat][1] = tempj;

			// 	a[my_i][my_j] = ot_eat;
			// 	a[ot_i][ot_j] = my_eat;

			// 	b[my_i][my_j] = b[ot_i][ot_j];
			// 	b[ot_i][ot_j] = dir;	
			// }
		}

		//만약 상어가 이동할 수 있는 구간이 없다면 리턴

		//상어가 가능한 구간으로 모두 이동해본다.
	}

	static boolean isValid(int r, int c){
		if(0<=r && r<4 && 0<=c && c<4) return true;
		else return false;
	}
}