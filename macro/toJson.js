const fs = require('fs');
const xlsx = require('xlsx');

function convertExcelToJson(filePath) {
    const workbook = xlsx.readFile(filePath);
    const sheet_name_list = workbook.SheetNames;
    const json_data = xlsx.utils.sheet_to_json(workbook.Sheets[sheet_name_list[0]], {
        header: ["좌표 이름", "위도", "경도", "고도"],
        range: 1, // 1행부터 시작
    });

    const json_output = JSON.stringify(json_data, null, 2);

    // Save the JSON data to a file
    fs.writeFileSync('output.json', json_output);

    console.log("JSON 파일이 생성되었습니다.");
}

// Usage example: node convertExcelToJson.js your_excel_file.xlsx
const filePath = process.argv[2];
if (filePath) {
    convertExcelToJson(filePath);
} else {
    console.error('Please provide the path to the Excel file.');
}
