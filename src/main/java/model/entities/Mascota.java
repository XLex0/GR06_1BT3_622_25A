package model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "mascota")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String genero;
    private int edad;
    private String raza;
    private String tamano;

    // Relación con Usuario (muchos a uno)
    @ManyToOne
    @JoinColumn(name = "usuario_id")  // Establece la columna que será la clave foránea
    private Usuario usuario;

    // Constructor por defecto
    public Mascota() {}

    // Constructor con parámetros
    public Mascota(String nombre, String genero, int edad, String raza, String tamano, Usuario usuario) {
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.raza = raza;
        this.tamano = tamano;
        this.usuario = usuario;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
