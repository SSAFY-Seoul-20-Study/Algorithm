package homework;
import java.util.*;
import java.io.*;
public class Solution {
    static int N,L;
    static int board[][];
    static int ans;
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        board = new int[N][N];
        for(int i =0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++){
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        garoCheck();
        seroCheck();
        System.out.println(ans);
    }
    static void garoCheck(){
        for(int sero=0; sero<N; sero++){
            boolean check[] = new boolean[N];
            boolean isAns = true;
            garo : for(int i=0; i<N-1; i++){
                int flag = board[sero][i] - board[sero][i+1];
                if(flag > 1 || flag < -1) {
                    isAns = false;
                    break;
                }
                else if(flag == -1){
                    for(int j =0; j<L; j++){
                        if(i-j < 0 || check[i-j]) {
                            isAns = false;
                            break garo;
                        }
                        if(board[sero][i] != board[sero][i-j]) {
                            isAns = false;
                            break garo;
                        }
                        check[i-j] = true;
                    }
                }
                else if(flag == 1){
                    for(int j=1; j<=L; j++){
                        if(i+j>=N || check[i+j]) {
                            isAns = false;
                            break garo;
                        }
                        if(board[sero][i] - 1 != board[sero][i+j]) {
                            isAns = false;
                            break garo;
                        }
                        check[i+j] = true;
                    }
                }
            }
            if(isAns) ans++;
        }
    }
    static void seroCheck(){
        for(int garo=0; garo<N; garo++){
            boolean check[] = new boolean[N];
            boolean isAns = true;
            sero : for(int i=0; i<N-1; i++){
                int flag = board[i][garo] - board[i+1][garo];
                if(flag > 1 || flag < -1) {
                    isAns = false;
                    break;
                }
                else if(flag == -1){
                    for(int j=0; j<L; j++){
                        if(i-j<0 ||check[i-j]) {
                            isAns = false;
                            break sero;
                        }
                        if(board[i][garo] != board[i - j][garo]) {
                            isAns = false;
                            break sero;
                        }
                        check[i-j] = true;
                    }
                }
                else if(flag == 1){
                    for(int j=1; j<=L; j++){
                        if(i+j >= N || check[i+j]){
                            isAns = false;
                            break sero;
                        }
                        if(board[i][garo]-1 != board[i+j][garo]) {
                            isAns = false;
                            break sero;
                        }
                        check[i+j] = true;
                    }
                }
            }
            if(isAns) ans++;
        }
    }
}