package a0927;

import java.io.*;
import java.util.*;

public class 어항정리 {
    static int N, K;
    static int fishbowl[][];
    static int dx[] = {-1,1,0,0}, dy[] = {0,0,-1,1};
    static int ans;
    public static void main(String[] args) throws Exception{
        System.setIn(new FileInputStream("C:\\Dev\\ssafy_algo_backup\\algo\\input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        fishbowl = new int[N][N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            fishbowl[N-1][i] = Integer.parseInt(st.nextToken());
        }
        while(isAnswer()){
            findMinPlus();
            levitation();
            ans++;
        }
        System.out.println(ans);
    }
    //최소 어항 찾고 1씩 더하는 함수
    //어항 쌓기
    //공중부양 이후 시계방향 90도 회전
    //공중부양 가능 여부 체크 함수
    //물고기 수 차이 구하기
    //일렬로 다시 만들기
    // N/2개로 공중 부양 후 시계방향으로 180도 회전 (2번반복)
    //다시 물고기 조절 작업 이후 일렬로
    //levitation함수는 항상 어항을 일렬로 깔았을때만 실행한다.
    //쌓을때는 항상 한줄짜리 어항에 깔게된다.
    static void findMinPlus(){
        int min = Integer.MAX_VALUE;
        for (int i = 0; i <N; i++) {
            if(fishbowl[N-1][i] < min) min = fishbowl[N-1][i];
        }
        for (int i = 0; i < N; i++) {
            if(fishbowl[N-1][i] == min) fishbowl[N-1][i]++;
        }
    }
    static void levitation(){
        //우선 왼쪽의 어항을 하나 쌓고 시작
        fishbowl[N-2][0] = fishbowl[N-1][0];
        for (int i = 1; i < N; i++) {
            fishbowl[N-1][i-1] = fishbowl[N-1][i];
        }
        fishbowl[N-1][N-1] = 0;
        while(true){
            int levCol = isLev();
            int limit = 0;
            int box = getCount();
            for (int i = 0; i < N; i++) {
                if(fishbowl[N-1][i] != 0 && fishbowl[N-2][i] == 0) limit++;
            }
//            System.out.println(limit + " : " + levCol);
            if(limit < levCol) break;
            //공중 부양 가능
            else {
                levStack(box);
            }

        }
        spreadFish();
        openBowl();
        fold();
        spreadFish();
        openBowl();
//        for (int i = 0; i < N; i++) {
//            System.out.println(Arrays.toString(fishbowl[i]));
//        }
//        System.out.println();
    }
    static int isLev(){
        int count = 0;
        for (int i = 0; i < N; i++) {
            if(fishbowl[i][0] != 0) count++;
        }
        return count;
    }
    static int getCount(){
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N-1; j++) {
                if(fishbowl[j][i] != 0){
                    count++;
                    break;
                }
            }
        }
        return count;
    }
    static void levStack(int count){
        ArrayList<Integer> []tmp = new ArrayList[count];
        for (int i = 0; i < count; i++) {
            tmp[i] = new ArrayList<>();
        }

        for (int i = 0; i < count; i++) {
            ArrayDeque<Integer> deque = new ArrayDeque<>();
            int index = 0;
            while(index != N){
                if(fishbowl[index][i] != 0) deque.add(fishbowl[index][i]);
                index++;
            }
            while(!deque.isEmpty()){
                int x = deque.pollLast();
                tmp[i].add(x);
            }
        }
        //회전 끝난 배열을 쌓기만 하면 됨 시작 index는 N-1에서 count를 빼면 됨
        int x = 0;
        int y = 0;
        for (int i = N-1-count; i < N-1; i++) {
            for (int j = 0; j < tmp[x].size(); j++) {
                fishbowl[i][j] = tmp[x].get(y++);
            }
            x++;
            y=0;
        }
        //밀어!!!
        for (int i = count; i < N; i++) {
            fishbowl[N-1][i-count] = fishbowl[N-1][i];
        }

    }
    //어항 물고기 퍼트리기
    static void spreadFish(){
        int dummy[][] = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(fishbowl[i][j] == 0) continue;
                for (int d = 0; d < 4; d++) {
                    int nx = i + dx[d];
                    int ny = j + dy[d];
                    if ((ny < 0 || nx < 0 || ny >= N || nx >= N) || fishbowl[nx][ny] == 0) continue;
                    int diff = fishbowl[i][j] - fishbowl[nx][ny];
                    diff /= 5;
                    if(diff > 0){
                        dummy[i][j] -= diff;
                        dummy[nx][ny] += diff;
                    }
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                fishbowl[i][j] += dummy[i][j];
            }
        }
    }
    static void openBowl(){
        ArrayList<Integer> list = new ArrayList<>();
        for (int j = 0; j < N; j++) {
            for (int i = N-1; i >= 0 ; i--) {
                if(fishbowl[i][j] == 0) break;
                list.add(fishbowl[i][j]);
                fishbowl[i][j] = 0;
            }
        }
        for (int i = 0; i < N; i++) {
            fishbowl[N-1][i] = list.get(i);
        }
    }
    static void fold(){
        ArrayDeque<Integer> list = new ArrayDeque<>();
        for(int i = 0; i < N/2; i++) {
            list.add(fishbowl[N-1][i]);
            fishbowl[N-1][i] = 0;
        }
        int count = N/2;
        int index =0;
        while(count != index){
            fishbowl[N-2][N-1-index] = list.pop();
            index++;
        }
        ArrayDeque<Integer> down = new ArrayDeque<>();
        ArrayDeque<Integer> up = new ArrayDeque<>();
        for(int i = N/2; i < N/2 + N/4; i++) {
            up.add(fishbowl[N-1][i]);
            down.add(fishbowl[N-2][i]);
            fishbowl[N-1][i] = 0;
            fishbowl[N-2][i] = 0;
        }
        for (int i = N/2 + N/4; i < N; i++) {
            fishbowl[N-3][i] = down.pollLast();
            fishbowl[N-4][i] = up.pollLast();
        }


    }
    static boolean isAnswer(){
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            max = Math.max(max, fishbowl[N-1][i]);
            min = Math.min(min, fishbowl[N-1][i]);
        }
        if(Math.abs(max - min) <= K) return false;
        return true;
    }
}
