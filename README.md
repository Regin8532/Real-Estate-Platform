# 🏡 Real Estate Platform  
**Spring Boot | Thymeleaf | MySQL | Spring Security**

A full-stack **Real Estate Web Application** built using **Spring Boot 3**, **Thymeleaf**, **Spring Security**, **JPA (Hibernate)**, and **MySQL**.  
The platform allows users to list properties while admins review, approve, and manage listings securely.

---

## 🚀 Features

### 👤 User (Customer)
- User registration & login
- Secure authentication using **Spring Security**
- Add new property listings
- Upload property images using **Cloudinary**
- View own listed properties in user dashboard
- Track property approval status:
  - ⏳ Pending  
  - ✅ Approved

---

### 🛡️ Admin
- Secure admin-only access
- View all property listings
- Approve property submissions
- Delete properties
- Manage platform content efficiently

---

### 🌐 Public Access
- View **approved** property listings
- Advanced property search:
  - 📍 Location  
  - 🏷️ Property type (SALE / RENT)  
  - 💰 Price range  
  - 🔍 Keyword search  

---

## 🧱 Tech Stack

| Layer        | Technology |
|-------------|-----------|
| Backend     | Spring Boot 3.2 |
| Frontend    | Thymeleaf, HTML, CSS |
| Security    | Spring Security |
| ORM         | Spring Data JPA (Hibernate) |
| Database    | MySQL |
| Image Upload| Cloudinary |
| Build Tool  | Maven |
| Java        | Java 17 |
| Testing     | JUnit 5, Mockito |

---

## 🔐 Test Credentials

### 👤 Customer
- **Email:** `testcustomer@gmail.com`
- **Password:** `testcustomer`

### 🛡️ Admin
- **Email:** `testadmin@gmail.com`
- **Password:** `testadmin`
