package dijkstra;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		// 인접행렬을 이용해 그래프를 표현(weight[i][j]는 정점 i와 j사이의 가중치)
		int[][] weight = {
			{ 0, 1, 0, 2, 0, 0, 0, 0}, // 0번 정점과 연결된 정점들
			{ 1, 0, 4 ,3, 1, 6, 0, 0}, // 1번 정점과 연결된 정점들
			{ 0, 4, 0, 0, 0, 1, 1, 2}, // 2번 정점과 연결된 정점들
			{ 2, 3, 0, 0, 5, 0, 0, 0}, // 3번 정점과 연결된 정점들
			{ 0, 1, 0, 5, 0, 0, 2, 0}, // 4번 정점과 연결된 정점들
			{ 0, 6, 1, 0, 0, 0, 0, 9}, // 5번 정점과 연결된 정점들
			{ 0, 0, 1, 0, 2, 0, 0, 1}, // 6번 정점과 연결된 정점들
			{ 0, 0, 2, 0, 0, 9, 1, 0}	// 7번 정점과 연결된 정점들
		};
		
		int N = weight.length; // 그래프의 정점 개수(여기서는 8개)
		List<Edge>[]adjList = new List[N]; // 인접 리스트를 선언
		
		// 인접행렬을 인접리스트로 변환
		for(int i = 0; i < N; i++){ // i번째 정점에서 
			adjList[i] = new LinkedList<>(); // i번째 정점의 인접 리스트 초기화
			for(int j = 0; j < N; j++) { // j번째 정점으로 가는 간선이 있는지 확인
				if(weight[i][j] != 0) { // 가중치가 0이 아니면 간선이 존재함을 의미
					Edge e = new Edge(i,j,weight[i][j]); // 간선 객체 생성
					adjList[i].add(e); // i번째 정점에 간선 추가
				}
			}
		}
		
		// Dijkstra 알고리즘 객체 생성 및 초기화(그래프를 인자로 전달)
		DijkstraSP d = new DijkstraSP(adjList);
		
		System.out.println("정점 0으로부터의 최단 거리");
		
		int[] distance = d.shortestPath(0); // 정점 0에서 시작하는 최단 거리 계산
		
		// 각 정점까지의 최단 거리를 출력
		for(int i = 0; i < distance.length; i++){
			if(distance[i] == Integer.MAX_VALUE) { // 최단 거리가 없다면
				System.out.println("0과"+i+"사이에 경로 없음.");
			}else { // 최단 거리가 있다면 해당 거리 출력
				System.out.println("[0,"+i+"] = "+distance[i]);
			}
		}
		
		System.out.print("\n정점 0으로부터의 최단 경로\n");
		
		// 정점 0에서 각 정점으로 가는 최단 경로를 출력
		for(int i = 1; i < d.N; i++) {
			int back = i;
			System.out.print(back);	// 경로의 끝점 출력
			while(back != 0) {  // 경로를 역으로 추적
				System.out.print("<-"+d.previous[back]); // 이전 정점을 출력
				back = d.previous[back]; // 이전 정점으로 이동
			}
			System.out.println(); // 한 경로 출력이 끝나면 줄바꿈
		}
	}
	
	// 간선 클래스(정점 간의 연결과 가중치 정보를 저장)
	public static class Edge{
		int vertex; // 간선의 한쪽 끝 정점
		int adjvertex; // 간선의 다른쪽 끝 정점
		int weight; // 간선의 가중치
		
		public Edge(int u, int v, int wt) {
			vertex = u; // 출발정점
			adjvertex = v; // 도착 정점
			weight = wt; // 가중치
		}
	}
	
	// 다익스트라 알고리즘 클래스
	public static class DijkstraSP{
		public int N; // 그래프 정점의 수
		List<Edge>[] graph; // 인접 리스트로 표현된 그래프
		public int[] previous; // 최단 경로상 이전 정점을 기록하기 위해
		
		// 생성자(그래프를 인자로 받아 초기화)
		public DijkstraSP(List<Edge>[] adjList) {
			N = adjList.length; // 정점 개수 설정
			previous = new int[N]; // 이전 정점을 기록할 배열 초기화
			graph = adjList; // 인접 리스트 그래프 저장
		}
		
		// 최단 경로를 찾는 매서드
		public int[] shortestPath(int s){
			boolean[] visited = new boolean[N]; // 각 정점을 방문했는지 여부를 기록
			int[] D = new int[N]; // 최단 거리를 저장하는 배열
			for(int i = 0; i < N; i++) { // 초기화
				previous[i] = -1; // 이전 정점 초기화
				
				visited[i] = false; // 아직 방문하지 않음
				D[i] = Integer.MAX_VALUE; // 최단 거리는 처음에 무한대로 설정
			}
			
			previous[s] = 0; // 시작점 s의 관련 정보 초기화, 시작점의 이전 정점은 자기 자신
			D[s] = 0; //distance, 시작점에서 시작점까지의 거리는 0
			
			// 다익스트라 알고리즘 수행
			for(int k = 0; k < N; k++){ // 방문하지 않은 정점들 중에서
				int minVertex = -1;	// D 원소 중 최소인 minVertex 찾기
				int min = Integer.MAX_VALUE;
				
				// 방문하지 않은 정점 중에서 최단 거리가 가장 짧은 정점 찾기
				for(int j = 0; j < N; j++) {
					if((!visited[j]) && (D[j] < min)) {
						minVertex = j;
						min = D[j];
					}
				}
				visited[minVertex] = true; // 해당 정점을 방문했다고 표시
				
				// 선택된 정점과 인접한 정점들의 최단 거리를 업데이트
				for(Edge e: graph[minVertex]) {	// minVertex에 인접한 각 정점에 대해
					if(!visited[e.adjvertex]) {	// 아직 방문하지 않은 정점에 대해
						int currentDist = D[e.adjvertex]; // 현재까지의 거리
						int newDist = D[minVertex] + e.weight; // 새로운 거리
						if(newDist < currentDist) { // 더 짧은 거리가 발견되면
							D[e.adjvertex] = newDist;	// 최단 거리 업데이트
							previous[e.adjvertex] = minVertex; // 이전 정점 기록
						}
					}
				}
			}
			return D; // 각 정점까지의 최단 거리 배열 반환
		}
	}
}





















