dependencies {
    implementation(project(":ecommerce-application"))
    implementation(project(":ecommerce-domain"))
    implementation(project(":ecommerce-common"))

    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Mapstruct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.5.Final") // KAPT 사용

    // QueryDsl
    implementation ("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

    // Mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    // TestContainer
    testImplementation ("org.testcontainers:mysql")
    testImplementation ("com.redis:testcontainers-redis")
}