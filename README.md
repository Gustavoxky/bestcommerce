# BestCommerce1 - Spring Boot REST API

This repository contains the source code for the BestCommerce1 project, a Spring Boot REST API for managing products and user authentication.

## ProductController

The `ProductController` handles operations related to products, such as retrieving all products, getting a specific product by ID, creating, updating, and deleting products.

### Endpoints:

- **GET /api/products**
  - Returns a list of all products.

- **GET /api/products/{id}**
  - Returns details for a specific product identified by its ID.

- **POST /api/products**
  - Creates a new product based on the provided JSON payload.

- **PUT /api/products/{id}**
  - Updates the details of a specific product identified by its ID. Requires a JSON payload with the updated information.

- **DELETE /api/products/{id}**
  - Deletes a specific product identified by its ID.

## UserController

The `UserController` manages user-related functionality, including user registration, login, user information retrieval, and logout. User authentication is implemented using JSON Web Tokens (JWT).

### Endpoints:

- **POST /api/register**
  - Registers a new user. Requires a JSON payload with the user's registration information (username, email, password).

- **POST /api/login**
  - Authenticates a user based on the provided email and password. Generates a JWT for successful logins, which is stored in an HTTP-only cookie.

- **GET /api/user**
  - Retrieves user information based on the JWT stored in the HTTP-only cookie. Requires an authenticated user.

- **POST /api/logout**
  - Logs out the currently authenticated user by clearing the JWT cookie.

## JWT Configuration

JWT configuration is managed in the `JwtConfig` object within the `UserController`. It includes the token expiration time and the secret key used for signing the tokens. Make sure to customize the secret key for production use.

Feel free to explore and extend this Spring Boot application for your specific needs. If you encounter any issues or have suggestions for improvement, please open an issue in this repository.

Happy coding!
