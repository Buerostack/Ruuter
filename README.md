# Ruuter

**Maintained by**: Rainer Türner
**Status**: Active Development

## About

Ruuter is a declarative REST router that turns YAML DSL files into REST endpoints and executes each endpoint's business logic using a strict, declarative syntax. It enables rapid API development without writing code, using only configuration files to define routing, request processing, and response handling.

Originally developed at the Information System Authority of Estonia (Riigi Infosüsteemi Amet, RIA) since June 2018. Designed and development led by [Rainer Türner](https://www.linkedin.com/in/rainer-t%C3%BCrner-aba66274/) until April 2025.

This repository is maintained by Rainer Türner starting from September 10, 2025, as a clean version of Ruuter independent from its original owner RIA.

Original repository: https://github.com/buerokratt/Ruuter

## Quick Start

Get Ruuter running in under 5 minutes:

```bash
git clone https://github.com/buerostack/ruuter.git
cd ruuter
docker-compose up -d
```

The service will be available at `http://localhost:8080`

## Installation

### Prerequisites

- Docker and Docker Compose (recommended)
- OR Java 11+ with Maven (for local development)

### Option 1: Docker (Recommended)

```bash
docker-compose up -d
```

### Option 2: Local Development

```bash
mvn clean install
java -jar target/ruuter.jar
```

## Basic Usage

Create a YAML file in the `DSL/` directory to define an endpoint:

```yaml
# DSL/GET/hello.yml
response:
  body: "Hello, World!"
  status: 200
```

This creates a `GET /hello` endpoint that returns a simple response.

See the `examples/` directory for more complex use cases.

## Documentation

- [Architecture Overview](docs/architecture/overview.md)
- [Data Flow](docs/architecture/data-flow.md)
- [File Structure](docs/architecture/file-structure.md)
- [How-To Guides](docs/how-to/)
- [API Reference](docs/reference/)

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for development guidelines and contribution process.

## License

See [LICENSE](LICENSE) file for details.
