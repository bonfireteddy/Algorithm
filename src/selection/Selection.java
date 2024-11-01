package selection;

import java.util.Random;

public class Selection {

    public static void main(String[] args) {
        int A[] = {48, 12, 70, 38, 75, 67, 96, 52, 81};
        int k = 5;
        System.out.println(k + "번째로 작은 요소: " + selection(A, 0, A.length - 1, k));
    }

    // QuickSelect 알고리즘
    public static int selection(int[] A, int left, int right, int k) {
        // 피벗을 설정하고 파티션을 실행합니다.
        int pivotIndex = partition(A, left, right);

        // Small 그룹의 크기를 계산합니다.
        int S = (pivotIndex-1) - left + 1; // 현재 피벗 위치까지의 요소 수

        // k번째 작은 요소가 Small 그룹에 속해있는 경우
        if (k <= S) {
            return selection(A, left, pivotIndex - 1, k);
        }
        // 피벗이 k번째 작은 요소일 경우
        else if (k == S + 1) {
            return A[pivotIndex];
        }
        // Large 그룹에서 k번째 작은 요소를 찾을 경우
        else {
            return selection(A, pivotIndex + 1, right, k - S - 1);
        }
    }

    // 파티션 함수
    public static int partition(int[] A, int left, int right) {
        Random rand = new Random();
        int pivotIndex = left + rand.nextInt(right - left + 1);
        int pivotValue = A[pivotIndex];
        
        // 피벗을 왼쪽 끝으로 이동시킵니다.
        swap(A, pivotIndex, left);
        int p = left;

        for (int i = left + 1; i <= right; i++) {
            if (A[i] < pivotValue) {
                p++;
                swap(A, i, p);
            }
        }

        // 피벗을 올바른 위치로 이동시킵니다.
        swap(A, left, p);
        return p; // 피벗 위치 반환
    }

    // 두 요소를 교환하는 함수
    public static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }
}
