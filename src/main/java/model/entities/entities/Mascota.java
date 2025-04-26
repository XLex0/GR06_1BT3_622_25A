package model.entities.entities;

import jakarta.persistence.*;
import model.entities.Usuario;

import java.lang.Float;

@Entity
@Table(name = "mascota")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String raza;

    @Column(nullable = false)
    private Integer edad;

    @Column(precision = 5, scale = 2)
    private Float peso;

    @Column(columnDefinition = "TEXT")
    private String comportamiento;

    @Column(length = 10)
    private String genero;

    /**
     * Este campo te permite recibir sólo el ID en el JSON:
     *     { "nombre": "...", "raza": "...", …, "usuarioId": 42 }
     */
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    /**
     * Si en algún momento necesitas la entidad Usuario,
     * la puedes mapear en lazy (opcional):
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private Usuario usuario;

    public Mascota() {}

    /** Constructor «ligero» que sólo recibe el usuarioId */
    public Mascota(String nombre, String raza, Integer edad,
                   Float peso, String comportamiento,
                   String genero, Long usuarioId) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.peso = peso;
        this.comportamiento = comportamiento;
        this.genero = genero;
        this.usuarioId = usuarioId;
    }

    // --- Getters y setters ---

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

    public String getRaza() {
        return raza;
    }
    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getEdad() {
        return edad;
    }
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Float getPeso() {
        return peso;
    }
    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public String getComportamiento() {
        return comportamiento;
    }
    public void setComportamiento(String comportamiento) {
        this.comportamiento = comportamiento;
    }

    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /** Sólo el ID del usuario, para JSON de entrada/salida */
    public Long getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    /** Acceso opcional a la entidad Usuario si la necesitas en código */
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioId = (usuario != null ? usuario.getId() : null);
    }
}
