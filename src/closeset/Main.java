package closeset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        int N = 30;
        ArrayList<Points> arrList = new ArrayList<>();
        
        // 좌표를 리스트에 추가
        arrList.add(new Points(8, 53));
        arrList.add(new Points(94, 19));
        arrList.add(new Points(15, 76));
        arrList.add(new Points(23, 93));
        arrList.add(new Points(54, 80));
        arrList.add(new Points(31, 7));
        arrList.add(new Points(11, 12));
        arrList.add(new Points(35, 88));
        arrList.add(new Points(44, 17));
        arrList.add(new Points(76, 9));
        arrList.add(new Points(60, 32));
        arrList.add(new Points(70, 22));
        arrList.add(new Points(67, 52));
        arrList.add(new Points(71, 42));
        arrList.add(new Points(80, 97));
        arrList.add(new Points(86, 67));
        arrList.add(new Points(15, 36));
        arrList.add(new Points(16, 8));
        arrList.add(new Points(93, 75));
        arrList.add(new Points(66, 17));
        arrList.add(new Points(86, 4));
        arrList.add(new Points(93, 27));
        arrList.add(new Points(87, 78));
        arrList.add(new Points(89, 10));
        arrList.add(new Points(89, 97));
        arrList.add(new Points(78, 54));
        arrList.add(new Points(92, 91));
        arrList.add(new Points(94, 25));
        arrList.add(new Points(87, 65));
        arrList.add(new Points(99, 60));
        
        // x 좌표를 기준으로 오름차순 정렬
        Collections.sort(arrList, Comparator.comparingInt(p -> p.x));
        
        // 정렬된 좌표를 출력
        for (int i = 0; i < N; i++) {
            System.out.print("(" + arrList.get(i).x + "," + arrList.get(i).y + ") ");
            if ((i + 1) % 5 == 0) { // 5개씩 출력 후 줄바꿈
                System.out.println();
            }
        }
        
        System.out.println();

        // 최근접 점 쌍을 찾고 결과를 출력
        Result result = CloseSet(arrList, 0, N - 1);
        System.out.println("가장 짧은 거리: " + result.distance);
        System.out.println("가장 짧은 점 쌍: (" + result.p1.x + "," + result.p1.y + ")와 (" + result.p2.x + "," + result.p2.y + ")");
    }

    // 두 점을 나타내는 클래스
    public static class Points {
        int x, y;

        public Points(int a, int b) {
            x = a;
            y = b;
        }
    }

    // 최근접 점 쌍의 정보를 저장하는 클래스
    static class Result {
        public int distance;
        public Points p1, p2;

        public Result(int distance, Points p1, Points p2) {
            this.distance = distance;
            this.p1 = p1;
            this.p2 = p2;
        }
    }

    // 두 점 사이의 거리의 제곱을 계산 (sqrt 생략으로 효율적 비교)
    private static int distance(Points p1, Points p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }

    // 분할 정복 알고리즘으로 최근접 점 쌍을 찾는 함수
    public static Result CloseSet(ArrayList<Points> points, int left, int right) {
        // 기본 케이스: 점이 3개 이하일 때는 brute-force로 해결
        if (right - left <= 3) {
            return bruteForce(points, left, right);
        }

        // 중간 인덱스와 해당 점을 찾음
        int mid = left + (right - left) / 2;
        Points midPoint = points.get(mid);

        // 좌측 및 우측 점들에 대해 각각 최소 거리 계산
        Result leftResult = CloseSet(points, left, mid);
        Result rightResult = CloseSet(points, mid + 1, right);

        // 좌우 결과 중 더 작은 거리를 minResult로 설정
        Result minResult = leftResult.distance < rightResult.distance ? leftResult : rightResult;

        // 중간 지점 근처의 점들을 strip 리스트에 추가 (x 좌표 기준)
        ArrayList<Points> strip = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (Math.abs(points.get(i).x - midPoint.x) < minResult.distance) {
                strip.add(points.get(i));
            }
        }

        // strip을 y 좌표 기준으로 정렬하여 최단 거리 확인
        strip.sort(Comparator.comparingInt(p -> p.y));
        return findClosestInStrip(strip, minResult);
    }

    // brute-force 방식으로 최근접 점 쌍 찾기
    private static Result bruteForce(ArrayList<Points> points, int left, int right) {
        int minDist = Integer.MAX_VALUE;
        Result minResult = null;

        // 모든 점 쌍의 거리를 계산하여 최단 거리와 점 쌍을 찾음
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                int dist = distance(points.get(i), points.get(j));
                if (dist < minDist) {
                    minDist = dist;
                    minResult = new Result(dist, points.get(i), points.get(j));
                }
            }
        }
        return minResult;
    }

    // strip 내에서 y 좌표 기준으로 최단 거리를 갖는 점 쌍을 찾는 함수
    private static Result findClosestInStrip(ArrayList<Points> strip, Result minResult) {
        int minDist = minResult.distance;

        // 각 점을 시작으로 y 좌표가 근접한 점들만 비교하여 최단 거리 확인
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < minDist; j++) {
                int dist = distance(strip.get(i), strip.get(j));
                if (dist < minDist) {
                    minDist = dist;
                    minResult = new Result(dist, strip.get(i), strip.get(j));
                }
            }
        }
        return minResult;
    }
}
