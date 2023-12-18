package com.react.roadmap.function;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Node {
    private String code;
    private String centralNode;
    private String latitude;
    private String longitude;
    private List<NeighborNode> nearNodes;

    // 추가된 내용: 휴리스틱 정보를 저장할 List<HeuristicNode> 필드
    private List<HeuristicNode> heuristic;

    // 추가된 내용: 이웃 노드를 나타내는 클래스
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NeighborNode {
        private String code;
        private double weight;
    }

    // 추가된 내용: 휴리스틱 노드를 나타내는 클래스
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HeuristicNode {
        private String code;

        @JsonProperty("h-weight") // JSON 데이터의 키와 매핑
        private double hWeight;
    }
}
