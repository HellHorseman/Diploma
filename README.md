**Project README**

# Resale Platform Backend

This collaborative project, led by the team comprising **Ependi Hadzhiev**, **Sergey Syutin**, and **Eduard Sheffer**, aims to develop the backend part of a resale platform for selling items. The frontend part of the website is already implemented and can be run using Docker. Below are the instructions for setting up and running the frontend:

## Frontend Setup

To install Docker on your computer, follow the instructions at [Docker Desktop](https://www.docker.com/products/docker-desktop/).

Once Docker is installed, open the command line (or terminal) and execute the following command to run the frontend:

docker run -p 3000:3000 --rm ghcr.io/bizinmitya/front-react-avito:v1.21

## Backend Development
The backend part of the project involves implementing the following functionality:

## User authentication and authorization.
Role assignment for users: regular users and administrators.
CRUD operations for announcements and comments: administrators can delete or edit all announcements and comments, while regular users can only modify their own.
Allow users to leave comments under each announcement.
Display and store images for announcements, as well as user avatars.
## Nice to Have
Ensure code is properly formatted.
Avoid code duplications exceeding three lines; common operations should be placed in services or utility methods.
Meaningful naming for variables and classes.
No commented-out sections, unreachable instructions, or unused methods in the code.
Follow the Single Responsibility Principle (SRP): each class should perform one task or work with one entity.
Prioritize integration tests, but some components, such as mappers, can be tested with unit tests.
Feel free to contribute to the project and make it even better! If you have any questions, please reach out to the project contributors.
