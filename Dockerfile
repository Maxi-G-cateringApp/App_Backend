FROM openjdk:17
EXPOSE 8080
ADD target/maxig-cateringservice.jar maxig-cateringservice.jar
ENTRYPOINT ["java","-jar","/maxig-cateringservice.jar"]