# Ruta Producer

Microservicio productor que recibe informacion de rutas de vehiculos mediante una API REST y las publica en una cola de RabbitMQ para su procesamiento asincronico.

## Tecnologias

- Java 21
- Spring Boot 4.0.2
- Spring AMQP (RabbitMQ)
- Spring Web
- Lombok
- Maven
- Docker

## Arquitectura

Este servicio forma parte de un patron productor-consumidor:

```
Cliente HTTP --> [Ruta Producer] --> RabbitMQ --> [Ruta Consumer] --> rutas.json
```

## Endpoint

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| POST | `/api/gps/ruta` | Recibe una ruta y la publica en RabbitMQ |

### Request Body

```json
{
  "patente": "BCDF56",
  "rutaInicio": "Santiago",
  "rutaFin": "Valparaiso",
  "horaSalida": "2026-02-15T08:00:00",
  "horaLlegada": "2026-02-15T12:00:00"
}
```

| Campo | Tipo | Descripcion |
|-------|------|-------------|
| patente | String | Patente del vehiculo |
| rutaInicio | String | Punto de origen de la ruta |
| rutaFin | String | Punto de destino de la ruta |
| horaSalida | LocalDateTime | Hora de salida |
| horaLlegada | LocalDateTime | Hora de llegada |

> El campo `fechaActualizacion` se asigna automaticamente en el servidor al momento de publicar el mensaje. La zona horaria configurada es `America/Santiago`.

### Response

- **200 OK**: `"Ruta enviada correctamente"`
- **500 Internal Server Error**: `"Error al enviar ruta: [detalle]"`

## Configuracion RabbitMQ

| Propiedad | Valor |
|-----------|-------|
| Queue | `gps.ruta.queue` |
| Exchange | `ruta.exchange` (Topic) |
| Routing Key | `gps.ruta` |
| Host | `rabbitmq` |
| Puerto | `5672` |

## Estructura del Proyecto

```
src/main/java/com/musabeli/ruta_producer/
├── RutaProducerApplication.java      # Clase principal
├── config/
│   └── RabbitMQConfig.java           # Configuracion de colas y exchange
├── controllers/
│   └── RutaController.java           # Endpoint REST
├── dto/
│   └── RutaDTO.java                  # Objeto de transferencia de datos
└── services/
    └── RutaProducerService.java      # Logica de publicacion a RabbitMQ
```

## Ejecucion Local

### Prerrequisitos

- Java 21
- Maven 3.9+
- RabbitMQ ejecutandose en `localhost:5672`

### Compilar y ejecutar

```bash
mvn clean package
java -jar target/ruta-producer-0.0.1-SNAPSHOT.jar
```

El servicio estara disponible en `http://localhost:8083`.

## Ejecucion con Docker

### Construir imagen

```bash
docker build -t ruta-producer .
```

### Ejecutar con Docker Compose

```bash
docker compose up -d
```

> Requiere que la red `app-network` exista previamente:
> ```bash
> docker network create app-network
> ```

## Ejemplo de Uso

```bash
curl -X POST http://localhost:8083/api/gps/ruta \
  -H "Content-Type: application/json" \
  -d '{
    "patente": "BCDF56",
    "rutaInicio": "Santiago",
    "rutaFin": "Valparaiso",
    "horaSalida": "2026-02-15T08:00:00",
    "horaLlegada": "2026-02-15T12:00:00"
  }'
```
