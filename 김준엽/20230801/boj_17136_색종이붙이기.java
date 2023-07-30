package homework;

import java.io.*;
import java.util.*;

public class 색종이붙이기 {
    public static int [][] board = new int[10][10];
    public static int answer = -1;
    static int[] squareCnt = {0,5,5,5,5,5};
    static boolean isPapers(int y, int x, int s){
        for(int i =0; i<s; i++){
            for(int j=0; j<s; j++){
                if(board[y+i][x+j] == 0) return false;
            }
        }
        return true;
    }
    static void checkPapers(int y, int x, int s, int width){
        if(width == 1) squareCnt[s]++;
        else squareCnt[s]--;
        for(int i=0; i<s ; i++){
            for(int j=0; j<s; j++){
                board[y+i][x+j] = width;
            }
        }
    }
    static void backtracking(int y, int x, int cnt){
        if(x>9){
            backtracking(y+1,0,cnt);
            return;
        }
        if(y>9){
            if(answer == -1) answer = cnt;
            else if(answer > cnt) answer = cnt;
            return;
        }
        if(board[y][x] == 0) {
            backtracking(y,x+1,cnt);
            return;
        }
        for(int s=5;s>=1;s--){
            if(squareCnt[s] != 0 && y+s<=10 && x+s<=10){
                if(isPapers(y,x,s)){
                    checkPapers(y,x,s,0);
                    backtracking(y,x+s,cnt+1);
                    checkPapers(y,x,s,1);
                }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for(int i=0; i<10; i++){
            StringTokenizer st  = new StringTokenizer(br.readLine(), " ");
            for(int j=0; j<10; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        backtracking(0,0,0);
        System.out.println(answer);
    }
}
