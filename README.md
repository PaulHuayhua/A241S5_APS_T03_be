# Sistema de Predicción de Cosechas

API REST para la gestión y predicción de rendimientos agrícolas con Spring Boot y PostgreSQL.

## Descripción

Sistema para gestionar el ciclo completo de producción agrícola, desde el registro de parcelas hasta la predicción de rendimientos con inteligencia artificial.

Funcionalidades principales:
- Gestión de parcelas, siembras y cosechas
- Predicciones de rendimiento con IA (Groq)
- Monitoreo de datos climáticos
- Control de costos y análisis de eficiencia
- Sistema de reportes y alertas

## Tecnologías

- Java 17
- Spring Boot 3.3
- Spring Data JPA
- PostgreSQL
- Maven

## Requisitos

- JDK 17+
- Maven 3.6+
- PostgreSQL 14+
- API Key de Groq

## Instalación

### 1. Configurar variables de entorno

Crea un archivo `.env` con:

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=prediccion_cosechas
DB_USER=postgres
DB_PASSWORD=tu-password
GROQ_API_KEY=tu-api-key
```

### 2. Crear la base de datos

```bash
psql -U postgres -c "CREATE DATABASE prediccion_cosechas;"
```

### 3. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080/api`

## Estructura del Proyecto

```
src/main/java/com/agroapp/prediccion/
├── catalogos/       Cultivos, variedades, tipos de suelo
├── gestion/         Parcelas, siembras, cosechas
├── mlclima/         Predicciones y datos climáticos
├── finanzas/        Costos de campaña
├── geografia/       Departamentos y municipios
├── seguridad/       Usuarios, roles, auditoría
└── config/          Configuraciones generales
```

## Endpoints Principales

**Dashboard**
```
GET /api/dashboard/estadisticas
```

**Catálogos**
```
GET /api/cultivos
GET /api/variedades
GET /api/tipos-suelo
```

**Gestión**
```
GET /api/parcelas
POST /api/parcelas
GET /api/siembras
POST /api/siembras
GET /api/cosechas
POST /api/cosechas
```

**Predicciones**
```
GET /api/predicciones
POST /api/predicciones
GET /api/datos-climaticos
```

## Verificación

Prueba que la aplicación funciona correctamente:

```bash
curl http://localhost:8080/api/dashboard/estadisticas
curl http://localhost:8080/api/cultivos
```

## Solución de Problemas

**Base de datos no existe**
```bash
psql -U postgres -c "CREATE DATABASE prediccion_cosechas;"
```

**Puerto 8080 ocupado**

Cambia el puerto en `application.yaml`:
```yaml
server:
  port: 8081
```

**PostgreSQL no está ejecutándose**

Windows: Abre `services.msc` y verifica el servicio PostgreSQL

Linux:
```bash
sudo systemctl start postgresql
```