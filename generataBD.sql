CREATE DATABASE mascotas;

-- Crear tabla Usuario
CREATE TABLE Usuario (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    contraseña VARCHAR(255) NOT NULL,
    rol VARCHAR(20) CHECK (rol IN ('Paseador', 'Cliente')) DEFAULT 'Cliente'
);

-- Crear tabla Mascota
CREATE TABLE Mascota (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    raza VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    peso DECIMAL(5,2),
    comportamiento TEXT,
    genero VARCHAR(10),
    usuario_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id) ON DELETE CASCADE
);

-- Crear tabla Ticket
CREATE TABLE Ticket (
    id SERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    duracion INTERVAL NOT NULL,
    asignado BOOLEAN DEFAULT FALSE,
    usuario_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id) ON DELETE CASCADE
);

-- Crear tabla Postulación
CREATE TABLE Postulacion (
    id SERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    ticket_id INT NOT NULL,
    usuario_id INT NOT NULL,
    aprobado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (ticket_id) REFERENCES Ticket(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id) ON DELETE CASCADE
);

