package med.voll.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.DadosAtualizacaoMedico;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosDetalhamentoMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("medicos")
public class MedicoController {
  @Autowired
  private MedicoRepository medicoRepository;

  @PostMapping
  @Transactional
  public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
    var medico = new Medico(dados);
    medicoRepository.save(medico);
    var uri = uriBuilder.path("/meidcos/{id}").buildAndExpand(medico.getId()).toUri();
    return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
  }

  @GetMapping
  public ResponseEntity<Page<DadosListagemMedico>> listar(
      @PageableDefault(size = 15, sort = { "nome" }) Pageable paginacao) {
    var pages = medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    return ResponseEntity.ok(pages);
  }

  @PutMapping
  @Transactional
  public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
    var medico = medicoRepository.getReferenceById(dados.id());
    medico.atualizarInformacoes(dados);
    return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity deletar(@PathVariable Long id) {
    var medico = medicoRepository.getReferenceById(id);
    medico.inativar();
    return ResponseEntity.noContent().build();
  }
}
