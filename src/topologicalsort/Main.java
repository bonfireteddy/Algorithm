package topologicalsort;

import java.util.*;

public class Main {

    public static void main(String[] args) {
   	 
        int N = 9; // 총 9개의 정점
        List<Integer>[] adjList = new List[N]; // 인접 리스트 배열
        int[] indegree = new int[N]; // 각 정점의 진입 차수를 저장하는 배열
        
        // 인접 리스트 초기화
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }

        // 간선 추가와 함께 진입 차수 증가
        adjList[2].add(0);
        indegree[0]++;
        adjList[2].add(1);
        indegree[1]++;
        adjList[0].add(1);
        indegree[1]++;
        adjList[1].add(3);
        indegree[3]++;
        adjList[1].add(4);
        indegree[4]++;
        adjList[4].add(5);
        indegree[5]++;
        adjList[5].add(3);
        indegree[3]++;
        adjList[5].add(7);
        indegree[7]++;
        adjList[3].add(6);
        indegree[6]++;
        adjList[6].add(7);
        indegree[7]++;
        adjList[7].add(8);
        indegree[8]++;

        TopologicalSort t = new TopologicalSort(adjList, indegree);
        List<Integer> sortedSeq = t.tsort();
        System.out.print("순방향 위상 정렬: ");
        System.out.println(sortedSeq); // 위상 정렬 출력

        RTopologicalSort rt = new RTopologicalSort(adjList);
        List<Integer> reversedSortedSeq = rt.tsort();
        System.out.print("역방향 위상 정렬: ");
        System.out.println(reversedSortedSeq); // 역방향 위상 정렬 출력
    }
}

//순방향 위상 정렬 클래스 (Kahn's Algorithm 기반)
class TopologicalSort {
 int N; // 정점의 수
 List<Integer>[] graph; // 인접 리스트
 int[] indegree; // 진입 차수를 저장하는 배열

 public TopologicalSort(List<Integer>[] adjList, int[] indegree) {
     N = adjList.length; // 정점의 수
     graph = adjList; // 그래프의 인접 리스트
     this.indegree = indegree; // 진입 차수 배열
 }

 // 순방향 위상 정렬 함수
 public List<Integer> tsort() {
     return bfs(); // BFS를 이용한 위상 정렬 실행
 }

 // BFS 기반 위상 정렬 구현
 private List<Integer> bfs() {
     List<Integer> result = new ArrayList<>(); // 정렬 결과를 담을 리스트
     Queue<Integer> queue = new LinkedList<>(); // BFS 탐색을 위한 큐

     // 진입 차수가 0인 정점을 큐에 추가
     for (int i = 0; i < N; i++) {
         if (indegree[i] == 0) {
             queue.add(i);
         }
     }

     // BFS 진행
     while (!queue.isEmpty()) {
         int current = queue.poll(); // 큐에서 정점을 꺼냄
         result.add(current); // 꺼낸 정점을 결과 리스트에 추가

         // 인접한 정점의 진입 차수를 1 감소시키고, 0이 되면 큐에 추가
         for (int neighbor : graph[current]) {
             indegree[neighbor]--; // 간선 제거 (진입 차수 감소)
             if (indegree[neighbor] == 0) { // 진입 차수가 0이면 큐에 추가
                 queue.add(neighbor);
             }
         }
     }

     // 모든 정점이 처리되었을 때 결과 반환
     return result;
 }
}

//역방향 위상 정렬 클래스
class RTopologicalSort {
	 int N; // 그래프의 정점 수
	 boolean[] visited; // DFS 수행 중 방문 여부 체크
	 List<Integer>[] adjList; // 인접 리스트 형태의 입력 그래프
	 List<Integer> sequence; // 위상 정렬 순서를 담을 리스트
	
	 public RTopologicalSort(List<Integer>[] graph) { // 생성자
	     N = graph.length;
	     visited = new boolean[N];
	     adjList = graph;
	     sequence = new ArrayList<>();
	 }
	
	 public List<Integer> tsort() { // 역방향 위상 정렬을 위한 DFS 수행
	     for (int i = 0; i < N; i++) {
	         if (!visited[i]) {
	             dfs(i);
	         }
	     }
	     Collections.reverse(sequence); // sequence를 역순으로 만들기
	     return sequence;
	 }
	
	 public void dfs(int v) { // DFS 수행
	     visited[v] = true;
	     for (int w : adjList[v]) { // u의 방문이 끝나고 앞으로 방문해야하는 각 정점 v에 대해
	         if (!visited[w]) {
	             dfs(w);
	         }
	     }
	     sequence.add(v); // 모든 인접 정점을 방문한 후 현재 정점 v를 리스트에 추가
	 }
}
