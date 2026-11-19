plugins {
    id("java")
}

group = "com.josedaal.automation"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //GENERADO AUTOMATICAMENTE
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    //GENERADO AUTOMATICAMENTE
    testImplementation("org.seleniumhq.selenium:selenium-java:4.11.0")
    //Mantener esta version con advertencia si no cambiar
}

tasks.test {
    useJUnitPlatform()
}
