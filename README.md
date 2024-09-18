<div align="center">
  <h1>//////📚 Online Book Store 📚\\\\\\</h1>
</div> 

![Online-Book-Store-Logo.png](images/book-logo.png)
---
💁Welcome to the online bookstore management system!

📚 The "Book Store" project is an online platform for book enthusiasts. 
Users have the ability to browse a wide range of books across various genres, explore their descriptions, 
learn about authors, and discover prices.

The service offers a convenient search for books by categories to provide quick access to literary works. 
Each user can create a personal account, track purchase history. 
Adding books to the cart and completing orders is done easily and efficiently, 
providing customers with a convenient and secure way to purchase their selected literature.
### 📷 Visual Overview
[![Watch on Loom](https://img.shields.io/badge/Watch%20on-Loom-00a4d9)](https://www.loom.com/share/79258adc8cca46069736d902d73330e3?sid=77bcb216-ed29-4741-b3cc-3eaa582cdf0d)

---
<p align="center">
  <a href="#technologies">Technologies</a> |
  <a href="#getting-started">Getting Started</a> |
  <a href="#domain-models">Domain Models</a> |
  <a href="#endpoints">Endpoints</a>
</p>

---

<h2 id="technologies"> ⚙️ Technologies</h2>

---
<ul style="list-style: none">
 <li><img src="images/java-logo.png" alt="" width="20" style="position: relative; top: 5px;"> Java 21</li>
 <li><img src="images/maven-logo.png" alt="" width="20" style="position: relative; top: 10px;"> Maven</li>
 <li><img src="images/spring-logo.png" alt="" width="20" style="position: relative; top: 5px;"> Spring framework <i><small>(boot, data, security)</small></i> </li>
 <li><img src="images/lombok-logo.png" alt="" width="20" style="position: relative; top: 5px;"> Lombok</li>
 <li><img src="images/mapstruct-logo.png" alt="" width="20" style="position: relative; top: 5px;"> MapStruct</li>
 <li><img src="images/mysql-logo.png" alt="" width="20" style="position: relative; top: 5px;"> MySQL 8</li>
 <li><img src="images/hibernate-logo.png" alt="" width="20" style="position: relative; top: 5px;"> Hibernate</li>
 <li><img src="images/liquibase-logo.svg" alt="" width="20" style="position: relative; top: 5px;"> Liquibase</li>
 <li><img src="images/junit5-logo.png" alt="" width="20" style="position: relative; top: 5px;"> JUnit5 <i><small>(+ Mockito)</small></i></li>
 <li><img src="images/docker-logo.png" alt="" width="20" style="position: relative; top: 5px;"> Docker</li>
 <li><img src="images/Swagger-logo.png" alt="" width="20" style="position: relative; top: 5px;"> Swagger</li>
</ul>


---
<h2 id="getting-started"> 👤 Getting Started</h2>

---
1. Make sure you have installed

<ul style="list-style: none">
<li>
<img src="images/java-logo.png" alt="" width="25" style="position: relative; top: 5px;"> 
<a href="https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html">JDK 21</a>
</li>
<li>
<img src="images/docker-logo.png" alt="" width="25" style="position: relative; top: 5px;"> 
<a href="https://www.docker.com/products/docker-desktop/">Docker</a>
</li>
</ul>

2. Clone the repository:
    - Open your terminal and paste: 
    ```text
    git clone https://github.com/vikkushnir/Book-store.git
    ```
3. Create the .env file with the corresponding variables:<br>
    Look for variables names in file: `.env.sample`
4. Build the project:
    - Open your terminal and paste: `mvn clean package`
5. Use Docker Compose:
    - Open your terminal and paste: `docker-compose build` and `docker-compose up`

---
Here is the Postman collection to test the program:

[![Run In Postman](https://run.pstmn.io/button.svg)](https://www.postman.com/navigation-geoscientist-40209657/book-store-api/collection/paafu5d/?action=share&creator=34526088)

You can access the Swagger UI for detailed API documentation and interactive testing at:

[![Swagger UI](https://img.shields.io/badge/Swagger-UI-green)](http://ec2-54-221-101-88.compute-1.amazonaws.com/api/swagger-ui/index.html#/)

#### Credentials

| **Role** | **Login**                | **Password**       |
|:---------|:-------------------------|:-------------------|
| ADMIN    | sara.smith@example.com   | StrongPassword123  |
| USER     | emily.smith@example.com  | StrongPassword123  |


---
<h2 id="domain-models"> 📋 Domain Models</h2>

---

- **User:** Contains information about the registered user including their authentication details and personal information.
- **Role:** Represents the role of a user in the system, for example, admin or user.
- **Book:** Represents a book available in the store.
- **Category:** Represents a category that a book can belong to.
- **ShoppingCart:** Represents a user's shopping cart.
- **CartItem:** Represents an item in a user's shopping cart.
- **Order:** Represents an order placed by a user.
- **OrderItem:** Represents an item in a user's order.

---
<h2 id="endpoints"> 💬 Endpoints</h2>

---
#### Authorization

| **HTTP method** | **Endpoint**       | **Description**                                      |
|:----------------|:-------------------|:-----------------------------------------------------|
| POST            | /api/auth/register | Register a new user to the system                    |
| POST            | /api/auth/login    | Login with email and password. Response - JWT token  |

#### Book management

| **HTTP method** | **Endpoint**      | **Role** | **Description**                                                                                                  |
|:----------------|:------------------|----------|:-----------------------------------------------------------------------------------------------------------------|
| GET             | /api/books        | USER     | Get all books per website pages                                                                                  |
| GET             | /api/books/{id}   | USER     | Get the book by its id number                                                                                    |
| GET             | /api/books/search | USER     | Search books by title, author, price, isbn *(author=value&title=value&priceFrom=value&priceTo=value&isbn=value)* |
| POST            | /api/books        | ADMIN    | Create a new book                                                                                                |
| PUT             | /api/books/{id}   | ADMIN    | Update the book by its id number                                                                                 |
| DELETE          | /api/books/{id}   | ADMIN    | Delete the book by its id number                                                                                 |

#### Categories management

| **HTTP method** | **Endpoint**               | **Role** | **Description**                                    |
|:----------------|:---------------------------|----------|:---------------------------------------------------|
| GET             | /api/categories            | USER     | Get all categories per website pages               |
| GET             | /api/categories/{id}       | USER     | Get the category by its id number                  |
| GET             | /api/categories/{id}/books | USER     | Get list of books by the category by its id number |
| POST            | /api/categories            | ADMIN    | Create a new category                              |
| PUT             | /api/categories/{id}       | ADMIN    | Update the category by its id number               |
| DELETE          | /api/categories/{id}       | ADMIN    | Delete the category by its id number               |

#### Shopping cart management

| **HTTP method** | **Endpoint**              | **Role** | **Description**                                            |
|:----------------|:--------------------------|----------|:-----------------------------------------------------------|
| GET             | /api/cart                 | USER     | Get shopping cart                                          |
| POST            | /api/cart                 | USER     | Add a new book to shopping cart                            |
| PUT             | /api/cart/cart-items/{id} | USER     | Endpoint for updating quantity of an item in shopping cart |
| DELETE          | /api/cart/cart-items/{id} | USER     | Delete book from shopping cart by id                       |

#### Order management

| **HTTP method** | **Endpoint**                    | **Role** | **Description**                                                           |
|:----------------|:--------------------------------|----------|:--------------------------------------------------------------------------|
| POST            | /api/orders                     | USER     | Place an order based on your shopping cart, then shopping cart is deleted |
| GET             | /api/orders                     | USER     | Get all orders for user                                                   |
| GET             | /api/orders/{id}/items          | USER     | Get all order items by order id                                           |
| GET             | /api/orders/{id}/items/{itemId} | USER     | Get info about order item by order id and item id                         |
| PATCH           | /api/orders/{id}                | ADMIN    | Update order status for order by id                                       |
___

<h2 id="challenges"> 💡 Challenges</h2>
During the development of the online bookstore management system, several challenges were encountered:

**Database Schema Evolution:** Managing changes to the database schema without disrupting the live application was challenging. I used Liquibase to handle versioning and applying incremental changes to the database.

**Security:** Ensuring secure authentication and authorization was critical. Implementing JWT for secure login sessions and role-based access control required meticulous planning and testing.

**Data Mapping:** Converting data between different layers (database entities, DTOs) was complex. MapStruct helped automate and simplify the mapping process, reducing boilerplate code and potential errors.

---

<h2 id="improvements"> 🚀 Possible Improvements</h2>
There are several potential improvements to enhance the online bookstore management system:

**Search Optimization:** Enhancing the search functionality with advanced filters, natural language processing, and auto-suggestions.

**Payment Integration:** Integrating more payment gateways to provide users with multiple payment options.

**User Profile Management:** Enhancing user profile management with more customization options, such as notification preferences, profile customization, account security settings.

---
## 👨‍💻Author

* Github: [@vikkushnir](https://github.com/vikkushnir)
* LinkedIn: [Viktor Kushnir](https://www.linkedin.com/in/viktor-kushnir-716b1a18b/)
* Telegram: [@Viktor_K2](https://t.me/viktor_k2)

---

##  📝License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.md) file for details.
