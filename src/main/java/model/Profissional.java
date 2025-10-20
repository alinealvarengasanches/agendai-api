package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profissional {

    private Long id;
    private String nome;
    private String fotoUrl;
    private String bio;
    private String email; // Para o login
    private String senha; // Para o login (em produção, seria hash)
}