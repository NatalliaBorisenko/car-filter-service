# Car Filter Service

The project implements a car filtering service with unit tests to verify filtering functionality based on criteria
such as car code, car name, engine type, steering wheel side and delivery date.

## Features

- Filtering cars by:
    - `carCode` using `EQUALS`, `NOT_EQUALS`, `CONTAINS`, `NOT_CONTAINS`, and `IN` filter types
    - `carName` using `EQUALS`, `NOT_EQUALS`, `CONTAINS`, `NOT_CONTAINS`, and `IN` filter types
    - `engineType` using `EQUALS`, `NOT_EQUALS`, `CONTAINS`, `NOT_CONTAINS`, and `IN` filter types
    - `deliveryDate` using `EQUALS`, `GREATER_THAN`, `LESS_THAN` filter type
    - `steeringWheelSide` using `EQUALS`, `NOT_EQUALS` filter type
    - Combined filters for multiple criteria (e.g., `engineType` and `steeringWheelSide` with `EQUALS`)
    - No-filter scenario to return all cars
- Comprehensive unit tests to validate filter behavior
- Mocking of service dependencies using Mockito for isolated testing
- Assertions powered by AssertJ for clear and readable test validations

## Technologies

- **Java**: 17
- **Maven**: Build tool for dependency management and test execution.
- **TestNG**: Testing framework for structuring and running tests.
- **Mockito**: Mocking framework for simulating service behavior.
- **AssertJ**: Fluent assertions for test validations.

## Setup

To run the project locally, follow these steps:

1. **Clone the repository**:
   git clone https://github.com/your-username/car-filter-service.git
   cd car-filter-service

2 **Install dependencies: Ensure you have Maven installed, then run**:
mvn clean install

3 **Run all tests: Execute all unit tests using the TestNG XML suite**:
mvn test -DsuiteXmlFile=testng-carFilterTests.xml

4 **Alternatively, run all tests without specifying the XML file**:
mvn test