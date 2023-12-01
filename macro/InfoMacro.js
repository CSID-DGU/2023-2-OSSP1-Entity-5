const fs = require('fs');

// 원본 JSON 파일 이름
const inputFileName = 'node.json';

// 새로운 JSON 파일 이름
const outputFileName = 'edgeInfo.json';

// JSON 파일 읽기
const nodeData = JSON.parse(fs.readFileSync(inputFileName, 'utf-8'));

// 필요한 데이터 추출 및 가공
const edgeInfoData = nodeData.map(nodeItem => {
  const code = nodeItem.code;
  const nearNodes = nodeItem.nearNodes.map(nearNode => ({ 
      code: nearNode.code,
      Info: "Info 추가해야함"
  }));

  return { code, nearNodes };
});

// 새로운 JSON 파일 생성
fs.writeFileSync(outputFileName, JSON.stringify(edgeInfoData, null, 2), 'utf-8');

console.log(`새로운 JSON 파일 '${outputFileName}'이 성공적으로 생성되었습니다.`);
