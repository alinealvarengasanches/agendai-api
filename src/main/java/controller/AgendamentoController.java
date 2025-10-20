package controller;

import model.Agendamento;
import model.HorarioLivre;
import model.Profissional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AgendamentoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
// Configuração essencial para permitir que o React acesse a API
@CrossOrigin(origins = "http://localhost:3000")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    // ---------------------- 1. Perfil e Agenda (Acesso Público) ----------------------

    @GetMapping("/perfil")
    public ResponseEntity<Profissional> getProfissionalPerfil() {
        Profissional profissional = agendamentoService.getProfissionalPerfil();
        if (profissional != null) {
            // Removendo a senha antes de enviar para o frontend
            profissional.setSenha(null);
            return ResponseEntity.ok(profissional);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/agenda/disponivel")
    public List<HorarioLivre> getHorariosDisponiveis() {
        return agendamentoService.getHorariosDisponiveis();
    }

    // ---------------------- 2. Agendamento (Acesso Cliente) ----------------------

    @PostMapping("/agendamento/confirmar")
    public ResponseEntity<?> confirmarAgendamento(@RequestBody Agendamento agendamento) {
        try {
            Agendamento novoAgendamento = agendamentoService.criarAgendamento(agendamento);
            return new ResponseEntity<>(novoAgendamento, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Retorna um erro caso o horário não esteja mais disponível
            return new ResponseEntity<>(Map.of("erro", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // ---------------------- 3. Login e Gestão (Acesso Profissional) ----------------------

    @PostMapping("/login")
    public ResponseEntity<?> loginProfissional(@RequestBody Profissional loginRequest) {
        Profissional profissional = agendamentoService.autenticar(loginRequest.getEmail(), loginRequest.getSenha());

        if (profissional != null) {
            // Em uma aplicação real, você retornaria um JWT aqui.
            // Para simplicidade, apenas retorna o sucesso e o perfil (sem a senha).
            profissional.setSenha(null);
            return ResponseEntity.ok(Map.of("mensagem", "Login bem-sucedido", "perfil", profissional));
        }
        return new ResponseEntity<>(Map.of("erro", "Credenciais inválidas"), HttpStatus.UNAUTHORIZED);
    }

    // Endpoint de exemplo para visualização pós-login
    @GetMapping("/agendamentos/todos")
    public List<Agendamento> getTodosAgendamentos() {
        // Em um projeto real, aqui você verificaria se o usuário está autenticado
        return agendamentoService.getTodosAgendamentos();
    }
}