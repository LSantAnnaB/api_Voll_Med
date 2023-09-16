package med.voll.api.infra.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.model.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.ConsultaRepository;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {

  @Autowired
  private ConsultaRepository consultaRepository;

  public void validar(DadosAgendamentoConsulta dados) {
    var primeiroHorario = dados.data().withHour(7);
    var ultimoHorario = dados.data().withHour(18);
    var pacientePossuiOutraConsultaNoDia = consultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(),
        primeiroHorario, ultimoHorario);

    if (pacientePossuiOutraConsultaNoDia) {
      throw new ValidacaoException("Paciente já possui consulta agendada ");
    }
  }
}
