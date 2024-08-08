# 인덱스 분석

인덱스별 속도비교와 EXPLAIN 명령어를 고려하여 최적의 성능을 위한 인덱스를 설계하였다.
### 1) 특정 스케줄의 가능한 좌석 조회

![요약.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F1%EB%B2%88%2F%EC%9A%94%EC%95%BD.png)

**인덱스 미사용시**
![0번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F1%EB%B2%88%2F0%EB%B2%88.png)

**idx_concert_schedule_id 사용**
![1번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F1%EB%B2%88%2F1%EB%B2%88.png)

**idx_concert_schedule_id, idx_status 사용**
![2번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F1%EB%B2%88%2F2%EB%B2%88.png)

**idx_concert_schedule_id_status 사용(최종선택)**
![3 번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F1%EB%B2%88%2F3%20%EB%B2%88.png)


### 2) 특정 스케줄의 특정 좌석 조회

![정리.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F2%EB%B2%88%2F%EC%A0%95%EB%A6%AC.png)

**인덱스 미사용시**
![0번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F2%EB%B2%88%2F0%EB%B2%88.png)

**idx_concert_schedule_id 사용**
![1번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F2%EB%B2%88%2F1%EB%B2%88.png)

**idx_concert_schedule_id_seat_number 사용**
![2번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F2%EB%B2%88%2F2%EB%B2%88.png)

**idx_seat_number 사용(최종선택)**
![3번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F2%EB%B2%88%2F3%EB%B2%88.png)


### 3) 예약,결제 조인 조회

![정리.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F3%EB%B2%88%2F%EC%A0%95%EB%A6%AC.png)

**인덱스 미사용시**
![1 번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F3%EB%B2%88%2F1%20%EB%B2%88.png)

**idx_status_created_at 사용**
![2번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F3%EB%B2%88%2F2%EB%B2%88.png)

**idx_status_created_at, idx_reservation_id 사용**
![3번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F3%EB%B2%88%2F3%EB%B2%88.png)

**idx_reservation_id 사용(최종선택)**
![4번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F3%EB%B2%88%2F4%EB%B2%88.png)



-----


# 트랜잭션 처리의 한계와 해결방안

MSA 환경에서는 단일 트랜잭션을 처리하는 것이 복잡해지므로, 트랜잭션 범위를 분산 처리해야 한다. 
하지만 트랜잭션의 분산 처리도 이슈가 존재한다.

 

#### 트랜잭션
    - 문제점: 여러 서비스 간의 트랜잭션 일관성 유지하기 어려움.
    - 해결방안:
        SAGA 패턴: 장기 트랜잭션을 작은 트랜잭션으로 나누어 각 서비스에서 로컬 트랜잭션을 수행. 실패 시 보상 작업을 통해 일관성을 유지한다.
        보상 트랜잭션: 실패한 트랜잭션에 대해 보상 작업을 수행하여 데이터 일관성을 유지한다.

#### 일관성
    - 문제점: 분산된 시스템에서 데이터 일관성을 유지하기 어려움.
    - 해결방안:
        최종 일관성 (Eventually Consistent): 각 서비스가 일관성을 유지하도록 설계하며, 최종적으로 데이터 일관성이 확보되도록 한다.
        이벤트 기반 커뮤니케이션: 서비스 간의 데이터 변경을 이벤트를 통해 전달하고, 이벤트 리스너를 통해 데이터 일관성을 유지한다.

#### 테스트
    -  문제점: 여러 서비스를 통합하여 테스트하는 것이 복잡.
    -  해결방안:
          모의 데이터 및 테스트 환경: 테스트 환경을 설정하고 모의 데이터를 사용 테스트한다.

#### 복구
    -  문제점: 서비스 간 트랜잭션 실패 시 복구가 어려움.
    -  해결방안:
          트랜잭션 로그: 각 서비스에서 트랜잭션 로그를 유지하여 실패 시 복구할 수 있다.
          Retry 메커니즘: 실패한 작업을 재시도할 수 있는 메커니즘을 구현한다.




