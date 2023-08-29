import java.util.*;
import java.io.*;

/**
 * 문제에 대한 조건 설명이 애매모호해서 언뜻 보면 탐색 문제처럼 보일 수 있다.
 * (계단이 물리적으로 존재해서 겹쳐 놓을 수 없을 것 같다는 착각을 일으킴.)
 * 하지만, 지문을 잘 보면, 길이 완성되는 구간의 최대 혹은 최소를 구하라는 언급도 없고,
 * 테스트 케이스를 분석해보면 각 길을 독립적으로 분석해서 길을 이용해 횡단이 가능한지
 * 확인하는 문제임을 알 수 있다. 각 행과 열은 상호간 영향을 주지 않는다.
 * ->따라서 단순 구현문제
 * 
 * 전략: 하나의 행 혹은 열을 {{a, k1}, {b, k2}, ... ,{d, k4}}로 표현한다.
 * a, b, ..., d는 배열의 값 그대로인 높이를 나타내며, k는 해당 높이가
 * 연속으로 뭉쳐있는 구간의 크기를 나타낸다.
 */
public class Main_bj_g3_14890_경사로 {
	static int[][] arr; //문제에서 주어진 값들
	static int N, L;

	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		arr = new int[N][N];

		for(int i=0;i<N;i++){
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++){
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int res =0;

		//각 행과 열에 대해서 횡단이 가능한지 검사한 후, 가능하다면 res를 1 증가시킨다.
		for(int i=0;i<N;i++){
			if(check(i, 0, 0, 1)){
				res++;
			}
		}
		for(int j=0;j<N;j++){
			if(check(0, j, 1, 0)){
				res++;
			}
		}

		System.out.println(res);

		br.close();
	}

	/**
	 * 위에서 말한 전략대로 새로운 자료 형태를 만든 다음,
	 * W개의 뭉텅이가 생겼다면, (0, 1) 페어부터 (W-2) (W-1) 페어까지를 검사한다.
	 * 만약 붙어있는 두개의 뭉텅이가 2 이상의 고저차를 가졌다면, 
	 * 다리를 이용해 횡단이 불가능 하므로 불가능을 리턴.
	 * 붙어있는 두개의 뭉텅이가 1의 고저차를 가졌으면서 낮은 쪽의 크기가 L 이상이면 
	 * 낮은 쪽의 크기를 L만큼 빼고 다리가 연결되었다고 친다.
	 * (원본 배열엔 영향을 주지 않음)
	 * 이런 방식으로 횡단이 가능하면 가능을 리턴한다.
	 * @param r 배열의 시작 row
	 * @param c 배열의 시작 col
	 * @param dr 탐색 row 방향
	 * @param dc 탐색 col 방향
	 * @return true: 횡단 가능 false: 횡단 불가능
	 */
	static boolean check(int r, int c, int dr, int dc){
		// 해당 줄의 뭉텅이 정보를 2칸 정수가 들어있는 list를 통해 표현한다.
		// {a, b} a:높이 b:뭉텅이 크기
		List<int[]> list = new ArrayList<>();
		list.add(new int[]{arr[r][c], 1});
		r = r + dr;
		c = c + dc;

		//list 생성
		while(isValid(r, c)){
			int l = list.size()-1;
			if(list.get(l)[0]==arr[r][c]){
				list.get(l)[1] ++;
			}
			else{
				list.add(new int[]{arr[r][c], 1});
			}
			r = r + dr;
			c = c + dc;
		}

		//list를 통해서 뭉텅이 검사
		for(int i=0;i<list.size()-1;i++){
			int[] small, big;

			int[] now = list.get(i);
			int[] next = list.get(i+1);

			if(now[0] > next[0]) {big = now; small = next;}
			else {big = next; small = now;}

			if(big[0] - small[0] >1) return false;

			if(small[1] >= L){
				small[1] -=L;
			}
			else{
				return false;
			}
		}
		return true;
	}


	static boolean isValid(int r, int c){
		if(0<=r && r<N && 0<=c && c<N) return true;
		else return false;
	}
}