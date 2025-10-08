# Contributing to Ruuter

Thank you for your interest in contributing to Ruuter! This document provides guidelines and instructions for contributing to the project.

## Code of Conduct

- Be respectful and professional
- Welcome newcomers and help them get started
- Focus on constructive feedback
- Respect differing viewpoints and experiences

## How to Contribute

### Reporting Issues

Before creating an issue:

1. Check if the issue already exists
2. Gather relevant information (version, environment, steps to reproduce)
3. Provide clear, concise description

Create an issue with:

- **Clear title**: Summarize the problem
- **Description**: Detailed explanation
- **Steps to reproduce**: For bugs
- **Expected behavior**: What should happen
- **Actual behavior**: What actually happens
- **Environment**: OS, Java version, Docker version

### Suggesting Enhancements

Enhancement suggestions are welcome! Include:

- **Use case**: Why is this needed?
- **Proposed solution**: How should it work?
- **Alternatives**: Other approaches considered
- **Examples**: Sample code or configuration

### Pull Requests

1. **Fork the repository**
   ```bash
   git clone https://github.com/yourusername/ruuter.git
   cd ruuter
   ```

2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make your changes**
   - Follow code style guidelines
   - Add tests if applicable
   - Update documentation

4. **Test your changes**
   ```bash
   mvn clean test
   docker-compose up -d
   # Run manual tests
   ```

5. **Commit your changes**
   ```bash
   git add .
   git commit -m "Add feature: description"
   ```

   Commit message format:
   - `Add:` for new features
   - `Fix:` for bug fixes
   - `Update:` for improvements
   - `Docs:` for documentation
   - `Refactor:` for code refactoring

6. **Push to your fork**
   ```bash
   git push origin feature/your-feature-name
   ```

7. **Create Pull Request**
   - Go to the original repository
   - Click "New Pull Request"
   - Select your branch
   - Fill in PR template

## Development Setup

### Prerequisites

- Java 11 or higher
- Maven 3.6+
- Docker and Docker Compose (for testing)
- Git

### Local Development

1. **Clone the repository**
   ```bash
   git clone https://github.com/buerostack/ruuter.git
   cd ruuter
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run locally**
   ```bash
   java -jar target/ruuter.jar
   ```

4. **Run with Docker**
   ```bash
   docker-compose up -d
   ```

### Project Structure

```
ruuter/
├── src/               # Java source code
├── DSL/              # DSL endpoint definitions
├── docs/             # Documentation
├── examples/         # Code examples
└── pom.xml           # Maven configuration
```

## Coding Standards

### Java Code

- Follow Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public methods
- Keep methods focused and concise
- Write unit tests for new functionality

Example:
```java
/**
 * Processes the DSL configuration and returns the result.
 *
 * @param config the DSL configuration
 * @return processed result
 * @throws ConfigException if configuration is invalid
 */
public Result processDSL(Config config) throws ConfigException {
    // Implementation
}
```

### DSL Files

- Use lowercase with hyphens for filenames
- Include comments for complex logic
- Follow YAML syntax standards
- Validate before committing

Example:
```yaml
# Get user profile with validation
validate:
  required:
    - userId

getUserProfile:
  call:
    url: "${constants.api_url}/users/${request.params.userId}"
    method: GET

response:
  body: "${getUserProfile.result}"
  status: 200
```

### Documentation

- Use Markdown for all documentation
- Include code examples
- Keep language clear and concise
- Update docs when changing functionality
- Use Mermaid for diagrams

## Testing

### Unit Tests

- Write tests for new features
- Maintain or improve code coverage
- Use descriptive test names

```java
@Test
public void testDSLParsingWithValidConfig() {
    // Test implementation
}
```

### Integration Tests

- Test DSL endpoints end-to-end
- Verify external service integration
- Test error scenarios

### Manual Testing

1. Create test DSL files
2. Start Ruuter
3. Send requests with curl or Postman
4. Verify responses

## Documentation Requirements

When contributing, ensure documentation includes:

- [ ] README.md updated if needed
- [ ] Architecture docs updated for structural changes
- [ ] How-to guides for new features
- [ ] API reference updated
- [ ] Examples provided for new functionality
- [ ] CHANGELOG.md updated

## Review Process

1. **Automated checks**: CI/CD runs tests and linting
2. **Code review**: Maintainer reviews code quality
3. **Documentation review**: Check docs completeness
4. **Testing**: Verify functionality works as expected
5. **Approval**: Maintainer approves and merges

## Getting Help

- **Issues**: Use GitHub issues for bugs and features
- **Discussions**: Use GitHub discussions for questions
- **Email**: Contact maintainer for private matters

## Recognition

Contributors will be:

- Listed in CHANGELOG.md
- Credited in release notes
- Added to contributors list

## License

By contributing, you agree that your contributions will be licensed under the MIT License.

---

**Maintainer**: Rainer Türner
**Repository**: https://github.com/buerostack/ruuter

Thank you for contributing to Ruuter!
