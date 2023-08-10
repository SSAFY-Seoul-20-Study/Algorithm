import java.io.*;
import java.util.*;

public class Main_bj_g5_17471_게리맨더링 {

	static int N;
	static boolean[] v;

	static int[] val;
	static Map<Integer, Set<Integer>> dict = new HashMap<>();
	static int min =999999999;

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		val = new int[N+1];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=1;i<N+1;i++)
			val[i] = Integer.parseInt(st.nextToken());
		
		//dict(node) = Set{node1, ndoe2, node3...}
		//형식으로 그래프를 표현함
		for(int i=1;i<N+1;i++){
			st = new StringTokenizer(br.readLine());
			int W = Integer.parseInt(st.nextToken());
			Set<Integer> set = new HashSet<>();
			for(int j=0;j<W;j++){
				set.add(Integer.parseInt(st.nextToken()));
			}
			dict.put(i, set);
		}
		v = new boolean[N];
		subs(0);

		if(min==999999999) min=-1;
		System.out.println(min);
		
	}

	static void subs(int cnt){
		if(cnt==N){
			//모든 부분집합을 탐색한다. 똑같은 경우를 두번 탐색하게되지만,
			//2^10 = 1024개 경우가 최대임으로 시간은 충분하다고 판단.

			//부분집합 결과를 이용해서, set으로 표현되는 두개의 구역을 구한다.
			//이 두개의 구역은 연결되어있을 수도 있고,
			//연결되어있지 않을 수도 있다.
			Set<Integer> target_set1 = new HashSet<>();
			Set<Integer> target_set2 = new HashSet<>();
			int set1_start = -1;
			int set2_start = -1;
			for(int i=0;i<N;i++){
				if(v[i]){
					if(set1_start==-1) set1_start = i+1;
					target_set1.add(i+1);
				}
				if(!v[i]){
					if(set2_start==-1) set2_start = i+1;
					target_set2.add(i+1);
				}
			}
			if(set1_start==-1 && set2_start==-1) return;

			//current_set을 새로 만들어서, target_set속 임의의 점 set_start를 넣은다음,
			//BFS를 이용해서 set_start 부터 시작하여 탐색을 한다.
			//다음 노드로의 연결이 존재하고, 다음 노드가 아직 current set에 없다면,
			//current set과 queue에 다음 노드를 넣는 방식으로 동작한다.
			Set<Integer> current_set = new HashSet<>();
			Queue<Integer> que = new ArrayDeque<>();
			current_set.add(set1_start);
			que.add(set1_start);

			while(!que.isEmpty()){
				int node = que.poll();
				Set<Integer> nextSet = dict.get(node);
				if(nextSet !=null){
					for(int next: nextSet){
						if(!current_set.contains(next) && target_set1.contains(next)){
							current_set.add(next);
							que.add(next);
						}
					}
				}
			}

			//탐색 완료 후의 current set이 target set이 동일하지 않으면,
			//해당 구역이 연결되어 있지 않다는 것이므로, 해당 경우엔 계산을 하지 않는다.

			if(!current_set.equals(target_set1))
				return;
			
			//두번째 구역 계산
			//함수로 모듈화해 깔끔하게 만들 수 있을것 같긴 한데
			//귀찮음으로 막구현~~
			current_set = new HashSet<>();
			que = new ArrayDeque<>();
			current_set.add(set2_start);
			que.add(set2_start);

			while(!que.isEmpty()){
				int node = que.poll();
				Set<Integer> nextSet = dict.get(node);
				if(nextSet !=null){
					for(int next: nextSet){
						if(!current_set.contains(next) && target_set2.contains(next)){
							current_set.add(next);
							que.add(next);
						}
					}
				}
			}

			if(!current_set.equals(target_set2))
				return;


			//두 구역이 모두 연결되어 있다면, 문제에서 주어진 조건을 만족하는 것이므로,
			//첫번째 구역의 합 A, 두번째 구역의 합 B를 구해서
			//그 차이가 min보다 작다면 min을 새롭게 구한 차이로 업데이트한다.
			//이를 통해서 모든 구역의 부분집합 가능성을 탐색해서
			//그중에서 인구의 차이가 가장 적은 경우를 알 수 있다.
			int A =0;
			for(int node: target_set1){
				A += val[node];
			}
			int B =0;
			for(int node: target_set2){
				B += val[node];
			}

			min = Math.min(min, Math.abs(A-B));

			return;
		}
		v[cnt] = true;
		subs(cnt+1);
		v[cnt] = false;
		subs(cnt+1);
	}


}
