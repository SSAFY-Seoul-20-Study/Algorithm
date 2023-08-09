import java.io.*;
import java.util.*;

// 게리맨더링

public class BJ17471 {
	
	static int n;
	static int ans = Integer.MAX_VALUE;
	static boolean[] checkTeam;
	static boolean[] visited;
	static int[] population;				    // 각 노드의 인구
	static ArrayList<Integer>[] adj;		// 각 노드에 대해 인접 노드 저장
	static ArrayDeque<Integer> queue = new ArrayDeque<>();
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		
		checkTeam = new boolean[n + 1];
		visited = new boolean[n + 1];
		population = new int[n + 1];
		adj = new ArrayList[n + 1];
		for(int i = 1; i <= n; i++) {
			adj[i] = new ArrayList<>();
		}
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= n; i++) {
			population[i] = Integer.parseInt(st.nextToken());
		}
		
		for(int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			int num = Integer.parseInt(st.nextToken());
			for(int j = 0; j < num; j++) {
				adj[i].add(Integer.parseInt(st.nextToken()));
			}
		}

		makeTeam(1);
		
		if(ans == Integer.MAX_VALUE) ans = -1;
		System.out.println(ans);
	}
	
	private static void makeTeam(int idx) {
		if(idx == n + 1) {
			if(isConnected(true) && isConnected(false)) {		// 나눈 두 팀이 각각 연결되어 있다면
				calculateEachPopulation();						        // 각 팀의 인구 계산하기
			}
			return;
		}
		
		checkTeam[idx] = true;
		makeTeam(idx + 1);
		
		checkTeam[idx] = false;
		makeTeam(idx + 1);
	}
	
	private static boolean isConnected(boolean flag) {		// flag 팀의 노드들이 서로 연결되었는지 판단
		
		// 변수 초기화
		Arrays.fill(visited, false);
		queue.clear();
		
		// 탐색을 시작할 노드 찾기
		for(int i = 1; i <= n; i++) {
			if(checkTeam[i] == flag) {
				visited[i] = true;
				queue.add(i);
				break;
			}
		}
		
		// flag 팀의 노드가 존재하지 않은 경우 (즉, 두 개의 팀으로 나뉘지 않은 경우)
		if(queue.size() == 0) return false;					
		
		// 연결된 노드 중 flag 팀에 대하여 bfs 탐색하여 방문 처리하기
		while(!queue.isEmpty()) {
			int poll = queue.poll();
			
			for(int i = 0; i < adj[poll].size(); i++) {
				int target = adj[poll].get(i);
				if(checkTeam[target] == flag && !visited[target]) {
					visited[target] = true;
					queue.add(target);
				}
			}
		}
		
		// flag 팀에 대하여 방문 처리가 되지 않은 노드가 있는지 판단 (즉, flag 팀의 노드들이 모두 연결되지 않음)
		for(int i = 1; i <= n; i++) {
			if(checkTeam[i] == flag && !visited[i]) return false;
		}
		
		return true;
	}
	
	private static void calculateEachPopulation() {
		
		int totalTrueTeam = 0;
		int totalFalseTeam = 0;
		
		for(int i = 1; i <= n; i++) {
			if(checkTeam[i]) totalTrueTeam += population[i];
			else totalFalseTeam += population[i];
		}
		
		
		// 최솟값 ans 업데이트
		int diff = Math.abs(totalTrueTeam - totalFalseTeam);
		ans = Integer.min(ans, diff);
	}

}
