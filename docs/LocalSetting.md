# Spring Boot Application
> Spring Boot Application

## 프로젝트 로컬 세팅

---

### 1. 설정 파일 생성

```
docs/app.yml 파일을 
C:\blog\benggri\springboot\ 로 이동
```

### 2. 로컬 톰캣 설정

#### 1. postgresql driver 설정

```
postgresql-42.2.23.jar 파일을 로컬 톰캣 설치 위치/lib 에 붙여넣기 한다.
```

#### 2. context.xml, server.xml, web.xml 수정

```
로컬 톰캣 설치 위치/conf 폴더 하위 context.xml, server.xml 수정
```

##### 2-1. context.xml

```
<Context>
...
    <!-- 라인 추가 -->
    <ResourceLink name="jdbc/study"
                  global="jdbc/study"
                  type="javax.sql.DataSource"
    />
</Context>
```

##### 2-2. server.xml : 37라인 하위 추가

```
  <GlobalNamingResources>
  ...
    <!-- 라인 추가 -->
    <Resource auth="Container" driverClassName="org.postgresql.Driver" factory="org.apache.tomcat.jdbc.pool.DataSourceFactory" global="jdbc/study" initialSize="20" maxIdle="20" maxTotal="20" maxWaitMillis="15000" name="jdbc/study" password="study" testOnBorrow="true" type="javax.sql.DataSource" url="jdbc:postgresql://localhost:5432/study" username="study" validationQuery="select 1"/>
  </GlobalNamingResources>
```

##### 2-3. web.xml

```
        <resource-ref>
            <description>DB Connection</description>
            <res-ref-name>jdbc/study</res-ref-name>
            <res-type>javax.sql.DataSource</res-type>
            <res-auth>Container</res-auth>
        </resource-ref>
</web-app>
```

### 3. 프로젝트 기본 설정(Eclipse)

#### 3-1. Project Facets 확인

```
Dynamic Web Module 버전 4.0 일 경우 강제로 3.1로 변경해야함
워크스페이스/platformadmin/.settings/org.eclipse.wst.common.project.facet.core.xml 파일 수정
<installed facet="jst.web" version="3.1"/> 
```

#### 3-2. Deplyment Assembly 확인

```
/src/main/java                WEB-INF/classes
/src/main/resources           WEB-INF/classes
/src/main/webapp              /
/target/m2e-wtp/web-resources /
Maven Dependencies            WEB-INF/lib
```

#### 3-3. Servers 탭에서 서버 추가 

#### 3-4. 기동

---
