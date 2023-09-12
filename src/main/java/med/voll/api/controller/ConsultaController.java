package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaConsultaService;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;

@Controller
@RequestMapping("consulta")
public class ConsultaController {

  @Autowired
  private AgendaConsultaService agendaConsultaService;

  @PostMapping
  @Transactional
  public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {

    return ResponseEntity.ok(new DadosDetalhamentoConsulta(null, null, null, null));
  }
}
