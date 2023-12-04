// CentralNode.java
package com.react.roadmap.data;

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
public class CentralNode {
    @JsonProperty("code")
    private String code;
    @JsonProperty("doors")
    private List<String> doors;

    // 생성자, Getter, Setter 생략 (IDE에서 자동 생성 가능)

    // finish 코드에 해당하는 doors 배열의 코드들 가져오기
    public List<String> getDoorsForFinishCode(String finishCode) {
        if (code.equals(finishCode)) {
            return doors;
        } else {
            // finish 코드에 해당하는 doors 배열이 없는 경우 빈 리스트 반환
            return List.of();
        }
    }
}
