const fs = require('fs');
const xlsx = require('xlsx');

function jsonToExcel(jsonFilePath, excelFilePath) {
    // JSON 파일 읽기
    const jsonData = JSON.parse(fs.readFileSync(jsonFilePath, 'utf-8'));

    // JSON 데이터를 pandas DataFrame으로 변환
    const ws = xlsx.utils.json_to_sheet(jsonData);

    // Workbook 생성 및 sheet 추가
    const wb = xlsx.utils.book_new();
    xlsx.utils.book_append_sheet(wb, ws, 'Sheet 1');

    // 엑셀 파일로 저장
    xlsx.writeFile(wb, excelFilePath);

    console.log("엑셀 파일이 생성되었습니다.");
}

// Usage example: node jsonToExcel.js input.json output.xlsx
const jsonFilePath = process.argv[2];
const excelFilePath = process.argv[3];

if (jsonFilePath && excelFilePath) {
    jsonToExcel(jsonFilePath, excelFilePath);
} else {
    console.error('Please provide the path to the JSON file and the desired Excel file.');
}
