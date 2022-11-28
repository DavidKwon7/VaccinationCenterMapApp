# VaccinationCenterMapApp

[![Android CI](https://github.com/DavidKwon7/VaccinationCenterMapApp/actions/workflows/android.yml/badge.svg?branch=master)](https://github.com/DavidKwon7/VaccinationCenterMapApp/actions/workflows/android.yml)

[untitled12.webm](https://user-images.githubusercontent.com/70066242/204265279-fe1003b8-6068-4480-93f4-ee6656bfd886.webm)

▲ 실제 App에서 플레이 영상입니다.
이 영상에서는 마커 처리 및 타입에 따른 색 변화를 준 것을 확인할 수 있으며, 마커를 클릭한 경우에 말풍선 및 SlidingUpPanel을 사용하여 선택한 마커에 대한 정보를 담고 있음을 확인할 수 있습니다. 또한 추가적으로  floating button을 터치하였을 때, 현재 본인의 위치로 화면이 이동되는 것을 확인할 수 있습니다. 

## 사용 기술
- kotlin
- clean architecture
- multi module
- Coroutine flow 
- hilt 
- timber
- retrofit
- room 
- jetpack navigation 
- naver map 
- tedPermisson 
- slidingUpPanel 
- github Actions
- build Src + kotlin dsl 

## 기술 사용 이유 

- 왜 room? 
DataStore, ROOM, safeArgs 중 한 가지를 선택하여 데이터를 저장해야 했습니다. 초기에는 safeArgs를 사용하여 방향으로 개발을 진행하였습니다. 
그렇지만 ROOM을 사용하면 비교적 좀 더 많은 데이터를 관리하기 쉬워질 것이먀, 추후에 대량의 데이터를 기존의 앱에서 처리하게 될 가능성도 있기 때문에 안정적으로 개발을 진행하자는 생각이 들어 ROOM으로 전환하게 되었습니다.

- 왜 XML? 
Compose, XML 중 선택을 해야 했습니다. 현재 Compose를 공부하고 있지만, 제한된 시간에 빠르게 개발을 진행해야 하는 상황이라 판단하고 좀 더 익숙한 XML을 선택하였습니다. 

## 추가 예정 기술

- [ ] SplashFragment prgressbar를 제대로 구현하지 못 하였습니다. 추가적인 공부를 통해 지식을 좀 더 쌓고 제대로 구현을 할 계획입니다. 
 
- [ ] slidingUpPanel에 '좋아요' 버튼을 넣어준 후, ROOM을 사용하여 좋아요를 누른 예방접종센터를 저장하는 기능을 추가할 계획입니다. 
 
- [ ] 검색 기능을 추가하여 본인이 검색한 예방접종센터를 확인할 수 있도록 구현할 예정입니다. 

