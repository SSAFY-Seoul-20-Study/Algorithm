import java.util.*;

class Solution {
    int N, M;
    int[][] maze;
    boolean[][] rV, bV;
    int answer = Integer.MAX_VALUE;
    
    final int[] dr = {1, -1, 0, 0};
    final int[] dc = {0, 0, 1, -1};
    
    public int solution(int[][] m) {
        maze = m;
        N = maze.length;
        M = maze[0].length;
        
        int redR=-1, redC=-1, blueR=-1, blueC=-1;
        
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                if(maze[i][j]==1){
                    maze[i][j] = 0;
                    redR = i;
                    redC = j;
                }
                if(maze[i][j]==2){
                    maze[i][j] = 0;
                    blueR = i;
                    blueC = j;
                }
            }
        }
        
        rV = new boolean[N][M]; bV = new boolean[N][M];
        rV[redR][redC] = true;
        bV[blueR][blueC] = true;
        dfs(0, redR, redC, blueR, blueC, false, false);
        
        if(answer==Integer.MAX_VALUE) answer=0;
        
        return answer;
    }
    
    void dfs(int step, int redR, int redC, int blueR, int blueC, boolean redEnd, boolean blueEnd){
        if (answer <= step) return;
        
        if (redEnd && blueEnd){
            answer = step;
            return;
        }
        
        List<int[]> nextRedCords = new ArrayList<>();
        List<int[]> nextBlueCords = new ArrayList<>();
        
        if(redEnd){
            nextRedCords.add(new int[]{redR, redC});
        }
        else{
            for(int k=0;k<4;k++){
                int nr = redR + dr[k];
                int nc = redC + dc[k];
                if(isValid(nr, nc)==false || rV[nr][nc]==true || maze[nr][nc]==5) continue;
                nextRedCords.add(new int[]{nr, nc});
            }
        }
        if(blueEnd){
            nextBlueCords.add(new int[]{blueR, blueC});
        }
        else{
            for(int k=0;k<4;k++){
                int nr = blueR + dr[k];
                int nc = blueC + dc[k];
                if(isValid(nr, nc)==false || bV[nr][nc]==true || maze[nr][nc]==5) continue;
                 nextBlueCords.add(new int[]{nr, nc});
            }
        }
        
        for(int[] nextRedCord: nextRedCords){
            for(int[] nextBlueCord: nextBlueCords){
                
                int rnr = nextRedCord[0], rnc = nextRedCord[1], bnr = nextBlueCord[0], bnc = nextBlueCord[1];
                
                //위치가 교환 되는 경우 스킵
                if(rnr==blueR && rnc==blueC && bnr==redR && bnc==redC) continue;
                
                //두 공의 위치가 겹칠 경우 스킵
                if(rnr==bnr && rnc==bnc) continue;
                
                rV[rnr][rnc] = true;
                bV[bnr][bnc] = true;
                
                boolean nextRedEnd = false, nextBlueEnd = false;
                if(maze[rnr][rnc]==3) nextRedEnd = true;
                if(maze[bnr][bnc]==4) nextBlueEnd = true;
                
                dfs(step+1, rnr, rnc, bnr, bnc, nextRedEnd, nextBlueEnd);
                
                rV[rnr][rnc] = false;
                bV[bnr][bnc] = false;
            }
        }
        
    }
    
    boolean isValid(int r, int c){
        if(0<=r && r<N && 0<=c && c<M) return true;
        else return false;
    }
}
