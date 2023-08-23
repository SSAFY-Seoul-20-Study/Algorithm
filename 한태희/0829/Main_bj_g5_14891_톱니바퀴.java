import java.util.*;
import java.io.*;

/**
 * 구현 문제. 이 문제에서 주의해야 하는 점은 톱니바퀴를 회전시킬 경우,
 * 맞물려 있는 톱니바퀴도 동시에 같이 회전한다는 것으로,
 * 이를 구현하기 위해서 한번의 연산 (특정 톱니바퀴를 시계 방향 혹은 반시계 방향으로)이 주어지면,
 * 그 연산으로 인해 모든 톱니바퀴들이 돌아가는 방향을 먼저 구하고,
 * 그 다음 한꺼번에 저장되어 있던 방향으로 톱니바퀴를 돌려야한다.
 * iter(operation){
 * 		op = input()
 * 		check()
 * 		rotate()
 * }
 * score()
 */
public class Main_bj_g5_14891_톱니바퀴 {
	static int[][] gears; //i:기어의 번호 j: 0은 N극 1은 S극
	static boolean[] v; //한번의 연산에서 방문이 되었는지 체크
	static int[] r; //한번의 연산에서 회전해야 하는 방향을 체크

	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		gears = new int[4][8];
		//0번 12시 ~ 2번 3시 6번 9시

		for(int i=0;i<4;i++){
			String s = br.readLine();
			for(int j=0;j<8;j++){
				gears[i][j] = s.charAt(j) - '0';
			}
		}

		int K = Integer.parseInt(br.readLine());
		for(int k=0;k<K;k++){
			StringTokenizer st = new StringTokenizer(br.readLine());
			int g = Integer.parseInt(st.nextToken()) -1;
			int dir = Integer.parseInt(st.nextToken());
			v = new boolean[4];
			r = new int[4];
			v[g] = true;
			r[g] = dir;
			check(g, dir);
			rotate();
		}
		System.out.println(socre());

		br.close();
	}

	/**
	 * 재귀적인 호출을 통해서, 연결되어 있는 톱니바퀴가 돌아가는 방향을 구한다.
	 * 만약 2시와 6시가 같은 극으로 연결되어 있다면 돌아가지 않는다.
	 * 다른 극으로 연결되어 있다면 반대 방향으로 돌아간다.
	 * @param now 현재 톱니바퀴 번호
	 * @param dir 현재 톱니바퀴가 돌아가는 방향
	 * @return 함수의 명시적 리턴은 없지만, r배열에 각 톱니바퀴가 돌아가는 방향이 저장된다.
	 */
	static void check(int now, int dir){
		//양 방향을 탐색한다.
		int[] dx = {-1, 1};
		for(int d :dx){
			int next = now + d;
			//만약 다음 위치가 존재하지 않는 곳이거나 이미 탐색 되었다면 패스
			if(next<0 || 4<=next)continue;
			else if(v[next]) continue;

			//현재 탐색 위치가 왼쪽이면서 지금 기어의 9시와 다음 기어의 2시의 극이 다르거나
			boolean cond1 = d==-1 && gears[now][6]!= gears[next][2];
			//현재 탐색 위치가 오른쪽이면서 지금 기어의 2시와 다음 기어의 9시의 극이 다르다면
			boolean cond2 = d==1 && gears[now][2] != gears[next][6];

			if(cond1 || cond2){
				//회전 방향을 역으로 바꾸고
				int next_dir = dir==1 ? -1 : 1;
				//다음 톱니바퀴를 방문처리하고
				v[next] = true;
				//다음 톱니바퀴에 역방향 회전을 주고 재귀 check를 한다.
				r[next] = next_dir;
				check(next, next_dir);
			}
		}
	}

	/**
	 * r 배열에 저장되어 있는 방향 대로, 톱니바퀴 4개를 회전시킨다.
	 * 회전시키는 방법은 전형적인 배열돌리기
	 */
	static void rotate(){
		for(int i=0;i<4;i++){
			int[] gear = gears[i];
			if(r[i]==1){ //시계 방향으로 회전
				int temp = gear[7];
				for(int j=7;j>0;j--){
					gear[j] = gear[j-1];
				}
				gear[0] = temp;
			}
			else if(r[i]==-1){ //반시계 방향으로 회전
				int temp = gear[0];
				for(int j=0;j<7;j++){
					gear[j] = gear[j+1];
				}
				gear[7] = temp;
			}
		}
	}

	/**
	 * 
	 * @return 문제의 조건대로 출력해야할 점수를 반환한다.
	 */
	static int socre(){
		int n = 1;
		int ret = 0;
		for(int i=0;i<4;i++){
			//12시 방향의 극이 S극이라면
			//1번기어 +1점 2번기어 +2점 3번기어 +4점 4번기어 +8점
			if(gears[i][0]==1) ret += n;
			n *=2;
		}
		return ret;
	}

}