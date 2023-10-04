import java.util.*;
import java.io.*;

public class Main_bj_p5_23291_어항_정리 {
	static int N, K;
	static List<List<Integer>> a, b;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		a = new ArrayList<>();
		a.add(new ArrayList<>());
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			a.get(0).add(Integer.parseInt(st.nextToken()));
		}

		step1();
		step2();
		step3();

		System.out.println(a);

		br.close();
	}

	static void step1() {
		int minN = Integer.MAX_VALUE;
		for (int n : a.get(0)) {
			minN = Math.min(minN, n);
		}
		for (int i = 0; i < a.get(0).size(); i++) {
			if (a.get(0).get(i) == minN) {
				a.get(0).remove(i);
				a.get(0).add(i, minN + 1);
			}
		}
	}

	static void step2() {
		int n = a.get(0).get(0);
		a.get(0).remove(0);
		a.add(new ArrayList<>());
		a.get(1).add(n);

		while (true) {
			int width = a.get(1).size();
			int height = a.size();

			if (height > a.get(0).size() - width) {
				break;
			}

			b = new ArrayList<>();
			for (int i = 0; i < width + 1; i++) {
				b.add(new ArrayList<>());
			}
			for (int i = width; i < a.get(0).size(); i++) {
				b.get(0).add(a.get(0).get(i));
			}

			int level = 1;
			for (int i = width - 1; i >= 0; i--) {
				for (int j = 0; j < height; j++) {
					b.get(level).add(a.get(j).get(i));
				}
				level++;
			}

			a = b;
		}
	}

	static void step3() {
		int width = a.get(1).size();
		int height = a.size();
		b = new ArrayList<>();
		for (int i = 0; i < height; i++) {
			b.add(new ArrayList<>());
		}
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				b.get(j).add(0);
			}
		}
		for (int i = width; i < a.get(0).size(); i++) {
			b.get(0).add(0);
		}

		System.out.println(a);

		for (int i = 0; i < a.size(); i++) {
			compare(i, 0, i + 1, 0);
		}
		for (int j = 1; j < height; j++) {
			for (int i = 0; i < width - 1; i++) {
				compare(i, j, i + 1, j);
			}
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height - 1; j++) {
				compare(i, j, i, j + 1);
			}
		}

		for (int i = 0; i < a.get(0).size(); i++) {
			int av = a.get(0).get(i);
			int bv = b.get(0).get(i);
			b.get(0).set(i, av + bv);
		}
		// for(int i=0; i<width;i++){
		// for(int j=1;j<jh)
		// }
	}

	static void compare(int x1, int y1, int x2, int y2) {
		int bigx, bigy, smallx, smally, big, small;
		int v1 = a.get(y1).get(x1), v2 = a.get(y2).get(x2);
		if (v1 >= v2) {
			bigx = x1;
			bigy = y1;
			big = v1;
			smallx = x2;
			smally = y2;
			small = v2;
		} else {
			bigx = x2;
			bigy = y2;
			big = v2;
			smallx = x1;
			smally = y1;
			small = v1;
		}

		int dit = (big - small) / 5;
		b.get(smally).set(smallx, b.get(smally).get(smallx) + dit);
		b.get(bigy).set(bigx, b.get(bigy).get(bigx) - dit);
	}

	static void step4() {
		b = new ArrayList<>();
		b.add(new ArrayList<>());

		int width = a.get(1).size();
		int height = a.size();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				b.get(0).add(a.get(j).get(i));
			}
		}
		for (int i = width; i < a.get(0).size(); i++) {
			b.get(0).add(a.get(0).get(i));
		}
		a = b;
	}

}