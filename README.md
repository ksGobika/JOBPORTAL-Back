# Job Portal Backend (Spring Boot + MySQL)

Spring Boot REST API backend for the Job Portal Application using Spring Data JPA, Hibernate, and MySQL.

---

## 🚀 Prerequisites

1. **Java JDK**: Version 17 or 21 (or compatible STS JDK).
2. **MySQL Server**: Running locally on port `3306`.
3. **IDE**: Spring Tool Suite (STS) / Eclipse IDE with Spring Tools installed, or VS Code / IntelliJ IDEA.

---

## 🛠️ How to Open & Run in Spring Tool Suite (STS) / Eclipse

### Step 1: Open STS / Eclipse
Launch your Spring Tool Suite (STS) or Eclipse IDE.

### Step 2: Import the Backend Project as Maven Project
1. Go to the menu: **File** -> **Import...**
2. In the import wizard, expand **Maven** and choose **Existing Maven Projects**, then click **Next**.
3. For **Root Directory**, click **Browse...** and select the `backend` folder:
   ```
   .../spring_boot_mysql_setup/backend
   ```
4. STS will detect `pom.xml`. Ensure `pom.xml` is checked in the Projects list.
5. Click **Finish**. STS will import the project and automatically download dependencies.

### Step 3: Verify / Configure MySQL Connection
Open `src/main/resources/application.properties`:
```properties
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/jobportal?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
> **Note**: Update `spring.datasource.username` and `spring.datasource.password` if your MySQL credentials differ from `root` / `root`.

### Step 4: Run the Backend Application
1. In the **Project Explorer** or **Package Explorer**, expand:
   `src/main/java` -> `com.jobportal` -> `JobPortalApplication.java`
2. **Right-click** on `JobPortalApplication.java`
3. Select **Run As** -> **Spring Boot App** (or **Java Application**).
4. Watch the **Console** tab. You should see:
   - Spring Boot logo
   - Hibernate auto-creating database tables in MySQL `jobportal`
   - `Seeding database from classpath resource: /db.json`
   - `Tomcat started on port 8080 (http)`

---

## 💻 Running via Command Line (Alternative)

If you prefer running without opening STS:
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

---

## 📡 REST API Endpoints Overview

The backend exposes full RESTful endpoints with CORS enabled for `http://localhost:3000`:

| Entity | Method | Endpoint | Description |
|---|---|---|---|
| **Users** | `GET` | `/users?email={email}` | Find user by email (Login) |
| | `POST` | `/users` | Register a new user |
| | `GET` | `/users/{id}` | Get user by ID |
| | `PATCH` | `/users/{id}` | Update profile / employer details |
| **Jobs** | `GET` | `/jobs` or `/jobs?status=approved` | List all jobs |
| | `GET` | `/jobs/{id}` | Get job details |
| | `POST` | `/jobs` | Post a new job |
| | `PATCH` | `/jobs/{id}` | Update job status |
| | `DELETE` | `/jobs/{id}` | Delete job |
| **Applications**| `GET` | `/applications?seekerId={id}` | Get job seeker applications |
| | `GET` | `/applications?employerId={id}` | Get employer received applications |
| | `POST` | `/applications` | Submit application |
| | `PATCH` | `/applications/{id}` | Update application status |
| **Messages** | `GET` | `/messages` or `/messages?senderId={id}` | Get chat messages |
| | `POST` | `/messages` | Send message |
| **Announcements**| `GET` | `/announcements` | Get system announcements |
| | `POST` | `/announcements` | Post announcement |
| **FAQs** | `GET` | `/faqs` | Get FAQs |
| | `POST` | `/faqs` | Add FAQ |
| **Reviews** | `GET` | `/reviews?employerId={id}` | Get company reviews |
| | `POST` | `/reviews` | Submit company review |

