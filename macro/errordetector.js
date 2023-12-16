const fs = require('fs');

// JSON 파일 경로
const jsonFilePath = 'node.json';

// JSON 파일 읽기
const jsonString = fs.readFileSync(jsonFilePath, 'utf-8');

// JSON 파싱
const nodes = JSON.parse(jsonString);

// 밖의 코드들을 저장할 Set
const outsideCodes = new Set();

// nearNodes 배열 내의 코드들을 저장할 Set
const nearNodeCodes = new Set();

// 각 노드에 대해 처리
nodes.forEach(node => {
    // 밖의 코드 추가
    outsideCodes.add(node.code);

    // nearNodes 배열 내의 코드 추가
    if (node.nearNodes) {
        node.nearNodes.forEach(nearNode => {
            nearNodeCodes.add(nearNode.code);
        });
    }
});

// 밖의 코드로 지정되지 않은 nearNodes 코드 찾기
const missingOutsideCodes = [...nearNodeCodes].filter(code => !outsideCodes.has(code));

// 결과 출력
console.log('Outside Codes:', outsideCodes);
console.log('NearNode Codes:', nearNodeCodes);
console.log('Missing Outside Codes in NearNodes:', missingOutsideCodes);
