const fs = require('fs');

// 두 지점 간의 거리를 계산하는 함수 (피타고라스 이용)
function calculateDistance(lat1, lon1, alt1, lat2, lon2, alt2) {
    const R = 6371000; // 지구 반지름 (단위: m)
    const dLat = deg2rad(lat2 - lat1);
    const dLon = deg2rad(lon2 - lon1);
    const dAlt = alt2 - alt1;

    const a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    // 피타고라스 정리를 이용한 거리 계산
    const distance = Math.sqrt(Math.pow(c, 2) + Math.pow(dAlt, 2));

    return distance;
}

function deg2rad(deg) {
    return deg * (Math.PI / 180);
}

// 휴리스틱을 계산하고 업데이트하는 함수
function updateHeuristic(nodes, centralNodes, outputNodes) {
    nodes.forEach(node => {
        // 기존의 휴리스틱 배열 제거
        node.heuristic = [];

        centralNodes.forEach(centralNode => {
            centralNode.doors.forEach(destinationCode => {
                // output.json에서 도착지 코드에 해당하는 좌표 정보 가져오기
                const destinationNode = outputNodes.find(outputNode => outputNode['좌표 이름'] === destinationCode);

                if (destinationNode) {
                    // 현재 노드의 좌표 및 고도 정보
                    const currentLat = node.latitude;
                    const currentLon = node.longitude;
                    const currentAlt = node.altitude || 0; // 만약 노드에 고도 정보가 없다면 0으로 가정

                    // 도착지 좌표 및 고도 정보
                    const destinationLat = destinationNode['위도'];
                    const destinationLon = destinationNode['경도'];
                    const destinationAlt = destinationNode['고도'] || 0; // 만약 도착지에 고도 정보가 없다면 0으로 가정

                    // 두 지점 간의 거리 계산
                    const distance = calculateDistance(currentLat, currentLon, currentAlt, destinationLat, destinationLon, destinationAlt);

                    // 휴리스틱 업데이트
                    node.heuristic.push({
                        code: destinationCode,
                        'h-weight': distance,
                    });
                }
            });
        });
    });

    // 업데이트된 휴리스틱을 파일에 쓰기
    fs.writeFileSync('node.json', JSON.stringify(nodes, null, 2), 'utf-8');
}

// 파일에서 데이터 읽기
const nodes = JSON.parse(fs.readFileSync('node.json', 'utf-8'));
const centralNodes = JSON.parse(fs.readFileSync('centralNode.json', 'utf-8'));
const outputNodes = JSON.parse(fs.readFileSync('output.json', 'utf-8'));

// 휴리스틱 업데이트 함수 호출
updateHeuristic(nodes, centralNodes, outputNodes);

// 결과 출력
console.log('휴리스틱이 업데이트되었습니다.');
