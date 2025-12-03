# Chat Service Setup Guide

## Overview
The chat service provides AI-powered skincare product recommendations using AWS Bedrock Agent with Knowledge Base (S3).

## Architecture

```
Frontend (FloatingChat.jsx)
    ↓
API Gateway (port 8888)
    ↓
Chat Service (port 8085)
    ↓
    ├─→ User Service (fetch user preferences - optional)
    └─→ AWS Bedrock Agent
            ↓
        Knowledge Base (S3)
            - Product catalog
            - Product information
```

## Features

### Frontend
- **Floating Chat Icon**: Always visible on all pages
- **Responsive Chat Window**: 
  - Natural language input in Vietnamese/English
  - Product recommendations with images, prices, ratings
  - Quick suggestion buttons
  - Loading states and animations
- **Location**: `review-platform-fe/src/Component/FloatingChat.jsx`

### Backend
- **Endpoint**: `POST /api/chat`
- **Request**:
  ```json
  {
    "message": "Gợi ý sữa rửa mặt cho da dầu",
    "userId": "user-id-here"
  }
  ```
- **Response**:
  ```json
  {
    "message": "Natural language explanation in Vietnamese",
    "recommendation": {
      "query": "original query",
      "detected_filters": {
        "skin_type": ["oily"],
        "concern_type": ["acne"],
        "category": ["cleanser"],
        "price_range": {"min": null, "max": null}
      },
      "products": [
        {
          "id": "p1-001",
          "name": "Product Name",
          "brand_name": "Brand",
          "category": "cleanser",
          "price": 329000,
          "rating": 4.6,
          "image_url": "https://...",
          "skin_type": ["oily"],
          "concern_type": ["acne"],
          "reason": "Phù hợp da dầu, giúp kiểm soát nhờn"
        }
      ]
    }
  }
  ```

## Setup Instructions

### 1. Configure AWS Bedrock Agent
Edit `review-platform/chat-service/.env`:
```env
AWS_REGION=us-east-1
AWS_ACCESS_KEY_ID=your_actual_access_key
AWS_SECRET_ACCESS_KEY=your_actual_secret_key

# Your Bedrock Agent ID and Alias
BEDROCK_AGENT_ID=your_agent_id_here
BEDROCK_AGENT_ALIAS_ID=TSTALIASID
```

Or add to your main `.env` file at the root.

**Note:** The Bedrock Agent should already have:
- Knowledge Base configured with S3 bucket containing product data
- Agent instructions for product recommendations
- Proper IAM permissions

### 2. Build and Run

#### Local Development
```bash
cd review-platform/chat-service
./mvnw spring-boot:run
```

#### Docker
```bash
# Build and start all services
docker-compose up -d --build chat-service

# Or rebuild everything
docker-compose up -d --build
```

### 3. Test the Service

#### Direct API Test
```bash
curl -X POST http://localhost:8085/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Gợi ý sữa rửa mặt cho da dầu",
    "userId": "64da2cbb-9f1a-49cb-8bae-05f626e91c40"
  }'
```

#### Through API Gateway
```bash
curl -X POST http://localhost:8888/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Serum trị mụn hiệu quả",
    "userId": "user-id"
  }'
```

#### Frontend
1. Start the frontend: `npm run dev` in `review-platform-fe`
2. Navigate to any page
3. Click the floating chat icon (bottom-right corner)
4. Type your question or click a suggestion

## How It Works

1. **User sends message** via FloatingChat component
2. **Chat Service fetches user preferences** (if userId provided, can be null for guests)
3. **Invokes Bedrock Agent** with:
   - User's question
   - User preferences (optional)
4. **Agent queries Knowledge Base** (S3) for relevant products
5. **Agent returns response** with:
   - Natural language explanation in Vietnamese
   - Structured JSON with product recommendations
6. **Frontend displays** products with images, prices, and reasons

### Agent Response Format

The agent should return responses in this format:

```
Natural language explanation in Vietnamese (2-5 sentences)

JSON_RESULT_START
{
  "query": "original user query",
  "detected_filters": {...},
  "products": [...]
}
JSON_RESULT_END
```

### Example Queries
- "Gợi ý sữa rửa mặt cho da dầu"
- "Serum trị mụn hiệu quả"
- "Kem dưỡng ẩm cho da khô dưới 500k"
- "Sản phẩm làm sáng da cho mọi loại da"

## Configuration

### application.properties
```properties
server.port=8085

# AWS Configuration
aws.region=${AWS_REGION:us-east-1}
aws.access-key-id=${AWS_ACCESS_KEY_ID}
aws.secret-access-key=${AWS_SECRET_ACCESS_KEY}

# AWS Bedrock Agent
bedrock.agent.id=${BEDROCK_AGENT_ID}
bedrock.agent.alias-id=${BEDROCK_AGENT_ALIAS_ID:TSTALIASID}

# Service URLs
service.user.url=${USER_SERVICE_URL:http://localhost:8081}
```

### API Gateway Route
```yaml
- id: chat-service
  uri: http://localhost:8085
  predicates:
    - Path=/api/chat/**
```

## Dependencies

### Backend (pom.xml)
- Spring Boot 3.5.8
- AWS SDK Bedrock Agent Runtime 2.20.0
- Lombok
- Jackson (JSON parsing)

### Frontend
- React
- Axios
- Tailwind CSS

## Troubleshooting

### Chat service not responding
1. Check if AWS credentials are configured correctly
2. Verify the service is running: `docker ps | grep chat_service`
3. Check logs: `docker logs chat_service`

### No products returned
1. Verify Bedrock Agent has access to Knowledge Base
2. Check S3 bucket has product data
3. Ensure agent instructions are configured correctly
4. Test agent directly in AWS Console

### Frontend chat not appearing
1. Check browser console for errors
2. Verify FloatingChat is imported in App.jsx
3. Clear browser cache and reload

### JSON parsing errors
1. Check AI response format in logs
2. Ensure JSON markers are present: `JSON_RESULT_START` and `JSON_RESULT_END`
3. Validate JSON structure matches the schema

## Future Enhancements

- [ ] Add chat history persistence (PostgreSQL)
- [ ] Implement conversation context (remember previous messages)
- [ ] Add user feedback on recommendations
- [ ] Support image uploads for product identification
- [ ] Multi-language support (English, Vietnamese)
- [ ] Rate limiting and caching
- [ ] Analytics and recommendation tracking

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/chat` | Send chat message and get AI recommendations |

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `AWS_REGION` | AWS region for Bedrock | `us-east-1` |
| `AWS_ACCESS_KEY_ID` | AWS access key | Required |
| `AWS_SECRET_ACCESS_KEY` | AWS secret key | Required |
| `BEDROCK_AGENT_ID` | Bedrock Agent ID | Required |
| `BEDROCK_AGENT_ALIAS_ID` | Bedrock Agent Alias ID | `TSTALIASID` |
| `USER_SERVICE_URL` | User service URL | `http://localhost:8081` |
| `SERVER_PORT` | Chat service port | `8085` |
