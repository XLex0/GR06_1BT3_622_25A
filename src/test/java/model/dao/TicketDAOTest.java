package model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.entities.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TicketDAOTest {
    @Mock
    private EntityManager em;

    @Mock
    private EntityTransaction transaction;

    private TicketDAO dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(em.getTransaction()).thenReturn(transaction);
        dao = new TicketDAO(em);
    }

    @Test
    void give_ticketNoexiste_when_modificar_then_returnFalse(){
        // Datos del ticket inexistente
        Long ticketId = 99L;
        LocalDate fecha = LocalDate.of(2025, 5, 18);
        LocalTime hora = LocalTime.of(10, 0);
        LocalTime duracion = LocalTime.of(0, 30);

        when(em.find(Ticket.class, ticketId)).thenReturn(null);

        TicketDAO dao = new TicketDAO(em);
        boolean resultado = dao.actualizar(fecha, hora, duracion, ticketId);

        assertFalse(resultado);
        verify(em).find(Ticket.class, ticketId);
        verify(em, never()).merge(any());
        verify(transaction, never()).commit();
    }

    @Test
    void given_ticketModificado_when_esCorrect_true(){
        Ticket ticketExiste = new Ticket(); //Ticket existe
        ticketExiste.setId(1L);

        when(em.getTransaction()).thenReturn(transaction);
        when(em.find(Ticket.class, 1L)).thenReturn(ticketExiste);

        TicketDAO dao = new TicketDAO(em);

        LocalDate fecha = LocalDate.of(2025, 5, 18);
        LocalTime hora = LocalTime.of(10, 0);
        LocalTime duracion = LocalTime.of(0, 30);

        boolean resultado = dao.actualizar(fecha, hora, duracion, 1L);

        assertTrue(resultado);
        verify(em).find(Ticket.class, 1L);
        verify(transaction).begin();
        verify(transaction, never()).rollback();
    }

}
