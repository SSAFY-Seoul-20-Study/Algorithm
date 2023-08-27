import java.util.*;
import java.io.*;
public class Main_bj_톱니바퀴 {
    static ArrayList<Integer>[] gear = new ArrayList[4];
    static int K, ans;
    static boolean check[];
    static int rotationDir[];
    //
    public static void main(String args[]) throws Exception
    {
        System.setIn(new FileInputStream("/Users/kim-junyup/IdeaProjects/algorithmn/src/res.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for(int i=0; i<4; i++) {
            String input = br.readLine();
            gear[i] = new ArrayList<Integer>();
            for(int j=0; j<8; j++) {
                gear[i].add(Integer.parseInt(Character.toString(input.charAt(j))));
            }
        }
        K = Integer.parseInt(br.readLine());
        for(int i=0; i<K; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int gearNum = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            rotation(gearNum-1, dir);
//            System.out.println(Arrays.toString(check));
        }
        if(gear[0].get(0) == 1) ans += 1;
        if(gear[1].get(0) == 1) ans += 2;
        if(gear[2].get(0) == 1) ans += 4;
        if(gear[3].get(0) == 1) ans += 8;
        System.out.println(ans);
        br.close();
    }
    static void rotation(int gearNum, int dir) {
        //기어가 돌아 갈지 말지 체크하는 변수
        check = new boolean[4];
        rotationDir = new int[4];
        validRotation(gearNum);
        //현재 돌리는 톱니바퀴의 방향에 따라 돌려지는 방향이 달라짐
        if(gearNum == 0){
            rotationDir[0] = dir;
            rotationDir[1] = -dir;
            rotationDir[2] = dir;
            rotationDir[3] = -dir;
        }
        else if(gearNum == 1){
            rotationDir[0] = -dir;
            rotationDir[1] = dir;
            rotationDir[2] = -dir;
            rotationDir[3] = dir;
        }
        else if(gearNum == 2){
            rotationDir[0] = dir;
            rotationDir[1] = -dir;
            rotationDir[2] = dir;
            rotationDir[3] = -dir;
        }
        else if(gearNum == 3){
            rotationDir[0] = -dir;
            rotationDir[1] = dir;
            rotationDir[2] = -dir;
            rotationDir[3] = dir;
        }
        for(int i =0; i<4; i++){
            if(check[i] == true){
                if(rotationDir[i] == 1){
                    rightRotation(gear[i]);
                }
                else if(rotationDir[i] == -1){
                    leftRotation(gear[i]);
                }
            }
        }

    }
    static void validRotation(int gearNum) {
        check[gearNum]= true;
        if(gearNum == 0) {
            if(gear[0].get(2) != gear[1].get(6)) check[1] = true;
            if(check[1]) {
                if(gear[1].get(2) != gear[2].get(6)) check[2] = true;
            }
            if(check[2]) {
                if(gear[2].get(2) != gear[3].get(6)) check[3] = true;
            }
        }
        else if(gearNum == 1) {
            if(gear[1].get(6) != gear[0].get(2)) check[0] = true;
            if(gear[1].get(2) != gear[2].get(6)) check[2] = true;
            if(check[2]) {
                if(gear[2].get(2) != gear[3].get(6)) check[3] = true;
            }
        }
        else if(gearNum == 2) {
            if(gear[2].get(2) != gear[3].get(6)) check[3] = true;
            if(gear[2].get(6) != gear[1].get(2)) check[1] = true;
            if(check[1]) {
                if(gear[1].get(6) != gear[0].get(2)) check[0] = true;
            }
        }
        else if (gearNum == 3) {
            if(gear[3].get(6) != gear[2].get(2)) check[2] = true;
            if(check[2]) {
                if(gear[2].get(6) != gear[1].get(2)) check[1] = true;
            }
            if(check[1]) {
                if(gear[0].get(2) != gear[1].get(6)) check[0] = true;
            }
        }
    }
    static void leftRotation(ArrayList<Integer> x) {
        int tmp = x.get(0);
        x.remove(0);
        x.add(tmp);
    }
    static void rightRotation(ArrayList <Integer> x) {
        int tmp = x.get(7);
        x.remove(7);
        x.add(0,tmp);
    }
}