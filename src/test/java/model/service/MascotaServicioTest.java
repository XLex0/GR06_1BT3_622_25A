package model.service;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MascotaServicioTest {

    static MascotaServicio ms = null;

    @BeforeAll
    public static void sepUp(){
        ms = new MascotaServicio();
    }

    @ParameterizedTest
    @CsvSource({
            "-1",  // Edad negativa
            "0",   // Edad cero
            "26"   // Edad mayor al límite permitido (25)
    })
    void given_mascotaActiva_when_edadInvalida_then_actualizacionRechazada(int edad) {
        boolean resultado = ms.actualizarMascota(
                "Toby", "Beagle", edad, 10f,
                "Activo", "Macho", 1L
        );

        assertFalse(resultado);
    }

    @ParameterizedTest
    @CsvSource({
            "-5",   // Peso negativo
            "0",    // Peso cero
            "250"   // Peso mayor al límite permitido (200)
    })
    void given_mascotaActiva_when_pesoInvalido_then_actualizacionRechazada(float peso) {
        boolean resultado = ms.actualizarMascota(
                "Luna", "Golden", 5, peso,
                "Tranquila", "Hembra", 1L
        );

        assertFalse(resultado);
    }


    @ParameterizedTest
    @CsvSource({
            "'', Labrador",      // nombre vacío
            "Rocky, ''",         // raza vacía
            "null, Labrador",    // nombre null
            "Rocky, null"        // raza null
    })
    void given_camposObligatoriosInvalidos_when_actualizarMascota_then_rechaza(String nombre, String raza) {
        boolean resultado = ms.actualizarMascota(
                "null".equals(nombre) ? null : nombre,
                "null".equals(raza) ? null : raza,
                5, 20f, "Sociable", "Macho", 1L
        );

        assertFalse(resultado);
    }




    }


