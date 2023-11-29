package com.react.roadmap.function;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Node {
    private String code;
    private String centralNode;
    private String latitude;
    private String longitude;
    private List<nearNode> nearNodes;
    private List<heuristic> heuristics;
}
