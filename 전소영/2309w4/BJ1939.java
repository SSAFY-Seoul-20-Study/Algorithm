import java.io.*;
import java.util.*;

// 중량제한

public class BJ1939 {

	static class Bridge {
		
		int to;
		int weight;
		Bridge next;
		
		Bridge(int to, int weight, Bridge next) {
			this.to = to;
			this.weight = weight;
			this.next = next;
		}
	}
	static int n;
	static int start, end;
	static boolean[] visited;
	static Bridge[] adj;
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		adj = new Bridge[n];
		int m = Integer.parseInt(st.nextToken());
		
		int min = Integer.MAX_VALUE;
		int max = 0;
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken());
			
			min = Integer.min(min, c);
			max = Integer.max(max, c);
			
			adj[a] = new Bridge(b, c, adj[a]);
			adj[b] = new Bridge(a, c, adj[b]);
		}
		
		st = new StringTokenizer(br.readLine());
		start = Integer.parseInt(st.nextToken()) - 1;
		end = Integer.parseInt(st.nextToken()) - 1;
		
		int ans = 0;
		while(min <= max) {
			int mid = (min + max) / 2;
			
			if(bfs(mid)) {
				ans = mid;
				min = mid + 1;
			}
			else max = mid - 1;
		}
		System.out.println(ans);
	}
	
	private static boolean bfs(int target) {
		
		ArrayDeque<Integer> queue = new ArrayDeque<>();
		visited = new boolean[n];
		visited[start] = true;
		queue.add(start);
		
		while(!queue.isEmpty()) {
			int poll = queue.poll();
			if(poll == end) return true;
			
			for(Bridge bridge = adj[poll]; bridge != null; bridge = bridge.next) {
				if(visited[bridge.to] || target > bridge.weight) continue;
				visited[bridge.to] = true;
				queue.add(bridge.to);
			}
		}
		
		return false;
	}
}
