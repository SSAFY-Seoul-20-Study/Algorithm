import java.util.*;
import java.io.*;

public class Backjoon_17136_색종이_붙히기
{
	static int[][] arr;
	static int N=10;

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

		//for(int [] b : arr) System.out.println(Arrays.toString(b));
		int paperUseCnt = 0;
		int MAX_PAPER = 5;
		int[] psizelist = {5, 4, 3, 2, 1};
		papersize:for(int psize:psizelist){
			int left_p = MAX_PAPER;

			for(int i=0;i<=N-psize;i++){
				for(int j=0;j<=N-psize;j++){
					if(paperCanAttatched(i,j,psize)){
						attachPaper(i,j,psize);
						paperUseCnt ++;
						left_p --;
						if(left_p ==0) continue papersize;
					}
				}
			}
		}
		if(!CoveredAll())
			paperUseCnt = -1;

		System.out.println(paperUseCnt);

		br.close();
	}

	private static boolean paperCanAttatched(int row, int col, int psize) {
		for(int i=row;i<row+psize;i++)
			for(int j=col;j<col+psize;j++)
				if(arr[i][j] ==0)
					return false;
			
		
		return true;
	}

	private static void attachPaper(int row, int col, int psize) {
		for(int i=row;i<row+psize;i++)
			for(int j=col;j<col+psize;j++)
				arr[i][j] = 0;
	}

	private static boolean CoveredAll() {
		for(int i=0;i<N;i++)
			for(int j=0;j<N;j++)
				if(arr[i][j]==1)
					return false;
			
		return true;
	}

}