import java.util.*;
import java.io.*;

public class Main_bj_g1_15644_구슬_탈출_3 {
	static int N, M;
	static int[][] map; //0: 빈곳 1: 벽 2: 구멍
	static Map<Integer, Integer> visited = new HashMap<>();

	static int dr[] = {-1, 0, 1, 0}; //상 우 하 좌
	static int dc[] = {0, 1, 0, -1};

	static int answer;
	static ArrayDeque<Character> q;
	static String answerString;

    public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		int redX =-1, redY=-1, blueX=-1, blueY=-1;

		for(int i=0; i<N; i++) {
			String s = br.readLine();
			for(int j=0; j<M; j++) {
				char c = s.charAt(j);
				map[i][j] = s.charAt(j);
				if(c=='.' || c=='R' || c=='B'){
					map[i][j] = 0;
					if(c=='R'){
						redX = i;
						redY = j;
					}
					else if(c=='B'){
						blueX = i;
						blueY = j;
					}
				}
				else if(c=='#'){
					map[i][j] = 1;
				}
				else if(c=='O'){
					map[i][j] = 2;
				}
			}
		}
		//System.out.println(Arrays.deepToString(map));

		answer =11;
		q = new ArrayDeque<>();

		dfs(redX, redY, blueX, blueY, 0);

		if(answer == 11){
			System.out.println(-1);
		}
		else{
			System.out.println(answer);
			System.out.println(answerString);
		}
		br.close();
	}

	static void dfs(int redR, int redC, int blueR, int blueC, int depth) {
		if(depth == 10){
			return;
		}

		if(depth >= answer){
			return;
		}

		int key = redR*1000000+redC*10000+blueR*10+blueC;
		if(visited.containsKey(key) && visited.get(key) <= depth){
			return;
		}

		visited.put(key, depth);

		for(int k=0; k<4; k++){
			//Move Blue
			int blueX = blueR;
			int blueY = blueC;
			boolean hitRed = false;
			while(true){
				blueX += dr[k];
				blueY += dc[k];

				if(map[blueX][blueY] == 2){
					return;
				}
				else if(blueX==redR && blueY==redC){
					hitRed = true;
				}
				else if(map[blueX][blueY] == 1){
					blueX -= dr[k];
					blueY -= dc[k];
					if(hitRed==true){
						blueX -= dr[k];
						blueY -= dc[k];
					}
					break;
				}
			}


			//Move Red
			int redX = redR;
			int redY = redC;
			boolean hitBlue = false;

			while(true){
				redX += dr[k];
				redY += dc[k];

				if(map[redX][redY] == 2){
					answer = Math.min(answer, depth+1);
					answerString = "";
					for(char c : q){
						answerString += c;
					}
					if(k==0){
						answerString += 'U';
					}
					if(k==1){
						answerString += 'R';
					}
					if(k==2){
						answerString += 'D';
					}
					if(k==3){
						answerString += 'L';
					}
					return;
				}
				else if(redX==blueR && redY==blueC){
					hitBlue = true;
				}
				else if(map[redX][redY] == 1){
					redX -= dr[k];
					redY -= dc[k];
					if(hitBlue){
						redX -= dr[k];
						redY -= dc[k];
					}
					break;
				}
			}

			if(k==0){
				q.addLast('U');
			}
			if(k==1){
				q.addLast('R');
			}
			if(k==2){
				q.addLast('D');
			}
			if(k==3){
				q.addLast('L');
			}
			dfs(redX, redY, blueX, blueY, depth+1);
			q.removeLast();
		}
	}

}