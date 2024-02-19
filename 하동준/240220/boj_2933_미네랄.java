package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class boj_2933_미네랄 {

	static int r, c, mNum;
	static char[][] board;
	static boolean[][] v;
	static int[][] move = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());

		board = new char[r][c];
		v = new boolean[r][c];

		for (int i = 0; i < r; i++) {
			String line = br.readLine();
			for (int j = 0; j < c; j++) {
				board[i][j] = line.charAt(j);
				if (board[i][j] == 'x') {
					mNum++;
				}
			}
		}

		int T = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < T; i++) {
			int h = r - Integer.parseInt(st.nextToken());
			int trg = -1;
			// 빵야
			if (i % 2 == 0) {
				// 왼쪽에서 오른쪽으로
				for (int j = 0; j < c; j++) {
					if (board[h][j] == 'x') {
						// 이 자리네요. 쏘세요.
						trg = j;
						break;
					}
				}
			} else {
				// 왼쪽에서 오른쪽으로
				for (int j = c - 1; j >= 0; j--) {
					if (board[h][j] == 'x') {
						// 이 자리네요. 쏘세요.
						trg = j;
						break;
					}
				}
			}

			if (trg != -1) {
				// 명중
				board[h][trg] = '.';
				mNum--;
				b1:
				for (int[] dxy : move) {
					// BFS 하면서 숫자를 세보자.
					int x = h + dxy[0];
					int y = trg + dxy[1];
					if ((0<=x && x<r && 0<=y && y<c) == false) continue;
					if (board[x][y] == '.') {
						continue;
					}
					boolean isBottom = false;

					for (int j = 0; j < r; j++) {
						for (int k = 0; k < c; k++) {
							v[j][k] = false;
						}
					}

					if (0 <= x && x < r && 0 <= y && y < c) {
						ArrayDeque<int[]> q = new ArrayDeque<>();
						ArrayList<int[]> cluster = new ArrayList<>();
						HashMap<Integer, Integer> gaps = new HashMap<>();
						int cnt = 0;
						q.offer(new int[]{x, y});
						cluster.add(new int[]{x, y});
						gaps.put(y, x);
						v[x][y] = true;

						while (!q.isEmpty()) {
							int[] cur = q.poll();
							cnt++;
							if (cnt == mNum) {
								// 클러스터 변화 없음
								break b1;
							}
							if (cur[0] == r - 1) {
								// 바닥. 이거는 아님
								isBottom = true;
							}
							for (int[] ddxy : move) {
								int nx = cur[0] + ddxy[0];
								int ny = cur[1] + ddxy[1];
								if (0 <= nx && nx < r && 0 <= ny && ny < c && !v[nx][ny]
									&& board[nx][ny] == 'x') {
									v[nx][ny] = true;
									q.offer(new int[]{nx, ny});
									cluster.add(new int[]{nx, ny});
									if (gaps.containsKey(ny)) {
										gaps.put(ny, Math.max(nx, gaps.get(ny)));
									} else {
										gaps.put(ny, nx);
									}
								}
							}
						}
						// 만약 이게 바닥이 아니라면 이동시켜야.
						if (!isBottom && cluster.size() != 0) {
							int minGap = r + 1;
							for (int yy : gaps.keySet()) {
								int gap = 0;
								for (int j = gaps.get(yy) + 1; j < r; j++) {
									if (j == r - 1 && board[j][yy] == '.') {
										gap++;
										minGap = Math.min(minGap, gap);
										break;
									}
									if (board[j][yy] == 'x') {
										minGap = Math.min(minGap, gap);
										break;
									}
									gap++;
								}
							}
							// 이거 기준으로 내리자.
							for (int[] xy : cluster) {
								board[xy[0]][xy[1]] = '.';
							}
							for (int[] xy : cluster) {
								if (xy[0] + minGap >= r) {
									continue;
								}
								board[xy[0] + minGap][xy[1]] = 'x';
							}

						}
					}

				}
			}
		}
		for (int e = 0; e < r; e++) {
			for (int j = 0; j < c; j++) {
				System.out.print(board[e][j]);
			}
			System.out.println();
		}
	}
}
