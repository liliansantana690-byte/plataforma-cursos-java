package com.plataforma.util;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.kernel.font.PdfFont;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GeradorCertificadoPDF {

    public static void gerar(String nomeUsuario, String tituloCurso, int cargaHoraria, String caminhoSaida) {
        try {
            new File(caminhoSaida).getParentFile().mkdirs();

            PdfWriter writer   = new PdfWriter(caminhoSaida);
            PdfDocument pdf    = new PdfDocument(writer);
            Document document  = new Document(pdf, PageSize.A4.rotate());

            PdfFont fontTitulo = PdfFontFactory.createFont("Helvetica-Bold");
            PdfFont fontTexto  = PdfFontFactory.createFont("Helvetica");

            document.add(new Paragraph("CERTIFICADO DE CONCLUSÃO")
                    .setFont(fontTitulo).setFontSize(28)
                    .setTextAlignment(TextAlignment.CENTER).setMarginTop(60));

            document.add(new Paragraph("\nCertificamos que")
                    .setFont(fontTexto).setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph(nomeUsuario)
                    .setFont(fontTitulo).setFontSize(22)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.DARK_GRAY));

            document.add(new Paragraph("concluiu com êxito o curso")
                    .setFont(fontTexto).setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\"" + tituloCurso + "\"")
                    .setFont(fontTitulo).setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("com carga horária de " + cargaHoraria + " horas")
                    .setFont(fontTexto).setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER));

            String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            document.add(new Paragraph("\nEmitido em " + data)
                    .setFont(fontTexto).setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER).setMarginTop(40));

            document.close();
            System.out.println("Certificado gerado: " + caminhoSaida);

        } catch (IOException e) {
            System.err.println("Erro ao gerar PDF: " + e.getMessage());
        }
    }
}
