package dfs;

import java.util.*;

public class Main {
	public static void main(String[] args) {
		int N = 10;
		List<Edge>[] adjList = new List[N];
		for (int i = 0; i < N; i++) {
         adjList[i] = new LinkedList<>();
     }

     // 그래프 구성
		adjList[0].add(new Edge(2));
     adjList[0].add(new Edge(1));

     adjList[1].add(new Edge(3));
     adjList[1].add(new Edge(0));

     adjList[2].add(new Edge(3));
     adjList[2].add(new Edge(0));

     adjList[3].add(new Edge(9));
     adjList[3].add(new Edge(8));
     adjList[3].add(new Edge(2));
     adjList[3].add(new Edge(1));

     adjList[4].add(new Edge(5));

     adjList[5].add(new Edge(7));
     adjList[5].add(new Edge(6));
     adjList[5].add(new Edge(4));

     adjList[6].add(new Edge(7));
     adjList[6].add(new Edge(5));

     adjList[7].add(new Edge(6));
     adjList[7].add(new Edge(5));

     adjList[8].add(new Edge(3));

     adjList[9].add(new Edge(3));
		
		// 그림과 같은 10개의 vertext로 구성된 그래프 생성
		
		print_graph_Matrix(adjList, N); //인접 리스트를 배열로 출력하는 함수
		System.out.print("DFS: ");
		DFS d = new DFS(adjList);
		System.out.println();
	}
	
	// 인접 리스트를 인접 행렬로 출력하는 함수
   public static void print_graph_Matrix(List<Edge>[] adjList, int N) {
       // 인접 행렬 생성
       int[][] matrix = new int[N][N];

       // 인접 리스트를 기반으로 인접 행렬 채우기
       for (int i = 0; i < N; i++) {
           for (Edge edge : adjList[i]) {
               matrix[i][edge.adjvertex] = 1;
           }
       }

       // 인접 행렬 출력
       for (int i = 0; i < N; i++) {
           for (int j = 0; j < N; j++) {
               System.out.print(matrix[i][j] + " ");
           }
           System.out.println();
       }
   }
}

// 간선 클래스
class Edge {
	int adjvertex; // 간선의 다른쪽 정점
	public Edge(int v) { // 생성자
		adjvertex = v;
	}
}

// DFS 클래스
class DFS {
	int N;
	List<Edge>[] graph;
	private boolean[] visited; //방문한 것을 체크하기 위한 bool array
	
	public DFS(List<Edge>[] adjList) {
		N = adjList.length;
		graph = adjList;
		visited = new boolean[N];
		for(int i=0; i<N; i++) {
			visited[i] = false; // visited 초기화
		}
		// 각 노드의 인접 리스트를 내림차순으로 정렬
		// 왜 이렇게 해줄까? => 방문하는 노드의 순서를 제어하기 위해
		// 지금 인접리스트에 Edge를 오름차순으로 추가하고 있는데 이 경우에는 방문하는 노드의
		// 순서가 달라진다. 상관은 없는데 과제 예시하고 똑같이 맞추기 위함이다.
//      for(int i = 0; i < N; i++) {
//          graph[i].sort((e1, e2) -> e2.adjvertex - e1.adjvertex);
//      }
      
   // 그래프가 다 이어져있다는 보장이 없기때문에 반복문을 추가해주는 것 같다
		for(int i = 0; i<N; i++) { 
			if(!visited[i]) {
				dfs(i); // DFS 실행
			}
		}
	}
	
	private void dfs(int i) {
		visited[i] = true; // 정점 i가 방문되어 visited[i]를 true로
		System.out.print(i+" "); // 정점 i가 방문되었음을 출력
		for(Edge e: graph[i]) { // 정점 i에 인접한 각 정점에 대해
			if(!visited[e.adjvertex]) {
				dfs(e.adjvertex);
			}
		}
	}
	
}

