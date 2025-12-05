# Review Platform - Microservices Architecture

A comprehensive beauty product review platform built with microservices architecture, featuring user authentication, product management, social interactions, and AI-powered chatbot assistance.

## 📖 Introduction

This project is a full-stack beauty product review platform that allows users to:
- Browse and search beauty products with advanced filtering
- Create and share product reviews and posts
- Interact with other users through comments and likes
- Get personalized product recommendations via AI chatbot
- Manage products and users through admin panel

The platform follows microservices architecture principles, with each service handling a specific domain responsibility. The frontend is built with React, while the backend services are built with Spring Boot and communicate through an API Gateway.

## 🛠️ Tech Stack

### Frontend
- **React 19.1.0** - UI library
- **Vite 7.0.0** - Build tool and dev server
- **React Router DOM 7.6.3** - Client-side routing
- **Tailwind CSS 3.3.5** - Utility-first CSS framework
- **Axios 1.10.0** - HTTP client
- **React Toastify 11.0.5** - Toast notifications
- **Framer Motion 12.23.22** - Animation library
- **Lucide React 0.525.0** - Icon library

### Backend
- **Java 21** - Programming language
- **Spring Boot 3.5.6-3.5.7** - Application framework
- **Spring Cloud Gateway 2025.0.0** - API Gateway
- **Spring Data JPA** - Database abstraction
- **Spring Data Neo4j** - Graph database integration
- **Spring Security OAuth2** - Authentication & authorization
- **MapStruct 1.5.5-1.6.0** - Object mapping
- **Lombok** - Boilerplate code reduction
- **Flyway** - Database migration
- **SpringDoc OpenAPI 2.8.13** - API documentation

### Databases
- **PostgreSQL 16** (with pgvector extension) - Relational database for products, posts, and admin data
- **Neo4j 5.23** - Graph database for user relationships and social features

### Infrastructure & DevOps
- **Docker & Docker Compose** - Containerization and orchestration
- **Maven** - Build and dependency management
- **Nginx** - Web server for frontend (production)

### Cloud Services
- **AWS Cognito** - User authentication and authorization
- **AWS S3** - Object storage for product images
- **AWS API Gateway** - External API endpoint for chatbot
- **AWS Bedrock** - AI/ML service for chatbot (via API Gateway)

### Testing
- **JUnit** - Unit testing framework
- **Testcontainers** - Integration testing with containers
- **REST Assured 5.5.0** - API testing

## 🚀 How to Run

### Prerequisites
- **Java 21** or higher
- **Maven 3.6+**
- **Node.js 18+** and npm
- **Docker** and **Docker Compose**
- **AWS Account** with:
  - Cognito User Pool configured
  - S3 bucket for product images
  - API Gateway endpoint for chatbot (optional)

### Environment Variables

Create a `.env` file in the root directory with the following variables:

```env
# AWS Cognito Configuration
COGNITO_CLIENT_SECRET=your_cognito_client_secret
VITE_COGNITO_DOMAIN=https://your-cognito-domain.auth.region.amazoncognito.com
VITE_COGNITO_CLIENT_ID=your_cognito_client_id

# AWS S3 Configuration
AWS_ACCESS_KEY_ID=your_aws_access_key_id
AWS_SECRET_ACCESS_KEY=your_aws_secret_access_key
AWS_ACCESS_KEY_ID_PROD=your_prod_access_key_id
AWS_SECRET_ACCESS_KEY_PROD=your_prod_secret_access_key

# Application URLs
FRONTEND_URL=http://localhost:3000
AUTH_SERVICE_URL=http://localhost:8080
VITE_API_URL=http://localhost:8888
VITE_APP_URL=http://localhost:3000

# AWS Bedrock (Optional - for chatbot)
AWS_REGION=ap-southeast-2
BEDROCK_AGENT_ID=your_bedrock_agent_id
BEDROCK_AGENT_ALIAS_ID=TSTALIASID
```

### Running with Docker Compose (Recommended)

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Microservices
   ```

2. **Set up environment variables**
   - Create a `.env` file in the root directory with the variables listed above

3. **Start all services**
   ```bash
   docker-compose up --build
   ```

   This will start:
   - All database containers (Neo4j, PostgreSQL instances)
   - All backend microservices
   - API Gateway
   - Frontend application

4. **Access the application**
   - Frontend: http://localhost:3000
   - API Gateway: http://localhost:8888
   - Neo4j Browser: http://localhost:7474
   - PostgreSQL instances:
     - Product Service: localhost:5432
     - Post Service: localhost:5433
     - Admin Service: localhost:5434

### Running Locally (Development)

#### Backend Services

1. **Start databases**
   ```bash
   docker-compose up neo4j postgres-product postgres-post postgres-admin
   ```

2. **Build common library**
   ```bash
   cd review-platform/common-lib
   mvn clean install
   ```

3. **Run each service** (in separate terminals):
   ```bash
   # Auth Service (Port 8080)
   cd review-platform/auth-service
   mvn spring-boot:run

   # User Service (Port 8081)
   cd review-platform/user-service
   mvn spring-boot:run

   # Product Service (Port 8082)
   cd review-platform/product-service
   mvn spring-boot:run

   # Post Service (Port 8083)
   cd review-platform/post-service
   mvn spring-boot:run

   # Admin Service (Port 8084)
   cd review-platform/admin-service
   mvn spring-boot:run

   # API Gateway (Port 8888)
   cd review-platform/api-gateway
   mvn spring-boot:run
   ```

#### Frontend

1. **Install dependencies**
   ```bash
   cd review-platform-fe
   npm install
   ```

2. **Set up environment variables**
   - Create a `.env` file in `review-platform-fe/`:
   ```env
   VITE_API_URL=http://localhost:8888
   VITE_APP_URL=http://localhost:3000
   VITE_COGNITO_DOMAIN=https://your-cognito-domain.auth.region.amazoncognito.com
   VITE_COGNITO_CLIENT_ID=your_cognito_client_id
   ```

3. **Run development server**
   ```bash
   npm run dev
   ```

4. **Access the application**
   - Frontend: http://localhost:3000

### Database Setup

The databases are automatically initialized when using Docker Compose. For local development:

- **Neo4j**: Default credentials are `neo4j/12345678`
- **PostgreSQL**: Default credentials are `postgres/12345`
- **Flyway migrations**: Run automatically on service startup

## 📁 Project Structure

```
Microservices/
├── docker-compose.yml              # Docker Compose configuration
├── .env                           # Environment variables (create this)
├── README.md                      # This file
│
├── review-platform/               # Backend microservices
│   ├── pom.xml                    # Parent Maven POM
│   │
│   ├── common-lib/               # Shared library for all services
│   │   ├── src/main/java/
│   │   │   └── com/example/commonlib/
│   │   │       ├── dto/          # Shared DTOs
│   │   │       ├── entity/      # Shared entities
│   │   │       └── exception/   # Shared exceptions
│   │   └── pom.xml
│   │
│   ├── api-gateway/              # Spring Cloud Gateway
│   │   ├── src/main/resources/
│   │   │   ├── application.yml          # Local routing config
│   │   │   └── application-docker.yml    # Docker routing config
│   │   └── pom.xml
│   │
│   ├── auth-service/             # Authentication service (Port 8080)
│   │   ├── src/main/java/
│   │   │   └── com/example/authservice/
│   │   │       ├── controller/  # Auth endpoints
│   │   │       ├── service/     # Auth logic
│   │   │       └── config/     # Security config
│   │   └── pom.xml
│   │
│   ├── user-service/            # User management service (Port 8081)
│   │   ├── src/main/java/
│   │   │   └── com/example/userservice/
│   │   │       ├── controller/  # User endpoints
│   │   │       ├── service/     # User business logic
│   │   │       ├── repository/ # Neo4j repositories
│   │   │       └── dto/        # User DTOs
│   │   └── pom.xml
│   │
│   ├── product-service/         # Product management service (Port 8082)
│   │   ├── src/main/java/
│   │   │   └── com/example/productservice/
│   │   │       ├── controller/  # Product endpoints
│   │   │       ├── service/     # Product business logic
│   │   │       ├── repository/ # JPA repositories
│   │   │       ├── entity/     # Product entities
│   │   │       └── dto/        # Product DTOs
│   │   ├── data/product/       # Initial data
│   │   └── pom.xml
│   │
│   ├── post-service/            # Post and review service (Port 8083)
│   │   ├── src/main/java/
│   │   │   └── com/example/postservice/
│   │   │       ├── controller/  # Post endpoints
│   │   │       ├── service/     # Post business logic
│   │   │       ├── repository/ # JPA repositories
│   │   │       └── entity/     # Post entities
│   │   ├── data/post/          # Initial data
│   │   └── pom.xml
│   │
│   ├── admin-service/           # Admin management service (Port 8084)
│   │   ├── src/main/java/
│   │   │   └── com/example/adminservice/
│   │   │       ├── controller/  # Admin endpoints
│   │   │       ├── service/     # Admin business logic
│   │   │       └── repository/ # JPA repositories
│   │   ├── data/admin/        # Initial data
│   │   └── pom.xml
│   │
│   └── chat-service/           # Chatbot service (Port 8085) - Currently disabled
│       ├── src/main/java/
│       │   └── com/example/chatservice/
│       └── pom.xml
│
└── review-platform-fe/         # Frontend React application
    ├── src/
    │   ├── Component/          # Reusable React components
    │   │   ├── FloatingChat.jsx      # AI chatbot component
    │   │   ├── ProductCard.jsx       # Product card component
    │   │   ├── Post.jsx              # Post component
    │   │   ├── CreatePost.jsx        # Post creation form
    │   │   └── ProtectedRoute.jsx    # Route protection
    │   │
    │   ├── Layout/             # Layout components
    │   │   ├── Header.jsx            # Navigation header
    │   │   ├── UserLayout.jsx        # User pages layout
    │   │   ├── AdminLayout.jsx       # Admin pages layout
    │   │   └── AccountLayout.jsx     # Auth pages layout
    │   │
    │   ├── Page/               # Page components
    │   │   ├── LoginPage.jsx         # Login page
    │   │   ├── FeedPage.jsx          # User feed page
    │   │   ├── ProductPage.jsx       # Product listing page
    │   │   ├── ProductDetail.jsx     # Product detail page
    │   │   ├── ProfilePage.jsx      # User profile page
    │   │   ├── AdminProductPage.jsx  # Admin product management
    │   │   └── AdminProductCreate.jsx # Admin product creation
    │   │
    │   ├── Util/               # Utility functions
    │   │   └── axios.jsx            # Axios configuration
    │   │
    │   ├── App.jsx             # Main app component
    │   └── main.jsx            # Entry point
    │
    ├── public/                # Static assets
    ├── package.json           # Node dependencies
    ├── vite.config.js         # Vite configuration
    ├── tailwind.config.js     # Tailwind CSS configuration
    └── Dockerfile             # Frontend Dockerfile
```

## 🔌 API Endpoints

### API Gateway Base URL
- **Local**: http://localhost:8888
- **Docker**: http://api-gateway:8888

### Service Endpoints

#### Auth Service (`/api/auth`)
- `POST /api/auth/exchange` - Exchange authorization code for tokens
- `POST /api/auth/refresh` - Refresh access token
- `GET /logout` - Logout user

#### User Service (`/api/user`)
- `GET /api/user/profile` - Get user profile
- `PUT /api/user/profile` - Update user profile
- `GET /api/user/{id}` - Get user by ID

#### Product Service (`/api/product`)
- `GET /api/product` - List products (with filters)
- `GET /api/product/{id}` - Get product details
- `POST /api/product` - Create product (admin)
- `PUT /api/product/{id}` - Update product (admin)
- `DELETE /api/product/{id}` - Delete product (admin)
- `GET /api/brand` - List brands
- `GET /api/review` - List reviews
- `POST /api/review` - Create review
- `GET /api/skintype` - List skin types
- `GET /api/concern` - List concern types

#### Post Service (`/api/posts`)
- `GET /api/posts` - List posts
- `POST /api/posts` - Create post
- `GET /api/posts/{id}` - Get post details
- `PUT /api/posts/{id}/like` - Like/unlike post
- `POST /api/comments` - Create comment
- `GET /api/comments/{postId}` - Get comments for post

#### Admin Service (`/api/admin`)
- Admin-specific endpoints for managing users and content

## 🔐 Authentication Flow

1. User clicks "Login" → Redirected to AWS Cognito login page
2. User authenticates → Cognito redirects to `/auth/callback` with authorization code
3. Frontend exchanges code for tokens → Calls `/api/auth/exchange`
4. Tokens stored in sessionStorage → User is authenticated
5. Protected routes check for token → Redirect to login if missing
6. API requests include token → Backend validates JWT token

## 🐳 Docker Services

| Service | Container Name | Port | Description |
|---------|---------------|------|-------------|
| Frontend | review_frontend | 3000 | React application |
| API Gateway | api_gateway | 8888 | Spring Cloud Gateway |
| Auth Service | auth_service | 8080 | Authentication service |
| User Service | user_service | 8081 | User management |
| Product Service | product_service | 8082 | Product management |
| Post Service | post_service | 8083 | Posts and reviews |
| Admin Service | admin_service | 8084 | Admin operations |
| Neo4j | neo4j | 7474, 7687 | Graph database |
| PostgreSQL (Product) | postgres_product | 5432 | Product database |
| PostgreSQL (Post) | postgres_post | 5433 | Post database |
| PostgreSQL (Admin) | postgres_admin | 5434 | Admin database |

## 📝 Development Notes

### Building Individual Services
```bash
cd review-platform/<service-name>
mvn clean install
```

### Running Tests
```bash
cd review-platform/<service-name>
mvn test
```

### Database Migrations
- Migrations are handled automatically by Flyway on service startup
- Migration scripts are located in `src/main/resources/db/migration/`

### Frontend Build
```bash
cd review-platform-fe
npm run build
```

## 🤝 Contributing

1. Create a feature branch
2. Make your changes
3. Test thoroughly
4. Submit a pull request

## 📄 License

See [LICENSE](LICENSE) file for details.

## 🆘 Troubleshooting

### Port Already in Use
- Check if services are already running: `docker ps`
- Stop existing containers: `docker-compose down`
- Change ports in `docker-compose.yml` if needed

### Database Connection Issues
- Ensure databases are running: `docker ps | grep postgres`
- Check database credentials in `docker-compose.yml`
- Verify network connectivity between services

### CORS Errors
- Ensure `FRONTEND_URL` is correctly set in environment variables
- Check API Gateway CORS configuration in `application.yml`

### Authentication Issues
- Verify Cognito configuration in `.env`
- Check that Cognito callback URLs are registered
- Ensure tokens are being stored correctly in sessionStorage

## 📧 Contact

For questions or issues, please open an issue on the repository.

