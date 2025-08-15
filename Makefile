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

# Build the project
.PHONY: build
build:
	mvn clean compile

# Run the Spring Boot app
.PHONY: run
run:
	mvn spring-boot:run

# Clean target folder
.PHONY: clean
clean:
	mvn clean

# Download dependencies
.PHONY: deps
deps:
	mvn dependency:resolve

# Package into a JAR file
.PHONY: package
package:
	mvn clean package

# Force update all dependencies
.PHONY: update
update:
	mvn clean install -U

# Clean and rebuild
.PHONY: rebuild
rebuild: clean build
