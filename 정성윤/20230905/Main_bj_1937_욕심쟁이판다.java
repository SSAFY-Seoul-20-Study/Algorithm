package BaekJoon;

import java.io.*;
import java.util.*;

public class Main_bj_1937_욕심쟁이판다 {
	public static int N;
	public static int[][] field;
	public static int[] di = {-1,0,1,0}; //상우하좌
	public static int[] dj = {0,1,0,-1};
	public static int ans = 0;
	public static int[][] dpfield;
	
	//대나무의 크기 순으로 정렬하기 위한 priority queue.
	//new int { 대나무 크기, 대나무 위치 i, 대나무 j} 가 들어가서 크기,i,j 순으로 내림차순 정리한다
	//내림차순 정렬과 pq를 쓰는 이유는, 결국 이 문제에선 자기보다 큰 값이 주변에 있어야 한칸을 이동할 수 있기 때문
	//즉, 가장 큰 순대로 pq에서 뽑으면서 주변에 이미 자신보다 더 큰값이 존재하는지만 체크해주면 된다.
	//예를들어, 14라는 값이 poll 되면 4방으로 14보다 큰 값이 있는지 field에서 확인한다.
	//만약 있다면, 그 값중 dpfield의 값이 가장 큰 값에 +1을 한 값을 dpfield[i][j]에 저장한다
	//14가 저장될 칸 주변에 15와 16이 있는데 각각 dpfield의 값은 5 2 라면, 결국 14 칸에선 15가 있는 칸으로 가면 5+1 = 6칸을 이동가능하다는 뜻
	//만약 없다면, 결국 그 칸에서 더 이상 이동할 수 없다는 뜻이므로 그대로 1을 저장한다.
	//이런식으로 가장 큰 대나무부터 시작해서 가장 작은 대나무까지 pq를 poll 시키만 하면 해결!!!
	
	public static PriorityQueue<int[]> locate = new PriorityQueue<>(new Comparator<int[]>() {
		@Override
		public int compare(int[] o1, int[] o2) {
			if(o1[0] != o2[0]) {
				return -Integer.compare(o1[0], o2[0]);
			}
			else{
				if(o1[1] != o2[1]) {
					return -Integer.compare(o1[1], o2[1]);
				}
				else {
					if(o1[2]!= o2[2]) {
						return -Integer.compare(o1[2], o2[2]);
					}
				}
			}
			return 0;
		}
	});
	
	public static void findbig(int r, int c) {
		ans = Math.max(ans, dpfield[r][c]);
		//4방 탐색한 칸 중 dpfield가 가장 큰 값을 찾기 위한 pq
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for(int d=0;d<4;d++) {
			int ni = r + di[d];
			int nj = c + dj[d];
			if(ni>=0 && ni< N && nj>=0 && nj<N && field[ni][nj] > field[r][c] && dpfield[ni][nj] >= dpfield[r][c]) {
				pq.offer(dpfield[ni][nj]);
			}
			//만약 현재 field[r][c] 보다 더 큰 대나무가 근처에 있다면,
			//그중 dpfield(즉, 그 칸부터 이동할 수 있는 최대 수)에 1을 더한 값을 저장한다
			if(pq.size()>0) {
				dpfield[r][c] = pq.poll()+1;
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine().trim());
		
		//대나무의 값을 저장할 field
		field = new int[N][N];
		//현재 칸에서 몇칸을 이동할 수 있는지 저장하기 위한 배열
		//처음엔 모두 1로 초기화
		dpfield = new int[N][N];
		
		for(int i=0;i<N;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine().trim());
			for(int j=0;j<N;j++) {
				field[i][j] = Integer.parseInt(st.nextToken());
				dpfield[i][j] = 1;
				//대나무 크기 순으로 정렬되게끔 priority queued인 locate에 삽입
				locate.offer(new int[] {field[i][j],i,j});
			}
		}
		
		//locate에서 대나무 크기 내림차순으로 빼기
		while(!locate.isEmpty()) {
			int[] now = locate.poll();
			findbig(now[1],now[2]);
			ans = Math.max(ans,dpfield[now[1]][now[2]]);
		}

		System.out.println(ans);
	}
}