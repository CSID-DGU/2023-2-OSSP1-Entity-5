# 2023-2-OSSP1-Entity-5
2023학년도 공개SW프로젝트_01 Entity 팀의 <동국대학교 편한 길 찾기> 프로젝트입니다.<br>

## 🧑🏻‍💻팀원 소개
|강은지|김준범|류민주|박고운|
|:-:|:-:|:-:|:-:|
|<img src="https://avatars.githubusercontent.com/u/97174348?v=4" width="100px" />|<img src="https://avatars.githubusercontent.com/u/144897551?v=4" width="100px" />|<img src="https://avatars.githubusercontent.com/u/135093109?v=4" width="100px" />|<img src="https://avatars.githubusercontent.com/u/104720260?v=4" width="100px" />|
|[@EJ-KANG02](https://github.com/EJ-KANG02)|[@Semitigerx](https://github.com/Semitigerx)|[@minji02](https://github.com/minij02)|[@Goraniii](https://github.com/Goraniiii)|

## 🛠️기술 스택
<img src="https://img.shields.io/badge/Intellij-000000?style=flat-square&logo=intellijidea&logoColor=white"/> <img src="https://img.shields.io/badge/Git-F05032?style=flat-square&logo=git&logoColor=white"/> <img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/>

<img src="https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=React&logoColor=black"/> <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white"/> <img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white"/> <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=javascript&logoColor=black"/> <img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white"/>

## 기존 프로젝트 소개
[Roadmap](https://github.com/CSID-DGU/2023-1-OSSP1-Roadmap-10) -  kakao map을 이용한 동국대학교 내 길찾기 및 건물 정보 안내 서비스<br><br>
'운동선수에서 Treadmill의 속도 및 경사도에 따른 산소섭취량' 논문의 공식을 일반화하여 적용한 회귀식 (산소 섭취량) = 1.67 x (속도) + 0.15 x (경사도) x (속도) + 3.5 (ml/kg) 을 이용하여 가중치를 선정하였다.
이에 따라 최종 가중치 공식은 산소섭취량에 기반을 둔
가중치 = ( 0.15 x 경사도 + 3.5 ) x 거리
로 결정하였다.
길찾기 알고리즘으로 우선순위 큐 다익스트라를 사용하였다.

## 기존 프로젝트에서의 개선점
1. 알고리즘 개선
우선순위 큐 다익스트라 알고리즘 대신 A* 알고리즘을 사용하여, 시간복잡도를 O(E*log(V))에서 O(E)로 개선하였다. 
2. 노드 개선
건물 노드와 실내 노드의 개념을 분리하고, 노드를 구체화하였다. 또한 노드를 삭제하거나 수정하여 부정확하게 연결되어 있는 간선들과 centralNode 적용 시 불필요한 노드 이동이 발생하는 문제를 해결하였다.
3. 가중치 개선
기존 프로젝트의 가중치는 기존 공식을 임의로 변형하여 가중치 식을 적용하였는데, METS 열량 소비 공식을 그대로 가져와 가중치의 객관성을 확보하였다. 또한 간선 유형을 평지+오르막길, 내리막길, 계단 올라감, 계단 내려감 등의 네가지 유형으로 나누어 가중치 유형을 구체화하였다.
 
## 프로젝트 소개
동국대학교 서울캠퍼스는 남산자락에 위치하여 상당한 언덕과 건물간의 고저차가 존재한다. 신입생 혹은 외부인, 해당 건물에 처음 가는 사람의 경우 최적화된 경로를 알기 어려워 언덕과 계단을 등반하게 되는 경우가 발생한다. 이에 동국대학교 서울캠퍼스 내에서 편한 길로 이동할 수 있는 길찾기 정보를 제공하고자 한다.

## 프로젝트 목표
개선 부분인 알고리즘, 노드, 가중치 세 가지를 주제로 효율적인 노드 탐색, 정확한 노드 및 간선 정의, 소모 열량에 기반하여 경로의 편의성 극대화를 목표로 한다.

## 알고리즘 작동 방식
1. 시작 노드와 도착 노드를 각각 central 노드로 입력받고, 도착지 central 노드의 문 노드 배열을 가져와 도착지 후보들로 설정한다.
2. 시작 노드를 현재 노드로 설정하고, 노드의 초기 거리를 0으로 설정한다. 이때, 나머지 노드의 초기 거리는 무한대로 설정한다.
3. 이후 현재 노드에서 인접한 노드로의 가중치(g)와 인접한 노드부터 도착지 후보들까지의 휴리스틱 값(h)을 가져와 f=g+h를 계산한다.
4. 여러 인접 노드들 중 f 값이 최소인 노드를 현재 노드로 둔다.
5. 도착지 후보들 중 하나에 도달하기 전까지 3,4를 반복한다.

## 가중치 설정
METS 대사량 공식(신체활동 중 에너지소비량 예측을 위한 대사계산법)을 기반으로 설정한다.
1. 평지+오르막길 : 0.1x거리 + 1.8x경사도x거리
2. 내리막길 : 평지에서 6.6퍼센트 적용→ (0.1x거리)×0.934
3. 계단 오르막길 : (0.2*분당 스텝 수 + 1.33*(1.8*스텝높이*분당 스텝 수))*(전체 스텝 수/분당 스텝 수)
4. 계단 내리막길 : 1/3*(0.2*분당 스텝 수 + 1.33*(1.8*스텝높이*분당 스텝 수))*(전체 스텝 수/분당 스텝 수)

## 결과 화면
