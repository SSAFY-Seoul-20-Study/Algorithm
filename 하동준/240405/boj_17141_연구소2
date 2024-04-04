import java.util.*;
import java.io.*;

public class Main {
    //연구소 위치 관련 클래스
    static class position{
        int x, y;
        public position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    static int N,M, answer = Integer.MAX_VALUE;
    static int[][] laboratory;	//연구실 정보 저장 배열
    static position[] route;	//M개 선택된 바이러스 위치 저장 배열
    static int[] dx = {0, 0, -1, 1};	//상하좌우 x 변경값
    static int[] dy = {-1, 1, 0, 0};	//상하좌우 y 변경값
    static ArrayList<position> virus = new ArrayList<>();	//바이러스 위치 저장 리스트
    public static void main(String[] args) throws IOException {
        //입력값 처리하는 BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //결과값 출력하는 BufferedWriter
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine()," ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        laboratory = new int[N][N];
        //1. 입력되는 연구소의 정보에서 바이러스의 위치를 저장합니다.
        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine()," ");
            for(int j=0;j<N;j++){
                laboratory[i][j] = Integer.parseInt(st.nextToken());
                if(laboratory[i][j] == 2)
                    virus.add(new position(j, i));
            }
        }
        route = new position[M];
        //2. 모든 바이러스에서 M개의 바이러스를 선택합니다.
        search(0, 0);
        //4. 모든 바이러스의 경우에 소요되는 요일에 최소 값을 결과로 출력합니다.
        if(answer == Integer.MAX_VALUE)	//모든 칸을 바이러스로 감염 실패시
            bw.write("-1");
        else		//모든 칸 바이러스 감염 성공시 최소 요일 BufferedWriter 저장
            bw.write(answer + "");
        bw.flush();		//결과 출력
        bw.close();
        br.close();
    }
    //M개의 바이러스를 선택하는 재귀 함수
    static void search(int count, int start){
        //3. 선택한 바이러스에 대하여 BFS탐색으로 문제에 목표에 만족하는 요일을 구합니다.
        if(count == M){
            bfs();
            return;
        }
        for(int i=start;i<virus.size();i++){
            route[count] = virus.get(i);
            search(count+1, i+1);
        }
    }
    //M개의 선택된 바이러스 기준 BFS탐색으로 바이러스 감염 및 걸리는 요일 구하는 함수
    static void bfs(){
        Queue<position> queue = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];
        int result = 0;
        for(int i=0;i<M;i++){
            visited[route[i].y][route[i].x] = true;
            queue.add(route[i]);
        }
        while(!queue.isEmpty()){
            if(result >= answer)
                return;
            int size = queue.size();
            for(int i=0;i<size;i++){
                position cur = queue.poll();
                //바이러스 인접한 칸 탐색
                for(int j=0;j<4;j++){
                    int tempX = cur.x + dx[j];
                    int tempY = cur.y + dy[j];
                    if(inLaboratory(tempX,tempY) && !visited[tempY][tempX]){
                        if(laboratory[tempY][tempX] != 1){
                            visited[tempY][tempX] = true;
                            queue.add(new position(tempX, tempY));
                        }
                    }
                }
            }
            result++;	//요일 증가
        }
        if(laboratoryCheck(visited))	//모든 칸이 감염되었을 때
            answer = Math.min(answer, result-1);
    }
    //모든 연구소 칸이 감염되었는지 확인하는 함수
    static boolean laboratoryCheck(boolean[][] visited){
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(laboratory[i][j] != 1 && !visited[i][j])
                    return false;
            }
        }
        return true;
    }
    //이동한 칸이 연구소 안에 존재하는지 확인하는 함수
    static boolean inLaboratory(int x, int y){
        if(x>=0 && y>=0 && x<N && y<N)
            return true;
        return false;
    }
}
