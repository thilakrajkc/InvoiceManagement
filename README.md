# Invoice Management Project Implementation Details:

A RESTful Spring Boot application that provides invoice management. 
Including invoice creation ,payments, and overdue handling.

Create invoices with a specific amount and due date
Pay invoices partially or fully
Automatically detect and process overdue invoices 
Status tracking: PENDING, PAID, VOID
Handles partial payments by marking original as paid and generating a new invoice for remaining + late fee
Built using DTOs, services, controller, Entity ,Repository and exception handling.
Docker support to deploy with a single command.

-----------------------------------------------

# Libraries Used:

Spring Web, 
Spring Data JPA, 
H2 In-Memory Database, 
Lombok – Reduce boilerplate, 
SLF4J – Logging, 
Docker + Docker Compose– Containerization

----------------------------------------------

# Run the project:

Clone the repository
git clone https://github.com/thilakrajkc/InvoiceManagement.git

Build the project
Maven clean and double click Package to generate .jar file
or with command ./mvnw clean package -DskipTests

For direct run:
Run the Spring Boot application
java -jar target/invoice-project-0.0.1-SNAPSHOT.jar

I have added docker file 
Can run with command : docker compose up --build
Note: Docker should be installed in the system

It will run on the port:
http://localhost:8080

----------------------------------------------

# APIs:

# Create new Invoice
POST /invoices

Request Body:
{
"amount": 100.00,
"due_date": "2025-07-25"
}

response:
{ "id": 1 }



# Get all invoices.
GET /invoices

Response:

[
{
"id": 1,
"amount": 250.0,
"paidAmount": 0.0,
"dueDate": "2025-07-16",
"status": "VOID",
"remainingAmount": 250.0
},
{
"id": 2,
"amount": 550.0,
"paidAmount": 0.0,
"dueDate": "2025-07-19",
"status": "PENDING",
"remainingAmount": 550.0
},
{
"id": 3,
"amount": 300.0,
"paidAmount": 300.0,
"dueDate": "2025-07-25",
"status": "PAID",
"remainingAmount": 0.0
},
{
"id": 4,
"amount": 800.0,
"paidAmount": 400.0,
"dueDate": "2025-07-19",
"status": "PENDING",
"remainingAmount": 400.0
},
{
"id": 5,
"amount": 265.0,
"paidAmount": 0.0,
"dueDate": "2025-07-22",
"status": "PENDING",
"remainingAmount": 265.0
}
]



# Pay a specific invoice.
POST invoices/payments/3

{
"amount": 300
}


# Process all overdue invoices.
POST /invoices/process-overdue

{
"late_fee": 10.00,
"overdue_days": 3
}

--------------------------------------



