package com.techchallenge.domain.entity;

public class Usuario {

    private String matricula;
    private String senha;
    private String email;


    public Usuario(String matricula, String senha, String email) {
        this.matricula = matricula;
        this.senha = senha;
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }


    public static final class UsuarioBuilder {
        private String matricula;
        private String senha;
        private String email;

        private UsuarioBuilder() {
        }

        public static UsuarioBuilder anUsuario() {
            return new UsuarioBuilder();
        }

        public UsuarioBuilder matricula(String matricula) {
            this.matricula = matricula;
            return this;
        }

        public UsuarioBuilder senha(String senha) {
            this.senha = senha;
            return this;
        }

        public UsuarioBuilder email(String email) {
            this.email = email;
            return this;
        }

        public Usuario build() {
            return new Usuario(matricula, senha, email);
        }
    }
}
