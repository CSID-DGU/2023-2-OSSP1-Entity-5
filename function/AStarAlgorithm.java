package com.react.roadmap.function;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AStarAlgorithm {

    // 각 노드까지의 현재까지의 비용(gScore), 총 예상 비용(fScore), 이전 노드(previousNode)를 저장하는 맵들
    private Map<String, Double> gScoreMap = new HashMap<>();
    private Map<String, Double> fScoreMap = new HashMap<>();
    private Map<String, String> previousNodeMap = new HashMap<>();

    // A* 알고리즘을 통해 최단 경로를 찾는 메서드
    public List<String> findShortestPath(Node[] nodes, String start,String finish, List<String> finishNodes) {
        // 주어진 노드들을 fScore 기준으로 정렬하는 우선순위 큐
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(node -> fScoreMap.get(node.getCode())));

        // 초기화
        for (Node node : nodes) {
            gScoreMap.put(node.getCode(), Double.POSITIVE_INFINITY);
            fScoreMap.put(node.getCode(), Double.POSITIVE_INFINITY);
        }

        // 시작 노드의 초기 비용 및 휴리스틱 함수를 이용한 초기 fScore 설정
        gScoreMap.put(start, 0.0);
        fScoreMap.put(start, heuristic(nodes, start, finish));

        // 우선순위 큐에 시작 노드 추가
        priorityQueue.offer(getNodeByCode(nodes, start));

        // 우선순위 큐가 빌 때까지 반복
        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();

            // 도착지 후보 중 하나에 도달하면 종료
            if (finishNodes.contains(currentNode.getCode())) {
                // 도착한 노드가 도착지 후보 중 하나이면 바로 반환
                return reconstructPath(currentNode, start);
            }

            // 현재 노드의 이웃 노드들을 탐색
            for (Node.NeighborNode neighborNode : currentNode.getNearNodes()) {
                String neighborCode = neighborNode.getCode();
                double edgeWeight = neighborNode.getWeight();


                // 새로운 gScore 계산
                double tentativeGScore = gScoreMap.get(currentNode.getCode()) + edgeWeight;

                // 각 도착지 후보에 대한 휴리스틱 계산
                for (String finishNode : finishNodes) {
                    double heuristicValue = heuristic(nodes, neighborCode, finishNode);

                    // 새로운 f 값 계산 (g + h)
                    double fScore = tentativeGScore + heuristicValue;

                    // 주변 노드 중에서 최소 가중치를 가진 노드만 업데이트
                    if (fScore < fScoreMap.get(neighborCode)) {
                        gScoreMap.put(neighborCode, tentativeGScore);
                        fScoreMap.put(neighborCode, fScore);
                        previousNodeMap.put(neighborCode, currentNode.getCode());

                        // 주변 노드 중에서 최소 가중치를 가진 노드만 우선순위 큐에 추가
                        priorityQueue.removeIf(node -> node.getCode().equals(neighborCode));  // 기존에 같은 코드를 가진 노드가 있다면 제거
                        priorityQueue.offer(getNodeByCode(nodes, neighborCode));
                    }
                }
            }
        }

        // 도착지에 도달하지 못한 경우 null 반환 또는 예외 처리 등을 추가할 수 있습니다.
        return null;
    }

    // 최단 경로를 직접 추적하는 메서드
    private List<String> reconstructPath(Node lastNode, String startNodeCode) {
        List<String> shortestPath = new ArrayList<>();
        String currentNodeCode = lastNode.getCode();

        // 역으로 추적하여 최단 경로 찾기
        while (currentNodeCode != null) {
            shortestPath.add(currentNodeCode);
            if(currentNodeCode.equals(startNodeCode))
            {
                break;
            }
            currentNodeCode = previousNodeMap.get(currentNodeCode);

        }

        // 경로 뒤집기
        Collections.reverse(shortestPath);
        return shortestPath;
    }

    // 휴리스틱 함수 구현
    private double heuristic(Node[] nodes, String currentNodeCode, String finishNode) {
        Node currentNode = getNodeByCode(nodes, currentNodeCode);
        // 도착지 코드에 해당하는 휴리스틱 값을 찾기
        for (Node.HeuristicNode heuristicNode : currentNode.getHeuristic()) {
            if (heuristicNode.getCode().equals(finishNode)) {
                return heuristicNode.getHWeight();
            }
        }

        // 도착지 코드에 해당하는 휴리스틱 값이 없을 경우 기본값 반환
        return Double.POSITIVE_INFINITY;
    }

    // 코드에 해당하는 노드 찾기
    private Node getNodeByCode(Node[] nodes, String code) {
        for (Node node : nodes) {
            if (node.getCode().equals(code)) {
                return node;
            }
        }
        return null;
    }
}
