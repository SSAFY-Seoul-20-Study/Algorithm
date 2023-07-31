import java.util.*;
import java.io.*;

public class Backjoon_17135_캐슬_디펜스
{
	static int[][] origarr; //게임의 초기상태를 저장
	static int[][] gamearr; //게임의 진행 상황을 저장
	static int N;
	static int M;
	static int D;

	static int[] marr; //possCombs을 구하기 위해서 사용. 그 뒤엔 사용안함.
	static int[] comb= new int[3]; //마찬가지로 possComb를 구하기 위해서 사용.
	//이 두개의 멤버변수는 로컬 변수로 사용해도 무방하나, 최초 구현 과정에서 다른 방식으로 이용하다가 멤버 변수에 그대로 두게 됨.

	static List<int[]> possCombs = new ArrayList<>(); //일차원 배열로 표현된 조합이 여러개 들어가 있는 List. [{1,2,3}, {1,2,4} ...] 형태

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
		for(int[] bowmans:possCombs){ //전체 조합중에서 한가지 가능한 조합을 뽑는 방식으로 각 조합당 득점량을 탐색한다.
			gamearr = deepcopy2d(origarr, N, M); //게임 진행을 위해 gamearr를 생성한다.
			int p=0;
			while(enemyIsAlive()){ //적이 아직 살아있으면,
				//궁수의 턴
				for(int b:bowmans){ //각 궁수가 적을 마크하고
					markEnemy(b);
				}
				
				p+=killMarkedEnemy(); //궁수에게 마크당한 적을 제거하고

				//적의 턴
				moveEnemy(); //모든 적이 한칸 아래로 움직인다.
			}
			if(p>maxP) maxP = p; //만약 신기록을 세웠으면, 최고점수를 업데이트 한다.
		}

		System.out.print(maxP);

		br.close();
	}

	private static int[][] deepcopy2d(int[][] input, int N, int M){ //2차원 배열 딥카피
		int[][] output = new int[N][M];
		for(int i=0;i<N;i++){
			for(int j=0;j<M;j++){
				output[i][j] = input[i][j];
			}
		}
		return output;
	}

	private static void getPossCombs(int c, int idx, int depth) {
		if(c==0){ //조합 뽑기 기회를 모두 소진한 경우 comb를 possCombs에 추가
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

	private static void markEnemy(int b) { //모든 보드판을 탐색하면서, 사거리 내의 가장 가깝고, 동일 거리면 가장 왼쪽에 있는 적을 마크.
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

	static int getDist(int y1, int x1, int y2, int x2){ //맨해탄 거리 구하기
		return Math.abs(x1-x2) + Math.abs(y1-y2);
	}

	private static int killMarkedEnemy() { //2번으로 마크된 적을 제거.
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

	private static void moveEnemy(){ //아랫줄부터 윗줄로 탐색하며 행을 하나씩 내리고, 맨 윗 행은 0으로 채움.
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