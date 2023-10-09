import java.util.*;
import java.io.*;

public class Main_bj_g3_26093_고양이_목에_리본_달기 {

	final static int INF = Integer.MAX_VALUE;
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		int[][] arr = new int[N][K];
		for(int i=0;i<N;i++){
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<K;j++){
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int x=1;x<N;x++){
			int first=0;
			int f_idx= -1;
			int second=0;
			for(int k=0;k<K;k++){
				int n = arr[x-1][k];
				if(n > first){
					second = first;
					first = n;
					f_idx = k;
				}
				else if(n > second){
					second = n;
				}
			}

			for(int k=0;k<K;k++){
				if(k!=f_idx){
					arr[x][k] += first;
				}
				else{
					arr[x][k]+= second;
				}
			}

		}

		int max = 0;
		for(int i=0;i<K;i++){
			max = Math.max(max, arr[N-1][i]);
		}

		System.out.println(max);

		br.close();

	}

}