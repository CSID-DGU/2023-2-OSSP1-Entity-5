package com.react.roadmap.function;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DijkstraAlgorithm {

    int visitCount = 0;

    // 각 노드까지의 현재까지의 비용(gScore), 이전 노드(previousNode)를 저장하는 맵들
    private Map<String, Double> gScoreMap = new HashMap<>();
    private Map<String, String> previousNodeMap = new HashMap<>();

    // 다익스트라 알고리즘을 통해 최단 경로를 찾는 메서드
    public List<String> findShortestPath(Node[] nodes, String start, String finish, List<String> finishNodes) {
        // 초기화
        for (Node node : nodes) {
            gScoreMap.put(node.getCode(), Double.POSITIVE_INFINITY);
        }

        // 시작 노드의 초기 비용 설정
        gScoreMap.put(start, 0.0);

        // 방문한 노드를 저장하는 집합
        Set<String> visitedNodes = new HashSet<>();

        // 우선순위 큐가 빌 때까지 반복
        while (true) {
            // 현재 비용이 가장 작은 노드 선택
            String currentNodeCode = getMinCostNode(nodes, visitedNodes);
            visitedNodes.add(currentNodeCode);

            visitCount++;

            // 도착지 후보 중 하나에 도달하면 종료
            if (finishNodes.contains(currentNodeCode)) {
                // 도착한 노드가 도착지 후보 중 하나이면 바로 반환
                System.out.print(visitCount + " ");
                return reconstructPath(currentNodeCode, start);
            }

            // 현재 노드의 이웃 노드들을 탐색
            for (Node.NeighborNode neighborNode : getNodeByCode(nodes, currentNodeCode).getNearNodes()) {
                visitCount++;
                String neighborCode = neighborNode.getCode();
                double edgeWeight = neighborNode.getWeight();

                // 새로운 gScore 계산
                double tentativeGScore = gScoreMap.get(currentNodeCode) + edgeWeight;

                // 현재까지의 최소 비용보다 작으면 업데이트
                if (tentativeGScore < gScoreMap.get(neighborCode)) {
                    gScoreMap.put(neighborCode, tentativeGScore);
                    previousNodeMap.put(neighborCode, currentNodeCode);
                }
            }

            // 모든 노드를 방문한 경우 종료
            if (visitedNodes.size() == nodes.length) {
                break;
            }
        }

        // 도착지에 도달하지 못한 경우 null 반환 또는 예외 처리 등을 추가할 수 있습니다.
        return null;
    }

    // 현재 비용이 가장 작은 노드를 찾는 메서드
    private String getMinCostNode(Node[] nodes, Set<String> visitedNodes) {
        String minNode = null;
        double minCost = Double.POSITIVE_INFINITY;

        for (Node node : nodes) {
            visitCount++;
            String nodeCode = node.getCode();
            if (!visitedNodes.contains(nodeCode) && gScoreMap.get(nodeCode) < minCost) {
                minCost = gScoreMap.get(nodeCode);
                minNode = nodeCode;
            }
        }

        return minNode;
    }

    // 최단 경로를 직접 추적하는 메서드
    private List<String> reconstructPath(String lastNodeCode, String startNodeCode) {
        List<String> shortestPath = new ArrayList<>();
        String currentNodeCode = lastNodeCode;

        // 역으로 추적하여 최단 경로 찾기
        while (currentNodeCode != null) {
            shortestPath.add(currentNodeCode);
            if (currentNodeCode.equals(startNodeCode)) {
                break;
            }
            currentNodeCode = previousNodeMap.get(currentNodeCode);
        }

        // 경로 뒤집기
        Collections.reverse(shortestPath);
        return shortestPath;
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
