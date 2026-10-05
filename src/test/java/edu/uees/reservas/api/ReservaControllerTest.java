package edu.uees.reservas.api;

import edu.uees.reservas.domain.Reserva;
import edu.uees.reservas.service.ReservaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservaController.class)
class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService service;

    @Test
    void crearReservaValidaDevuelve201() throws Exception {
        given(service.crear(anyString(), anyString()))
                .willReturn(new Reserva("R-001", "NORMAL"));

        mockMvc.perform(post("/api/reservas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"R-001\",\"tipo\":\"NORMAL\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("R-001"))
                .andExpect(jsonPath("$.tipo").value("NORMAL"));
    }

    @Test
    void crearReservaInvalidaDevuelve400() throws Exception {
        mockMvc.perform(post("/api/reservas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"\",\"tipo\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}