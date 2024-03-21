package com.techchallenge.domain.entity;

public class Email {

    private String remetente;
    private String destinatari;

    private String texto;

    private String assunto;

    private String anexo;

    public Email(String remetente, String destinatari, String texto, String assunto, String anexo) {
        this.remetente = remetente;
        this.destinatari = destinatari;
        this.texto = texto;
        this.assunto = assunto;
        this.anexo = anexo;
    }

    public String getRemetente() {
        return remetente;
    }

    public String getDestinatari() {
        return destinatari;
    }

    public String getTexto() {
        return texto;
    }

    public String getAssunto() {
        return assunto;
    }

    public String getAnexo() {
        return anexo;
    }


    public static final class EmailBuilder {
        private String remetente;
        private String destinatari;
        private String texto;
        private String assunto;
        private String anexo;

        private EmailBuilder() {
        }

        public static EmailBuilder anEmail() {
            return new EmailBuilder();
        }

        public EmailBuilder remetente(String remetente) {
            this.remetente = remetente;
            return this;
        }

        public EmailBuilder destinatari(String destinatari) {
            this.destinatari = destinatari;
            return this;
        }

        public EmailBuilder texto(String texto) {
            this.texto = texto;
            return this;
        }

        public EmailBuilder assunto(String assunto) {
            this.assunto = assunto;
            return this;
        }

        public EmailBuilder anexo(String anexo) {
            this.anexo = anexo;
            return this;
        }

        public Email build() {
            return new Email(remetente, destinatari, texto, assunto, anexo);
        }
    }
}
