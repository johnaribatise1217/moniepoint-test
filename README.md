# Moniepoint Merchant Activities Analytical System

**Author:** Aribatise John Olugbadeleke

## Project Overview

The Moniepoint Merchant Activities Analytical System is a Spring Boot application designed to analyze merchant transaction data and provide comprehensive analytics insights. The system imports merchant activity data from CSV files and exposes RESTful APIs to retrieve analytics metrics such as top merchants, monthly active merchants, product adoption rates, KYC funnels, and transaction failure rates.

---

## Prerequisites

Before running this application, ensure you have the following installed on your system:

- **IDE:** Visual Studio Code or IntelliJ IDEA
- **Java:** JDK 21 or higher
- **Build Tool:** Maven 3.8+ or Gradle 7.0+
- **Database:** MySQL 8.0 or higher
- **API Client:** Postman (for testing endpoints)
- **Git:** For cloning the repository

---

## Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd merchantapi
```

### 2. Install Dependencies

Using Maven:

```bash
mvn install
```

Using Gradle:

```bash
gradle build
```

### 3. Configure Environment Variables

Create a `.env` file in the root project directory:

```bash
cp .env.example .env
```

Edit the `.env` file and add your MySQL database credentials:

```env
MYSQL_PORT=3306
MYSQL_DATABASE=merchant_db
MYSQL_USER=root
MYSQL_PASSWORD=your_password
```

### 4. Start the Application

Using Maven:

```bash
mvn spring-boot:run
```

Using Gradle:

```bash
gradle bootRun
```

You should see output indicating the application started on **port 8080**.

---

## Testing the APIs with Postman

### 1. Create a New Collection

- Open Postman
- Create a new collection named "Merchant Analytics"
- Set the base URL as `http://localhost:8080`

### 2. Available Endpoints

#### **1. Get Top Merchant**

- **Method:** `GET`
- **Endpoint:** `/analytics/top-merchant`
- **Description:** Retrieves the merchant with the highest transaction volume

#### **2. Get Monthly Active Merchants**

- **Method:** `GET`
- **Endpoint:** `/analytics/monthly-active-merchants`
- **Description:** Returns count of active merchants grouped by month in YYYY-MM format

#### **3. Get Product Adoption**

- **Method:** `GET`
- **Endpoint:** `/analytics/product-adoption`
- **Description:** Shows merchant adoption rates for each product type

#### **4. Get KYC Funnel**

- **Method:** `GET`
- **Endpoint:** `/analytics/kyc-funnel`
- **Description:** Displays KYC onboarding funnel metrics (documents submitted, verifications completed, tier upgrades)

#### **5. Get Failure Rates**

- **Method:** `GET`
- **Endpoint:** `/analytics/failure-rates`
- **Description:** Returns transaction failure rates by product (rounded to 1 decimal place)

---

## Response Formats

### Successful API Response

All successful responses follow this structure:

```json
{
  "success": true,
  "statusCode": "OK",
  "Response": {
    "data": "..."
  }
}
```

**Example Response - Top Merchant:**

```json
{
  "success": true,
  "statusCode": "OK",
  "Response": {
    "merchantId": "MERCH_001",
    "totalVolume": 1500000.50
  }
}
```

**Example Response - Monthly Active Merchants:**

```json
{
  "success": true,
  "statusCode": "OK",
  "Response": {
    "2025-01": 150,
    "2025-02": 200,
    "2025-03": 175
  }
}
```

### Error Response

Error responses follow this structure:

```json
{
  "success": false,
  "apiPath": "/analytics/top-merchant",
  "errorCode": "INTERNAL_SERVER_ERROR",
  "errorMessage": "No data found",
  "errorTime": "2026-02-21T14:32:12"
}
```

---

## Database Setup

The application uses Hibernate ORM with automatic schema generation. On startup, Spring Boot will:

1. Automatically create the `merchant_activities` table
2. Import CSV data from the `/data` folder
3. Apply database migrations if needed

Ensure your MySQL database exists before running the application.

---

## Project Structure

```
merchantapi/
├── src/main/java/com/moniepointmerchant/merchantapi/
│   ├── controller/      # REST API endpoints
│   ├── service/         # Business logic
│   ├── repository/      # Database access layer
│   ├── model/           # Entity classes
│   └── response/        # Response DTOs
├── src/main/resources/
│   └── application.yml  # Application configuration
├── data/                # CSV files for import
├── .env.example         # Environment variables template
├── pom.xml              # Maven configuration
└── README.md            # This file
```

---

## Troubleshooting

### Port 8080 Already in Use

Change the port in `application.yml`:

```yaml
server:
  port: 8081
```

### Database Connection Failed

- Verify MySQL is running: `mysql -u root -p`
- Check `.env` file has correct credentials
- Ensure `MYSQL_DATABASE` exists in MySQL

### No Data Imported

- Verify CSV files exist in `/data` folder
- Check application logs for import errors
- Ensure CSV format matches expected schema

---

## Notes

- The application uses Spring Data JPA with native SQL queries for analytics
- All monetary amounts are stored as `BigDecimal` for precision
- Enum values (Status, Product, Channel, etc.) are string-based in the database
- The system automatically skips duplicate imports if data already exists

