FROM --platform=linux/amd64 gradle:jdk21-alpine AS builder
WORKDIR /home/gradle/build
COPY ./src ./src
COPY ./*.gradle ./
RUN gradle bootJar
RUN java -Djarmode=tools -jar ./build/libs/backend_2.jar extract --launcher --layers

FROM --platform=linux/amd64 eclipse-temurin:21-jdk-alpine
WORKDIR application
COPY --from=builder /home/gradle/build/backend_2/dependencies/ ./
COPY --from=builder /home/gradle/build/backend_2/spring-boot-loader/ ./
COPY --from=builder /home/gradle/build/backend_2/snapshot-dependencies/ ./
COPY --from=builder /home/gradle/build/backend_2/application/ ./

EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]