Aplicación de Reservas de Hotel - Java EE

Aplicación web para la gestión de reservas de hoteles, desarrollada con Java EE, Servlets, JSP y JPA. Permite a los usuarios registrarse, iniciar sesión, buscar hoteles por ciudad, ver habitaciones disponibles y gestionar sus reservas.

Tecnologías Utilizadas
Backend:Java 11, Servlets 4.0, JPA 2.2 (Hibernate 5.6), jBCrypt
Frontend: JSP 2.3, JSTL 1.2, HTML5, CSS3
Base de Datos: Microsoft SQL Server
Servidor: Apache Tomcat 9.0
Build Tool: Apache Maven

Instrucciones de Instalación
1.  Clonar el repositorio.
2.  Crear una base de datos en SQL Server llamada `HotelDB`.
3.  Crear un usuario SQL (ej. `hotelappuser`) y asignarle el rol `db_owner` sobre `HotelDB`.
4.  Configurar las credenciales de la base de datos en el archivo `src/main/resources/META-INF/persistence.xml`.
5.  Construir el proyecto usando Maven: `mvn clean install`.
6.  Desplegar el archivo `target/ReservasHotel.war` en el directorio `webapps` de Apache Tomcat.
7.  Iniciar el servidor Tomcat y acceder a `http://localhost:8080/ReservasHotel/`.

Scripts SQL

CREATE DATABASE HotelDB;
GO

USE HotelDB;
GO

CREATE LOGIN hotelappuser WITH PASSWORD = 'Clave123'; 
GO

CREATE USER hotelappuser FOR LOGIN hotelappuser;
GO

ALTER ROLE db_owner ADD MEMBER hotelappuser;
GO

-- Crear las tablas
CREATE TABLE Usuarios (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL
);

CREATE TABLE Hoteles (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre NVARCHAR(150) NOT NULL,
    direccion NVARCHAR(255),
    ciudad NVARCHAR(100)
);

CREATE TABLE Habitaciones (
    id INT PRIMARY KEY IDENTITY(1,1),
    hotel_id INT NOT NULL,
    tipo NVARCHAR(50) NOT NULL,
    capacidad INT NOT NULL,
    precioPorNoche DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (hotel_id) REFERENCES Hoteles(id)
);

CREATE TABLE Reservas (
    id INT PRIMARY KEY IDENTITY(1,1),
    usuario_id INT NOT NULL,
    habitacion_id INT NOT NULL,
    fechaEntrada DATE NOT NULL,
    fechaSalida DATE NOT NULL,
    costoTotal DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuarios(id),
    FOREIGN KEY (habitacion_id) REFERENCES Habitaciones(id)
);
GO

-- Poblar Tablas (Hoteles/Habitaciones)

-- HOTELES
INSERT INTO Hoteles (nombre, direccion, ciudad) VALUES
('Hotel W Santiago', 'Isidora Goyenechea 3000', 'Santiago'),
('Hotel Cumbres Patagónicas', 'Av. Imperial 0655', 'Puerto Varas'),
('Hotel Bahía Encantada', 'Av. del Mar 2500', 'La Serena'),
('Hotel del Desierto', 'Av. Arturo Prat Chacón 2384', 'Antofagasta'),
('Hotel Viñas de Colchagua', 'Fundo El Peral s/n', 'Santa Cruz');
GO

-- HABITACIONES

INSERT INTO Habitaciones (hotel_id, tipo, capacidad, precioPorNoche) VALUES
(1, 'Wonderful King', 2, 180000.00),
(1, 'Spectacular King', 2, 220000.00),
(1, 'Cool Corner Suite', 3, 350000.00);

INSERT INTO Habitaciones (hotel_id, tipo, capacidad, precioPorNoche) VALUES
(2, 'Superior Vista Lago', 2, 150000.00),
(2, 'Suite Junior', 3, 210000.00),
(2, 'Habitación Standard', 2, 120000.00);

INSERT INTO Habitaciones (hotel_id, tipo, capacidad, precioPorNoche) VALUES
(3, 'Doble Vista al Mar', 2, 95000.00),
(3, 'Familiar con Balcón', 4, 140000.00),
(3, 'Matrimonial Standard', 2, 80000.00);
GO
