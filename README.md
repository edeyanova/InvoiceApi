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

  Response: A list of all invoices.

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





