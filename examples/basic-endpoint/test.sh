#!/bin/bash

# Test script for basic endpoint example

echo "Testing basic endpoint..."
echo ""

# Test the hello endpoint
echo "GET /hello"
curl -s http://localhost:8080/hello | jq .

echo ""
echo "Test complete!"
