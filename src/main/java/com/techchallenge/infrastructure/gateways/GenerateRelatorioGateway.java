package com.techchallenge.infrastructure.gateways;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.techchallenge.application.gateway.GenerateGateway;
import com.techchallenge.core.exceptions.BusinessException;
import com.techchallenge.domain.entity.Ponto;
import com.techchallenge.domain.entity.Usuario;
;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import java.io.*;
import java.util.List;


@Component
public class GenerateRelatorioGateway implements GenerateGateway {

    private JavaMailSender mailSender;

    public GenerateRelatorioGateway(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void generate(List<Ponto> pontos, Usuario usuario, String nomeMes, String anexo){
        try {
            Document document = new Document();
            var pdf = PdfWriter.getInstance(document, new FileOutputStream(anexo));
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph(
                    "Relatório de ponto - Referente ao mês de "+nomeMes,
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED));
            document.add(paragraph);
            document.add(new Chunk("Matricula do Usuário: " +usuario.getMatricula()+ "\n", font));

            document.add(new Chunk("Mês Referência: " + nomeMes+"\n", font));
            Font fontLista = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
            for (Ponto ponto : pontos){
                document.add(new Chunk("\n", font));
                document.add(new Chunk("Data: " + ponto.getDataPontoFormat().orElse("")+"\n", font));
                document.add(new Chunk("Entrada: " +ponto.getHoraEntradaFormat().orElse("")+ "\n", fontLista));
                document.add(new Chunk("Almoço : " +ponto.getHoraSaidaAlmocoFormat().orElse("")+ "\n", fontLista));
                document.add(new Chunk("Retorno: " +ponto.getHoraVoltaAlmocoFormat().orElse("")+ "\n", fontLista));
                document.add(new Chunk("Encerramento: " +ponto.getHoraSaidaFormat().orElse("")+ "\n", fontLista));
                document.add(new Chunk("Horas Trabalhadas: " +ponto.horasTrabalhadasString()+ "\n", fontLista));
                document.add(new Chunk("\n", font));
            }
            double horas = pontos.stream().mapToDouble( Ponto::horasTrabalhadas).sum();
            document.add(new Chunk("Horas Trabalhadas durante o mês de "  + nomeMes + ": "+horas +"\n", font));
            document.close();
        }catch (DocumentException ex){
            throw new BusinessException ("Falha na tentativa de contruir o arquivo",ex);
        } catch (FileNotFoundException e) {
            throw new BusinessException ("Falha ao tentar gerar o arquivo",e);
        }
    }
}
