Portfolio API Development

Overview

This project is an API designed to manage user portfolio information, including details such as projects, skills, experience, and education. It supports CRUD operations (Create, Read, Update, Delete) and incorporates JWT-based user authentication to ensure secure access to the system. The API also features user authorization, allowing users to modify only their own portfolio data.

Features

User Authentication: JWT (JSON Web Tokens) is used for secure authentication.
CRUD Operations: Full functionality to create, retrieve, update, and delete portfolio data.
User Authorization: Ensures users can only manage their own portfolio data.
Data Security: Secure access and management of portfolio information.
Endpoints

The following endpoints are available for managing portfolio data:

Authentication
POST /api/auth/register: Register a new user
POST /api/auth/login: Log in and obtain a JWT token
Portfolio
GET /api/portfolio: Get the authenticated user's portfolio
POST /api/portfolio: Create a new portfolio entry
PUT /api/portfolio: Update an existing portfolio entry
DELETE /api/portfolio: Delete a portfolio entry
Authentication

The API uses JWT for secure user authentication.
After successful login, the user will receive a JWT token, which should be included in the Authorization header for subsequent requests.
Authorization

Only the owner of a portfolio can modify or delete their portfolio data.
JWT tokens are checked to ensure users only access or modify their own portfolio.
Pros

Portfolio Management: Provides a convenient way for users to manage and showcase their portfolios.
Security: JWT ensures that user data is protected and only accessible by the user themselves.
Cons

Complexity: Implementing secure data management and authentication can be complex.
Scalability: Additional work may be needed to handle larger user bases and ensure high performance.
