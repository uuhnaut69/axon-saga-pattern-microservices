# Saga Pattern Microservices

## Prerequisites

- `Java 16`
- `Docker`
- `Docker-compose`

## Get Started

### Setup environment

```shell
docker-compose up -d
```

### Build projects

```shell
./mvnw clean install package -DskipTests=true
```

### Start Services

Run services `order-service`, `customer-service`, `inventory-service`

```shell
./mvnw -f order-service/pom.xml spring-boot:run
```

```shell
./mvnw -f customer-service/pom.xml spring-boot:run
```

```shell
./mvnw -f inventory-service/pom.xml spring-boot:run
```

| Service's name | URL |
| --- | --- |
| Order service | localhost:8090 |
| Customer service | localhost:8092 |
| Inventory service | localhost:8091 |
