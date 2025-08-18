# Default target
.PHONY: help
help:
	@echo "Available commands:"
	@echo "  make build       - Build the project"
	@echo "  make run         - Run the Spring Boot app"
	@echo "  make clean       - Clean build artifacts"
	@echo "  make deps        - Download dependencies"
	@echo "  make rebuild     - Clean and rebuild"
	@echo "  make package     - Package into a jar"
	@echo "  make update      - Force update all dependencies"
	@echo "  make validate    - Validate dependencies"
	@echo "  make test        - Run tests. Add test class if not added"

# Build the project
.PHONY: build
build:
	./mvnw clean compile

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

# runs tests, add the test class if needed
.PHONY: test
test:
	./mvnw -Dtest=UserServiceTests,PracticeApplicationTests,AddClassesTests test

# Clean and rebuild
.PHONY: rebuild
rebuild: clean build
