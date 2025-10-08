# How to Create a New Endpoint

This guide walks you through creating a new REST endpoint in Ruuter using DSL files.

## Prerequisites

- Ruuter running locally or in Docker
- Text editor
- Basic understanding of YAML syntax

## Steps

### 1. Determine Your Endpoint

Decide on:
- **HTTP Method**: GET, POST, PUT, DELETE
- **URL Path**: e.g., `/users/profile`
- **Functionality**: What should it do?

### 2. Create the DSL File

Create a YAML file in the appropriate directory based on the HTTP method:

```bash
# For GET /users/profile
touch DSL/GET/users/profile.yml

# For POST /api/data
touch DSL/POST/api/data.yml
```

### 3. Define the DSL Structure

Open the file and add your configuration:

```yaml
# DSL/GET/users/profile.yml

# Optional: Validate incoming request
validate:
  required:
    - userId
  types:
    userId: string

# Optional: Call external service
getUserData:
  call:
    url: "${constants.api_url}/users/${request.params.userId}"
    method: GET
    headers:
      Authorization: "Bearer ${constants.api_token}"

# Required: Define response
response:
  body: "${getUserData.result}"
  status: 200
  headers:
    Content-Type: "application/json"
```

### 4. Test the Endpoint

No restart needed! Test immediately:

```bash
curl "http://localhost:8080/users/profile?userId=123"
```

### 5. Verify the Response

Check that the response matches your expectations:

```json
{
  "id": "123",
  "name": "John Doe",
  "email": "john@example.com"
}
```

## Common Patterns

### Simple Static Response

```yaml
response:
  body:
    message: "Hello, World!"
    timestamp: "${timestamp}"
  status: 200
```

### With Request Body (POST)

```yaml
# DSL/POST/users/create.yml
validate:
  required:
    - name
    - email

createUser:
  call:
    url: "${constants.api_url}/users"
    method: POST
    body:
      name: "${request.body.name}"
      email: "${request.body.email}"

response:
  body: "${createUser.result}"
  status: 201
```

### Multiple Service Calls

```yaml
getUser:
  call:
    url: "${constants.api_url}/users/${request.params.id}"

getOrders:
  call:
    url: "${constants.api_url}/orders?userId=${request.params.id}"

response:
  body:
    user: "${getUser.result}"
    orders: "${getOrders.result}"
  status: 200
```

### With Error Handling

```yaml
callService:
  call:
    url: "${constants.api_url}/data"
    retry: 3
    on_error: fallback

fallback:
  response:
    body:
      error: "Service temporarily unavailable"
    status: 503
```

## Tips

1. **File naming**: Use lowercase with hyphens for multi-word names
2. **No restart needed**: Changes take effect immediately
3. **Use constants**: Store URLs and tokens in `constants.ini`
4. **Test incrementally**: Build complex endpoints step by step
5. **Check logs**: View Ruuter logs for debugging

## Next Steps

- [How to Call External APIs](call-external-api.md)
- [How to Handle Errors](handle-errors.md)
- [How to Use Variables](use-variables.md)
