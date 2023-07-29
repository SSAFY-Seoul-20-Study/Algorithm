import java.util.*;
import java.io.*;

public class Backjoon_17135_캐슬_디펜스
{
	static int[][] origarr;
	static int[][] gamearr;
	static int N;
	static int M;
	static int D;

	static List<int[]> possCombs = new ArrayList<>();
	static int[] comb= new int[3];
	static int[] marr;

	public static void main(String args[]) throws Exception
	{
		//System.setIn(new FileInputStream("./res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		origarr = new int[N][M]; //0빈공간 1적 2마크당한적

		for(int i=0;i<N;i++){
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++){
				origarr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		marr = new int[M];
		for(int j=0;j<M;j++){
			marr[j] = j;
		}

		getPossCombs(3, 0, 0);

		int maxP=0;
		for(int[] bowmans:possCombs){
			gamearr = deepcopy2d(origarr, N, M);
			int p=0;
			while(enemyIsAlive()){
				//궁수의 턴
				for(int b:bowmans){
					markEnemy(b);
				}
				
				p+=killMarkedEnemy();

				//적의 턴
				moveEnemy();
			}
			if(p>maxP) maxP = p;
		}

		System.out.print(maxP);

		br.close();
	}

	private static int[][] deepcopy2d(int[][] input, int N, int M){
		int[][] output = new int[N][M];
		for(int i=0;i<N;i++){
			for(int j=0;j<M;j++){
				output[i][j] = input[i][j];
			}
		}
		return output;
	}

	private static void getPossCombs(int c, int idx, int depth) {
		if(c==0){ //조합 뽑기 기회를 모두 소진한 경우 comb를 possCombs에 저장
			possCombs.add(Arrays.copyOf(comb, comb.length));
		}
		else if(depth == marr.length){ //뽑기 기회가 남았는데, marr를 끝까지 탐색한 경우 강제 반환
			return;
		}
		else{
			//depth에 위치한 숫자를 뽑은 경우
			comb[idx] = marr[depth];
			getPossCombs(c-1, idx+1, depth+1);

			//숫자를 뽑지 않는 경우
			getPossCombs(c, idx, depth+1);
		}
		
	}

	private static boolean enemyIsAlive(){
		for(int i=0;i<N;i++){
			for(int j=0;j<M;j++){
				if(gamearr[i][j]==1) return true;
			}
		}
		return false;
	}

	private static void markEnemy(int b) {
		int targeti=-1;
		int targetj=-1;
		int minDist=999999999;
		for(int i=0;i<N;i++){
			for(int j=0;j<M;j++){
				if(gamearr[i][j]==1 || gamearr[i][j]==2){
					int dist = getDist(N, b, i, j);
					if(dist <=D && (dist < minDist || (dist==minDist && j < targetj))){
						targeti = i;
						targetj = j;
						minDist = dist;
					}
				}
			}
		}

		if(targeti!=-1)
			gamearr[targeti][targetj] = 2;
	}

	static int getDist(int y1, int x1, int y2, int x2){
		return Math.abs(x1-x2) + Math.abs(y1-y2);
	}

	private static int killMarkedEnemy() {
		int p=0;
		for(int i=0;i<N;i++){
			for(int j=0;j<M;j++){
				if(gamearr[i][j] == 2){
					gamearr[i][j] = 0;
					p++;
				}
			}
		}
		return p;
	}

	private static void moveEnemy(){
		for(int i=N-2;i>=0;i--){
			for(int j=0;j<M;j++){
				if(gamearr[i][j] == 0){
					gamearr[i+1][j] = 0;
				}
				else if(gamearr[i][j] == 1){
					gamearr[i+1][j] = 1;
				}
			}
		}

		for(int j=0;j<M;j++){
			gamearr[0][j] = 0;
		}
	}

}