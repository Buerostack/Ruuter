# Basic Endpoint Example

This example demonstrates how to create a simple GET endpoint using Ruuter's DSL.

## What This Example Shows

- Creating a basic GET endpoint
- Returning a JSON response
- Using static data

## Files

- `DSL/GET/hello.yml` - Simple "Hello World" endpoint
- `test.sh` - Script to test the endpoint

## Running the Example

### 1. Start Ruuter

From the project root:

```bash
docker-compose up -d
```

### 2. Copy DSL Files

```bash
cp examples/basic-endpoint/DSL/GET/hello.yml ../../DSL/GET/
```

### 3. Test the Endpoint

```bash
curl http://localhost:8080/hello
```

Expected response:

```json
{
  "message": "Hello, World!",
  "timestamp": "2025-10-08T12:00:00Z"
}
```

Or use the test script:

```bash
./test.sh
```

## How It Works

The DSL file `hello.yml` defines:

1. **Response Body**: JSON structure to return
2. **Status Code**: HTTP 200 OK
3. **Headers**: Content-Type set to application/json

## Next Steps

- Try modifying the response message
- Add more fields to the response
- See `multi-service-call` example for more complex scenarios
