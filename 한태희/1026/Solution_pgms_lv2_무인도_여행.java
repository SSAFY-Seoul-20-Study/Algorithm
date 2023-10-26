import java.util.*;

/*
# 무인도여행
입력 데이터를 문자열에서 배열로 변환하는 것이 제일 힘들었던 문제.
그 뒤엔 0이 아닌 구성원들의 그룹 만들기 (그룹은 구성원의 값의 합을 가짐)
그걸 어레이리스트 sort를 이용해 정렬하기
 */

class Solution {
    static int N, M;
    static int[][] a;
    static boolean[][] v;

    public int[] solution(String[] maps) {
        N = maps.length;
        M = maps[0].length();
        a = new int[N][M];
        for (int i = 0; i < N; i++) {
            String s = maps[i];
            for (int j = 0; j < M; j++) {
                char c = s.charAt(j);
                if (c == 'X') {
                    a[i][j] = 0;
                } else {
                    a[i][j] = c - '0';
                }
            }
        }

        List<Integer> list = new ArrayList<>();

        v = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (v[i][j] == false && a[i][j] != 0) {
                    v[i][j] = true;
                    list.add(makeGroup(i, j));
                }
            }
        }

        list.sort(
                (o1, o2) -> Integer.compare(o1, o2));
        int[] answer;

        if (list.size() == 0) {
            answer = new int[] { -1 };
        } else {
            answer = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                answer[i] = list.get(i);
            }
        }

        return answer;
    }

    static int[] dr = { -1, 1, 0, 0 };
    static int[] dc = { 0, 0, -1, 1 };

    int makeGroup(int r, int c) {
        int ret = a[r][c];
        for (int k = 0; k < 4; k++) {
            int nr = r + dr[k];
            int nc = c + dc[k];
            if (isValid(nr, nc) && v[nr][nc] == false && a[nr][nc] != 0) {
                v[nr][nc] = true;
                ret += makeGroup(nr, nc);
            }
        }
        return ret;
    }

    boolean isValid(int r, int c) {
        if (0 <= r && r < N && 0 <= c && c < M)
            return true;
        else
            return false;
    }
}