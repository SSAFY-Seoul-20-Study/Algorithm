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
		for(int[] l:perms) {
			int point=0;
			int i=0;
			int player = l[i];
			for(int inning=0;inning<E;inning++) {
				int out = 0;
				int [] bases = new int[4]; //주자, 1루, 2루, 3루
				while(out <3) {
					bases[0] = 1;
					int swing = arr[player][inning];
					if(swing==0) {bases[0]=0;  out++;}
					else point += movePlayers(bases, swing);
					
					i++;
					if(i==9) i =0;
					player = l[i];
				}
				
			}
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
				if(ci==3) {
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
