# Build stage
FROM eclipse-temurin:17-jdk as builder
WORKDIR /app
COPY app/app.jar ./  # Чітко вказуємо шлях копіювання

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Install dependencies
RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/*

# Setup non-root user
RUN addgroup --system --gid 1001 appgroup && \
    adduser --system --uid 1001 --gid 1001 appuser

# Copy from builder stage
COPY --from=builder /app/app.jar ./app.jar
RUN chown -R appuser:appgroup /app

# Runtime config
USER appuser
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]
