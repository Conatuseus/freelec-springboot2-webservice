# freelec-springboot2-webservice

![image.jpg](https://github.com/Conatuseus/TIL/blob/master/images/%EC%9D%B4%EB%8F%99%EC%9A%B1%EB%8B%98%20%EC%B1%85/Springboot.jpg?raw=true)

이동욱님의 <스프링 부트와 AWS로 혼자 구현하는 웹 서비스>를 읽고 따라해보며 배운 내용을 정리하는 공간입니다.

## 정리한 내용

- [gradle 프로젝트를 스프링 부트 프로젝트로 변경하기](https://github.com/Conatuseus/TIL/blob/master/Spring/%EA%B7%B8%EB%A0%88%EC%9D%B4%EB%93%A4%20%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%EB%A5%BC%20%EC%8A%A4%ED%94%84%EB%A7%81%20%EB%B6%80%ED%8A%B8%20%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%EB%A1%9C%20%EB%B3%80%EA%B2%BD%ED%95%98%EA%B8%B0.md)
- [.gitignore 사용하기](https://github.com/Conatuseus/TIL/blob/master/Git/gitignore.md)
- [JPA Auditing으로 생성일/수정일 자동화하기](https://velog.io/@conatuseus/2019-12-06-2212-%EC%9E%91%EC%84%B1%EB%90%A8-1sk3u75zo9)



### 메인 클래스
~~~ 
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
~~~
`@SpringBootApplication` 으로 인해 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정됩니다.
특히나 `@SpringBootApplication`이 있는 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트의 최상단에 위치해야만 합니다.

main 메서드에서 실행하는 SpringApplication.run으로 인해 내장 WAS를 실행합니다.
내장 WAS란 별도로 외부에 WAS를 두지 않고 애플리케이션을 실행할 때 내부에서 WAS를 실행하는 것을 이야기합니다.
이렇데 되면 항상 서버에 톰캣을 설치할 필요가 없게 되고, 스프링 부트로 만들어진 Jar 파일(실행 가능한 Java 패키징 파일)로 실행하면 됩니다.
 스프링 부트에서는 내장 WAS를 사용하는 것을 권장하고 있습니다.
 이유는 '언제 어디서나 같은 환경에서 스프링 부트를 배포'할 수 있기 때문입니다.

### annotation
`@RequiredArgsConstructor`
- 선언된 모든 final 필드가 포함된 생성자를 생성해 줍니다.
- final이 없는 필드는 생성자에 포함되지 않습니다.

