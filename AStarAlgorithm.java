package com.react.roadmap.function;
import java.util.*;

public class AStarAlgorithm {
    
    // 휴리스틱 값을 계산하는 메서드
    private double heuristic(Node currentNode, Node finishNode) {
        // currentNode의 휴리스틱 리스트에서 finishNode에 해당하는 휴리스틱 값을 찾아 반환합니다.
        return currentNode.getHeuristics().stream()
                .filter(h -> h.getCode().equals(finishNode.getCode()))
                .findFirst()
                .map(heuristic::getH_weight)
                .orElse(Double.POSITIVE_INFINITY); // 찾지 못하면 무한대를 반환
    }

    public List<String> findShortestPath(Node[] nodes, String startNodeCode, String finishNodeCode) {
        Map<String, Double> costMap = new HashMap<>(); // 실제 비용과 휴리스틱 비용의 합
        Map<String, Double> distanceMap = new HashMap<>(); // 실제 비용
        Map<String, String> previousNodeMap = new HashMap<>();

        // 초기화
        for (Node node : nodes) {
            distanceMap.put(node.getCode(), Double.POSITIVE_INFINITY);
            costMap.put(node.getCode(), Double.POSITIVE_INFINITY);
        }

        // 시작 노드 설정
        distanceMap.put(startNodeCode, 0.0);
        Node startNode = getNodeByCode(nodes, startNodeCode);
        Node finishNode = getNodeByCode(nodes, finishNodeCode);
        costMap.put(startNodeCode, heuristic(startNode, finishNode));

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(node -> costMap.get(node.getCode())));
        openSet.offer(startNode);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();

            // 목표 노드에 도달했는지 확인
            if (currentNode.getCode().equals(finishNodeCode)) {
                break;
            }

            // 이웃 노드 처리
            for (nearNode neighbor : currentNode.getNearNodes()) {
                String neighborCode = neighbor.getCode();
                double edgeWeight = neighbor.getWeight();
                double currentDistance = distanceMap.get(currentNode.getCode());
                double tentativeGScore = currentDistance + edgeWeight;
                double neighborHeuristic = heuristic(getNodeByCode(nodes, neighborCode), finishNode);

                double newCost = tentativeGScore + neighborHeuristic;

                if (newCost < costMap.get(neighborCode)) {
                    distanceMap.put(neighborCode, tentativeGScore);
                    costMap.put(neighborCode, newCost);
                    previousNodeMap.put(neighborCode, currentNode.getCode());
                    openSet.offer(getNodeByCode(nodes, neighborCode));
                }
            }
        }

        // 경로 재구성
        List<String> shortestPath = new ArrayList<>();
        String currentNodeCode = finishNodeCode;
        while (currentNodeCode != null) {
            shortestPath.add(currentNodeCode);
            currentNodeCode = previousNodeMap.get(currentNodeCode);
        }
        Collections.reverse(shortestPath);
        return shortestPath;
    }

    // 노드 코드로 노드 객체를 찾는 도우미 메서드
    private Node getNodeByCode(Node[] nodes, String code) {
        return Arrays.stream(nodes)
                     .filter(node -> node.getCode().equals(code))
                     .findFirst()
                     .orElse(null);
    }
}
