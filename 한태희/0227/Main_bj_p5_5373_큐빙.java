import java.util.*;
import java.io.*;

public class Main_bj_p5_5373_큐빙 {

	static char[][][] cube; // 0:up, 1:down, 2:front, 3:back, 4:left, 5:right
	static Map<Character, int[]> scopeMap = new HashMap<>(
		Map.ofEntries(
			Map.entry('U', new int[]{2, 4, 3, 5}),
			Map.entry('D', new int[]{3, 4, 2, 5})
		)
	);

	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int TC = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TC; tc++) {
			initCube();
			int N = Integer.parseInt(br.readLine());
			for(int n = 0; n < N; n++){
				
			}
		}

		br.close();
	}

	static void initCube(){
		cube = new char[6][][];
		cube[0] = new char[][]{{'w', 'w', 'w'}, {'w', 'w', 'w'}, {'w', 'w', 'w'}};
		cube[1] = new char[][]{{'y', 'y', 'y'}, {'y', 'y', 'y'}, {'y', 'y', 'y'}};
		cube[2] = new char[][]{{'r', 'r', 'r'}, {'r', 'r', 'r'}, {'r', 'r', 'r'}};
		cube[3] = new char[][]{{'o', 'o', 'o'}, {'o', 'o', 'o'}, {'o', 'o', 'o'}};
		cube[4] = new char[][]{{'g', 'g', 'g'}, {'g', 'g', 'g'}, {'g', 'g', 'g'}};
		cube[5] = new char[][]{{'b', 'b', 'b'}, {'b', 'b', 'b'}, {'b', 'b', 'b'}};
	}
}