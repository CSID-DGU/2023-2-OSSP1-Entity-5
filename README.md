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
길찾기 알고리즘으로 다익스트라를 사용하였다.

## 기존 프로젝트에서의 개선점
1. 알고리즘 개선
다익스트라 알고리즘 대신 A* 알고리즘을 사용하여, 시간복잡도를 O(E*log(V))에서 O(E)로 개선하였다. 
2. 노드 개선
건물 노드와 실내 노드의 개념을 분리하고, 노드를 구체화하였다. 또한 노드를 삭제하거나 수정하여 부정확하게 연결되어 있는 간선들과 centralNode 적용 시 불필요한 노드 이동이 발생하는 문제를 해결하였다.
3. 가중치 개선
기존 프로젝트의 가중치는 기존 공식을 임의로 변형하여 가중치 식을 적용하였는데, METS 열량 소비 공식을 그대로 가져와 가중치의 객관성을 확보하였다. 또한 간선 유형을 평지+오르막길, 내리막길, 계단 올라감, 계단 내려감 등의 네가지 유형으로 나누어 가중치 유형을 구체화하였다.
 
## 프로젝트 소개

## 프로젝트 목표




