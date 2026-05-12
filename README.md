# Banking AI - REST API

A Spring Boot REST API for banking operations.

## Features
- Create, Read, Update, Delete accounts
- H2 in-memory database with JPA
- Input validation and error handling

## Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Lombok

## API Endpoints
| Method | URL | Description |
|--------|-----|-------------|
| GET | /accounts | Get all accounts |
| GET | /accounts/{id} | Get account by ID |
| POST | /accounts | Create account |
| PUT | /accounts/{id} | Update account |
| DELETE | /accounts/{id} | Delete account |
