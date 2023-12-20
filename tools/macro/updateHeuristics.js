const fs = require('fs');

// JSON 파일을 읽는 함수
function readJsonFile(filename) {
    const data = fs.readFileSync(filename, 'utf8');
    return JSON.parse(data);
}

// JSON 파일을 쓰는 함수
function writeJsonFile(filename, data) {
    fs.writeFileSync(filename, JSON.stringify(data, null, 2), 'utf8');
}

// 두 지점 간의 거리를 계산하는 함수 (예: Haversine 공식)
function calculateDistance(lat1, lon1, lat2, lon2) {
    const R = 6371000; // 지구 반지름 (단위: m)
    const dLat = deg2rad(lat2 - lat1);
    const dLon = deg2rad(lon2 - lon1);
    const a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    const distance = R * c;

    return distance;
}

function deg2rad(deg) {
    return deg * (Math.PI / 180);
}

// 휴리스틱 값을 계산하는 함수
function calculateHeuristic(node, destinationNode) {
    const heuristicValue = calculateDistance(
        node.latitude,
        node.longitude,
        destinationNode.latitude,
        destinationNode.longitude
    );

    return heuristicValue;
}

// 메인 함수
function updateHeuristics() {
    const nodeData = readJsonFile('node.json');
    const destinationData = readJsonFile('data.json');

    for (const node of nodeData) {
        const heuristicArray = [];

        for (const destination of destinationData) {
            const destinationCode = destination[0];
            const destinationNode = nodeData.find(n => n.code === destinationCode);

            if (destinationNode) {
                const heuristicValue = calculateHeuristic(node, destinationNode);
                heuristicArray.push({
                    code: destinationCode,
                    'h-weight': heuristicValue
                });
            }
        }

        node.heuristic = heuristicArray;
    }

    writeJsonFile('node.json', nodeData);
}

// 메인 함수 호출
updateHeuristics();
