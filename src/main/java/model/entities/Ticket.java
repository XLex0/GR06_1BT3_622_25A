package model.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora")
    private LocalTime hora;

    @Column(name = "duracion")
    private LocalTime duracion; // 🔥 Ahora LocalTime

    private Boolean asignado;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Postulacion> postulaciones;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public LocalTime getDuracion() { return duracion; }
    public void setDuracion(LocalTime duracion) { this.duracion = duracion; }

    public Boolean getAsignado() { return asignado; }
    public void setAsignado(Boolean asignado) { this.asignado = asignado; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<Postulacion> getPostulaciones() { return postulaciones; }
    public void setPostulaciones(List<Postulacion> postulaciones) { this.postulaciones = postulaciones; }
}
