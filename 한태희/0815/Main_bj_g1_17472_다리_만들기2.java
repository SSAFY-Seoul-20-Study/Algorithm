import java.io.*;
import java.util.*;

public class Main_bj_g1_17472_다리_만들기2 {
	final static int[] di = {1, 0, -1, 0};
	final static int[] dj = {0, 1, 0, -1};
	static int N;
	static int M;
	static int arr[][];

	static int G, R=2;
	static int[] a, b;
	static boolean[] v;

	static Map<String, Integer> dict = new HashMap<>();

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		arr = new int[N][M];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		//DFS 탐색을 통하여 붙어있는 1을 같은 그룹으로 넘버링한다.
		//0 바다, 1은 땅을 의미하므로
		//!!! 편의를 위해 그룹은 2번부터 시작하도록 구현했음에 유의!
		int groupcount = 1;
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(arr[i][j]==1)
					makeGroup(i, j, ++groupcount);
			}
		}

		//Combination gC2 (g는 그룹 개수) 를 통해서
		//dict (HashMap)을 초기화한다.
		//dict엔 String 형태로 "g1+g2" 형태의 문자열이 key로 사용된다.
		//예를 들어 key가 "23" 이라면, 2번 그룹과 3번 그룹의 관계라는 의미이다.
		//value는 각 섬 사이에 놓을 수 있는 최소 크기 다리를 의미한다.
		//value의 초기값은 999999999로, 이것을 무한대로 간주한다.
		G = groupcount -1;
		a = new int[G]; b = new int[R];
		v = new boolean[G];
		for(int i=2;i<=groupcount;i++){
			a[i-2] = i; 
		}
		comb(0, 0);

		//지도의 임의의 좌표 i, j를 순차적으로 탐색하면서,
		//만약 오른쪽이나 아래쪽 직선으로 다리를 연결 가능한 또다른 섬이 있다면,
		//다리의 길이를 구한다.
		//그리고 그 다리의 길이가 기존에 찾았던 최솟값보다 더 짧을경우
		//dict에 저장된 섬 사이의 최소 다리 길이를 업데이트 한다.
		for(int i=0;i<N;i++){
			for(int j=0;j<M;j++) {
				if(arr[i][j] !=0){
					for(int k=0;k<2;k++){
						int row=i+di[k], col=j+dj[k];
						int startGroup = arr[i][j];
						while(isValid(row, col) && (arr[row][col]==0)){
							row=row+di[k]; col=col+dj[k];
						}
						if(isValid(row, col) && arr[row][col] != startGroup){
							int endGroup = arr[row][col];
							if(endGroup < startGroup){
								int temp = endGroup;
								endGroup = startGroup;
								startGroup = temp;
							}
							int brigeLength = Math.abs(row - i + col - j) - 1;
							String key = startGroup +""+ endGroup;
							if(brigeLength >=2)
								dict.put(key, Math.min(dict.get(key), brigeLength));
						}
					}
				}
			}
		}

		//위의 작업이 모두 완료되었다면, dict에는 각 섬 사이에 연결 가능한
		//다리의 최소 길이가 저장된다.
		//크루스칼 알고리즘을 이용하여 최소 신장 트리를 구한다.
		//(크루스칼 알고리즘 구현 방식은 인터넷을 참조했음)
		//가장 짧은 길이의 다리부터 탐색하여, 사이클이 형성되지 않는 조건에서
		//섬들을 연결하여, 모든 섬들이 연결될때까지 반복한다.
		//최소 신장 트리가 완성되면, 다리 길이를 가장 짧게 사용하여
		//모든 섬을 연결시킨 것이다.
		List<String> keys = new ArrayList<>(dict.keySet());
		Collections.sort(keys, (value1, value2) -> (dict.get(value1).compareTo(dict.get(value2))));
		int[] parent = new int[groupcount +1];
		for(int i=0;i<groupcount+1;i++) parent[i] = i;
		int total=0;

		for(String pair : keys){
			if(!hasSameParent(parent) && dict.get(pair) != 999999999){
				int g1 =  pair.charAt(0) -'0';
				int g2 = pair.charAt(1) -'0';

				if(parent[g1] != parent[g2]){
					if(parent[g2] > parent[g1])
						changeParent(parent, parent[g2], parent[g1]);
					else if(parent[g1] > parent[g2])
						changeParent(parent, parent[g1], parent[g2]);
					total += dict.get(pair);
				}
			}
		}

		//모든 섬의 부모가 같으면 모든 섬이 연결된 것이므로
		//다리 길이의 총 합을 출력한다.
		//아니라면, 연결되지 않은 섬이 있는 것이므로 -1을 출력한다.
		if(hasSameParent(parent))
			System.out.println(total);
		else
			System.out.println(-1);
		
	}
	
	static void makeGroup(int row, int col, int g) {
		arr[row][col] = g;
		for(int k=0;k<4;k++) {
			int i = row + di[k];
			int j = col + dj[k];
			if(0<=i && i<N && 0<=j && j<M && arr[i][j]==1)
				makeGroup(i, j, g);
		}
	}
	
	static void comb(int cnt, int start){
		if(cnt ==R){
			dict.put(b[0]+""+b[1], 999999999);
			return;
		}
		for(int i=start;i<G;i++){
			if(v[i]) continue;
			v[i] = true;
			b[cnt] = a[i];
			comb(cnt+1, i+1);
			v[i] = false;
		}
	}

	static boolean isValid(int r, int c){
		if(0<=r && r<N && 0<=c && c<M)
			return true;
		else
			return false;
	}

	static boolean hasSameParent(int[] parent){
		int p = parent[2];
		for(int i=2;i<parent.length;i++){
			if(parent[i] != p) return false;
		}
		return true;
	}

	static void changeParent(int[] parent, int tar, int res){
		for(int i=2;i<parent.length;i++){
			if(parent[i] == tar) parent[i] = res;
		}
	}

}
