package med.voll.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.service.AgendaConsultaService;
import med.voll.api.model.consulta.DadosAgendamentoConsulta;
import med.voll.api.model.consulta.DadosDetalhamentoConsulta;
import med.voll.api.model.medico.Especialidade;

import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ConsultaControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoJacksonTesterJson;
  @Autowired
  private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoJacksonTesterJson;
  @MockBean
  private AgendaConsultaService agendaConsultaService;

  @Test
  @DisplayName("Deveria devolver código 400 quando informações invalidas")
  @WithMockUser
  void testAgendarCenario01() throws Exception { // Adicione o "throws Exception"
    var response = mockMvc.perform(post("/consulta")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("Deveria devolver código 200 quando informações estao invalidas")
  @WithMockUser
  void testAgendarCenario02() throws Exception { // Adicione o "throws Exception"
    var data = LocalDateTime.now().plusHours(1);
    var especialidade = Especialidade.CARDIOLOGIA;
    var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 5l, data);

    when(agendaConsultaService.agendar(any())).thenReturn(dadosDetalhamento);

    var response = mockMvc
        .perform(
            post("/consulta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosAgendamentoJacksonTesterJson.write(
                    new DadosAgendamentoConsulta(2l, 5l, data, especialidade))
                    .getJson()))
        .andReturn().getResponse();
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    var jsonEsperado = dadosDetalhamentoJacksonTesterJson.write(dadosDetalhamento).getJson();

    assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
  }
}
