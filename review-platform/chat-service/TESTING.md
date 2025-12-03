# Chat Service Testing Guide

## Architecture Flow

```
Frontend (localhost:3000)
    ↓
    POST /api/chat
    ↓
API Gateway (localhost:8888)
    ↓
    Routes to chat-service:8085
    ↓
Chat Service
    ├─→ User Service (fetch preferences)
    └─→ AWS Bedrock Agent
            ↓
        Knowledge Base (S3)
```

## Testing Steps

### 1. Direct Chat Service Test (Bypass Gateway)

Test the chat service directly:

```bash
curl -X POST http://localhost:8085/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Gợi ý sữa rửa mặt cho da dầu",
    "userId": "guest"
  }'
```

Expected response:
```json
{
  "message": "Natural language explanation in Vietnamese...",
  "recommendation": {
    "query": "Gợi ý sữa rửa mặt cho da dầu",
    "detected_filters": {...},
    "products": [...]
  }
}
```

### 2. Through API Gateway Test

Test through the API Gateway (recommended):

```bash
curl -X POST http://localhost:8888/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Serum trị mụn hiệu quả",
    "userId": "64da2cbb-9f1a-49cb-8bae-05f626e91c40"
  }'
```

### 3. Frontend Test

1. Open browser: `http://localhost:3000`
2. Look for floating chat icon (bottom-right corner)
3. Click the icon to open chat
4. Type a question or click a suggestion
5. Verify:
   - Message appears in chat
   - Loading animation shows
   - AI response appears with products
   - Product cards display correctly

### 4. Test with User Preferences

**Create user preference first:**
```bash
curl -X POST http://localhost:8888/api/user/preference \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "test-user-123",
    "skinType": "OILY",
    "concerns": ["ACNE", "LARGE_PORES"]
  }'
```

**Then test chat with that user:**
```bash
curl -X POST http://localhost:8888/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Gợi ý sản phẩm phù hợp với tôi",
    "userId": "test-user-123"
  }'
```

The agent should consider the user's skin type and concerns.

### 5. Test Guest User (No Preferences)

```bash
curl -X POST http://localhost:8888/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Kem dưỡng ẩm tốt nhất",
    "userId": "guest"
  }'
```

Should work without user preferences.

## Sample Test Queries

### Vietnamese Queries
- "Gợi ý sữa rửa mặt cho da dầu"
- "Serum trị mụn hiệu quả"
- "Kem dưỡng ẩm cho da khô dưới 500k"
- "Sản phẩm làm sáng da cho mọi loại da"
- "Toner cho da nhạy cảm bị đỏ"
- "Kem chống nắng không gây bết dính"

### English Queries
- "Recommend cleanser for oily skin"
- "Best serum for acne"
- "Moisturizer under 500k"

## Troubleshooting

### Chat service not responding
```bash
# Check if service is running
docker ps | grep chat_service

# Check logs
docker logs chat_service

# Check if port is accessible
curl http://localhost:8085/actuator/health
```

### API Gateway not routing
```bash
# Check gateway logs
docker logs api_gateway

# Verify route configuration
curl http://localhost:8888/actuator/gateway/routes
```

### No products returned
1. Verify Bedrock Agent is configured correctly
2. Check AWS credentials in environment variables
3. Test agent directly in AWS Console
4. Verify Knowledge Base has data

### Frontend chat not working
1. Open browser DevTools (F12)
2. Check Console for errors
3. Check Network tab for API calls
4. Verify axios is configured correctly

## Expected Response Format

```json
{
  "message": "Dựa trên nhu cầu của bạn về sữa rửa mặt cho da dầu, tôi gợi ý 3 sản phẩm phù hợp...",
  "recommendation": {
    "query": "Gợi ý sữa rửa mặt cho da dầu",
    "detected_filters": {
      "skin_type": ["oily"],
      "concern_type": [],
      "category": ["cleanser"],
      "price_range": {
        "min": null,
        "max": null
      }
    },
    "products": [
      {
        "id": "p1-001",
        "name": "CeraVe Foaming Facial Cleanser",
        "brand_name": "CeraVe",
        "category": "cleanser",
        "price": 289000,
        "rating": 4.7,
        "image_url": "https://example.com/image.jpg",
        "skin_type": ["oily", "combination"],
        "concern_type": ["acne", "large_pores"],
        "reason": "Sữa rửa mặt dạng bọt nhẹ nhàng, kiểm soát dầu hiệu quả."
      }
    ]
  }
}
```

## Performance Testing

### Load Test
```bash
# Install Apache Bench
# Windows: Download from Apache website
# Mac: brew install httpd
# Linux: sudo apt-get install apache2-utils

# Run load test (100 requests, 10 concurrent)
ab -n 100 -c 10 -p request.json -T application/json \
  http://localhost:8888/api/chat
```

### Response Time
- Expected: < 3 seconds for simple queries
- With user preferences: < 4 seconds
- Complex queries: < 5 seconds

## Monitoring

### Check Service Health
```bash
# Chat service
curl http://localhost:8085/actuator/health

# API Gateway
curl http://localhost:8888/actuator/health
```

### View Logs
```bash
# Real-time logs
docker logs -f chat_service

# Last 100 lines
docker logs --tail 100 chat_service

# With timestamps
docker logs -t chat_service
```

## Integration Test Checklist

- [ ] Chat service starts successfully
- [ ] API Gateway routes to chat service
- [ ] Direct API call works (port 8085)
- [ ] Gateway API call works (port 8888)
- [ ] Frontend chat icon appears
- [ ] Chat window opens/closes
- [ ] Messages send successfully
- [ ] Loading state shows
- [ ] AI response appears
- [ ] Products display with images
- [ ] User preferences are fetched (if userId provided)
- [ ] Guest users work (no preferences)
- [ ] Vietnamese queries work
- [ ] English queries work
- [ ] Error handling works (invalid requests)
- [ ] CORS is configured correctly
