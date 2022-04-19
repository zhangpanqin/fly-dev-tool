### gradle

```shell
# 根据 settings.gradle 生成 gradle-wrapper.jar gradle-wrapper.properties gradlew, gradlew.bat
gradle wrapper --gradle-version 7.4.2 --distribution-type bin
# task wrapper 可以根据配置生成
./gradlew wrapper
# 升级版本
./gradlew wrapper --gradle-version 7.4.2
```

### spring doc (swagger)

http://localhost:8080/swagger-ui/index.html
http://localhost:8080/v3/api-docs