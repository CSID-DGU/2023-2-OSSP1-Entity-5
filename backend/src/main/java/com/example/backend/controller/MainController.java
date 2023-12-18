package com.react.roadmap.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.react.roadmap.data.CentralNode;
import com.react.roadmap.function.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class MainController {


    private final AppRunner appRunner;
    private final AStarAlgorithm aStarAlgorithm;
    private final ObjectMapper objectMapper;

    @Autowired
    public MainController(AppRunner appRunner, AStarAlgorithm aStarAlgorithm, ObjectMapper objectMapper) {
        this.appRunner = appRunner;
        this.aStarAlgorithm = aStarAlgorithm;
        this.objectMapper = objectMapper;
    }






    @GetMapping("map")
    public Map<String, Object> dataInsert(@RequestParam String start, @RequestParam String finish) {
        Node[] nodeArr = appRunner.getNodeArr();

        // centralNode.json에서 finish 코드에 해당하는 doors 배열의 코드들 가져오기
        List<String> finishNodes = getDoorsForCentralNode(finish);
        if (finishNodes.isEmpty()) {
            // finish 코드가 centralNode.json에 없는 경우에 대한 처리
            // 적절한 에러 응답을 반환하거나 애플리케이션 로직에 따라 처리
            // 여기서는 간단히 빈 결과를 반환합니다.
            return Map.of("error", "centralNode.json에 finish 코드가 없습니다.");
        }
/*  노드 방문횟수 확인용 코드
        String[] sNodes =  {"OO", "P", "Q", "S", "T", "W", "Y", "Z", "X", "G2", "R18C", "R19C", "R49C", "R76C"};;
        String[] fNodes =  {"A", "BB", "BM", "C", "E", "F", "FF", "H", "I", "J", "K", "L", "H", "MK", "G", "N"};;

        for(String s: sNodes)
        {
            for(String f: fNodes){
                List<String> f_near = getDoorsForCentralNode(f);
                List<String> shortestPath = dijkstraAlgorithm.findShortestPath(nodeArr, s, f, f_near);
            }
        }
*/
        List<String> shortestPath = aStarAlgorithm.findShortestPath(nodeArr, start, finish, finishNodes);
        GetLatLng getLatLng = new GetLatLng();

        // 최단 경로의 노드들의 위도 및 경도 가져오기
        List<List<Double>> dLatLng = getLatLng.getLatLng(nodeArr, shortestPath);

        // 결과를 저장할 Map 생성
        Map<String, Object> result = Map.of(
                "shortestPath", shortestPath,
                "dLatLng", dLatLng
        );

        return result;
    }


    private List<String> getDoorsForCentralNode(String finishCode) {
        try {
            // centralNode.json 파일 읽기
            CentralNode[] centralNodes = objectMapper.readValue(new File("C:\\2023-1-OSSP1-Roadmap-10-master\\roadmap\\src\\main\\resources\\static\\json\\centralNode.json"), CentralNode[].class);

            // Stream을 사용하여 finish 코드에 해당하는 doors 배열의 코드들 반환
            return Arrays.stream(centralNodes)
                    .filter(centralNode -> centralNode.getDoorsForFinishCode(finishCode).size() > 0)
                    .flatMap(centralNode -> centralNode.getDoorsForFinishCode(finishCode).stream())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            // 예외 처리: 파일 읽기 실패 등의 경우
            return List.of();
        }
    }
}
