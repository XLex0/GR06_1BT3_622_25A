package model.service;
import model.dao.MascotaDAO;
import model.dao.TicketDAO;
import model.dao.UsuarioDAO;
import model.entities.Mascota;
import model.entities.Ticket;
import model.entities.Usuario;
import model.service.MascotaServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TicketServicioTest {
    @Mock
    TicketDAO ticketDAO;

    @InjectMocks
    TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void given_ClienteNoExiste_when_solicitaTickets_then_retornaFalse() {
        Long idUsuario = 999L; //Usuario que no existe en la base de datos

        when(ticketDAO.buscarTodosPorUsuario(idUsuario)).thenReturn(null);

        List<Ticket> resultadoConsulta = ticketDAO.buscarTodosPorUsuario(idUsuario);
        TicketService ticketService = new TicketService();

        boolean resultado = ticketService.existenTickets(resultadoConsulta);

        assertFalse(resultado);
    }

    @Test
    void given_ClienteRegistradoSinTickets_when_solicitaTickets_then_retornaFalse() {
        Long idUsuario = 88L;//Usuario que si existe en la base de datos

        when(ticketDAO.buscarTodosPorUsuario(idUsuario)).thenReturn(Collections.emptyList());

        List<Ticket> resultadoConsulta = ticketDAO.buscarTodosPorUsuario(idUsuario);
        TicketService ticketService = new TicketService();

        boolean resultado = ticketService.existenTickets(resultadoConsulta);

        assertFalse(resultado);
    }

    @Test
    void given_Tickets_when_filtrarNoCaducados_then_retornarSoloFuturos() {
        Usuario usuario = new Usuario();
        usuario.setId(99L);

        Ticket caducado = new Ticket();
        caducado.setFecha(LocalDate.now().minusDays(5));
        caducado.setUsuario(usuario);

        Ticket vigente = new Ticket();
        vigente.setFecha(LocalDate.now().plusDays(3));
        vigente.setUsuario(usuario);

        List<Ticket> tickets = Arrays.asList(caducado, vigente);

        TicketService ticketService = new TicketService();
        List<Ticket> resultado = ticketService.filtrarTicketsNoCaducados(tickets);

        assertEquals(1, resultado.size());
        assertEquals(vigente.getFecha(), resultado.get(0).getFecha());
    }

}


