# File Structure

## Overview

Ruuter uses a convention-over-configuration approach where the file system structure directly maps to REST API endpoints. This document describes the project organization and file system conventions.

## Project Root Structure

```
ruuter/
├── DSL/                      # DSL endpoint definitions
│   ├── GET/                  # GET endpoints
│   ├── POST/                 # POST endpoints
│   ├── PUT/                  # PUT endpoints
│   ├── DELETE/               # DELETE endpoints
│   ├── dev/                  # Development/testing endpoints
│   └── samples/              # Example DSL files
├── docs/                     # Documentation
│   ├── architecture/         # Architecture documentation
│   ├── how-to/              # How-to guides
│   └── reference/           # Reference documentation
├── examples/                 # Runnable code examples
├── src/                      # Java source code
├── target/                   # Build output
├── constants.ini            # Configuration constants
├── docker-compose.yml       # Docker composition
├── pom.xml                  # Maven configuration
├── README.md                # Project overview
├── LICENSE                  # License information
├── CHANGELOG.md             # Version history
└── CONTRIBUTING.md          # Contribution guidelines
```

## DSL Directory Structure

The `DSL/` directory contains YAML files that define REST endpoints. The structure follows this convention:

```
DSL/
└── {HTTP_METHOD}/
    └── {endpoint}/
        └── {path}.yml
```

### Mapping Convention

File system paths map directly to HTTP endpoints:

| File Path | HTTP Endpoint | Method |
|-----------|--------------|--------|
| `DSL/GET/users.yml` | `GET /users` | GET |
| `DSL/POST/users.yml` | `POST /users` | POST |
| `DSL/GET/users/profile.yml` | `GET /users/profile` | GET |
| `DSL/POST/iam/list.yml` | `POST /iam/list` | POST |
| `DSL/GET/api/v1/data.yml` | `GET /api/v1/data` | GET |

### DSL File Structure

Each DSL file is a YAML document with specific structure:

```yaml
# DSL/POST/iam/list.yml

# Optional: Input validation
validate:
  required:
    - userId
  types:
    userId: string

# Optional: Processing steps
getUserInfo:
  call:
    url: "http://users-api/user/${request.body.userId}"
    method: GET
    headers:
      Authorization: "Bearer ${constants.api_token}"

# Required: Response definition
response:
  body: "${getUserInfo.result}"
  status: 200
  headers:
    Content-Type: "application/json"
```

## Directory Purpose

### Development Directories

#### `DSL/dev/`

Development and testing endpoints not meant for production:

```
DSL/dev/
├── POST/
│   └── iam/
│       └── list.yml          # Development IAM endpoint
└── test/
    └── mock-data.yml         # Mock data endpoints
```

#### `DSL/samples/`

Example DSL files demonstrating features:

```
DSL/samples/
├── GET/
│   └── iam/
│       ├── list.yml          # Example GET endpoint
│       └── mock.yml          # Example with mock data
└── POST/
    ├── iam/
    │   ├── list.yml          # Example POST endpoint
    │   └── mock.yml          # POST with mock data
    └── to/
        ├── dmapper/
        │   └── users.yml     # Data mapper example
        └── resql/
            └── list-users.yml # Database query example
```

### Production Directories

Production endpoints organized by HTTP method:

```
DSL/
├── GET/
│   ├── health.yml            # Health check endpoint
│   ├── users/
│   │   ├── list.yml         # GET /users/list
│   │   ├── profile.yml      # GET /users/profile
│   │   └── {id}.yml         # GET /users/{id}
│   └── iam/
│       └── verify.yml        # GET /iam/verify
├── POST/
│   ├── users/
│   │   ├── create.yml       # POST /users/create
│   │   └── update.yml       # POST /users/update
│   └── auth/
│       ├── login.yml         # POST /auth/login
│       └── refresh.yml       # POST /auth/refresh
├── PUT/
│   └── users/
│       └── {id}.yml         # PUT /users/{id}
└── DELETE/
    └── users/
        └── {id}.yml         # DELETE /users/{id}
```

## Documentation Structure

### `docs/architecture/`

Architecture and design documentation:

```
docs/architecture/
├── overview.md              # System architecture overview
├── data-flow.md            # Data flow patterns
└── file-structure.md       # This document
```

### `docs/how-to/`

Step-by-step guides for common tasks:

```
docs/how-to/
├── create-endpoint.md      # How to create a new endpoint
├── call-external-api.md    # How to call external APIs
├── handle-errors.md        # Error handling guide
├── use-variables.md        # Variable usage guide
└── deploy.md               # Deployment guide
```

### `docs/reference/`

Reference documentation:

```
docs/reference/
├── dsl-syntax.md           # Complete DSL syntax reference
├── configuration.md        # Configuration options
├── constants.md            # Constants.ini reference
└── api.md                  # Generated API documentation
```

## Examples Structure

Runnable examples with complete setup:

```
examples/
├── basic-endpoint/
│   ├── README.md           # Example description
│   ├── DSL/
│   │   └── GET/
│   │       └── hello.yml   # Simple endpoint
│   └── test.sh             # Test script
├── multi-service-call/
│   ├── README.md
│   ├── DSL/
│   │   └── POST/
│   │       └── aggregate.yml
│   ├── mock-services/      # Mock APIs for testing
│   └── docker-compose.yml  # Complete environment
└── authentication/
    ├── README.md
    ├── DSL/
    │   ├── POST/
    │   │   └── login.yml
    │   └── GET/
    │       └── protected.yml
    └── setup.sh
```

## Configuration Files

### `constants.ini`

Global constants accessible from all DSL files:

```ini
[default]
api_base_url=http://backend-api:8080
auth_service_url=http://auth-service:9000
timeout=5000
max_retries=3

[database]
host=localhost
port=5432
name=ruuter_db
```

### `docker-compose.yml`

Container orchestration for Ruuter and dependencies:

```yaml
version: '3.8'
services:
  ruuter:
    build: .
    ports:
      - "8080:8080"
    volumes:
      - ./DSL:/app/DSL
      - ./constants.ini:/app/constants.ini
    environment:
      - SPRING_PROFILES_ACTIVE=production
```

## File Naming Conventions

### DSL Files

- Use lowercase letters
- Use hyphens for multi-word names: `user-profile.yml`
- Use descriptive names: `get-user-orders.yml` not `guo.yml`
- Avoid special characters except hyphens and underscores

### Documentation Files

- Use lowercase letters with hyphens: `data-flow.md`
- Use `.md` extension for Markdown files
- Group related files in subdirectories

### Example Directories

- Use descriptive directory names: `multi-service-call/` not `example2/`
- Include README.md in each example directory
- Keep examples self-contained

## Path Variables

Use curly braces for path variables in filenames:

```
DSL/GET/users/{id}.yml        → GET /users/123
DSL/GET/orders/{orderId}/items/{itemId}.yml → GET /orders/456/items/789
```

Access in DSL:

```yaml
response:
  body:
    userId: "${request.path.id}"
    message: "User ${request.path.id} retrieved"
```

## Best Practices

### Organization

1. **Group by Domain**: Organize related endpoints together
2. **Consistent Naming**: Use consistent naming across similar endpoints
3. **Avoid Deep Nesting**: Keep directory depth reasonable (max 4-5 levels)
4. **Separate Concerns**: Development, samples, and production in different directories

### DSL Files

1. **One Endpoint Per File**: Each YAML file defines one endpoint
2. **Descriptive Names**: File name should indicate endpoint purpose
3. **Comments**: Add comments for complex logic
4. **Validation**: Include input validation where appropriate

### Documentation

1. **Update Together**: Update docs when changing code
2. **Examples**: Include examples in documentation
3. **Keep Current**: Review and update documentation regularly
4. **Link Related**: Cross-reference related documents

### Version Control

Files to track:
- All DSL files
- Documentation
- Examples
- Configuration templates

Files to ignore (`.gitignore`):
```
target/
*.class
*.jar
*.log
.env
constants.local.ini
```

## Navigation Examples

### Adding a New Endpoint

1. Determine HTTP method and path
2. Create file: `DSL/{METHOD}/{path}.yml`
3. Define DSL structure
4. Test endpoint
5. Document in `docs/reference/api.md`

### Finding an Endpoint

1. Know the HTTP method and path
2. Navigate to `DSL/{METHOD}/`
3. Follow path structure
4. Open corresponding `.yml` file

### Adding Documentation

1. Determine documentation type (architecture/how-to/reference)
2. Create or update file in appropriate `docs/` subdirectory
3. Update links in README.md
4. Add to table of contents if applicable
