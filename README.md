
# BOOK
=============================
```
Spring Boot In Action(201512,ISBN:9781617292545)
Spring Boot实战(201609,ISBN:9787115433145,丁雪丰译)
```

## 下载
[Spring Boot In Action](https://www.manning.com/books/spring-boot-in-action)

[Spring Tool Suite:STS](http://spring.io/tools)

[Spring Boot](http://projects.spring.io/spring-boot/)

[Spring Boot CLI](http://repo.spring.io/release/org/springframework/boot/spring-boot-cli/)

[Spring Initializr](http://start.spring.io/)

[Grails](https://grails.org/)

[JSON格式化](http://tool.oschina.net/codeformat/json)

[PostgreSQL](https://www.postgresql.org/)

[pgAdmin](https://www.pgadmin.org/)

## Spring Boot为两款流行的数据库迁移库提供了自动配置支持。
[Flyway](http://flywaydb.org)

[Liquibase](http://www.liquibase.org)

## 部署
```shell
$ tar -xzf spring-boot-cli-1.5.9.RELEASE-bin.tar.gz -C /opt
$ vi ~/.bash_profile
export JAVA_HOME=/opt/jdk1.8.0_151
export M2_HOME=/opt/apache-maven-3.5.2
export SPRING_HOME=/opt/spring-1.5.9.RELEASE
export PATH=$JAVA_HOME/bin:$M2_HOME/bin:$SPRING_HOME/bin:$PATH
$ java -version
java version "1.8.0_151"
$ mvn -version
Apache Maven 3.5.2
$ spring --version (spring.bat --version)
Spring CLI v1.5.9.RELEASE
```

## MAVEN
```shell
<repository>
	<id>spring-repo</id>
	<name>Spring Repository</name>
	<url>http://repo.spring.io/release</url>
</repository>
mvn clean
mvn eclipse:clean eclipse:eclipse -DdownloadSources=true
mvn test
mvn package -DskipTests
mvn spring-boot:run
- http://127.0.0.1:8080/
```

## Spring Boot CLI
```shell
$ spring help run
$ spring shell
```


# Examples
=============================

## ch01 入门
```shell
$ spring run -d ch01/HelloController.groovy
- http://127.0.0.1:8080/
```

## ch02 开发第一个应用程序
```shell
$ spring init -dweb,data-jpa,h2,thymeleaf --build maven ch02
$ spring init -dweb,data-jpa,h2,thymeleaf --build gradle ch02
```

## ch03 自定义配置
```shell
#3.1.1 保护应用程序
[user] Using default security password: c8d3d40b-cbb2-4fa9-ba9d-b70a223e0b75
#3.2.1 自动配置微调
$ java -jar target/sbia-ch03-*.jar --spring.main.show-banner=false
$ java -jar target/sbia-ch03-*.jar --spring.thymeleaf.cache=false
$ java -jar target/sbia-ch03-*.jar --server.port=8000
$ java -jar target/sbia-ch03-*.jar --logging.level.root=DEBUG
#3.2.2 应用程序Bean 的配置外置
$ java -jar target/sbia-ch03-*.jar --amazon.associate_id=habuma-99
#3.2.3 使用Profile 进行配置
$ java -jar target/sbia-ch03-*.jar --spring.profiles.active=development
$ java -jar target/sbia-ch03-*.jar --spring.profiles.active=production
#3.3 定制应用程序错误页面
- http://127.0.0.1:8080/fail
- http://127.0.0.1:8080/myfail
```

## ch04 测试

## ch05 Groovy与Spring Boot CLI
```shell
$ cd ch05/readinglist
# 用Spring Boot CLI（在项目目录里）运行即可
$ spring run . (失败)
$ spring run ./**
# 用CLI 运行测试
$ spring test tests/ReadingListControllerTest.groovy
$ spring test tests/ReadingListControllerSpec.groovy (失败)
$ spring test tests
# 创建可部署的产物
$ mkdir target
$ spring jar target/ReadingList.jar ./**
$ java -jar target/ReadingList.jar
# Web
- http://127.0.0.1:8080/
- http://127.0.0.1:8080/hi
```

## ch06 在Spring Boot中使用Grails (未试验)
```shell
# 6.1 使用GORM 进行数据持久化
# 6.2 使用Groovy Server Pages 定义视图
$ cd ch06/gormgsp
# 6.3 结合Spring Boot 与Grails 3
$ cd ch06/grails
```

## ch07 深入Actuator
```shell
# MongoDB安装部署
$ tar xzf mongodb-linux-x86_64-rhel70-3.2.9.tgz -C /opt
$ ln -s /opt/mongodb-linux-x86_64-rhel70-3.2.9 /usr/local/mongodb
$ cd /usr/local/mongodb
$ mkdir -p /usr/local/mongodb/{db,log}
$ vi /usr/local/mongodb/mongod.conf
processManagement:
   fork: true
net:
   port: 27017
storage:
   dbPath: /usr/local/mongodb/db
systemLog:
   destination: file
   path: "/usr/local/mongodb/log/mongod.log"
   logAppend: true
storage:
   journal:
      enabled: true
# 启动服务
$ bin/mongod --config mongod.conf
$ tail -100f log/mongod.log
# 查看状态
$ bin/mongo 127.0.0.1:27017/admin --eval "db.stats()"
# 停止服务
$ bin/mongo 127.0.0.1:27017/admin --eval "db.shutdownServer()"
# 连接数据库
$ bin/mongo 127.0.0.1:27017/test
> db.serverStatus().version
> db.stats()
> show collections;
> quit()
```

```shell
# 验证readinglist-java
$ cd ch07/readinglist-java
$ mvn package -DskipTests
$ java -jar target/sbia-*.jar
#
# 7.1 揭秘Actuator 的端点 (REST)
$ java -jar target/sbia-*.jar --management.security.enabled=false --endpoints.shutdown.enabled=true
- http://127.0.0.1:8080/
## 7.1.1 查看配置明细
- http://127.0.0.1:8080/beans
- http://127.0.0.1:8080/autoconfig
- http://127.0.0.1:8080/env
- http://127.0.0.1:8080/env/amazon.associate_id (单个值)
- http://127.0.0.1:8080/mappings
## 7.1.2 运行时度量
- http://127.0.0.1:8080/metrics
- http://127.0.0.1:8080/metrics/mem.free (单个值)
- http://127.0.0.1:8080/trace
- http://127.0.0.1:8080/dump
- http://127.0.0.1:8080/health
## 7.1.3 关闭应用程序
$ curl -X POST http://127.0.0.1:8080/shutdown
{"message":"Shutting down, bye..."}
## 7.1.4 获取应用信息
$ java -jar target/sbia-*.jar --management.security.enabled=false --info.contactEmail=support@myreadinglist.com
- http://127.0.0.1:8080/info
#
# 7.2 连接Actuator 的远程shell (CRaSH)
Using default security password: efe30c70-5bf0-43b1-9d50-c7a02dda7d79
$ ssh user@localhost -p 2000
Password authentication
Password:
 :: Spring Boot ::        (v1.5.9.RELEASE)
> autoconfig | less
> beans
> metrics (一旦看完了metrics命令提供的度量信息，按Ctrl+C就能回到shell了。)
> endpoint list
> endpoint invoke health (传入不带Endpoint后缀的Bean名称)
> help
> exit
#
# 7.3 通过JMX 监控应用程序 (MBean)
$ jconsole (Actuator的端点都发布在org.springframework.boot域下。)
#
# 7.4 定制Actuator
## 7.4.1 修改端点ID
$ java -jar target/sbia-*.jar --management.security.enabled=false --endpoints.info.id=myinfo
- http://127.0.0.1:8080/myinfo
## 7.4.2 启用和禁用端点
$ java -jar target/sbia-*.jar --management.security.enabled=false \
    --endpoints.metrics.enabled=false
$ java -jar target/sbia-*.jar --management.security.enabled=false \
    --endpoints.enabled=false \
	--endpoints.metrics.enabled=true
- http://127.0.0.1:8080/health
- http://127.0.0.1:8080/metrics
## 7.4.3 添加自定义度量信息
ReadingListController
ApplicationContextMetrics
ReadingListMetrics
- http://127.0.0.1:8080/metrics
## 7.4.4 创建自定义跟踪仓库
MongoTraceRepository
$ java -jar target/sbia-*.jar --management.security.enabled=false
- http://127.0.0.1:8080/info
- http://127.0.0.1:8080/trace
$ bin/mongo 127.0.0.1:27017/test
> show collections;
trace
> db.trace.count()
> db.trace.find()
## 7.4.5 插入自定义健康指示器
AmazonHealth
- http://127.0.0.1:8080/health
#
# 7.5 保护Actuator 端点
$ java -jar target/sbia-*.jar --management.context-path=//mgmt
SecurityConfig(manager/s3cr3t)
- http://127.0.0.1:8080/mgmt/health
- http://127.0.0.1:8080/mgmt/metrics
```

## ch08 部署Spring Boot应用程序
```shell
# 启动MySQL
mysql> select version();
5.6.21
#
# 8.1 衡量多种部署方式
# 8.2 部署到应用服务器
$ cd ch08/readinglist_war
$ mvn package -DskipTests
# 对于Tomcat而言，可以把WAR文件复制到Tomcat的webapps目录里。
- http://127.0.0.1:8080/readinglist-1.0/
# 构建过程生成的WAR文件仍可直接运行
$ java -jar target/readinglist-*.war
$ java -jar target/readinglist-*.war --spring.profiles.active=prod4mysql
(或设置环境变量: export SPRING_PROFILES_ACTIVE=prod4mysql)
- http://127.0.0.1:8080/ (craig/password)
#
# 8.3 推上云端
[Cloud Foundry/Pivotal Web Services](http://run.pivotal.io/)
[Heroku](https://www.heroku.com/)
```

## 附录A Spring Boot开发者工具
```
# A.1 自动重启
# A.2 LiveReload
# A.3 远程开发
# A.4 默认的开发时属性
# A.5 全局配置开发者工具
```

## 附录B Spring Boot起步依赖

## 附录C 配置属性

## 附录D Spring Boot依赖

## 延展阅读


# FAQ
=============================
## Q: [MAVEN]Unable to read jar manifest from
```
$ mvn eclipse:eclipse
[INFO] Unable to read jar manifest from D:\yrepo\maven_repo\org\apache\tomcat\embed\tomcat-embed-core\8.5.23\tomcat-embed-core-8.5.23.jar
=>A: 无法读取jar中\META-INF\MANIFEST.MF文件，删除该jar相关文件重新下载
```
