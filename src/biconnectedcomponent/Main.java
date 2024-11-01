package biconnectedcomponent;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int N = 4; // 노드 수
        List<Integer>[] adjList = new List[N]; // 인접 리스트 초기화
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>(); // 각 노드에 대한 리스트 생성
        }

        // 그래프에 간선 추가
        adjList[0].add(1);
        adjList[1].add(0);
        adjList[1].add(2);
        adjList[2].add(1);
        adjList[1].add(3);
        adjList[3].add(1);
        adjList[2].add(3);
        adjList[3].add(2);

        // Biconnected 객체 생성 및 실행
        Biconnected b = new Biconnected(adjList);
        b.bc(0, -1); // 0번 노드부터 탐색 시작, 부모 노드는 -1
        // 노드 0은 DFS탐색의 첫 번째 노드이므로 부모 노드가 존재하지 않으므로 -1을 넣어
        // 부모가 없다는 의미를 전달한다.
        
    }
}

// 엣지 클래스: 두 노드 사이의 간선 정보를 저장
class Edges {
    int u, v; // 두 노드의 번호

    public Edges(int u, int v) {
        this.u = u;  // 입력받은 순서를 그대로 유지
        this.v = v;
    }

    @Override
    public String toString() {
        return u + " " + v; // 엣지의 두 노드 출력
    }
}

// Biconnected 클래스: 이중 연결 요소를 찾는 클래스
class Biconnected {
    int N; // 노드 수
    List<Integer>[] adjList; // 그래프의 인접 리스트
    boolean[] visited; // 노드 방문 여부 체크
    int sequence = 1; // DFS 순서를 기록하는 변수
    int[] dfsnum; // DFS 방문 순서, 처음 방문하는 노드부터 차례대로 순서가 매겨진다.
    int[] lownum; // DFS탐색 중에 각 노드에서 도달할 수 있는 가장 작은 dfsnum값을 저장
    // 즉, 그 노드가 자손 노드를 통해서 갈 수 있는 가장 작은 번호의 조상 노드를 가리킨다.
    
    Stack<Edges> stack; 
    // 간선을 저장할 스택, 그래프의 간선을 저장해 두었다가, 하나의 이중 연결 성분을 찾으면 
    // 그 성분에 속한 간선을 스택에서 꺼내어 출력한다.

    public Biconnected(List<Integer>[] adjLists) {
        N = adjLists.length;
        adjList = adjLists;
        visited = new boolean[N];
        dfsnum = new int[N];
        lownum = new int[N];
        stack = new Stack<>();
    }

    // DFS를 사용한 이중 연결 요소 찾기 함수
    public void bc(int v, int u) {
       // 현재 노드 v를 방문 처리하고 DFS 방문 순서와 low 값을 설정
       visited[v] = true;
       dfsnum[v] = lownum[v] = sequence++; // dfsnum과 lownum을 현재 탐색 순서로 설정

       for (int w : adjList[v]) { // v와 인접한 모든 노드를 탐색
           if (w != u && dfsnum[w] < dfsnum[v]) { // w가 부모 u가 아니면서 아직 탐색되지 않은 경우
               stack.push(new Edges(v, w)); // (v, w) 간선을 스택에 푸시
           }
           if (!visited[w]) { // w가 아직 방문되지 않은 노드인 경우
              bc(w, v); // w에 대해 DFS 수행

              // 자식 노드를 탐색 후 lownum[v] 갱신
              lownum[v] = Math.min(lownum[v], lownum[w]);

              // 자식 노드를 통해서 자신보다 더 상위 노드로 갈 수 없는 경우
              if (lownum[w] >= dfsnum[v]) {
                  System.out.println("단절점 발견: " + v);

                  // 간선 (v, w)까지의 모든 간선을 스택에서 꺼내 출력 (이중 연결 성분)
                  while (!stack.isEmpty()) {
                      Edges e = stack.pop();
                      System.out.println("이중 연결 성분 간선: " + e);
                      if (e.u == v && e.v == w) {
                          break;
                      }
                  }
              }
          } else if (w != u) { // w가 이미 방문된 노드이지만 부모가 아닌 경우 (역간선)
              // 역간선을 통해 lownum[v] 값을 갱신
              lownum[v] = Math.min(lownum[v], dfsnum[w]);

              // (v, w)를 스택에 푸시 (역간선도 이중 연결 성분에 포함될 수 있음)
              stack.push(new Edges(v, w));
          }
       }
   }

}
