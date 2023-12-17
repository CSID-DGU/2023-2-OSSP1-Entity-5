const fs = require('fs');
const axios = require('axios');

// Google Elevation API 키
const apiKey = 'AIzaSyBs42sHe56egc3FbUJnPzGf35AHRc4A5c4';

function getElevation(lat, lng) {
    const url = `https://maps.googleapis.com/maps/api/elevation/json?locations=${lat},${lng}&key=${apiKey}`;
    return axios.get(url)
        .then(response => response.data.results[0].elevation)
        .catch(error => {
            console.error(`Error fetching elevation data: ${error.message}`);
            return null;
        });
}

function updateElevation(jsonData) {
    const updatedData = jsonData.map(async entry => {
        const elevation = await getElevation(entry.위도, entry.경도);
        return { ...entry, 고도: elevation };
    });

    return Promise.all(updatedData);
}

function saveToJson(data) {
    const json_output = JSON.stringify(data, null, 2);
    fs.writeFileSync('output_with_elevation2.json', json_output);
    console.log("Updated JSON 파일이 생성되었습니다.");
}

// JSON 파일 읽기
const filePath = 'output.json';
const jsonData = JSON.parse(fs.readFileSync(filePath, 'utf-8'));

// 업데이트된 고도 정보 가져오기
updateElevation(jsonData)
    .then(updatedData => saveToJson(updatedData))
    .catch(error => console.error(`Error: ${error.message}`));
