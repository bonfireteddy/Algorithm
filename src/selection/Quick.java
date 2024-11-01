package selection;

public class Quick {
	public static void sort(Comparable[] a) {
		// 배열 a를 입력받아 정렬을 시작
		sort(a, 0, a.length - 1);
	}
	
	// low는 배열의 첫번째 인덱스, high는 배열의 마지막 인덱스
	private static void sort(Comparable[] a, int low, int high) {
		if(high <= low) { // 배열의 크기가 1이하이면 정렬이 완료된 것이므로 종료 
			return;
		}
		int j = partition(a, low, high); // 피벗 기준 배열 분할
		sort(a, low, j-1); // 피벗보다 작은 부분 순환 호출
		sort(a, j+1, high); // 피벗보다 큰 부분 순환 호출	 
	}
	
	private static int partition(Comparable[] a, int pivot, int high) {
		int i = pivot; // 배열의 첫 번째 요소
		int j = high + 1; // 배열의 마지막 요소
		// +1을 하는 이유는 루프가 시작될때 --j로 1을 빼면서 시작하기 때문에 
		// 배열의 마지막 요소까지 제대로 비교하도록 하기 위한 것임.
		
		while(true) {
			while(isless(a[++i], a[pivot])) { // a[++i]가 p보다 작을 때 true를 반환
				if(i == high) {// 피벗보다 작으면
					break;
				}
			}
			while(isless(a[pivot], a[--j])) {  
				if(j == pivot) {// 피벗보다 크면
					break;
				}
			}
			if(i >= j) { // i와 j가 교차되면 루프 나가기
				break;
			}
			swap(a, i, j);
		}
		swap(a, pivot, j); // 피벗과 a[j] 교환
		return j; // a[j]의 피벗이 "영원히" 자리 잡은 곳
	}
	
	private static boolean isless(Comparable i, Comparable j) { // 키 비교
		return(i.compareTo(j) < 0); // i가 j보다 작을 때 true를 반환
	}
	
	private static void swap(Comparable[] a, int i, int j) { // 원소 교환
		Comparable temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	public void testQuickSort(Integer [] data) {
		double quickTime = 0;
		long start, end;
		start = System.nanoTime();//currentTimeMillis();
		Quick.sort(data);
		end = System.nanoTime();//.currentTimeMillis();
		quickTime = (double) (end - start);
		System.out.println("Quick 정렬 소요 시간:" + quickTime);
		System.out.println();
	}
}
