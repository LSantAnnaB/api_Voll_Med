package med.voll.api.infra.validacoes;

import med.voll.api.model.consulta.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta {

  void validar(DadosAgendamentoConsulta dados);
}
