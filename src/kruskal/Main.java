package kruskal;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		int[][] weight = {
			{ 0, 9, 10, 0, 0, 0, 0},
			{ 9, 0, 0 ,10, 5, 0, 3},
			{ 10, 0, 0, 9, 7, 2, 0},
			{ 0, 10, 9, 0, 0, 4, 8},
			{ 0, 5, 7, 0, 0, 0, 1},
			{ 0, 0, 2, 4, 0, 0, 6},
			{ 0, 3, 0, 8, 1, 6, 0},
		};
		
		int N = weight.length;
		int M = 0; // 그래프 간선의 수
		List<Edge>[]adjList = new List[N];
		
		for(int i = 0; i < N; i++) {
			adjList[i] = new LinkedList<>();
		
			for(int j = 0; j < N; j++) {
				if(weight[i][j] != 0) {
					Edge e = new Edge(i,j, weight[i][j]);
					adjList[i].add(e);
					M++;
				}
			}
		}
		
		KruskalMST k = new KruskalMST(adjList, M); //kruskalMST 객체 생성
		Edge[] tree = new Edge[N-1]; // 최소 신장 트리의 간선을 출력하기
		
		System.out.print("최소 신장 트리 간선: ");
		tree = k.mst(); //mst() 메소드 호출
		
		int sum = 0;
		for(int i = 0; i < tree.length; i++) {
			System.out.print("("+tree[i].vertex + ","+tree[i].adjvertex + ")");
			sum += tree[i].weight;
		}
		
		System.out.print("\n\n");
		System.out.println("최소 신장 트리의 간선 가중치 합 = "+sum);
		
	}
	
	public static class Edge{
		int vertex, adjvertex; // 간선의 양끝 정점들
		int weight; // 간선의 가중치
		
		public Edge(int u, int v, int wt) {
			vertex = u;
			adjvertex = v;
			weight = wt;
		}
	}
	
	public static class KruskalMST{
		int N, M; // 그래프 정점, 간선의 수
		List<Edge>[]graph;
		UnionFind uf; //Union-Find 연산을 사용하기 위해
		Edge[] tree;
		
		//weight를 기준으로 우선순위 큐를 사용하기 위해 간선의 가중치 기준으로 오름차순 정렬
		class Weight_Comparison implements Comparator<Edge>{ 
			public int compare(Edge e, Edge f) {
				if(e.weight > f.weight) {
					return 1;
				}else if(e.weight < f.weight) {
					return -1;
				}
				return 0;
			}
		}
		
		public KruskalMST(List<Edge>[] adjList, int numOfEdges) {
			N = adjList.length;
			M = numOfEdges;
			graph = adjList;
			uf = new UnionFind(N); // Union-Find 연산을 사용하기 위해
			tree = new Edge[N-1];
		}
		
		public Edge[] mst(){ // Kruskal 알고리즘
			// 우선순위 큐를 weight기준으로 구성하기 위해
			Weight_Comparison BY_WEIGHT = new Weight_Comparison();
			
			// 자바 라이브러리의 우선순위 큐 사용
			// 우선순위 큐의 크기로 M(간선의 수)을 지정, BY_WEIGHT는 line 24의 comparator
			PriorityQueue<Edge> pq = new PriorityQueue<Edge>(M, BY_WEIGHT);
			
			for(int i = 0; i < N; i++) {
				for(Edge e : graph[i]) {
					pq.add(e); // edgeArray의 간선 객체들을 pq에 삽입
				}
			}
			
			int count = 0;
			
			// 최소 신장 트리는 N개의 정점에 대해 항상 N-1개의 간선으로 구성되기 때문에 
			// N-1개의 간선이 추가될 때까지만 반복한다.
			while(!pq.isEmpty() && count < N-1) {
				Edge e = pq.poll(); // 최소 가중치를 가진 간선을 pq에서 제거하고 가져옴
				int u = e.vertex;   // 가져온 간선의 한쪽 정점
				int v = e.adjvertex; // 가져온 간선의 맞은편쪽 정점
				if(!uf.isConnected(u, v)) { // u와 v가 각각 다른 집합에 속해 있으면
					uf.union(u, v); // u가 속한 집합과 v가 속한 집합의 합집합 수행
					tree[count++] = e; //e를 MST의 간선으로서 tree에 추가
				}
			}
			
			return tree;
		}
	}
	public static class UnionFind{
		protected int[]p; // 배열 크기는 정점의 수 N이고 p[i]는 i의 부모를 저장
		protected int[]rank; // level을 저장(depth)
		
		public UnionFind(int N){
			p = new int[N];
			rank = new int[N];
			for(int i = 0; i < N; i++) {
				p[i] = i; // 각 i 자기 자신이 부모이므로
				rank[i] = 0; // 각각의 rank를 0으로 초기화
			}
		}
		
		// i가 속한 집합의 루트를 순환으로 찾고 최종적으로 경로상의 각 원소의 부모를 루트로 경로 압축
		protected int find(int i) { // 경로 압축
			if(i != p[i]) {
				p[i] = find(p[i]); // 리턴하며 경로상의 각 노드의 부모가 루트가 되도록
			}
			return p[i];
		}
		
		// i와 j가 같은 트리에 있는지를 검사
		public boolean isConnected(int i, int j) {
			return find(i) == find(j);
		}
		
		public void union(int i, int j) { // Union 연산
			int iroot = find(i);
			int jroot = find(j);
			if(iroot == jroot) {
				return; // 루트가 동일하면 더 이상의 수행없이 그대로 리턴
			}
			
			//rank가 높은 루트가 승자로 union을 수행
			if(rank[iroot] > rank[jroot]) {
				p[jroot] = iroot; // iroot가 승자
			}else if(rank[iroot] < rank[jroot]) {
				p[iroot] = jroot; // jroot가 승자
			}else {
				p[jroot] = iroot; // 둘 중 하나를 임의로 승자
				rank[iroot]++; // iroot의 rank 1 증가
			}
			
		}
	}
}






















