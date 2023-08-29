package BaekJoon;

import java.io.*;
import java.util.*;

public class Main_bj_14891_톱니바퀴 {
	
	static public ArrayDeque<Integer>[] gear = new ArrayDeque[4];
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		for(int i =0;i<4;i++) {
			st = new StringTokenizer(br.readLine().trim());
			String tmp = st.nextToken();
			gear[i] = new ArrayDeque<Integer>();

			for(int j=0;j<8;j++) {
				gear[i].offerLast(tmp.charAt(j)-'0');
			}
			//양옆의 비교를 용이하게 하기위해 9시를 가장 앞 위치로, 3시를 가장 마지막으로 오게끔 큐 재배치
			gear[i].offerFirst(gear[i].pollLast());
			gear[i].offerFirst(gear[i].pollLast());
			
		}
		int K = Integer.parseInt(br.readLine().trim());
		for(int i=0;i<K;i++) {
			st = new StringTokenizer(br.readLine().trim()," ");
			int mg = Integer.parseInt(st.nextToken())-1;
			int direc = Integer.parseInt(st.nextToken());
			
			//움직이는 기어들 판단
			boolean[] move = new boolean[4];
			move[mg] = true;
			ArrayList<Object[]> tl = new ArrayList<>();
			for(int d=0;d<4;d++) {
				tl.add(gear[d].toArray());
			}
			
			//오른쪽 부터
			for(int d = mg+1; d<4;d++) {
				if(tl.get(d)[0] == tl.get(d-1)[4]) break;
				else move[d] = true;
			}
			//왼쪽 탐색
			for(int d= mg-1;d>=0; d--) {
				if(tl.get(d)[4] == tl.get(d+1)[0]) break;
				else move[d] = true;
			}
			
			//이제 움직여야할 톱니바퀴들을 확인했으니 이동!
			int origindirec = direc;
			//오른쪽부터 돌리기!
			for(int d = mg+1;d<4;d++) {
				direc = direc*-1;
				if(move[d] == false) break;
				else {
					//시계방향 회전
					if(direc ==1) {
						gear[d].offerFirst(gear[d].pollLast());
					}
					//반시계 방향 회전
					else if(direc == -1) {
						gear[d].offerLast(gear[d].pollFirst());
					}
				}
			}			
			//왼쪽 돌리기!
			direc = origindirec;
			for(int d= mg-1;d>=0; d--) {
				direc = direc*-1;
				if(move[d] == false) break;
				else {
					//시계방향 회전
					if(direc ==1) {
						gear[d].offerFirst(gear[d].pollLast());
					}
					//반시계 방향 회전
					else if(direc == -1) {
						gear[d].offerLast(gear[d].pollFirst());
					}
				}
			}
			//현재 톱니바퀴 돌리기
			direc = origindirec;
			if(direc ==1) {
				gear[mg].offerFirst(gear[mg].pollLast());
			}
			//반시계 방향 회전
			else if(direc == -1) {
				gear[mg].offerLast(gear[mg].pollFirst());
			}
		}
		
		//점수 계산
		int score = 0;
		ArrayList<Object[]> tl = new ArrayList<>();
		for(int d=0;d<4;d++) {
			tl.add(gear[d].toArray());
		}
		for(int i=0;i<4;i++) {
			if((int)tl.get(i)[2] == 1) {
				score += (int)Math.pow(2, i);
			}
		}
		System.out.println(score);
		br.close();
		
		//ArrayDeque에서는 특정위치 값을 뽑아낼 수 있는 방법이 없나?? 진짜 불편하기 그지 없다
	}

}
