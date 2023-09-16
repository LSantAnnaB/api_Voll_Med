package med.voll.api.model.consulta;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.model.medico.Especialidade;

public record DadosAgendamentoConsulta(
                Long idMedico,

                @NotNull Long idPaciente,

                @NotNull @Future LocalDateTime data,

                Especialidade especialidade) {

}
