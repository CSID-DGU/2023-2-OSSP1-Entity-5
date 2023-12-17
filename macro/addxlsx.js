const fs = require('fs');
const xlsx = require('xlsx');

// JSON 파일 경로
const outputJsonFilePath = 'output_with_elevation2.json';
const xlsxFilePath = 'updatedElevation.xlsx';

// JSON 파일 읽기
const outputJsonString = fs.readFileSync(outputJsonFilePath, 'utf-8');
const outputData = JSON.parse(outputJsonString);

// 엑셀 파일 읽기
const workbook = xlsx.readFile(xlsxFilePath);
const sheetName = workbook.SheetNames[0];
const worksheet = workbook.Sheets[sheetName];

// 기존 데이터의 행 수 확인
const lastRow = worksheet['!ref'].split(':')[1];
const lastRowIndex = Number(lastRow.charAt(1));

// 새로운 데이터 추가
outputData.forEach((data, index) => {
    const rowIndex = lastRowIndex + index + 1;

    worksheet[`A${rowIndex}`] = { t: 's', v: data['좌표 이름'] };
    worksheet[`B${rowIndex}`] = { t: 'n', v: data['위도'] };
    worksheet[`C${rowIndex}`] = { t: 'n', v: data['경도'] };
    worksheet[`D${rowIndex}`] = { t: 'n', v: data['고도'] };
});

// 변경된 내용을 다시 엑셀 파일에 쓰기
xlsx.writeFile(workbook, xlsxFilePath);

// 결과 출력
console.log('updatedElevation.xlsx 파일이 업데이트되었습니다.');
