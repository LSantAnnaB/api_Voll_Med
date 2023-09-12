package med.voll.api.domain.consulta.validacoes;

import java.time.DayOfWeek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.ValidacaoException;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamentoDeConsulta {

  public void validar(DadosAgendamentoConsulta dados) {
    var dataConsulta = dados.data();

    var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    var antesDaAberturaDaClina = dataConsulta.getHour() < 7;
    var depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;
    if (domingo || antesDaAberturaDaClina || depoisDoEncerramentoDaClinica) {
      throw new ValidacaoException("Consulta fora do horario de funcionamento da clinica");

    }
  }

}
