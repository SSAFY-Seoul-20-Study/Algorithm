package algostudy;
import java.util.*;
import java.io.*;

public class Backjoon_17070_파이프_옮기기
{
	public static void main(String args[]) throws Exception
	{
		System.setIn(new FileInputStream("./res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringBuilder sb = new StringBuilder();
	
		int N = Integer.parseInt(br.readLine());
		int[][] obst_Arr = new int[N][N];
		int[][][] res_Arr = new int[N][N][3]; //i, j, (0수평, 1대각선, 2수직)
		res_Arr[0][1][0] = 1;

		for(int i=0;i<N;i++){
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for(int j=0;j<N;j++){
				obst_Arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				if(i==0 && j==1)
					continue;

				if(obst_Arr[i][j]==1)
					continue;

				if(!isValid(obst_Arr, N, i, j-1) && !isValid(obst_Arr, N, i-1, j-1) && !isValid(obst_Arr, N, i-1, j))
					continue;
				
				if(isValid(obst_Arr, N, i, j-1) && isValid(obst_Arr, N, i-1, j-1) && isValid(obst_Arr, N, i-1, j)){
					res_Arr[i][j][0] = res_Arr[i][j-1][0] + res_Arr[i][j-1][1];
					res_Arr[i][j][1] = res_Arr[i-1][j-1][0] + res_Arr[i-1][j-1][1] + res_Arr[i-1][j-1][2];
					res_Arr[i][j][2] = res_Arr[i-1][j][1] + res_Arr[i-1][j][2];
				}


			}
		}
		
		int result = res_Arr[N-1][N-1][0] + res_Arr[N-1][N-1][1] + res_Arr[N-1][N-1][2];
		System.out.println(result);

		br.close();
	}

	static boolean isValid(int[][] arr, int N, int row, int col){
		if(0<=row && row<N && 0<=col && col<N && arr[row][col]==0)
			return true;
		else{
			return false;
		}
	}

}