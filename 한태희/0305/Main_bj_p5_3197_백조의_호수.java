import java.util.*;
import java.io.*;

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main_bj_p5_3197_백조의_호수 {
    static final int MAX = 1500;
    static int R, C;
    static boolean Find;
    static char[][] MAP = new char[MAX][MAX];
    static boolean[][] Visit = new boolean[MAX][MAX];
    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};
    static Queue<Point> Swan_Q = new LinkedList<>(), Swan_NQ = new LinkedList<>(), Q = new LinkedList<>(), NQ = new LinkedList<>();
    static Point Swan_Pos;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        Find = false;
        String[] firstLine = br.readLine().split(" ");
        R = Integer.parseInt(firstLine[0]);
        C = Integer.parseInt(firstLine[1]);
        
        for (int i = 0; i < R; i++) {
            String line = br.readLine();
            for (int j = 0; j < C; j++) {
                MAP[i][j] = line.charAt(j);
                if (MAP[i][j] != 'X') Q.add(new Point(i, j));
                if (MAP[i][j] == 'L') {
                    Swan_Pos = new Point(i, j);
                }
            }
        }
        
        // Solution logic starts here
        int Day = 0;
        Swan_Q.add(new Point(Swan_Pos.x, Swan_Pos.y));
        Visit[Swan_Pos.x][Swan_Pos.y] = true;

        while (!Find) {
            Swan_BFS();
            
            if (!Find) {
                Water_BFS();
                Q = NQ;
                Swan_Q = Swan_NQ;

                NQ = new LinkedList<>();
                Swan_NQ = new LinkedList<>();
                Day++;
            }
        }
        
        System.out.println(Day);
        br.close();
    }

	public static void Swan_BFS() {
        while (!Swan_Q.isEmpty() && !Find) {
            Point p = Swan_Q.poll();
            int x = p.x;
            int y = p.y;

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx >= 0 && ny >= 0 && nx < R && ny < C) {
                    if (!Visit[nx][ny]) {
                        if (MAP[nx][ny] == '.') {
                            Visit[nx][ny] = true;
                            Swan_Q.add(new Point(nx, ny));
                        } else if (MAP[nx][ny] == 'L') {
                            Visit[nx][ny] = true;
                            Find = true;
                            break;
                        } else if (MAP[nx][ny] == 'X') {
                            Visit[nx][ny] = true;
                            Swan_NQ.add(new Point(nx, ny));
                        }
                    }
                }
            }
        }
    }

    public static void Water_BFS() {
        while (!Q.isEmpty()) {
            Point p = Q.poll();
            int x = p.x;
            int y = p.y;

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx >= 0 && ny >= 0 && nx < R && ny < C) {
                    if (MAP[nx][ny] == 'X') {
                        MAP[nx][ny] = '.';
                        NQ.add(new Point(nx, ny));
                    }
                }
            }
        }
    }


}