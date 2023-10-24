package main;

import java.io.*;
import java.util.*;

public class Main_BJ_26093_고양이목에방울달기 {
	
	public static int[][] catscore;
	public static int N,K;
	public static int[] dp;
	
	public static PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
		@Override
		public int compare(int[] o1, int[] o2) {
			return -Integer.compare(o1[0], o2[0]);
		};
	});
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		catscore = new int[N][K];
		dp = new int[K];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<K;j++) {
				catscore[i][j] = Integer.parseInt(st.nextToken());
			}			
		}
		
		for(int j=0;j<K;j++) {
			dp[j] = catscore[0][j];
		}
		
		//Priority Queue로 바로 전 칸의 dp를 저장해가며 최대 값을 구한다
		//즉, pq에 int[]로 {지금까지의 최대값, j index} 를 넣어둔다음, 최대값을 기준으로 pq로 정렬시키고 만약 j값이 같다면 하나 더 뽑는다.
		//근데 왜 Arrays.stream(int[]).max().getAsInt() 를 사용하면 시간초과가 나는지 모르겠다
		for(int i=1;i<N;i++) {
			pq.clear();
			for(int j=0;j<K;j++) {
				pq.offer(new int[] {catscore[i-1][j],j});
			}
			for(int j=0;j<K;j++) {
				int[] tmp = pq.poll();
				if(tmp[1] == j) {
					int[] tmp2 = pq.poll();
					catscore[i][j] += tmp2[0];
					pq.offer(tmp2);
				}
				else {
					catscore[i][j] += tmp[0];
				}
				pq.offer(tmp);
			}
		}

		System.out.println(Arrays.stream(catscore[N-1]).max().getAsInt());
	}

}
