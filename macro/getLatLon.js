const fs = require('fs');

// JSON 파일 경로
const nodeJsonFilePath = 'node.json';
const outputJsonFilePath = 'output.json';

// JSON 파일 읽기
const nodeJsonString = fs.readFileSync(nodeJsonFilePath, 'utf-8');
const outputJsonString = fs.readFileSync(outputJsonFilePath, 'utf-8');

// JSON 파싱
const nodes = JSON.parse(nodeJsonString);
let outputNodes = JSON.parse(outputJsonString);

// "좌표 이름"에 해당하는 코드 번호를 찾아서 위도와 경도를 추가
outputNodes = outputNodes.map(outputNode => {
    const coordinateName = outputNode['좌표 이름'];
    const nodeWithCoordinate = nodes.find(node => node.code === coordinateName);

    if (nodeWithCoordinate) {
        outputNode['위도'] = nodeWithCoordinate.latitude;
        outputNode['경도'] = nodeWithCoordinate.longitude;
    }

    return outputNode;
});

// 변경된 내용을 다시 파일에 쓰기
fs.writeFileSync(outputJsonFilePath, JSON.stringify(outputNodes, null, 2), 'utf-8');

// 결과 출력
console.log('output.json 파일이 업데이트되었습니다.');
