dependencies {
    implementation(project(":ecommerce-application"))
    implementation(project(":ecommerce-domain"))
    implementation(project(":ecommerce-common"))

    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    // TestContainer
    testImplementation ("org.testcontainers:mysql")
}