package com.techchallenge.domain.entity;
import com.techchallenge.domain.valueObject.PontoId;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;



public class Ponto {


    public static final String FORMAT_DATE_TIME = "dd/MM/yyyy HH:mm";
    private static final String FORMAT_DATE = "dd/MM/yyyy";
    private PontoId pontoId;
    private Usuario usuario;
    private LocalDate dataPonto;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaidaAlmoco;
    private LocalDateTime horaVoldaAlmoco;
    private LocalDateTime horaSaida;
    private Integer mes;
    private Integer ano;

    public Ponto(Usuario usuario, LocalDateTime horaEntrada) {
        this.usuario = usuario;
        this.dataPonto = horaEntrada.toLocalDate();
        this.pontoId = new PontoId(usuario.getMatricula(), dataPonto);
        this.horaEntrada = horaEntrada;
        this.mes = horaEntrada.getMonth().getValue();
        this.ano = horaEntrada.getYear();
    }

    public Ponto marcarPonto(LocalDateTime now){
        if(isHoraAlmoco()){
            this.horaSaidaAlmoco = now;
            return this;
        }
        if(isVoltaAlmoco()){
            this.horaVoldaAlmoco = now;
            return  this;
        }
        if(isHoraSaida()){
            this.horaSaida = now;
            return this;
        }
        return  this;
    }

    public Ponto marcarPonto(){
        return marcarPonto(LocalDateTime.now());
    }

    private boolean isHoraSaida() {
        return Optional.ofNullable(horaSaida).isEmpty();
    }

    private boolean isVoltaAlmoco() {
       return Optional.ofNullable(horaVoldaAlmoco).isEmpty();
    }
    private boolean isHoraAlmoco() {
        return Optional.ofNullable(horaSaidaAlmoco).isEmpty();
    }


    public Double horasTrabalhadas(){
        long segundos = 0L;
        if(null != horaEntrada && null != horaSaidaAlmoco) {
            Duration primeiroPeriodo = Duration.between(horaEntrada, horaSaidaAlmoco);
            segundos += primeiroPeriodo.getSeconds();
        }
        if(null != horaVoldaAlmoco && null != horaSaida){
            Duration segundoPeriodo = Duration.between(horaVoldaAlmoco, horaSaida);
            segundos += segundoPeriodo.getSeconds();
        }
        return (double) ((segundos / 60.0) / 60.0);
    }

    public Optional<String> getId() {
        return Optional.ofNullable(pontoId.getId());
    }

    public Optional<String> getHoraEntradaFormat(){
        return  Optional.ofNullable(horaEntrada).map(hora -> hora.format(getFormatter(FORMAT_DATE_TIME)));
    }

    public Optional<String> getHoraSaidaAlmocoFormat(){
        return Optional.ofNullable(horaSaidaAlmoco).map(hora -> hora.format(getFormatter(FORMAT_DATE_TIME)));
    }

    public Optional<String> getHoraVoltaAlmocoFormat(){
        return Optional.ofNullable(horaVoldaAlmoco).map(hora -> hora.format(getFormatter(FORMAT_DATE_TIME)));
    }

    public Optional<String> getHoraSaidaFormat(){
        return Optional.ofNullable(horaSaida).map(hora -> hora.format(getFormatter(FORMAT_DATE_TIME)));
    }

    public Optional<String> getDataPontoFormat() {
        return Optional.ofNullable(dataPonto).map(data -> data.format(getFormatter(FORMAT_DATE)));
    }

    public DateTimeFormatter getFormatter(String format){
        return DateTimeFormatter.ofPattern(format);
    }


    public PontoId getPontoId() {
        return pontoId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getDataPonto() {
        return dataPonto;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDateTime getHoraSaidaAlmoco() {
        return horaSaidaAlmoco;
    }

    public LocalDateTime getHoraVoldaAlmoco() {
        return horaVoldaAlmoco;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public Integer getMes() {
        return mes;
    }

    public Integer getAno() {
        return ano;
    }


    public static final class PontoBuilder {
        private String FORMAT_DATE_TIME;
        private String FORMAT_DATE;
        private PontoId pontoId;
        private Usuario usuario;
        private LocalDate dataPonto;
        private LocalDateTime horaEntrada;
        private LocalDateTime horaSaidaAlmoco;
        private LocalDateTime horaVoldaAlmoco;
        private LocalDateTime horaSaida;
        private Integer mes;
        private Integer ano;

        private PontoBuilder() {
        }

        public static PontoBuilder aPonto() {
            return new PontoBuilder();
        }

        public PontoBuilder FORMAT_DATE_TIME(String FORMAT_DATE_TIME) {
            this.FORMAT_DATE_TIME = FORMAT_DATE_TIME;
            return this;
        }

        public PontoBuilder FORMAT_DATE(String FORMAT_DATE) {
            this.FORMAT_DATE = FORMAT_DATE;
            return this;
        }

        public PontoBuilder pontoId(PontoId pontoId) {
            this.pontoId = pontoId;
            return this;
        }

        public PontoBuilder usuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public PontoBuilder dataPonto(LocalDate dataPonto) {
            this.dataPonto = dataPonto;
            return this;
        }

        public PontoBuilder horaEntrada(LocalDateTime horaEntrada) {
            this.horaEntrada = horaEntrada;
            return this;
        }

        public PontoBuilder horaSaidaAlmoco(LocalDateTime horaSaidaAlmoco) {
            this.horaSaidaAlmoco = horaSaidaAlmoco;
            return this;
        }

        public PontoBuilder horaVoldaAlmoco(LocalDateTime horaVoldaAlmoco) {
            this.horaVoldaAlmoco = horaVoldaAlmoco;
            return this;
        }

        public PontoBuilder horaSaida(LocalDateTime horaSaida) {
            this.horaSaida = horaSaida;
            return this;
        }

        public PontoBuilder mes(Integer mes) {
            this.mes = mes;
            return this;
        }

        public PontoBuilder ano(Integer ano) {
            this.ano = ano;
            return this;
        }

        public Ponto build() {
            Ponto ponto = new Ponto(usuario, horaEntrada);
            ponto.horaSaidaAlmoco = this.horaSaidaAlmoco;
            ponto.horaVoldaAlmoco = this.horaVoldaAlmoco;
            ponto.horaSaida = this.horaSaida;
            ponto.mes = this.mes;
            ponto.pontoId = this.pontoId;
            ponto.ano = this.ano;
            ponto.dataPonto = this.dataPonto;
            return ponto;
        }
    }
}
