# How to Use Variables in DSL

This guide explains how to use variables and access data in Ruuter DSL files.

## Variable Syntax

Ruuter uses `${...}` syntax for variable substitution:

```yaml
response:
  body:
    message: "${request.params.name}"
```

## Variable Sources

### 1. Request Parameters

Access URL query parameters:

```yaml
# GET /users?id=123&name=John
response:
  body:
    userId: "${request.params.id}"        # "123"
    userName: "${request.params.name}"    # "John"
```

### 2. Path Variables

Access dynamic path segments:

```yaml
# File: DSL/GET/users/{id}/profile.yml
# Request: GET /users/123/profile
response:
  body:
    userId: "${request.path.id}"          # "123"
```

### 3. Request Body

Access POST/PUT request body fields:

```yaml
# POST /users with body: {"name": "John", "email": "john@example.com"}
createUser:
  call:
    url: "${constants.api_url}/users"
    body:
      name: "${request.body.name}"         # "John"
      email: "${request.body.email}"       # "john@example.com"
```

### 4. Request Headers

Access HTTP headers:

```yaml
response:
  body:
    authToken: "${request.headers.authorization}"
    userAgent: "${request.headers.user-agent}"
    tenantId: "${request.headers.x-tenant-id}"
```

### 5. Constants

Access values from `constants.ini`:

```ini
# constants.ini
[default]
api_url=http://backend:8080
api_token=secret123
```

```yaml
call:
  url: "${constants.api_url}/data"
  headers:
    Authorization: "Bearer ${constants.api_token}"
```

### 6. Step Results

Access results from previous steps:

```yaml
step1:
  call:
    url: "http://api/users/123"

step2:
  call:
    url: "http://api/orders"
    body:
      userId: "${step1.result.id}"
      userName: "${step1.result.name}"
```

### 7. System Variables

Built-in variables:

```yaml
response:
  body:
    timestamp: "${timestamp}"              # Current timestamp
    requestId: "${requestId}"              # Unique request ID
```

## Nested Data Access

Use dot notation for nested objects:

```yaml
# If step1.result is:
# {
#   "user": {
#     "profile": {
#       "name": "John"
#     }
#   }
# }

response:
  body:
    name: "${step1.result.user.profile.name}"   # "John"
```

## Array Access

Access array elements by index:

```yaml
# If step1.result.items is: ["apple", "banana", "cherry"]
response:
  body:
    first: "${step1.result.items[0]}"      # "apple"
    second: "${step1.result.items[1]}"     # "banana"
```

## String Concatenation

Combine variables with text:

```yaml
response:
  body:
    greeting: "Hello, ${request.params.name}!"
    fullUrl: "${constants.base_url}/api/${request.path.endpoint}"
```

## Default Values

Provide fallback values:

```yaml
response:
  body:
    # If name is not provided, use "Guest"
    greeting: "Hello, ${request.params.name:Guest}!"
```

## Conditional Variables

Use variables in conditions:

```yaml
premiumFeature:
  condition: "${request.headers.x-plan-type == 'premium'}"
  call:
    url: "${constants.api_url}/premium-data"

response:
  condition: "${premiumFeature.status == 200}"
  body: "${premiumFeature.result}"
```

## Variable Scope

Variables are available in different scopes:

| Scope | Available In | Example |
|-------|-------------|---------|
| Request | Entire DSL file | `${request.params.id}` |
| Constants | Entire DSL file | `${constants.api_url}` |
| Step Results | After step executes | `${step1.result}` |
| System | Entire DSL file | `${timestamp}` |

## Common Patterns

### Building Dynamic URLs

```yaml
getUserData:
  call:
    url: "${constants.api_url}/users/${request.params.userId}/orders/${request.params.orderId}"
```

### Header Injection

```yaml
callApi:
  call:
    url: "${constants.api_url}/data"
    headers:
      Authorization: "${request.headers.authorization}"
      X-Tenant-ID: "${request.headers.x-tenant-id}"
      X-Request-ID: "${requestId}"
```

### Request Transformation

```yaml
transformData:
  call:
    url: "${constants.api_url}/service"
    body:
      # Rename fields
      user_id: "${request.body.userId}"
      full_name: "${request.body.firstName} ${request.body.lastName}"
      created_at: "${timestamp}"
```

### Response Mapping

```yaml
getData:
  call:
    url: "${constants.api_url}/data"

response:
  body:
    # Map external API response to different structure
    id: "${getData.result.userId}"
    name: "${getData.result.userName}"
    email: "${getData.result.userEmail}"
```

## Debugging Variables

To see variable values, return them in response:

```yaml
debug:
  response:
    body:
      requestParams: "${request.params}"
      requestBody: "${request.body}"
      requestHeaders: "${request.headers}"
      constants: "${constants}"
```

## Best Practices

1. **Use descriptive step names**: `getUserProfile` not `step1`
2. **Validate before using**: Check required variables in `validate` section
3. **Use constants for URLs**: Don't hardcode service URLs
4. **Document complex mappings**: Add comments for clarity
5. **Handle missing values**: Provide defaults where appropriate

## See Also

- [How to Create an Endpoint](create-endpoint.md)
- [How to Call External APIs](call-external-api.md)
- [How to Handle Errors](handle-errors.md)
