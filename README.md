# 지하철 노선도 미션
[ATDD 강의](https://edu.nextstep.camp/c/R89PYi5H) 실습을 위한 지하철 노선도 애플리케이션

---

## 3단계

### 요구사항

- 기능 요구사항 - 스펙 추가하기
  - [ ] 추가된 요금 정책을 반영하세요.
  - [ ] 인수 테스트 변경 -> 문서화 변경 -> 기능 구현 순으로 진행하세요.
  - [ ] 개발 흐름을 파악할 수 있도록 커밋을 작은 단위로 나누어 구현해보세요.
- TODOList
  - [X] 인수테스트 수정
    - [X] 노선 등록 인수테스트 수정
  - [X] 노선 등록 기능 수정
    - [X] 노선별 추가 요금도 등록할 수 있도록 수정
  - [ ] 요금 정책 추가
    - [X] 노선별 추가요금 정책
      - [X] 노선별 추가요금 정책은 항상 적용된다.
      - [X] 경로 중 추가요금이 있는 노선을 환승하여 이용할 경우 가장 높은 금액의 추가요금만 적용한다.
    - [ ] 할인 정책
      - [ ] 로그인한 유저가 아닌 경우 적용되지 않는다.
      - [ ] 청소년 할인 정책
        - [ ] 13세 이상 ~ 19세 미만인 경우 할인 정책이 적용된다.
        - [ ] 운임에서 350원을 공제한 금액의 20%를 할인한다.
      - [ ] 어린이 할인 정책
        - [ ] 6세 이상 ~ 13세 미만인 경우 할인 정책이 적용된다.
        - [ ] 운임에서 350원을 공제한 금액의 50%를 할인한다.
> - 청소년: 13세 이상 ~ 19세 미만
> - 어린이: 6세 이상 ~ 13세 미만
---

## 2단계

### 요구사항

- 기능 요구사항 - 경로 조회 시 요금 정보 포함하기
  - [X] 경로 조회 결과에 요금 정보를 포함하세요.
  - [X] 인수테스트(수정) -> 문서화 -> 기능 구현 순으로 진행하세요.
  - [X] 개발 흐름을 파악할 수 있도록 커밋을 작은 단위로 나누어 구현해보세요.

- TODOList
  - [X] 인수테스트 수정
  - [X] 경로 조회 API 문서화 수정
  - [X] 요금 계산 기능 구현
    - [X] 10km 이내이면 기본운임(1,250 원)이다.
    - [X] 10km 초과 ~ 50km 이하의 경우 기본운임과 5km 마다 100원 추가된다.
    - [X] 50km 초과시 기본운임과 50km 이하의 초과운임과 8km 마다 100원이 추가된다.

---

## 1단계

### 요구사항

- 기능 요구사항 - 최소 시간 경로 타입 추가
  - [X] 경로 조회 시 최소 시간 기준으로 조회할 수 있도록 기능을 추가하세요.
  - [X] 노선추가 & 구간 추가 시 거리와 함께 소요시간 정보도 추가하세요.
  - [X] 인수 테스트(수정) -> 문서화 -> 기능 구현 순으로 진행하세요.
  - [X] 개발 흐름을 파악할 수 있도록 커밋을 작은 단위로 나누어 구현해보세요.

- 프로그래밍 요구사항
  - 인수테스트 주도 개발 프로세스에 맞춰서 기능을 구현하세요.
    - 요구사항 설명을 참고하여 인수 조건을 정의
    - 인수 조건을 검증하는 인수 테스트 작성
    - 인수 테스트를 충족하는 기능 구현
  - 인수 조건은 인수테스트 메서드 상단에 주석으로 작성하세요.
    - 뼈대 코드의 인수테스트 참고
  - 인수 테스트 이후 기능 구현은 TDD 로 진행하세요.
    - 도메인 레이어 테스트는 필수
    - 서비스 레이어 테스트는 선택

### TODOList

- [X] 인수 테스트 수정 및 추가
  - [X] 구간 추가 인수테스트 수정
  - [X] 경로 최소시간 조회 인수테스트 추가
- [X] 경로 찾기 문서화
- [X] 최소 시간 기준 조회 기능 구현