# InvoiceApi

This is a simple RESTful API for managing a list of invoices, built with Spring Boot. 
It supports CRUD operations (Create, Read, Update, Delete) for invoices.

# Requirements

- Java 17 or higher
- Maven 3.8.1 or higher

## Features
- Create a new invoice
- Retrieve a single invoice by its ID
- Retrieve all invoices
- Update an existing invoice
- Delete an invoice by its ID
- Sort and filter invoices

## Running the Project
```sh
git clone https://github.com/edeyanova/InvoiceApi.git
cd invoice-api
```

### Build the project
```sh
mvn clean install
```

### Run the application
```sh
mvn spring-boot:run
```
or 
```sh
java -jar target/invoice-api-0.0.1-SNAPSHOT.jar
```

### Access the api
Once the application is running, you can access the API at http://localhost:8080/invoices.

## API Endpoints
- Retrieve All Invoices
  GET /invoices

  Optional Query Parameters for Sorting and Filtering:
  - sortBy: The field to sort by.
  - direction: The sort direction, either asc for ascending or desc for descending (default is asc).
  - startDate: The start date of the invoice date range (format: YYYY-MM-DD).
  - endDate: The end date of the invoice date range (format: YYYY-MM-DD).
  - number: The invoice number to filter by.

  Example Queries:
  
  Retrieve all invoices sorted by invoice date in ascending order (default):
  ```sh
  GET /invoices?sortBy=invoiceDate
  ```

  Retrieve all invoices issued between two dates:
  ```sh
  GET /invoices?startDate=2024-05-05&endDate=2024-09-09
  ```

  Response: A list of all invoices, optionally sorted and filtered.

- Retrieve a Single Invoice by ID: 
  GET /invoices/{id}

  Response: The requested invoice.

- Create a New Invoice: 
  POST /invoices

  Request Body: JSON representation of an invoice.
  Response: The created invoice.

  Example:
  ```sh
    {
      "number": "001",
      "buyer": "ABC Company",
      "supplier": "XYZ Supplier",
      "items": [
          {
              "name": "Product A",
              "quantity": 2,
              "amount": 52.00
          },
          {
              "name": "Product B",
              "quantity": 1,
              "amount": 105.00
          }
      ],
      "invoiceDate": "2024-05-15",
      "dueDate": "2024-06-20"
    }
  ```

- Update an Existing Invoice:
  PUT /invoices/{id}

  Request Body: JSON representation of the updated invoice.
  Response: The updated invoice.

- Delete an Invoice by ID:
  DELETE /invoices/{id}

  Response: No content.

## In-Memory Database
The application uses the H2 in-memory database for storing invoice data.

## Running Tests
```sh
mvn test
```





