import java.util.*;
import java.io.*;

//인터넷 예제를 참조했습니다.
//출처:
//https://velog.io/@ohjinseo/%EB%B0%B1%EC%A4%80-17136%EB%B2%88-%EC%83%89%EC%A2%85%EC%9D%B4-%EB%B6%99%EC%9D%B4%EA%B8%B0-C

public class Backjoon_17136_색종이_붙히기
{
	static int N=10; //배열 크기
	static int M=5; //색총이의 최대 크기 (1~5)
	static int K=5; //색종이의 개수
	static int min = 999999999;

	static int[][] arr;
	static int[] paperLeft;

	public static void main(String args[]) throws Exception
	{
		//System.setIn(new FileInputStream("./res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		arr = new int[N][N];

		for(int i=0;i<N;i++){
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++){
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		paperLeft = new int[M+1];
		for(int m=1;m<=M;m++)
			paperLeft[m] = K;

		recursive_attach(0);
		if(min ==999999999)
			min = -1;
		System.out.println(min);
		br.close();
	}

	static void recursive_attach(int depth){
		if(depth > min) return;

		if(coveredAll() && min > depth) min = depth;

		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				if(arr[i][j] ==1){
					for(int m=5;m>=1;m--){
						if(paperLeft[m]>0 && paperCanAttatched(i, j, m)){
							paperLeft[m] --;
							attachPaper(i,j,m);

							recursive_attach(depth+1);
							//재귀 호출 이후 원상복구
							paperLeft[m] ++;
							detachPaper(i,j,m);

						}
					}

					return;
				}
			}
		}
	}

	private static boolean paperCanAttatched(int row, int col, int psize) {
		for(int i=row;i<row+psize;i++)
			for(int j=col;j<col+psize;j++)
				if(!isValid(i, j) || arr[i][j] ==0)
					return false;
			
		return true;
	}

	private static void attachPaper(int row, int col, int psize) {
		for(int i=row;i<row+psize;i++)
			for(int j=col;j<col+psize;j++)
				arr[i][j] = 0;
	}

		private static void detachPaper(int row, int col, int psize) {
		for(int i=row;i<row+psize;i++)
			for(int j=col;j<col+psize;j++)
				arr[i][j] = 1;
	}

	private static boolean coveredAll() {
		for(int i=0;i<N;i++)
			for(int j=0;j<N;j++)
				if(arr[i][j]==1)
					return false;
			
		return true;
	}

	
	static boolean isValid(int row, int col){
		if(0<=row && row<N && 0<=col && col <N)
			return true;
		else
			return false;
	}
}