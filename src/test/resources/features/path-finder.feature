Feature: 지하철 경로 조회

  # 최단거리 경로 조회
  # 조회 가능
  Scenario: 지하철 최단거리 경로를 조회할 수 있다.
    Given 지하철 역과 노선을 생성하고, 구간을 추가한다.
    When  "강남역"에서 "남부터미널역"까지 "최단거리" 기준으로 경로를 조회할 경우
    Then  경로 내 존재하는 역, 이동 거리, 소요 시간, 운임 비용와 환승요금이 포함된 요금 정보를 확인할 수 있다.
      | 강남역, 교대역, 남부터미널역 | 12 | 12 | 1350 + 800 |

  Scenario: 지하철 최단거리 경로를 조회할 수 있다.
    Given 지하철 역과 노선을 생성하고, 구간을 추가한다.
    When  "교대역"에서 "양재역"까지 "최단거리" 기준으로 경로를 조회할 경우
    Then  경로 내 존재하는 역, 이동 거리, 소요 시간, 운임 비용와 환승요금이 포함된 요금 정보를 확인할 수 있다.
      | 교대역, 남부터미널역, 양재역 | 5 | 10 | 1250 + 800 |

  Scenario: 지하철 최단거리 경로를 조회할 수 있다.
    Given 지하철 역과 노선을 생성하고, 구간을 추가한다.
    When  "양재역"에서 "신설동역"까지 "최단거리" 기준으로 경로를 조회할 경우
    Then  경로 내 존재하는 역, 이동 거리, 소요 시간, 운임 비용와 환승요금이 포함된 요금 정보를 확인할 수 있다.
      | 양재역, 강남역, 잠실역, 신설동역 | 60 | 33 | 2250 + 300 |

  # 조회 불가능
  Scenario: 출발역과 도착역이 동일할 경우 최단거리 경로를 조회할 수 없다.
    Given 지하철 역과 노선을 생성하고, 구간을 추가한다.
    When  출발역과 도착역이 동일한 경로를 "최단거리" 기준으로 조회할 경우
    Then  경로를 조회할 수 없다.

  Scenario: 출발역과 도착역이 연결되어 있지 않을 경우 최단거리 경로를 조회할 수 없다.
    Given 지하철 역과 노선을 생성하고, 구간을 추가한다.
    When  출발역과 도착역이 연결되어 있지 않은 경로를 "최단거리" 기준으로 조회할 경우
    Then  경로를 조회할 수 없다.

  Scenario: 출발역이 존재하지 않을 경우 최단거리 경로를 조회할 수 없다.
    Given 지하철 역과 노선을 생성하고, 구간을 추가한다.
    When  출발역이 존재하지 않는 경로를 "최단거리" 기준으로 조회할 경우
    Then  경로를 조회할 수 없다.

  Scenario: 도착역이 존재하지 않을 경우 최단거리 경로를 조회할 수 없다.
    Given 지하철 역과 노선을 생성하고, 구간을 추가한다.
    When  도착역이 존재하지 않는 경로를 "최단거리" 기준으로 조회할 경우
    Then  경로를 조회할 수 없다.

  # 최소 소요시간 경로 조회
  # 조회 가능
  Scenario: 지하철 최소 소요시간 경로를 조회할 수 있다.
    Given 지하철 역과 노선을 생성하고, 구간을 추가한다.
    When  "강남역"에서 "남부터미널역"까지 "최소 소요시간" 기준으로 경로를 조회할 경우
    Then  경로 내 존재하는 역, 이동 거리, 소요 시간, 운임 비용와 환승요금이 포함된 요금 정보를 확인할 수 있다.
      | 강남역, 양재역, 남부터미널역 | 13 | 6 | 1350 + 800 |

  Scenario: 지하철 최소 소요시간 경로를 조회할 수 있다.
    Given 지하철 역과 노선을 생성하고, 구간을 추가한다.
    When  "교대역"에서 "양재역"까지 "최소 소요시간" 기준으로 경로를 조회할 경우
    Then  경로 내 존재하는 역, 이동 거리, 소요 시간, 운임 비용와 환승요금이 포함된 요금 정보를 확인할 수 있다.
      | 교대역, 강남역, 양재역 | 20 | 8 | 1450 + 400 |

  # 조회 불가능
  Scenario: 출발역과 도착역이 동일할 경우 최소 소요시간 경로를 조회할 수 없다.
    Given 지하철 역과 노선을 생성하고, 구간을 추가한다.
    When  출발역과 도착역이 동일한 경로를 "최소 소요시간" 기준으로 조회할 경우
    Then  경로를 조회할 수 없다.

  Scenario: 출발역과 도착역이 연결되어 있지 않을 경우 최소 소요시간 경로를 조회할 수 없다.
    Given 지하철 역과 노선을 생성하고, 구간을 추가한다.
    When  출발역과 도착역이 연결되어 있지 않은 경로를 "최소 소요시간" 기준으로 조회할 경우
    Then  경로를 조회할 수 없다.

  Scenario: 출발역이 존재하지 않을 경우 최소 소요시간 경로를 조회할 수 없다.
    Given 지하철 역과 노선을 생성하고, 구간을 추가한다.
    When  출발역이 존재하지 않는 경로를 "최소 소요시간" 기준으로 조회할 경우
    Then  경로를 조회할 수 없다.

  Scenario: 도착역이 존재하지 않을 경우 최소 소요시간 경로를 조회할 수 없다.
    Given 지하철 역과 노선을 생성하고, 구간을 추가한다.
    When  도착역이 존재하지 않는 경로를 "최소 소요시간" 기준으로 조회할 경우
    Then  경로를 조회할 수 없다.