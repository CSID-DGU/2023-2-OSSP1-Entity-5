const fs = require('fs');

// JSON 파일 경로
const outputJsonFilePath = 'output.json';

// JSON 파일 읽기
const outputJsonString = fs.readFileSync(outputJsonFilePath, 'utf-8');

// JSON 파싱
let outputNodes = JSON.parse(outputJsonString);

// "좌표 이름"을 대문자로 변경
outputNodes = outputNodes.map(outputNode => {
    if (outputNode['좌표 이름']) {
        outputNode['좌표 이름'] = outputNode['좌표 이름'].toUpperCase();
    }
    return outputNode;
});

// 변경된 내용을 다시 파일에 쓰기
fs.writeFileSync(outputJsonFilePath, JSON.stringify(outputNodes, null, 2), 'utf-8');

// 결과 출력
console.log('output.json 파일의 "좌표 이름"이 대문자로 변경되었습니다.');
