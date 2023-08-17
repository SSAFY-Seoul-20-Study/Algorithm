import java.util.*;
import java.io.*;

public class Main_bj_g5_2771_로봇_청소기_서울_20반_한태희 {
	static int[][] arr; //0 더러움 2 깨끗함 1 벽
	static int N, M;
	static int[] robot = new int[3];

	static int[] dr = {-1, 0, 1, 0}; //북, 동, 남, 서
	static int[] dc = {0, 1, 0, -1};

	static int cnt =0;

	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N][M];

		st = new StringTokenizer(br.readLine());
		for(int i=0;i<3;i++)
			robot[i] = Integer.parseInt(st.nextToken());
		
		for(int i=0;i<N;i++){
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++)
				arr[i][j] = Integer.parseInt(st.nextToken());
		}

		doWork();

		System.out.println(cnt);
		br.close();
	}

	static void doWork(){
		work : while(true){
			//현재 칸이 아직 청소되지 않은 경우, 현재 칸을 청소한다.
			if(arr[robot[0]][robot[1]] == 0){
				arr[robot[0]][robot[1]] = 2;
				cnt ++;
				continue work;
			}
			//현재 칸의 주변 4칸 중 청소되지 않은 빈 칸이 없는 경우
			if(!needClean4way()){
				//바라보는 방향을 유지한 채로 한 칸 후진할 수 있다면 
				//한 칸 후진하고 1번으로 돌아간다.
				int back_d = (robot[2] + 2) % 4;
				int back_r = robot[0] + dr[back_d];
				int back_c = robot[1] + dc[back_d];
				if(arr[back_r][back_c] != 1){
					robot[0] = back_r;
					robot[1] = back_c;
					continue work;
				}
				//바라보는 방향의 뒤쪽 칸이 벽이라 후진할 수 없다면 작동을 멈춘다.
				else{
					break work;
				}
			}
			//현재 칸의 주변 4칸 중 청소되지 않은 빈 칸이 있는 경우,
			else{
				// 반시계 방향으로 90도 회전한다.
				robot[2] = (robot[2] + 3) % 4;
				// 바라보는 방향을 기준으로 앞쪽 칸이 청소되지 않은 
				//빈 칸인 경우 한 칸 전진한다.
				int next_r = robot[0] + dr[robot[2]];
				int next_c = robot[1] + dc[robot[2]];
				if(arr[next_r][next_c] ==0){
					robot[0] = next_r;
					robot[1] = next_c;
				}
				//1번으로 돌아간다.
				continue work;
			}
		}
	}

	static boolean needClean4way(){
		for(int d=0;d<4;d++){
			int r = robot[0] + dr[d];
			int c = robot[1] + dc[d];
			if(arr[r][c] ==0) return true;
		}
		return false;
	}

}