# test-practice

#### 테스트에 대한 심도 깊은 연구가 아니라 단순히 작성을 반복하면서 연습을 하기 위한 레파지토리입니다.

**Note**. 아래 순서에 따라서 테스트를 작성하며 연습해보자.

> Entity -> Repository -> Service -> Controller
-------

### 테스트 어노테이션 (Junit 5)

|      어노테이션       | 의미                                                                                                                                                                                                                  |
|:----------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|    **@Test**     | 해당 메서드를 테스트 메서드로 선언한다. (Junit에 의해 관리 됨)                                                                                                                                                                             |
|  **@BeforeAll**  | 클래스를 테스트 할 때 최초에 한번 실행되는 메서드다.                                                                                                                                                                                      |
|  **@AfterAll**   | 클래스를 테스트 할 때 모든 테스트가 종료된 후 마지막에에 한번 실행되는 메서드다.                                                                                                                                                                      |
| **@BeforeEach**  | 테스트 메서드를 실행할 때 한번 실행되는 메서드다. (여러개 테스트를 동시에 진행하는 경우 각 테스트 마다 실행된다.)                                                                                                                                                  |
|  **@AfterEach**  | 테스트 메서드가 종료될 때 한번 실행되는 메서드다. (여러개 테스트를 동시에 진행하는 경우 각 테스트 마다 실행된다.)                                                                                                                                                  |
| **@DataJpaTest** | JPA 관련 된 Bean만 등록하여 테스트를 가볍게 만들어주는 어노테이션이다. (엔티티와 JPA Repostiroy만으로 구성한다)                                                                                                                                           |
| **@Transaction**  | 스프링 어노테이션이지만, 테스트에서 사용 시 테스트가 종료된 후 데이터를 롤백한다. (default : rollback=true), 따라서 JPA의 영속성 컨테이너의 데이터가 변경되어 있어도, JPA는 트랜잭션 커밋 시점에 날리는 쿼리가 있을 경우, 롤백되는 경우 해당 쿼리는 실행하지 않는다. 해당 쿼리를 실행하기 위해서는 롤백을 시키지 않거나, 플러쉬를 통해 해결해야 한다. |

### Mockito 어노테이션

|      어노테이션       | 의미      |
|:----------------:|:-----------------------------------------------------|
|    **@Mock**     |  Mock 객체를 생성합니다. (반환형은 null, Optional.empty() 등 빈 값을 반환합니다.)|
|  **@InjectMocks**  | @Mock이 붙은 객체를 InjectMocks이 붙은 객체에 주입합니다. |
-------
-------

**Note**. Repository는 과연 단위 테스트를 해야할까?

> Repository는 보통 내부에 로직이 거의 없고 대부분을 EntityManager에 의존하기 때문에 테스트 시 통합테스트를 하는 경우가 많다.

**Note**. Service Layer를 테스트 할 때 필연적으로 Repository에 의존적이다. 통합 테스트를 해야할까?

> Repository에서의 결과를 모의 객체를 통해 미리 정해두고 Service Layer Logic을 테스트 하면 된다.  
> cf. [Mockito](https://site.mockito.org/)

**Note**. Controller는 무엇을 테스트 해야할까?

> 컨트롤러는 HTTP 요청을 받고, 응답 하는 역할을 한다. 이를 할 때 요청을 파싱하고, 데이터를 변수에 담아준다. 하지만 유닛 테스트에서는 http layer를 포함하지 않는다. 
> 따라서 컨트롤러 통합 테스트를 작성해야 한다. 이 때 실제로 컨트롤러에서 테스트 해야 할 범위는 URL로 잘 진입하는가, 데이터가 정상적으로 전달되었는가, validation 체크가 정상적으로 되었는가,
> 유효한 응답이 왔는가에 대해 테스트를 작성해야 한다. (interceptor, filter!)

**Note**. 테스트 커버리지(Test Coverage)란?

> 얼마나 테스트가 충분한가를 나타내는 척도. 내부적으로는 구문/조건/결정 등 여러가지의 측정 종류가 존재한다.
> 구문 커버리지는 코드 한줄이 한번 이상 실행되는것을 측정하고, 조건 커버리지는 모든 조건식이 충족되는지를 측정하고, 결정 커버리지는 모든 조건식이 참과 거짓을 가지면 충족한다.
> 이중에서 구문 커버리지가 가장 많이 사용된다. Java에는 JaCoCo 코드 커버리지 체크 라이브러리가 존재한다.