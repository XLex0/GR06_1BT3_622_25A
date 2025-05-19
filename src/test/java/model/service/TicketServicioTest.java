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

    @ParameterizedTest
    @CsvSource({
            "null, '12:00', '01:00'",
            "'2025-05-16', null, '01:00'",
            "'2025-05-16', '12:00', null",
            "null, null, null"
    })
    void given_ticket_when_campos_vacios_then_false(String fechaStr, String horaStr, String duracionStr) {
        LocalDate fecha = "null".equals(fechaStr) ? null : LocalDate.parse(fechaStr);
        LocalTime hora = "null".equals(horaStr) ? null : LocalTime.parse(horaStr);
        LocalTime duracion = "null".equals(duracionStr) ? null : LocalTime.parse(duracionStr);

        boolean resultado = ticketService.validarCampos(fecha, hora, duracion, 1L);
        assertFalse(resultado);
    }

    @ParameterizedTest
    @CsvSource({
            "'2023-07-18'",
            "'2024-05-31'",
            "'2021-12-25'"
    })
    void given_fechaIngresadaAnteriorALaActual_when_actualizarTicket_then_false(String fechaIngresadaStr) {
        LocalDate fechaIngresada = LocalDate.parse(fechaIngresadaStr);

        boolean resultado = ticketService.validarFecha(fechaIngresada, 1L);

        assertFalse(resultado);
    }
    @ParameterizedTest
    @CsvSource({
            "'00:10'",
            "'00:29'",
            "'03:01'",
            "'04:00'"
    })
    void given_duracionInvalida_when_actualizarTicket_then_false(String duracionStr) {
        LocalTime duracion = LocalTime.parse(duracionStr);
        TicketService ticketService = new TicketService();
        boolean resultado = ticketService.validarDuracion(duracion);
        assertFalse(resultado);
    }

}


