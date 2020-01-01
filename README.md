# freelec-springboot2-webservice

![image.jpg](https://github.com/Conatuseus/TIL/blob/master/images/%EC%9D%B4%EB%8F%99%EC%9A%B1%EB%8B%98%20%EC%B1%85/Springboot.jpg?raw=true)

이동욱님의 <스프링 부트와 AWS로 혼자 구현하는 웹 서비스>를 읽고 따라해보며 배운 내용을 정리하는 공간입니다.

## Environment

- Java: JDK 1.8

- Springboot 2.1.7.RELEASE
- mariaDB
- Spring Data JPA
- AWS EC2

- AWS RDS



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

`@Target(ElementType.PARAMETER)`
- 이 어노테이션이 생성될 수 있는 위치를 지정합니다.
- PARAMETER로 지정했으니 메서드의 파라미터로 선언된 객체에서만 사용할 수 있습니다.
- 이 외에도 클래스 선언문에 쓸 수 있는 TYPE 등이 있습니다.

`@interface`

- 이 파일을 어노테이션 클래스로 지정합니다.


### Entity 클래스에서는 절대 Setter 메서드를 만들지 않는다
자바빈 규약을 생각하면서 getter/setter를 무작정 생성하는 경우가 있는데, 이렇게 되면 해당 클래스의 인스턴스 값들이 언제 어디서 변해야 하는지
코드상으로 명확하게 구분할 수 없어, 차후 기능 변경시 복잡해집니다.

Setter 메서드를 절대 만들지 않고, 해당 필드의 값 변경이 필요하면 명확히 그 목적과 의도를 나타낼 수 있는 메서드를 추가해야만 합니다.





## Chapter6. AWS 서버 환경을 만들어보자 - AWS EC2

 외부에서 본인이 만든 서비스에 접근하려면 **24시간 작동하는 서버**가 필수입니다. 24시간 작동하는 서버에는 3가지 선택지가 있습니다.

- 집에서 PC를 24시간 구동시킨다.
- 호스팅 서비스(Cafe 24, 코리아호스팅 등)을 이용한다
- 클라우드 서비스(AWS, AZURE, GCP 등)를 이용한다.

비용은 호스팅 서비스나 집 PC를 이용하는 것이 저렴합니다. 만약 특정 시간에만 트래픽이 몰린다면 유동적으로 사양을 늘릴 수 있는 클라우드가 유리합니다.

클라우드 서비스는 쉽게 말하면 인터넷(클라우드)를 통해 서버, 스토리지, 데이터베이스, 네트워크, 소프트웨어, 모니터링 등의 컴퓨팅 서비스를 제공하는 것입니다. 단순히 물리 장비를 대여하는 것으로 생각하는데 그렇지 않습니다.

예를 들어 AWS의 EC2는 서버 장비를 대여하는 것이지만, 실제로는 그 안의 로그 관리, 모니터링, 하드웨어 교체, 네트워크 관리 등을 기본적으로 지원하고 있습니다. 개발자가 직접 해야 할 일을 AWS가 전부 지원을 하는 것입니다.

클라우드의 형태

- Infrastructure as a Service(IaaS, 아이아스, 이에스)
  - 기존 물리 장비를 미들웨어와 함께 묶어둔 추상화 서비스입니다.
  - 가상머신, 스토리지, 네트워크, 운영체제 등의 IT 인프라를 대여해 주는 서비스라고 보면 됩니다.
  - AWS의 EC2, S3 등
- Platform as a Service(PaaS, 파스)
  - 앞에서 언급한 IaaS에서 한번 더 추상화한 서비스입니다.
  - 한번 더 추상화했기 때문에 많은 기능이 자동화되어 있습니다.
  - AWS의 Beanstalk(빈스톡), Heroku(헤로쿠) 등
- Software as a Service(SaaS, 사스)
  - 소프트웨어 서비스를 이야기합니다.
  - 구글 드라이브, 드랍박스, 와탭 등



### EC2 서버에 접속하기

> ssh -i pem 키 위치  EC2의 탈련적 IP 주소

쉽게 ssh 접속을 할 수 있도록 설정

- pem 파일을 ~/.ssh/로 복사

- Pem 파일을 옮겨 놓으면 ssh 실행 시 **pem 키 파일을 자동으로 읽어** 접속을 진행

- >  cp pem 키를 내려받은 위치 ~/.ssh/



Pem 키가 잘 복사되었는지 확인

> cd ~/.ssh/
>
> ll

복사되었다면 pem 키의 권한 변경

> chmod 600 ~./ssh/pem키이름

권한을 변경하였다면 pem 키가 있는 ~/.ssh 디렉토리에 config 파일 생성

> vim ~/.ssh/config



Config 파일은 아래와 같이 작성

> 주석
>
> Host 본인이 원하는 서비스명
>
> ​	HostName ec2의 탄력적 IP주소
>
> ​	User ec2-user
>
> ​	IdentityFile ~/.ssh/pem키이름

생성된 config 파일은 실행 권한이 필요하므로 권한 설정

> chmod 700 ~/.ssh/config

다음의 명령어로 접속

> ssh config에 등록한 서비스명



### 아마존 리눅스1 서버 생성 시 꼭 해야 할 설정들

- Java 설치
- 타임존 변경: 기본 서버의 시간은 미국 시간대입니다. 한국 시간대가 되어야만 우리가 사용하는 시간이 모두 한국 시간으로 등록되고 사용됩니다.
- 호스트네임 변경: 현재 접속한 서버의 별명을 등록합니다. 실무에서는 한 대의 서버가 아닌 수십 대의 서버가 작동되는 데, IP만으로 어떤 서버가 어떤 역할을 하는지 알수 없습니다. 이를 구분하기 위해 보통 호스트 네임을 필수로 등록합니다.



#### java8 설치

아마존 리눅스 1의 기본 자바 버전은 7입니다.

현재 프로젝트에서 자바8을 사용하므로 자바 8을 EC2에 설치하겠습니다.

EC2에서 다음 명령어 실행

> sudo yum install -y java-1.8.0-openjdk-devel.x86_64

설치가 완료되면 인스턴스의 Java 버전을 8로 변경하겠습니다.

> sudo /usr/sbin/alternatives --config java

자바 버전 변경 후 사용하지 않는 Java7 삭제

> sudo yum remove java-1.7.0-openjdk

자바 버전 확인

> java -version



#### 타임존 변경

EC2 서버의 기본 타임존은 UTC입니다. 이는 한국의 시간과는 9시간 차이가 발생합니다. 이렇게 되면 서버에서 수행되는 Java 애플리케이션에서 생성되는 시간도 모두 9시간씩 차이가 나기 때문에 꼭 수정해야 할 설정입니다.

다음의 명령어를 차례로 수행합니다

> sudo rm /etc/localtime
>
> sudo ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime

타임존 확인

> date



#### Hostname 변경

여러 서버를 관리 중일 경우 **IP만으로 어떤 서비스의 서버인지** 확인이 어렵습니다.

그래서 각 서버가 어느 서버인지 표현하기 위해 HOSTNAME을 변경하겠습니다.

다음 명령어로 편집 파일을 열어봅니다.

> sudo vim /etc/sysconfig/network

화면에서 노출되는 항목 중 `HOSTNAME` 으로 되어있는 부분을 **본인이 원하는 서비스명**으로 변경합니다.

변경 후 다음 명령어로 서버를 재부팅합니다.

> sudo reboot

Hostname이 등록되었다면 한 가지 작업을 더 해야합니다.

호스트 주소를 찾을 때 가장 먼저 검색해 보는 /etc/hosts에 변경한 hostname을 등록합니다.

다음 명령어로 /etc/hosts 파일을 엽니다.

> sudo vim /etc/hosts

열어서 아래에 `127.0.0.1  등록한 HOSTNAME` 을 추가해줍니다.

:wq 명령어로 저장하고 종료하고, 정상적으로 등록되었는지 확인해봅니다.

> curl 등록한 호스트 이름



## EC2 서버에 프로젝트를 배포해보자

### EC2에 프로젝트 Clone 받기

먼저 EC2에 깃을 설치하겠습니다.

EC2로 접속해서 아래 명령어 실행

> sudo yum install git

설치가 완료되면 설치 상태를 확인합니다.

> git --version

git이 성공적으로 설치되면 git clone으로 프로젝트를 저장할 디렉토리를 생성

> mkdir ~/app && mkdir ~/app/step1

생성된 디렉토리로 이동

> cd ~/app/step1

본인의 깃허브 웹페이지에서 https 주소를 복사합니다.

복사한 주소를 통해 git clone을 진행

> git clone 복사한주소

클론이 끝났으면 클론된 프로젝트로 이동해서 파일들이 잘 복사 되었는지 확인합니다.

> cd 프로젝트명
>
> ll

그리고 코드들이 잘 수행되는지 테스트로 검증하겠습니다.

> ./gradlew test

만약 다음과 같이 gradlew 실행 권한이 없다는 메시지가 뜬다면

> -bash: ./gradlew: Permission denied

다음 명령어로 실행 권한을 추가한 뒤 다시 테스트를 수행합니다

> chmod +x ./gradlew

