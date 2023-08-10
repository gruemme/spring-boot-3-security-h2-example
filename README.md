# Spring Boot 3 Security example with h2 database

Example usage for Spring Security with Spring Boot 3 using HTTP Basic with users from an H2 database.

##

You can start the application with `./mvnw spring-boot:run` command.
The embedded H2 database will be started along with the application.

## Roles

A user can be logging in via HTTP Basic or be unauthenticated. An authenticated user has a role according
enum `UserRole`, *ADMIN* or *USER*.

## Endpoints

There are four endpoints

| Method | Path | Access by | Descriptions |
|--------| ------ |------------------------|
| GET    | /books | any user | List all existing |   
| GET    | /books/{id} | any user | Shows book with the given id |   
| POST    | /books | any authenticated user | Adds a new book |
| POST    | /persons | authenticated user with role ADMIN | Adds a new user with role USER |  

## Users

The initial admin user (admin/admin) is added via the `data.sql` that is added on the start of the 
application after creating on the database scheme. New user can be added via the rest endpoint. The entity
`Person` represents a user in the database.

## Spring security configuration

The class `SecurityConfiguration` creates two configuration beans for Spring Security.

* `passwordEncoder()` will configure bcrypt with 12 rounds as password hash algorithm. You can also generate a hash 
  of a given password on <https://bcrypt-generator.com/>.
* `filterChain(HttpSecurity)` configures:
  * CSRF is disabled for easier access on this *learning* project.
  * Default HTTP Basic as authentication method.
  * Session management is stateless.
  * Frames for the same origin are allowed. This is needed to use the h2-console ui.
  * Allowing unauthenticated access to the swagger ui.
  * Allowing unauthenticated access to the h2 console.
  * Allowing unauthenticated GET-access to `/books` and `/books/*`.
  * Restricting POST-access to `/persons` to the `ADMIN` role.
  * Restricting any other URL to an authenticated user.

The class `UserDetailsServiceImpl` will replace the default behavior of Spring Security with loading users from the
database. For a given username a `Person` entity is loaded from the database and passed with the password hash and
the role as a Spring Security `User` object implementing the `UserDetails` interface.

## Swagger UI

The swagger ui can be accessed via the url <http://localhost:8080/swagger-ui.html>. The class `BasicAuth`
is a marker for the swagger ui to indicate an endpoint needs HTTP Basic authentication.
