# secret key

## 생성 방법
```
터미널에서 아래 라인 수행
echo 'spring-boot-project-spring-security-blog-benggri-springboot-custom-secret1' | base64
결과
c3ByaW5nLWJvb3QtcHJvamVjdC1zcHJpbmctc2VjdXJpdHktYmxvZy1iZW5nZ3JpLXNwcmluZ2Jvb3QtY3VzdG9tLXNlY3JldDEK

```

## 적용 위치

* 설정 파일에 두지 않고 프로그램 소스 내부에 두는 것이 좋아보임