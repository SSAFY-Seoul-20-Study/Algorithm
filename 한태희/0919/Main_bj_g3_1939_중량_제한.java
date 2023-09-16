import java.util.*;
import java.io.*;

public class Main_bj_g3_1939_중량_제한 {
	static int N, M;
	static int start, end;
	static ArrayList<int[]>[] list;// 그래프 연결 정보
	static boolean[] v;

	final static int INF = Integer.MAX_VALUE;

	static int ans;
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		list = new ArrayList[N + 1];

		int left=INF;
		int right=0;

		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int z = Integer.parseInt(st.nextToken());
			left = Math.min(left, z);
			right = Math.max(right, z);

			if(list[a]==null) list[a] = new ArrayList<>();
			list[a].add(new int[] {b, z});
			if(list[b]==null) list[b] = new ArrayList<>();
			list[b].add(new int[] {a, z});
		}

		st = new StringTokenizer(br.readLine());
		start = Integer.parseInt(st.nextToken());
		end = Integer.parseInt(st.nextToken());
		ans = 0;

		while(left <= right){
			int mid = (left + right)/2;
			v = new boolean[N + 1];
			if(check(mid)){
				left = mid + 1;
				ans = mid;
			}
			else{
				right = mid - 1;
			}
		}

		System.out.println(ans);

		br.close();
	}

	/**
	 * 크기가 k 이상인 다리들만 사용하여 start에서 end로 건너갈 수 있는지 확인한다.
	 */
	static boolean check(int k){
		v = new boolean[N + 1];
		v[start] = true;
		Queue<Integer> q = new ArrayDeque<>();
		q.offer(start);
		
		while(q.isEmpty()==false){
			int now = q.poll();
			ArrayList<int[]> edges = list[now];

			if(edges==null) continue;

			for(int info[]: edges){
				int next = info[0];
				int weight = info[1];

				if(v[next]==false && weight >= k){
					if(next==end){
						return true;
					}
					q.offer(next);
					v[next] = true;
				}
			}
		}

		return false;
	}

}