package selfPractice.backjoon;

import java.util.*;
import java.io.*;

public class Main_backjoon_17281_야구 {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int E = Integer.parseInt(br.readLine());
		int [][] arr = new int[9][E];
		
		for(int e=0;e<E;e++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int p=0;p<9;p++) {
				arr[p][e] = Integer.parseInt(st.nextToken());
			}
		}
		
		perm(0);
		
		int max_point = 0;
		for(int[] l:perms) { //모든 순열의 경우의 수에 대해서 탐색

			//점수, 플레이어 초기화
			int point=0;
			int i=0;
			int player = l[i];

			//입력된 이닝만큼 반복
			for(int inning=0;inning<E;inning++) {

				//아웃카운트 초기화, 게임 경기장 초기화
				int out = 0;
				int [] bases = new int[4]; //주자, 1루, 2루, 3루

				while(out <3) { //3아웃까지 1이닝을 진행
					
					//타자가 베이스에 선다.
					bases[0] = 1;
					// 타자가 해당 이닝에 만드는 스윙 정보를 구한다.
					int swing = arr[player][inning];
					//스윙이 0이라면 아웃. 타자가 베이스에서 내려가고 아웃카운트 추가.
					if(swing==0) {bases[0]=0;  out++;}
					//스윙이 1 이상이라면 타자와 주자들이 모두 그 스윙 점수만큼 진루.
					//1-1루타 ~ 3-3루타, 4-> 홈런 (4루타와 동일)
					else point += movePlayers(bases, swing);
					
					//타순이 한칸 진행되어 다음 타자 준비
					i++;
					if(i==9) i =0;
					player = l[i];
				}
			}
			//만약 해당 순열 경우의 수가 가장 최대 점수를 얻었다면 최대 점수 업데이트.
			max_point = Math.max(point, max_point);
		}
		//for(int [] www:perms) System.out.println(Arrays.toString(www));
		System.out.println(max_point);

	}
	
	private static int movePlayers(int[] bases, int swing) {
		int point = 0;
		for(int i=3;i>=0;i--) {
			if(bases[i] ==1) {
				bases[i]=0;
				if(i+swing>=4)  point++;
				else  bases[i+swing]=1;
			}
		}
		return point;
	}

	static int N=8, R=8;
	static int[] a = {1, 2, 3, 4, 5, 6, 7, 8 };
	static boolean [] v = new boolean[N];
	static int[] b = new int[R];
	static List<int []> perms = new ArrayList<>();
	
	static void perm(int cnt) {
		if(cnt==R) {
			int [] c = new int[9];
			int bi=0, ci=0;
			while(ci<9) {
				if(ci==3) { //4번타자 (순열의 3번 인덱스) 에 1번타자 (입력의 0번 인덱스) 를 넣기 위한 작업
					c[ci] = 0;
				}
				else {
					c[ci] = b[bi];
					bi ++;
				}	
				ci++;	
			}
			perms.add(c);
			return;
		}
		for(int i=0;i<N;i++) {
			if(v[i]) continue;
			v[i] = true;
			b[cnt] = a[i];
			perm(cnt+1);
			v[i] = false;
		}
		
	}
	
	
	

}
