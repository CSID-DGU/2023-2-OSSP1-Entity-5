const fs = require('fs');

// 입력 파일명과 출력 파일명
const inputFileName = 'node.json';
const outputFileName = 'centralNode.json';

// 파일 읽기
fs.readFile(inputFileName, 'utf8', (err, data) => {
    if (err) {
        console.error(`Error reading ${inputFileName}: ${err.message}`);
        return;
    }

    try {
        // JSON 파싱
        const nodes = JSON.parse(data);

        // centralNode가 "O"인 노드들을 필터링하여 centralNode.json 파일에 저장
        const centralNodes = nodes
            .filter(node => node.centralNode === "O")
            .map(node => ({
                code: node.code,
                doors: node.nearNodes.map(nearNode => nearNode.code)
            }));

        // centralNode.json 파일 생성
        const jsonString = JSON.stringify(centralNodes, null, 2);
        fs.writeFile(outputFileName, jsonString, 'utf8', (err) => {
            if (err) {
                console.error(`Error writing to ${outputFileName}: ${err.message}`);
            } else {
                console.log(`Successfully created ${outputFileName}`);
            }
        });
    } catch (parseError) {
        console.error(`Error parsing JSON in ${inputFileName}: ${parseError.message}`);
    }
});
