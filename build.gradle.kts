plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.oracle.database.jdbc:ojdbc8:23.2.0.0")
    implementation("org.apache.logging.log4j:log4j-api:2.23.1")
    implementation("org.apache.logging.log4j:log4j-core:2.23.1")
    implementation("org.apache.xmlbeans:xmlbeans:3.0.1")
    implementation("org.apache.poi:poi-ooxml-full:5.2.5")
}

tasks.test {
    useJUnitPlatform()
}