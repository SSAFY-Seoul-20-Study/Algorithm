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
	static int min = 999999999; //가장 작게 색종이를 붙힐 수 있는 경우.
	//출력 결과이며, min=999999999 인경우 주어진 조건으로 색종이를 깔끔하게 덮을 수 있는 경우가 없는 것이므로 -1을 출력.

	static int[][] arr; //주어진 배열
	static int[] paperLeft; //각 크기의 종이가 몇개 남았는지 저장하는 배열. 1이상 5이하의 인덱스를 사용하고, 0번 인덱스는 방치. (접근 직관성을 위해서)

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

		recursive_attach(0); //min에 탐색 결과를 업데이트
		if(min ==999999999)
			min = -1;
		System.out.println(min);
		br.close();
	}

	static void recursive_attach(int depth){
		if(depth > min) return; //<<현재 붙인 색종이의 수가 min보다 크면 endgame(가망이 없음)으로 프루닝.>>

		if(coveredAll() && min > depth) min = depth; //만약 문제 배열이 모두 색종이로 덮혔고 depth가 min보다 작으면 최솟값 후보를 찾은 것이므로, min을 depth로 업데이트.

		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){ //한번의 재귀 구간에서 문제 배열의 모든 구간을 (종이 붙히기 시작점으로) 순차적으로 탐색.
				if(arr[i][j] ==1){ //<<종이 붙히기의 시작점이 1인지 확인해서 프루닝>>
					for(int m=5;m>=1;m--){ // 색종이의 크기를 5부터 1까지 반복하면서 
						if(paperLeft[m]>0 && paperCanAttatched(i, j, m)){ //해당 크기 종이가 남아있고, 종이 붙히기 시작점이 왼쪽 위라고 봤을때, 종이를 붙힐 수 있다면
							paperLeft[m] --; //해당 크기 종이 하나 제거
							attachPaper(i,j,m); //종이를 붙힘

							recursive_attach(depth+1); //다시 재귀적으로 다음 종이 붙히기를 진행.
							//재귀 호출 이후 원상복구
							paperLeft[m] ++;
							detachPaper(i,j,m);

						}
					}

					return; //<<제일 중요한 프루닝: 여기에 도달한다는건 색종이를 붙혀야 하는 공간이 남아 있는데, 더이상 색종이를 붙힐 수 있는 방법이 없다는 것으로 프루닝. >>
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