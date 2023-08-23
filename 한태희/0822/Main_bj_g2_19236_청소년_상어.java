import java.util.*;
import java.io.*;

public class Main_bj_g2_19236_청소년_상어 {

	static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
	static int[] dc = {0, -1, -1, -1, 0, 1, 1, 1};

	static int max_point;

	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[][] a = new int[4][4];
		// a : 4x4 공간 수족관. (앞으로 이 공간을 임의로 수족관이라고 부른다.)
		//     물고기들의 번호가 1이상 16 이하로 적혀있다.
		//      0인 곳은 비어있는 곳이다.
		
		int[][] b = new int[4][4];
		// b : 수족관 속의 물고기들의 방향. 인덱싱을 위해 문제에서 주어진 숫자보다 1 작다.
		//      k =  b[row][col], new_row = row + dr[k] 방식으로 사용한다. 
		//     -1 인 곳은 비어있는 곳이다.
		int[][] f = new int[16+1][2];
		// f : 문제에서 주어진 "작은 물고기부터 움직인다." 라는 요구사항을 구현하기 위해
		//     번호당 물고기의 위치를 기억하는 테이블이다.
		//     편의를 위해 1 ~16의 인덱스만을 사용하며
		//     0 인덱스에 접근하는 것은 의도를 벗어나는 행위이다.
		//     f[idx][0] (idx의 row) 가 -1이라면, 수족관에 존재하지 않는 물고기다. 
		f[0][0] = -1; 

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
		//SSAFY에서 배우는 재귀 패턴인 쓰기 및 되돌리기를 통한 메모리 전략은
		//청소년 상어 문제를 푸는데 부적절하다.
		//상어와 물고기들의 행동을 되돌리는 것은 매우 힘들 것이 뻔하기 때문에
		//굳이 하나의 배열을 사용하지 않고, 딥카피로 변화된 상태를
		//다음 재귀에 인자로 넘기는 것이 더 간단하다.
		int[][] a = deepcopy2D(Ba);
		int[][] b = deepcopy2D(Bb);
		int[][] f = deepcopy2D(Bf);
		//상어가 먹이를 먹는다.
		//상어가 먹은 물고기의 크기 eat, 방향 sharkDir을 저장한다.
		int eat = a[r][c];
		int sharkDir = b[r][c];
		//먹힌 물고기의 정보를 삭제한다.
		f[eat][0] = -1;
		a[r][c] = 0;
		b[r][c] = -1;

		//물고기들이 이동한다.
		//f 배열을 이용해 번호가 낮은 순부터 이동.
		for(int k=1;k<=16;k++){
			//f배열의 정의에 의해 row가 0으로 할당되어 있다면,
			//존재하지 않는 물고기이므로 pass
			if(f[k][0] == -1) continue;
			int my_i=f[k][0], my_j=f[k][1];
			int dir = b[my_i][my_j];
			if(dir==-1) continue;
			//8방향으로 물고기의 현재 방향부터 45도 반시계 방향으로 회전하며
			//이동이 가능한지 탐색 하고, 이동을 했다면
			//break로 탐색을 빠져나온다.
			for(int w=0;w<8;w++){
				int ot_i = dr[(dir+w)%8] + my_i;
				int ot_j = dc[(dir+w)%8] + my_j;
				//다음 칸이 상어이거나 혹은 수족관을 벗어나면 이동이 안된다.
				if(ot_i==r && ot_j==c) continue;
				else if(!isValid(ot_i, ot_j)) continue;
				//만약 이동 가능한 위치에 다른 물고기가 있다면,
				//서로의 위치를 바꾸며 이를 위해 구체적으로
				//a, b, f 배열을 스왑한다.
				//a, b 배열은 서로의 값을 바꾸면 되고,
				//f 배열은 양쪽 물고기 번호가 가지는 row 와 col 값을 바꾼다.
				if(a[ot_i][ot_j] !=0){
					int my_eat = k;
					int ot_eat = a[ot_i][ot_j];
					int tempi = f[my_eat][0], tempj=f[my_eat][1];
					f[my_eat][0] = f[ot_eat][0]; f[my_eat][1] = f[ot_eat][1];
					f[ot_eat][0] = tempi; f[ot_eat][1] = tempj;

					a[my_i][my_j] = ot_eat;
					a[ot_i][ot_j] = k;
					b[my_i][my_j] = b[ot_i][ot_j];
					b[ot_i][ot_j] = (dir+w)%8;
				}
				//만약 이동 가능한 위치에 다른 물고기가 없다면,
				//물고기를 그 빈곳에 이동시키고,
				//자신의 위치가 있던 곳은 빈 곳으로 만든다.
				//f 배열은 0번 인덱스 접근을 하면 안됨으로 이 조건 분기가 필요하다.
				else{
					int my_eat = k;
					f[my_eat][0] = ot_i; f[my_eat][1] = ot_j;

					a[ot_i][ot_j] = my_eat;
					a[my_i][my_j] = 0;

					b[ot_i][ot_j] = (dir+w)%8;
					b[my_i][my_j] = -1;
				}
				break;
			}
		}

		//상어가 자신의 방향에서 이동 가능한 구간으로 모두 이동해본다.
		//각 가능성을 재귀적으로 탐색한다.
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