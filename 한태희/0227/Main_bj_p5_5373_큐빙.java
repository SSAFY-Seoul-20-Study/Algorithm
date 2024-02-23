import java.util.*;
import java.io.*;

public class Main_bj_p5_5373_큐빙 {

	static char[] cube;
	/*
	 		27 28 29
			30 31 32
			33 34 35
	
 36 37 38   0  1  2   45 46 47  
 39 40 41	3  4  5   48 49 50
 42 43 44	6  7  8   51 52 53

			9  10 11
			12 13 14
			15 16 17

			18 19 20
			21 22 23
			24 25 26

			B
		L	U   R
		    F
			D
	 */

	static Map<Character, int[][]> scopeMap = new HashMap<>();

	static{
		scopeMap.put('U', new int[][]{{33, 34, 35}, {45, 48, 51} ,{11, 10, 9}, {44, 41, 38}});
		scopeMap.put('F', new int[][]{{42, 43, 44}, {6, 7, 8}, {51, 52, 53}, {20, 19, 18}});
		scopeMap.put('D', new int[][]{{15, 16, 17}, {53, 50, 47}, {29, 28, 27}, {36, 39, 42}});
		scopeMap.put('B', new int[][]{{2, 1, 0}, {38, 37, 36}, {24, 25, 26}, {47, 46, 45}});
		scopeMap.put('L', new int[][]{{0, 3, 6}, {9, 12, 15}, {18, 21, 24}, {27, 30, 33}});
		scopeMap.put('R', new int[][]{{8, 5, 2}, {35, 32, 29}, {26, 23, 20}, {17, 14, 11}});
	}

	static Map<Character, Integer> flatStartMap = new HashMap<>();

	static{
		flatStartMap.put('U', 0);
		flatStartMap.put('F', 9);
		flatStartMap.put('D', 18);
		flatStartMap.put('B', 27);
		flatStartMap.put('L', 36);
		flatStartMap.put('R', 45);
	}

	static int[] flatClockwiseArray = new int[]{2, 5, 8, 1, 4, 7, 0, 3, 6};


	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringBuilder sb = new StringBuilder();
		
		int TC = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TC; tc++) {
			initCube();
			int N = Integer.parseInt(br.readLine());
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int n = 0; n < N; n++){
				String s = st.nextToken();
				char target = s.charAt(0);
				boolean clockwise = s.charAt(1) == '+' ? true : false;
				rotate(target, clockwise);
			}	

			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					sb.append(cube[i*3+j]);
				}
				sb.append("\n");
			}
		}

		System.out.println(sb);

		br.close();
	}

	static void initCube(){
		cube = new char[54];
		char[] l = {'w', 'r', 'y', 'o', 'g', 'b'};
		for(int i=0;i<6;i++){
			for(int j=0;j<9;j++){
				cube[i*9+j] = l[i];
			}
		}
	}

	static void rotate(char target, boolean clockwise){
		rotateFlat(target, clockwise);
		rotate3DScope(target, clockwise);
	}

	static void rotateFlat(char target, boolean clockwise){
		int start = flatStartMap.get(target);
		int[] arr = flatClockwiseArray;
		char[] temp = new char[9];

		if(clockwise){
			for(int i=0;i<9;i++){
				temp[arr[i]] = cube[start + i];
			}
		}

		else{
			for(int i=0;i<9;i++){
				temp[i] = cube[start + arr[i]];
			}
		}

		for(int i=0;i<9;i++){
			cube[start + i] = temp[i];
		}
	}

	static void rotate3DScope(char target, boolean clockwise){
		int[][] arr = scopeMap.get(target);

		if(clockwise){
			char[] temp = {cube[arr[3][0]], cube[arr[3][1]], cube[arr[3][2]]};

			for(int i=3;i>0;i--){
				for(int j=0;j<3;j++){
					cube[arr[i][j]] = cube[arr[i-1][j]];
				}
			}

			for(int j=0;j<3;j++){
				cube[arr[0][j]] = temp[j];
			}
		}

		else{
			char[] temp = {cube[arr[0][0]], cube[arr[0][1]], cube[arr[0][2]]};

			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					cube[arr[i][j]] = cube[arr[i+1][j]];
				}
			}

			for(int j=0;j<3;j++){
				cube[arr[3][j]] = temp[j];
			}
		}

	}
}