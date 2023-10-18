import java.util.*;

/*
처음에 가장자리를 타고가는 과정을 구현하려 했지만 실패.

고민하다 전류가 표면을 타고 흐르는 현상에서 영감을 받음. 전류가 가장자리를 타고 가는 이유는 전자기적 반발로 인해 물체 속으로 들어가지 못하기 때문이다. 벡터장을 표현할 수 있는 방법은 없나 잠깐 고민하다 그럴 필요는 다는 것을 깨달음.

가로 세로 51 x 51 크기 공간에서 51x50 크기의 세로 연결과 50x51 크기의 가로 연결을 새로운 배열로 만들어서 표현한다.

입력으로 주어지는 직사각형의 모양을 통해서, 외곽 연결은 활성화되고, 직사각형 내부에 포함되는 연결은 비활성화된다. 이때, 한번 비활성화 된 연결은 다시는 활성화 될 수 없다.

이런 과정을 통해서 구해진 두개의 연결 배열을 이용해 dfs 탐색을 할 수 있다. 탐색을 통해 목표 지점에 도착한 경우 중 가장 거리가 짧은 경우가 정답이다.
 */
class Solution {
    int N = 51;
    short[][] wcon = new short[N + 1][N]; // 1: wcon[x][y] = 점 (x, y) ~ (x+1, y)가 연결 2:불가 :입력없음
    short[][] lcon = new short[N][N + 1]; // lcon[x][y] = 점 (x, y) ~ (x, y+1)가 연결
    boolean[][] v = new boolean[N + 1][N + 1];

    int X, Y;

    int ans = Integer.MAX_VALUE;
    int curr = 0;

    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        X = itemX;
        Y = itemY;
        checkCon(rectangle);
        v[characterY][characterX] = true;
        dfs(characterX, characterY);

        return ans;
    }

    void checkCon(int[][] rectangle) {
        for (int[] rect : rectangle) {
            int x1 = rect[0];
            int y1 = rect[1];
            int x2 = rect[2];
            int y2 = rect[3];

            for (int y = y1; y < y2; y++) {
                if (lcon[y][x1] == 0)
                    lcon[y][x1] = 1;
                if (lcon[y][x2] == 0)
                    lcon[y][x2] = 1;
            }
            for (int x = x1 + 1; x <= x2 - 1; x++) {
                for (int y = y1; y < y2; y++) {
                    lcon[y][x] = 2;
                }
            }

            for (int x = x1; x < x2; x++) {
                if (wcon[y1][x] == 0)
                    wcon[y1][x] = 1;
                if (wcon[y2][x] == 0)
                    wcon[y2][x] = 1;
            }
            for (int y = y1 + 1; y <= y2 - 1; y++) {
                for (int x = x1; x < x2; x++) {
                    wcon[y][x] = 2;
                }
            }

        }
    }

    void dfs(int x, int y) {
        // 만약 최종점에 도달한다면,
        if (x == X && y == Y) {
            ans = Math.min(ans, curr);
            return;
        }

        int nx, ny;

        // 오른쪽 방향
        nx = x + 1;
        ny = y;
        if (isValid(nx, ny) && v[ny][nx] == false && wcon[y][x] == 1) {
            curr++;
            v[ny][nx] = true;
            dfs(nx, ny);
            v[ny][nx] = false;
            curr--;
        }

        // 왼쪽 방향
        nx = x - 1;
        ny = y;
        if (isValid(nx, ny) && v[ny][nx] == false && wcon[ny][nx] == 1) {
            curr++;
            v[ny][nx] = true;
            dfs(nx, ny);
            v[ny][nx] = false;
            curr--;
        }

        // 위쪽 방향
        nx = x;
        ny = y + 1;
        if (isValid(nx, ny) && v[ny][nx] == false && lcon[y][x] == 1) {
            curr++;
            v[ny][nx] = true;
            dfs(nx, ny);
            v[ny][nx] = false;
            curr--;
        }

        nx = x;
        ny = y - 1;
        // 아래쪽 방향
        if (isValid(nx, ny) && v[ny][nx] == false && lcon[ny][nx] == 1) {
            curr++;
            v[ny][nx] = true;
            dfs(nx, ny);
            v[ny][nx] = false;
            curr--;
        }

    }

    boolean isValid(int x, int y) {
        if (0 <= x && x < N && 0 <= y && y < N)
            return true;
        else
            return false;
    }

}
