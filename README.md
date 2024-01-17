**Project README**

# Resale Platform Backend

This collaborative project, led by the team comprising **Sergey Syutin**, **Ependi Hadzhiev**, and **Eduard Sheffer**, aims to develop the backend part of a resale platform for selling items. The frontend part of the website is already implemented and can be run using Docker. Below are the instructions for setting up and running the frontend:

## Frontend Setup

To install Docker on your computer, follow the instructions at [Docker Desktop](https://www.docker.com/products/docker-desktop/).

Once Docker is installed, open the command line (or terminal) and execute the following command to run the frontend:

docker run -p 3000:3000 --rm ghcr.io/bizinmitya/front-react-avito:v1.21

**Backend Development for Resale Platform**

The goal of this backend development project is to create the Java backend for a resale platform. The backend is expected to implement the following functionality:

1. **User Authentication and Authorization:**
   - Implement user login and authentication mechanisms.

2. **Role Distribution Among Users:**
   - Define roles for users, distinguishing between regular users and administrators.

3. **CRUD Operations for Announcements and Comments:**
   - Allow administrators to delete or edit any announcement or comment.
   - Users should be able to perform CRUD operations on their own announcements and comments.

4. **User Commenting on Announcements:**
   - Enable users to leave comments under each announcement.

5. **Display and Storage of Images:**
   - Implement functionality to display and store images for both announcements and user avatars.


