
# NEXOS - API BANK INC  

## 📌 Descripción del Proyecto
Bank Inc lo ha contratado para crear un sistema que permita administrar sus tarjetas
y transacciones.

El proyecto implementa:
- Arquitectura **hexagonal** (Clean Architecture / Ports & Adapters)
- Persistencia **Jpa** con **Hibernate** y **PostgreSQL**
- Documentación de APIs con **Swagger/OpenAPI**

---

## 🗂️ Estructura del proyecto
src/
├─ main/
│ ├─ java/com/prueba/seti/
│ │ ├─ domain/ # Modelos y lógica de negocio
│ │ ├─ service/ # Casos de uso (UseCases)
│ │ ├─ adapter/ # Puertos de persistencia / POSTGRES
│ │ ├─ controller/ # Endpoints REST
│ │ └─ config/ # Configuraciones (Swagger, POSTGRES)
│ └─ resources/
│ ├─ application.yml # Configuración de base de datos y logs
└─ test/
└─ java/com/prueba/seti/
└─ (Pruebas unitarias y de integración)


---

## 💾 Persistencia

- Base de datos: **PostgreSQL**
- Conexión reactiva vía **jpa**
- Tablas principales:
    - `card` (cardId VARCHAR, productoId VARCHAR,firstName VARCHAR,lastName VARCHAR, expirationDate LocalDate,balance ,statuts Enum , currency Enum ,createAt LocalDate)
    - `transaction` (transactionId integer, cardEntity VARCHAR, amount DOUBLE, transactionDate Date , reversed BOOLEAN)
    
    

---
## 🔗 Requerimientos principales

1. Generar número de tarjeta.
2. Activar tarjeta.
3. Bloquear tarjeta.
4. Recargar saldo.
5. Consulta de saldo.
6. Transacción de compra.
7. Consultar transacción.
8. Anulacion transacción.

## 🔗 Endpoints principales

| Recurso     | Método | URL                                | Descripción                                   |
|-------------|--------|------------------------------------|-----------------------------------------------|
| Tarjeta     | GET    | `/api/card/{productoId}/number`    | Generar número de tarjeta                     |
| Tarjeta     | POST   | `/api/card/enroll`                 | Activar tarjeta                               |
| Tarjeta     | DELETE | `api/card/{cardId}`                | Bloquear tarjeta                              |
| Tarjeta     | POST   | `/api/card/balance`                | Recargar saldo                                |
| Tarjeta     | POST   | `/api/card/balance/{cardId}`       | Consulta de saldo                             |
| transacción | POST   | `/api/transaction/purchase`        | Transacción de compra                         |
| transacción    | GET    | `/api/transaction/{transactionId}` | Consultar transacción                              |
| transacción    | POST   | `/api/transaction/anulation`        | Anulacion transacción                             |


---

## Las pruebas Unitarias quedaron en un 80% de cobertura 

## 📊 Documentación Swagger

- URL: [http://localhost:9000/swagger-ui/index.html](http://localhost:9000/swagger-ui/index.html)
- Permite probar requests directamente desde el navegador.

---

## Levantar Proyecto con docker compose
- Solo es ejecutar  docker-compose up y se ejecuta solo
- En la raiz se encuentra un documento para poder ejecutarlo en local o en mi drive https://docs.google.com/document/d/1bX8BNSUdYfx942FsQkMcc90JYiq1tPbe24UESg3xids/edit?usp=sharing
- En la raiz del proyecto encuentran la coleccion de postman






