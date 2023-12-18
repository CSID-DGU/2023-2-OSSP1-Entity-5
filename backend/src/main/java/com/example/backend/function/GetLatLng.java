package com.react.roadmap.function;

import java.util.*;

public class GetLatLng {
    public List<List<Double>> getLatLng(Node[] nodes, List<String> shortestPath) {
        int line = 0;
        double dLat, dLng;
        String code;
        List<List<Double>> dLngLat = new ArrayList<>();

        for (String pathNode : shortestPath) {
            List<Double> temp = new ArrayList<>();
            line = 0;

            // 노드 코드가 최단 경로의 현재 노드와 일치하는지 찾음
            while (true) {
                code = nodes[line].getCode();
                if (code.equals(pathNode)) {
                    break;
                } else {
                    line++;
                }
            }
            // 여기서 line 변수는 현재 노드 코드(pathNode)와 일치하는 노드를 찾은 후의 인덱스입니다.
            // 해당 노드의 위도와 경도를 가져와서 temp 리스트에 추가합니다.
            temp.add(Double.parseDouble(nodes[line].getLatitude()));
            temp.add(Double.parseDouble(nodes[line].getLongitude()));

            // 최종적으로 temp 리스트를 dLngLat 리스트에 추가합니다.
            dLngLat.add(temp);

        }

        return dLngLat;
    }
}
