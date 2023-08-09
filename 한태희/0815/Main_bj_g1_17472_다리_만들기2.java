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
		
		int groupcount = 1;
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(arr[i][j]==1)
					makeGroup(i, j, ++groupcount);
			}
		}

		G = groupcount -1;
		a = new int[G]; b = new int[R];
		v = new boolean[G];
		for(int i=2;i<=groupcount;i++){
			a[i-2] = i; 
		}
		comb(0, 0);

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

		List<String> keys = new ArrayList<>(dict.keySet());
		Collections.sort(keys, (value1, value2) -> (dict.get(value1).compareTo(dict.get(value2))));
		int[] parent = new int[groupcount +1];
		int total=0;
		for(int i=0;i<groupcount+1;i++) parent[i] = i;
		for(String pair : keys){
			int g1 =  pair.charAt(0) -'0';
			int g2 = pair.charAt(1) -'0';
			if(parent[g1] != parent[g2]){
				if(parent[g2] > parent[g1])
					parent[g2] = parent[g1];
				else if(parent[g1] > parent[g2])
					parent[g1] = parent[g2];
			}
			
		}
		
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

}
