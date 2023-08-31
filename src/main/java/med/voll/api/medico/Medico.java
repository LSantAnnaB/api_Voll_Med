package med.voll.api.medico;

import med.voll.api.endereco.Endereco;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nome;
  private String email;
  private String crm;
  @Enumerated(EnumType.STRING)
  private Especialidade Especialidade;
  @Embedded
  private Endereco endereco;

  public Medico(DadosCadastroMedico dados) {
    this.nome = dados.nome();
    this.email = dados.email();
    this.crm = dados.crm();
    this.Especialidade = dados.especialidade();
    this.endereco = new Endereco(dados.endereco());
  }
}
