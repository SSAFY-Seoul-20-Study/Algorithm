package main;

import java.io.*;
import java.util.*;

public class Main_bj_19236_청소년상어 {
	public static HashMap<Integer,int[]> fishmap = new HashMap<Integer,int[]>();
	public static int[] shark = new int[] {0,0,0};
	//1번칸 부터 상 상좌 좌 하좌 하 하우 우 상우 순으로 설정
	public static int[] di = {0,-1,-1, 0, 1,1,1,0,-1};
	public static int[] dj = {0, 0,-1,-1,-1,0,1,1, 1};
	
	public static int N = 4;
	public static int[][][] sea = new int[N][N][2];
	
	public static int ans=0;
	
	//상어가 현재 위치의 물고기를 식사 후 0으로 변환.
	//만들긴 했는데 사실 딥카피를 굳이 이렇게 해야하나 모르겠다...?
	//시간복잡도 안터지니 뭐 큰 문제는 없을듯.
	public static int[][][] aftersharkeat(int[][][] beforesea) {
		int[][][] aftersea = new int[N][N][2];
		
		for(int i =0; i<4;i++) {
			for(int j=0;j<4;j++) {
				if(i==shark[0] && j==shark[1]) {
					aftersea[i][j][0] = 0;
					aftersea[i][j][0] = 0;
				}
				else {
					aftersea[i][j][0] = beforesea[i][j][0];
					aftersea[i][j][1] = beforesea[i][j][1];
				}
			}
		}
		return aftersea;
	}
	
	// 물고기의 방향을 수정하는 함수
	public static int changedirection(int r, int c, int d) {
		int direction = 0;
		
		//8방 탐색 시작
		for(int i=0; i<8;i++) {
			int test = d+i;
			//반시계방향으로 계속 돌아야 하기에, 만약 direction 인덱스가 8을 벗어난다면 1번으로 다시 이동하게끔 설정
			if(test>8) {
				test = test -8;
			}
			int ni = r + di[test];
			int nj = c + dj[test];
			//바다 범위를 벗어나지 않고 해당칸에 상어가 없다면
			if(ni>=0 && ni<N && nj>=0 && nj<N && (ni!=shark[0] || nj!=shark[1])) {
				direction = test;
				break;
			}
		}
		//만약 원 방향에서 이동가능하다면 바로 원 방향 값을 반환
		return direction;
	}
	
	//물고기들의 이동
	public static int[][][] changesea(int[][][] beforesea) {
		int[][][] aftersea = new int[N][N][2];
		
		//물고기들의 위치와 방향을 저장할 HashMap 생성
		//물고기들이 순서대로 이동해야하기에 이 같은 방법 사용.
		//PriorityQueue로도 구현 가능할것 같지만, compareTo를 오버라이딩 해줘야해서 귀찮으므로 이 방법 사용
		HashMap<Integer,int[]> fishmap = new HashMap<Integer,int[]>();
		for(int i = 0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(beforesea[i][j][0] != 0) {
					fishmap.put(beforesea[i][j][0], new int[] {i,j,beforesea[i][j][1]});
				}
			}
		}
		//aftersea로 beforesea 딥카피
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				aftersea[i][j][0] = beforesea[i][j][0];
				aftersea[i][j][1] = beforesea[i][j][1];
			}
		}
		
		//1번부터 16번까지의 물고기를 순서대로 이동
		for(int i=1;i<=16;i++) {
			int[] tmp = fishmap.get(i);
			//만약 i 물고기가 hashmap에 존재한다면 = 아직 안먹혔다면, 순서대로 방향별 이동 시작
			if(tmp != null) {
				
				//이동할 방향 설정
				//만약 바로 이동 가능하다면 기존의 방향(tmp[2])가 그대로 사용되게 구현
				int nextdirection = changedirection(tmp[0],tmp[1],tmp[2]);
				
				//움직일 수 있는 방향이 있다면
				if(nextdirection > 0) {
					int ni = tmp[0] + di[nextdirection];
					int nj = tmp[1] + dj[nextdirection];
					//이동할 위치의 물고기 번호화 방향 임시값에미리 저장
					int changeloca = aftersea[ni][nj][0];
					int chgdirec = aftersea[ni][nj][1];
					
					//현재위치 물고기와 이동할 위치의 물고기 정보를 hashmap에서 수정
					fishmap.put(changeloca, new int[] {tmp[0],tmp[1],chgdirec});
					fishmap.put(i, new int[] {ni,nj,nextdirection});
					
					//각 칸의 물고기 넘버와 방향 바꾸기
					aftersea[ni][nj][0] = i;
					aftersea[ni][nj][1] = nextdirection;
					aftersea[tmp[0]][tmp[1]][0] = changeloca;
					aftersea[tmp[0]][tmp[1]][1] = chgdirec;
			
				}
			}
		}
		//이동이 모두 완료된 aftersea 어레이를 반환
		return aftersea;
	}
	
	//디버깅용 현재 바다 출력
	public static void printsea(int[][][] ps) {
		StringBuilder sb = new StringBuilder();
		for(int i =0;i<N;i++) {
			for(int j = 0;j<N;j++) {
				sb.append("[").append(ps[i][j][0]).append(",").append(ps[i][j][1]).append("]").append(" ");
			}
			sb.append("\n");
		}
		sb.append("--------------------------------------");
		System.out.println(sb);
	}
	
	public static void game(int[][][] bs, ArrayList<int[]> sharkij,int eat) {
		//상어가 이동할 수 있는 곳이 더 이상 없다면 현재까지 먹은 eat 과 ans중 큰 값을 ans로 저장
		if(sharkij.size() == 0) {
			ans = Math.max(ans, eat);
			return;
		}
		//상어가 이동 가능한 위치만큼 반복 진행
		for(int i=0;i<sharkij.size();i++) {
			//다음 이동 위치 추출
			int[] hubo = sharkij.get(i);
			shark[0] = hubo[0];
			shark[1] = hubo[1];
			shark[2] = bs[hubo[0]][hubo[1]][1];
			
			//이동한 위치에서 물고기 식사
			int[][][] aftereat = aftersharkeat(bs);
			//식사 후 물고기들 이동
			int[][][] aftersea = changesea(aftereat);
			//상어가 이동 가능한 후보 자리들을 nextshark() 로 추출한 뒤 먹은 물고기를 eat 에 더해주고 재귀!
			game(aftersea,nextshark(aftersea),eat+bs[hubo[0]][hubo[1]][0]);
		}
	}
	
	public static ArrayList<int[]> nextshark(int[][][] bs){
		ArrayList<int[]> nxt = new ArrayList<>();
		int ni = shark[0] + di[shark[2]];
		int nj = shark[1] + dj[shark[2]];
		
		while(ni>=0 && ni<N && nj>=0 && nj <N) {
			if(bs[ni][nj][0] > 0) {
				nxt.add(new int[] {ni,nj});
			}
			ni = ni + di[shark[2]];
			nj = nj + dj[shark[2]];
		}
		return nxt;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		for(int i = 0; i<4;i++) {
			st = new StringTokenizer(br.readLine().trim()," ");
			for(int j = 0; j<4;j++) {
				int[] tmp = new int[2];
				//물고기 넘버
				tmp[0] = Integer.parseInt(st.nextToken());
				//물고기 방향
				tmp[1] = Integer.parseInt(st.nextToken());
				//HashMap 에 넣을 물고기 정보: {i,j,방향}
				sea[i][j][0] = tmp[0];
				sea[i][j][1] = tmp[1];
			}
		}
		
		ArrayList<int[]> initlist = new ArrayList<>();
		//상어의 첫 위치 (0,0)로 상어 투입!
		initlist.add(new int[] {0,0});
		//게임 시작!
		game(sea,initlist,0);
		System.out.println(ans);
		br.close();
	}

}