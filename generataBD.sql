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
                         estado BOOLEAN NOT NULL DEFAULT TRUE,
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
                        duracion  vARCHAR(50) NOT NULL,
                        asignado BOOLEAN DEFAULT FALSE,
                        usuario_id INT NOT NULL,
                        FOREIGN KEY (usuario_id) REFERENCES Usuario(id) ON DELETE CASCADE
);

-- Tabla intermedia Ticket_Mascota (para muchos a muchos)
CREATE TABLE Ticket_Mascota (
                                ticket_id INT NOT NULL,
                                mascota_id INT NOT NULL,
                                PRIMARY KEY (ticket_id, mascota_id),
                                FOREIGN KEY (ticket_id) REFERENCES Ticket(id) ON DELETE CASCADE,
                                FOREIGN KEY (mascota_id) REFERENCES Mascota(id) ON DELETE CASCADE
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


CREATE VIEW vista_ticket_mascota AS
SELECT
    t.id AS ticket_id,
    t.fecha,
    t.hora,
    t.duracion,
    t.asignado,
    t.usuario_id AS cliente_id,
    m.id AS mascota_id,
    m.nombre AS nombre_mascota,
    m.raza,
    m.edad,
    m.peso,
    m.comportamiento,
    m.genero,
    m.usuario_id AS dueño_id
FROM Ticket t
         JOIN Ticket_Mascota tm ON t.id = tm.ticket_id
         JOIN Mascota m ON tm.mascota_id = m.id;
