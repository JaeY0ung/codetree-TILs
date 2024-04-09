import java.io.*;
import java.util.*;

public class Main {
    static int L, Q;
    static int timeNow, timeBefore;
    static SeatInfo[] seat;
    static StringTokenizer st;
    static StringBuilder sb;

    static class SeatInfo {
        String customerName;
        List<String> foods;
        int lastEatCnt;

        SeatInfo() {
            this.customerName = "";
            this.foods = new ArrayList<>();
            this.lastEatCnt = 0;
        }

        SeatInfo(String customerName, List<String> foods) {
            this.customerName = customerName;
            this.foods = foods;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        st = new StringTokenizer(br.readLine());
        L = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        timeBefore = 0;
        seat = new SeatInfo[L];
        for (int i = 0; i < L; i++) seat[i] = new SeatInfo();

        for (int q = 0; q < Q; q++) {
            st = new StringTokenizer(br.readLine());
            int action = Integer.parseInt(st.nextToken());
            timeNow = Integer.parseInt(st.nextToken());
            // 1. 회전
            seat = rotate(timeNow - timeBefore);
            if (action == 100) { // 초밥 만들기
                make();
                eat();
            } else if (action == 200) { // 손님 입장
                customerCome();
                eat();
            } else {// 사진 촬영
                eat();
                TakePicture();
            }
            // DebugTakePicture(timeNow);
            timeBefore = timeNow;
        }
        System.out.println(sb.toString());
    }

    private static SeatInfo[] rotate(int cnt) {
        SeatInfo[] newSeat = new SeatInfo[L];
        for (int i = 0; i < L; i++) newSeat[i] = new SeatInfo();
        for (int i = 0; i < L; i++) {
            newSeat[i].customerName = seat[i].customerName;
            newSeat[i].lastEatCnt = seat[i].lastEatCnt;
        }

        for (int i = 0; i < L; i++) {
            int newI = (i + cnt) % L;
            newSeat[newI].foods = seat[i].foods;
        }
        return newSeat;
    }

    private static void eat() {
        for (SeatInfo seatInfo : seat) {
            if (seatInfo.customerName.isEmpty()) continue ;

            for (int i = seatInfo.foods.size() - 1; i >= 0; i--) { // foods 순회
                if (seatInfo.foods.get(i).equals(seatInfo.customerName)) { // 이름이 같으면
                    seatInfo.foods.remove(i); // 먹어서 없애기
                    if (--seatInfo.lastEatCnt == 0) { // 남은 먹을 음식 수 --
                        seatInfo.customerName = "";
                        break;
                    }
                }
            }
        }
    }

    private static void make() {
        int x = Integer.parseInt(st.nextToken());
        String name = st.nextToken();
        seat[x].foods.add(name);
    }

    private static void customerCome() {
        int x = Integer.parseInt(st.nextToken());
        String name = st.nextToken();
        int n = Integer.parseInt(st.nextToken());
        seat[x].customerName = name;
        seat[x].lastEatCnt = n;
    }

    private static void TakePicture() {
        int CustomerCnt = 0;
        int foodCnt = 0;
        for (SeatInfo seatInfo : seat) {
            if (!seatInfo.customerName.isEmpty()) CustomerCnt++;
            foodCnt += seatInfo.foods.size();
        }
        sb.append(CustomerCnt).append(" ").append(foodCnt).append("\n");
    }

    private static void DebugTakePicture(int time) {
        System.out.println("(" + time + "초) ");
        for (int i = 0; i < seat.length; i++) {
            SeatInfo seatInfo = seat[i];
            System.out.println("[" + i + "]"
                    + seatInfo.customerName + ", "
                    + seatInfo.lastEatCnt + ", "
                    + seatInfo.foods);
        }
        System.out.println();
    }
}