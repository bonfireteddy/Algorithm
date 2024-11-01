package bfs;

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
		System.out.print("BFS(0): ");
		BFS b = new BFS(adjList);
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

// BFS 클래스
class BFS {
	int N;
	List<Edge>[] graph;
	private boolean[] visited; //방문한 것을 체크하기 위한 bool array
	
	public BFS(List<Edge>[] adjList) {
		N = adjList.length;
		graph = adjList;
		visited = new boolean[N];
		for(int i=0; i<N; i++) {
			visited[i] = false; // visited 초기화
		}

		for(int i = 0; i<N; i++) {
			if(!visited[i]) {
				bfs(i); // bfs 실행
			}
		}
	}
	
	private void bfs(int i) {
		// 코드 구현
		Queue<Integer> q = new LinkedList<Integer>(); // 큐 선언
		visited[i] = true;
		q.add(i); // 큐에 시작 정점 s를 삽입
		while(!q.isEmpty()) {
			int j = q.remove(); // 큐에서 정점 j를 가져옴.
			System.out.print(j + " ");
			for(Edge e: graph[j]) { // 정점 j에 인접한 정점들 중 방문한된 정점 하나씩 방문
				if(!visited[e.adjvertex]) {
					visited[e.adjvertex] = true;
					q.add(e.adjvertex); // 새로이 방문된 정점을 큐에 삽입
				}
			}
		}
	}
}
