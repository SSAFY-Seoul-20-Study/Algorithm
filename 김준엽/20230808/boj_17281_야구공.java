package study;

import java.util.*;
import java.io.*;
public class Main_17281 {
	static int N;
	static int [][] sunsu;
	static int []likeSunsu;
	static int [] dummy = new int[8];
	static int prevHitter = 0;
	public static void main(String[] args) throws Exception{
		System.setIn(new FileInputStream("study/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		likeSunsu = new int[N];
		sunsu = new int [N][8];
		//입력
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			likeSunsu[i] = Integer.parseInt(st.nextToken());
			for(int j=0; j<8; j++) sunsu[i][j] = Integer.parseInt(st.nextToken());
		}
		
		for(int i =0; i<N; i++) {
			comb(0,0,i);
		}
		
		
	}
	static void comb(int depth , int start, int inning) {
		if(depth == 8) {
			System.out.println(Arrays.toString(dummy));
			return;
		}
		for(int i=start; i<8; i++) {
			dummy[depth] = sunsu[inning][i];
			comb(depth+1, start+1, inning);
		}
	}
	static void simulation() {
		//prevHitter부터 시작 + 9번타자 이후에 %를 이용하여 0번 타자로 시작하는 무한반복문
		int outCount = 0;
		int score = 0;
		
	}
	
}
