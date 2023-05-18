# Library React Spring Boot

This is a full-stack web application for a library. It uses React.js for the frontend, Java Spring Boot for the backend, and MySQL for the database.

This project is based on the course [Full Stack React and Java Spring Boot: The Developer Guide](https://www.udemy.com/course/full-stack-react-and-java-spring-boot-the-developer-guide) but includes some modifications and additional features.

## Features

- Browse and search for books
- View book reviews
- Sign in to checkout and return books, pay arrears, view borrowing history, and message administrators
- Administrators can add new books, adjust book copies, and reply to customer messages

## Third-Party Tools

The application uses Okta for authentication and Stripe for payments. 

### Okta

Register an account at [Okta Developer](https://developer.okta.com/) and create a new App Integration to get your Client ID and Issuer address.

### Stripe

Register an account at [Stripe](https://dashboard.stripe.com/register) to get your publishable key and secret key.

## Setup

1. Create a `.env.local` file in the `react-library\` directory with the following variables:
    - `REACT_APP_OKTA_CLIENT_ID`
    - `REACT_APP_OKTA_ISSUER`
    - `REACT_APP_STRIPE_PUBLIC_KEY`

2. Create an `application-local.properties` file in the `spring-boot-library\src\main\resources\` directory with the following variables:
    - `okta.oauth2.client-id`
    - `okta.oauth2.issuer`
    - `stripe.keys.secret`

3. Run the scripts in the `1. sql_scripts` directory in your MySQL database.

4. Navigate to the `spring-boot-library` directory and run the Spring Boot application.

5. Navigate to the `react-library\` directory, run `npm install` to install dependencies, and `npm start` to start the frontend.

## Addresses

- Frontend: `https://localhost:3000`
- Backend: `https://localhost:8443` (can be configured in the `.application.properties` file in the `spring-boot-library\src\main\resources\` directory, then the front should configure it in `.env` file in the `react-library` directory)

## Security

The application uses HTTPS for secure communication.

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
