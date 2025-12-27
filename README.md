 BarLoyalty – Microservices Backend

BarLoyalty este o aplicație backend bazată pe microservicii, dezvoltată în Java (Spring Boot),
care gestionează un sistem de loialitate pentru baruri.

Utilizatorii acumulează puncte la tranzacții, iar evenimentele sunt trimise în timp real
prin WebSocket.



Arhitectură

- Gateway Service
  - autentificare JWT
  - gestionare utilizatori, baruri, tranzacții
  - WebSocket (notificări loialitate)
  - observabilitate (Spring Actuator + Micrometer)

  PostgreSQL – bază de date
- Docker & Docker Compose – rulare locală
- GitHub Actions – CI/CD (urmează)



 Tehnologii folosite

- Java 21
- Spring Boot 3
- Spring Security (JWT)
- Spring Data JPA
- Spring WebSocket
- Spring Actuator & Micrometer
- PostgreSQL
- Docker
- GitHub Actions



 Rulare locală (Docker)

```bash
docker-compose up --build
