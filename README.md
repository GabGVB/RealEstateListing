# Real Estate Listing

## Description
Real Estate Listing is a web application that allows users to create, edit, delete, and filter real estate listings. It supports user authentication and session management, with basic CRUD functionalities and user-friendly messaging.

## Features
### Basic Features
- **User Authentication & Registration:** Users can sign up and log in with a username and password.
- **Add Listings:** Users can create listings by providing a title, description, price, and category.
- **Filter Listings:** Listings can be filtered by category and minimum/maximum price.
- **Delete Listings:** Users can delete their own listings.
- **Edit Listings:** Users can modify their own listings (title, description, price, category).
- **Logout:** Users can log out from the application.

### Additional Features
- **Success/Error Messages:** Informational messages for creating, editing, or deleting listings.
- **Session Management (JSESSIONID):** User sessions are managed using cookies.
- **Dynamic Redirection:** Non-authenticated users are automatically redirected to the login page.

## Project Structure
### Controllers
- **AnuntController:** Manages the core functionalities, including login, listing creation, deletion, editing, and filtering.

### Models
- **Anunt:** Represents a listing with attributes such as `id`, `title`, `description`, `price`, `categorie`, and `vanzator` (user).
- **Utilizator:** Represents a user with attributes such as `id`, `username`, `email`, and `password`.

### Repositories
- **AnuntRepository:** Interface for managing listings.
- **UtilizatorRepository:** Interface for managing users.

## User Interface
- **Login Page:** Form for username and password input, with a registration link.
- **Registration Page:** Form for username, password, and email input.
- **Main Page (Logged In):** List of all listings with filter options and buttons for adding listings, viewing user listings, and logging out.
- **Create Listing Page:** Form for entering title, description, price, and category.
- **My Listings Page:** List of the user's own listings with options to edit or delete.
- **Edit Listing Page:** Form to modify a listing.
- **Delete Confirmation Page:** Asks for confirmation before deleting a listing.

## Testing
- **Unit Testing:** Implemented with JUnit and Mockito.
  - `testCreateAnunt_Success()`: Tests successful listing creation.
  - `testCreateAnunt_EmptyTitle()`: Tests failure due to missing title.
  - `testCreateAnunt_InvalidPrice()`: Tests failure due to negative price.
- **API Testing:** Performed with Postman for user authentication (`/login`).

## Future Improvements
1. **Enhanced Form Validation:** More complex validation for email format and stronger password requirements.
2. **Persistent Database Integration (MySQL):** Replace H2 database with a persistent database for long-term data storage.
3. **Additional Features:** Adding image uploads, favorites system, and comparison functionalities.
4. **Extended Models:** Adding attributes like city, property type, and filters based on them. Also, including user phone numbers.
