# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve dependency:resolve-plugins
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Add metadata labels
LABEL org.opencontainers.image.title="My Java App" \
      org.opencontainers.image.description="Java application built with Maven and Docker" \
      org.opencontainers.image.version="1.0.0" \
      org.opencontainers.image.authors="joel5040"

# Copy built JAR from builder
COPY --from=builder /app/target/my-java-app-*.jar app.jar

# Create non-root user for security
RUN addgroup --system --gid 1001 appgroup && \
    adduser --system --uid 1001 --ingroup appgroup appuser

# Set user permissions
RUN chown -R appuser:appgroup /app

USER appuser

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=10s --retries=3 \
    CMD java -cp app.jar org.springframework.boot.loader.JarLauncher -version || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
