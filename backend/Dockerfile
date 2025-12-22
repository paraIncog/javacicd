# ---- Build stage ----
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Gradle wrapper + build files first (better caching)
COPY gradlew gradlew
COPY gradle/ gradle/
COPY build.gradle settings.gradle ./
# If you have more build files (gradle.properties, etc) copy them too
# COPY gradle.properties ./

# Download dependencies (cache layer)
RUN chmod +x ./gradlew && ./gradlew dependencies --no-daemon || true

# Copy source
COPY src/ src/

# Build bootJar (skip tests optionally)
RUN ./gradlew bootJar -x test --no-daemon

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
