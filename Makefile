# Default target
.PHONY: help
help:
	@echo "Available commands:"
	@echo "  make build            - Build the project"
	@echo "  make run              - Run the Spring Boot app"
	@echo "  make clean            - Clean build artifacts"
	@echo "  make deps             - Download dependencies"
	@echo "  make rebuild          - Clean and rebuild"
	@echo "  make package          - Package into a jar"
	@echo "  make update           - Force update all dependencies"
	@echo "  make validate         - Validate dependencies"
	@echo "  make test             - Run all tests"
	@echo "  make test-class       - Run specific test class. Usage: make test-class CLASS=UserServiceTest"
	@echo "  make test-method      - Run specific test method. Usage: make test-method CLASS=UserServiceTest METHOD=shouldCreateUser"
	@echo "  make debug            - Run with remote debug on port 5005"
	@echo "  make debug-wait       - Run with debug, wait for debugger to attach"
	@echo "  make format					 - Format code with Spotless"

DEBUG_PORT = 5005

# Build the project
.PHONY: build
build:
	./mvnw clean compile

format:
	./mvnw spotless:apply
# Run the Spring Boot app
.PHONY: run
run:
	./mvnw spring-boot:run

# Clean target folder
.PHONY: clean
clean:
	./mvnw clean

# Download dependencies
.PHONY: deps
deps:
	./mvnw dependency:resolve

# Package into a JAR file
.PHONY: package
package:
	./mvnw clean package

# Force update all dependencies
.PHONY: update
update:
	./mvnw clean install -U

# validate pom.xml, project structure
.PHONY: validate
validate:
	./mvnw validate

# Run all tests
.PHONY: test
test:
	./mvnw test

# Run specific test class. Usage: make test-class CLASS=UserServiceTest
.PHONY: test-class
test-class:
	./mvnw test -Dtest=$(CLASS)

# Run specific test method. Usage: make test-method CLASS=UserServiceTest METHOD=shouldCreateUser
.PHONY: test-method
test-method:
	./mvnw test -Dtest=$(CLASS)#$(METHOD)

# Run with remote debug on port 5005
.PHONY: debug
debug:
	./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=$(DEBUG_PORT)"

# Run with debug, wait for debugger to attach before starting
.PHONY: debug-wait
debug-wait:
	./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=$(DEBUG_PORT)"

# Clean and rebuild
.PHONY: rebuild
rebuild: clean build
