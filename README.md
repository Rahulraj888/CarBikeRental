# Car & Bike Rental (Java Servlet/JSP)

A Java EE web application for renting cars and bikes. Built with **JSP/Servlets**, **JDBC**, and **MySQL**.  
Implements user auth, vehicle browsing, booking flow, and basic admin actions.

## ✨ Features
- User registration & login (session-based)
- Browse vehicles (cars & bikes) with availability
- Create and manage bookings (pickup/return dates, price/day)
- Logout & redirect handling
- Contact form
- Environment-based DB config via properties

## 🧱 Tech Stack
- **Java** (JDK 8+ recommended)
- **JSP / Servlets** (Java EE 7, web.xml 3.1)
- **JDBC** + **MySQL**
- **Tomcat** 9/10 (or compatible servlet container)
- **java.util.logging** for logs

## 📂 Project Structure
```
src/
├─ main/java/com/groupb/rental/
│  ├─ constants/               # String constants for messages/paths
│  ├─ dao/                     # DAO interfaces & JDBC implementations
│  ├─ model/                   # POJOs: User, Vehicle, Booking
│  ├─ servlet/                 # UserServlet, BookingServlet, ContactServlet
│  └─ util/                    # DBConnection (loads properties), validators
├─ main/resources/
│  ├─ db-prod.properties       # db.url, db.username, db.password
│  ├─ db-test.properties
│  └─ logging.properties
└─ main/webapp/
   ├─ *.jsp                    # JSP views (login.jsp, register.jsp, booking.jsp, etc.)
   └─ WEB-INF/web.xml          # servlet mappings
```

## 🔌 Database Configuration
Update one of the property files in `src/main/resources/`:
```properties
# db-prod.properties
db.url=jdbc:mysql://localhost:3306/rentaldb
db.username=root
db.password=your_password
```
`DBConnection` loads `db-<env>.properties` based on an internal `env` value (default points to prod/test in code).

> **MySQL schema (suggested):**
```sql
CREATE DATABASE rentaldb;
USE rentaldb;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  role VARCHAR(20) DEFAULT 'USER'
);

CREATE TABLE vehicles (
  id INT AUTO_INCREMENT PRIMARY KEY,
  type VARCHAR(10) NOT NULL,     -- 'Car' or 'Bike'
  brand VARCHAR(50) NOT NULL,
  model VARCHAR(50) NOT NULL,
  price_per_day DECIMAL(10,2) NOT NULL,
  available TINYINT(1) DEFAULT 1
);

CREATE TABLE bookings (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  vehicle_id INT NOT NULL,
  pickup_date DATE NOT NULL,
  return_date DATE NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
);
```

## 🚀 Run Locally (Tomcat)
1. **Prereqs**: JDK 8+, MySQL running, Apache Tomcat 9/10.
2. **Configure DB**: Edit `db-prod.properties` with your MySQL credentials.
3. **Build/Deploy**:
   - Import the project into IntelliJ IDEA / Eclipse as a **Web** project.
   - Configure an **Artifact / WAR exploded** (IDE-managed) or generate a **WAR**.
   - Deploy to Tomcat and start the server.
4. **Open**: `http://localhost:8080/<context-path>/`

## 🔀 Servlet Endpoints (web.xml)
- `UserServlet` → `/UserServlet` (login, register, logout via `action` param)
- `BookingServlet` → `/BookingServlet` (list, book, manage; requires login)
- `ContactServlet` → `/ContactServlet`

JSP pages like `login.jsp`, `register.jsp`, `booking.jsp`, `about.jsp` are under `src/main/webapp/`.

## 🧪 Testing Notes
- A `redirectAfterLogin` session attribute is used to return users to the page they came from.
- Logging configured via `logging.properties` (java.util.logging).

## 📌 Known Limitations / To‑Dos
- No Maven/Gradle files in the repo; build is IDE/Tomcat driven.
- Add password hashing & input validation improvements.
- Provide seed data / SQL dump for vehicles.

## 📝 License
MIT (or your preferred license)
