package com.react.roadmap.function;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AStarAlgorithm {

    // A* 알고리즘을 통해 최단 경로를 찾는 메서드
    public List<String> findShortestPath(Node[] nodes, String start, String finish, List<String> finishNodes) {
        // 각 노드까지의 현재까지의 비용(gScore), 총 예상 비용(fScore), 이전 노드(previousNode)를 저장하는 맵들
        Map<String, Double> gScoreMap = new HashMap<>();
        Map<String, Double> fScoreMap = new HashMap<>();
        Map<String, String> previousNodeMap = new HashMap<>();

        // 모든 노드의 초기 비용 및 맵 초기화
        for (Node node : nodes) {
            gScoreMap.put(node.getCode(), Double.POSITIVE_INFINITY);
            fScoreMap.put(node.getCode(), Double.POSITIVE_INFINITY);
        }

        // 시작 노드의 초기 비용 및 휴리스틱 함수를 이용한 초기 fScore 설정
        gScoreMap.put(start, 0.0);
        fScoreMap.put(start, heuristic(nodes, start, finish));

        // 우선순위 큐를 활용하여 노드를 fScore 기준으로 정렬
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(node -> fScoreMap.get(node.getCode())));
        priorityQueue.offer(getNodeByCode(nodes, start));

        // 이미 방문한 노드를 저장하는 Set
        Set<String> visitedNodes = new HashSet<>();

        // 우선순위 큐가 빌 때까지 반복
        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();

            // 목표 노드 중 하나에 도달하면 종료
            if (finishNodes.contains(currentNode.getCode())) {
                break;
            }

            // 현재 노드를 방문한 노드로 추가
            visitedNodes.add(currentNode.getCode());

            // 현재 노드의 이웃 노드들을 탐색
            for (Node.NeighborNode neighborNode : currentNode.getNearNodes()) {
                String neighborCode = neighborNode.getCode();
                if (!visitedNodes.contains(neighborCode)) {
                    double edgeWeight = neighborNode.getWeight();

                    // 새로운 gScore 계산
                    double tentativeGScore = gScoreMap.get(currentNode.getCode()) + edgeWeight;

                    // 더 작은 gScore를 찾은 경우
                    if (tentativeGScore < gScoreMap.get(neighborCode)) {
                        // 비용 및 이전 노드 업데이트
                        gScoreMap.put(neighborCode, tentativeGScore);
                        double heuristicValue = heuristic(nodes, neighborCode, finish);
                        fScoreMap.put(neighborCode, tentativeGScore + heuristicValue);
                        previousNodeMap.put(neighborCode, currentNode.getCode());

                        // 우선순위 큐에 추가
                        priorityQueue.offer(getNodeByCode(nodes, neighborCode));
                    }
                }
            }
        }

        // 최단 경로 추적
        List<String> shortestPath = new ArrayList<>();

        for (String finishNode : finishNodes) {
            String currentNodeCode = finishNode;

            while (currentNodeCode != null) {
                shortestPath.add(currentNodeCode);
                currentNodeCode = previousNodeMap.get(currentNodeCode);
            }
        }

        // 경로 뒤집기
        Collections.reverse(shortestPath);

        return shortestPath;
    }

    // 휴리스틱 함수 구현
    private Double heuristic(Node[] nodes, String currentNodeCode, String finishNode) {
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
