package study;
import java.util.*;
import java.io.*;

class 청소년상어{
    static int dx[] = {0,-1,-1,0,1,1,1,0,-1}, dy[] = {0,0,-1,-1,-1,0,1,1,1};
    static int ans = Integer.MIN_VALUE;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<int[]>[] board = new ArrayList[4];

        for(int i=0; i<4; i++){
            StringTokenizer st  = new StringTokenizer(br.readLine(), " ");
            board[i] = new ArrayList<>();
            for(int j=0; j<4; j++){
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                board[i].add(new int[] {a,b});
            }
        }
        simulation(0,0, board, 0);
        System.out.println(ans);
    }
    static void simulation(int x, int y ,ArrayList<int[]> [] board, int sum) {
    	ArrayList<int[]>[] copiedBoard = deepCopyArrayListArray(board);
    	//현재 위치의 물고기를 먹기
    	sum += copiedBoard[x].get(y)[0];
    	int fishDir = copiedBoard[x].get(y)[1];
    	copiedBoard[x].get(y)[0] = -1;
    	copiedBoard[x].get(y)[1] = -1;
    	//물고기들 이동
    	findFish(copiedBoard);
    	
    	//상어가 먹을 수 있는 물고기를 찾기, 없으면 max 비교 후 return;
    	//먹을 수 있는 물고기의 개수와 위치를 반환하는 함수
    	ArrayList<int []> can = isEat(x,y,fishDir,copiedBoard);
    	//이후 백트래킹으로 다음 상어의 위치와 board를 복사해서 백트래킹 시작
    	if(can.size()==0) {
    		ans = Math.max(ans, sum);
    		return;
    	}
    	for(int []pos : can) {
    		copiedBoard[x].get(y)[0] = 0;
    		copiedBoard[x].get(y)[1] = 0;
    		simulation(pos[0],pos[1],copiedBoard,sum);
    	}
     }
    //[개수,x,y]
    static ArrayList<int[]> isEat(int x, int y, int dir, ArrayList<int[]>[] board) {
    	ArrayList<int []> tmp = new ArrayList<>();
    	while(true) {
    		x = x + dx[dir];
    		y = y + dy[dir];
    		if(0<=x && x<4 && 0<=y && y<4 && board[x].get(y)[0] != -1) {
    			tmp.add(new int [] {x,y});
    			continue;
    		}
    		return tmp;
    	}
    }
    static void findFish(ArrayList<int[]>[] board){
        fish: for(int num = 1; num<=16; num++){
            for(int i=0; i<4; i++){
                for(int j=0; j<4; j++){
                    //번호 순서대로 물고기 번호 찾기
                    if(board[i].get(j)[0] == num){
                        //물고기 이동시키기
                        moveFish(i,j,board);
                        continue fish;
                    }
                }
            }
        }
    }
    //-1은 상어가 있다는 것 0은 물고기가 먹어 치워져서 없는것
    static void moveFish(int x, int y, ArrayList<int[]>[] board){
        int fishNum = board[x].get(y)[0];
        int direction = board[x].get(y)[1];
        for(int i=0; i<8; i++){
            int nd = (direction + i) % 9;
            if(nd == 0) nd = 1;
            int nx = x + dx[nd];
            int ny = y + dy[nd];
            if(0 <= nx && nx<4 && 0<=ny && ny<4 && board[nx].get(ny)[0] != -1){
                int tmpFish = fishNum;
                int tmpDirect = nd;
                board[x].get(y)[0] = board[nx].get(ny)[0];
                board[x].get(y)[1] = board[nx].get(ny)[1];
                board[nx].get(ny)[0] = tmpFish;
                board[nx].get(ny)[1] = tmpDirect;
                return;
            }
        }
    }
    //2차원 배열은 백트래킹 이전으로 돌아가기가 힘들기 때문에 그때 그때 배열을 복사해서 사용
    static ArrayList<int[]>[] deepCopyArrayListArray(ArrayList<int[]>[] source) {
        if (source == null) {
            return null;
        }
        
        ArrayList<int[]>[] copy = new ArrayList[source.length];
        for (int i = 0; i < source.length; i++) {
            if (source[i] != null) {
                copy[i] = new ArrayList<>();
                for (int[] arr : source[i]) {
                    int[] arrCopy = Arrays.copyOf(arr, arr.length);
                    copy[i].add(arrCopy);
                }
            }
        }
        return copy;
    }
}