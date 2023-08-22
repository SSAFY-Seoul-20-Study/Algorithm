package main;

import java.io.*;
import java.util.*;
public class Main_bj_14503_로봇청소기 {

	public static int N,M;
	public static int[] di = {-1,0,1,0}; // 상우하좌 
	public static int[] dj = {0,1,0,-1};
	public static int[][] room;
	public static int direction;
	public static int roboti,robotj;
	public static int ans;
	public static boolean[][] visited;
	public static int flag = 0;
	
	public static int checkaround() {
		int direc = -1;
		for(int i = 0;i<4;i++) {
			int direci = direction -i;
			if(direci< 0) {
				direci += 4; 
			}
			int ni = roboti +di[direci];
			int nj = robotj +dj[direci];
			if(ni>=0 && ni <N && nj>=0 &&nj<M && visited[ni][nj] == false && room[ni][nj] ==0) {
				direc = direci;
				return direc;
			}
		}
		return direc;
	}
	
	//1번 프로세스
	public static void process1() {
		if (visited[roboti][robotj] == false) {
			visited[roboti][robotj] = true;
			ans+=1;
		}
	}
	
	//2번 프로세스
	public static void process2() {
		int ni = roboti - di[direction];
		int nj = robotj - dj[direction];
		if(ni>=0 && ni<N && nj>0 && nj<M && room[ni][nj] == 0) {
			roboti = ni;
			robotj = nj;
		}
		else {
			System.out.println(ans);
			flag = 1;
		}
	}
	
	//3번 프로세스
	public static void process3() {
		direction = direction -1;
		if(direction<0) {
			direction = 4+direction;
		}
		int ni = roboti + di[direction];
		int nj = robotj + dj[direction];
		if(ni>=0 && ni <N && nj>=0 && nj <M && room[ni][nj] == 0 && visited[ni][nj] == false) {
			roboti = ni;
			robotj = nj;
		}
	}
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine().trim(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine().trim(), " ");
		roboti = Integer.parseInt(st.nextToken());
		robotj = Integer.parseInt(st.nextToken());
		direction = Integer.parseInt(st.nextToken());
		
		room = new int[N][M];
		visited = new boolean[N][M];
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine().trim()," ");
			for(int j =0; j<M;j++) {
				room[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		//입출력 처리 끝
		
		//flag를 통해 종료조건이 완료되었는지 확인
		//종료조건;2번 과정에서 더이상 뒤로 갈 수 없는 경우. 에서 flag를 1로 변경하여 로봇청소기 작동 정지 처리 
		while(flag ==0 ) {
			process1();
			if(checkaround()<0) {
				process2();
				continue;
			}
			else if(checkaround()>=0) {
				process3();
			}
		}
		
		
	}

}