package study_algo;

import java.util.*;
import java.io.*;

// 15636KB	168ms
public class Main_boj_17471_게리맨더링 {

	static int N;// 구역 개수
	static int[] p; // 인구 수 배열
	static List<int[]> adj = new ArrayList<>(); // 인접한 구역 번호
	
	static int[] area_cnt = new int[2]; // 각 구역 인구수 중복조합
	static int[] area_v; // 한 구역에 해당되는 구역 번호
	static int[] area_v2; // 다른 구역에 해당하는 구역 번호
	static int res=Integer.MAX_VALUE;
	static boolean hasResult; // 두 선거구로 나눌 수 없는 경우 체킹을 위함
	
	public static void main(String[] args) throws Exception {
//		System.setIn(new FileInputStream("res/input_boj_17471.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 입력 받기
		N = Integer.parseInt(br.readLine());
		p = new int[N+1];
		
		st = new StringTokenizer(br.readLine());
		for(int i=1; i<=N; i++) {
			p[i] = Integer.parseInt(st.nextToken());
		}
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			int cnt = Integer.parseInt(st.nextToken());
			int[] tmp = new int[cnt];
			for(int c=0; c<cnt; c++) {
				tmp[c] = Integer.parseInt(st.nextToken());
			}
			adj.add(tmp);
		}

		
		// 구역 2개로 나누기
		/// 2구역의 인구 수를 cnt=(1~N/2)까지 한 구역의 인원수로 고정하면, 다른 구역은 자동으로 N-cnt.
		//// 각 구역의 인구 수 만큼 1번~N번의 조합을 구하고, 인접한지 확인하기
		///// 인접하다면 각 구역의 인구 수 구하기
		////// 구역 차의 최솟값 구하기
		
		for(int cnt=1; cnt<N/2+1; cnt++) {
			area_cnt[0] = cnt;
			area_cnt[1] = N-cnt;
			
			area_v = new int[cnt];
			hasResult = false;
			
			dfs(0, 1); // 한 구역번호 뽑기
		}
//		if(!hasResult) res = -1; // 두 구역으로 나눌 수 없는 경우
		if(res == Integer.MAX_VALUE) res = -1;
		
		System.out.println(res);
		br.close();
	}
	
	// 한 구역의 인원수 만큼, 구역번호 뽑기(조합)
	static void dfs(int cnt, int start) {
		if(cnt == area_cnt[0]) {
			area_v2 = find_another_v(area_v);
			if(isAdj(area_v) && isAdj(area_v2)) {
//				hasResult = true;
				int a = cal(area_v);
				int b = cal(area_v2);
				res = Math.min(res, Math.abs(a-b));
			}
			return;
		}
		for(int i=start; i<=N; i++) {
			area_v[cnt] = i;
			dfs(cnt+1, i+1);
		}
	}
	
	// 구역의 인구 수 구하기.
	static int cal(int[] a) {
		int tmp = 0;
		for(int i=0; i<a.length; i++) {
			tmp += p[a[i]];
		}
		return tmp;
	}
	
	// 한 구역에 속한 구역들이 인접한지 확인
	static boolean isAdj(int[] area) {
		if(area.length == 1) return true;

		boolean[] v = new boolean[N+1];
		Arrays.fill(v, true);
		for(int a:area) {
			v[a] = false;
		}
		Queue<Integer> q = new ArrayDeque<>();
		
		q.offer(area[0]);
		v[0] = true;
		
		while(!q.isEmpty()) {
			int cur = q.poll();
			
			for(int i=0; i<adj.get(cur-1).length; i++) {
				int next = adj.get(cur-1)[i];
				if(v[next]) continue;
				v[next] = true;
				q.offer(next);
			}
		}
		for(int i=1; i<=N; i++) {
			if(!v[i]) return false;
		}
		return true;
	}
	
	
	
	// 한 구역에 포함되지 않는 나머지 구역번호
	static int[] find_another_v(int[] a) {
		boolean[] v = new boolean[N+1];
		List<Integer> temp = new ArrayList<>();
		for(int i=0; i<a.length; i++) {
			v[a[i]] = true;
		}
		for(int i=1; i<=N; i++) {
			if(!v[i]) temp.add(i);
		}
		return temp.stream().mapToInt(Integer::intValue).toArray(); // List를 int[]로 변환
	}

}
