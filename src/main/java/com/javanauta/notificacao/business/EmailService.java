package com.javanauta.notificacao.business;

import com.javanauta.notificacao.business.dto.TarefasDTO;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final Resend resend;
    private final TemplateEngine templateEngine;

    @Value("${envio.email.remetente}")
    public String remetente;

    @Value("${envio.email.nomeRemetente}")
    private String nomeRemetente;
    
    public void enviaEmail(TarefasDTO dto) throws ResendException {

        try{
            Context context = new Context();
            context.setVariable("nomeTarefa", dto.getNomeTarefa());
            context.setVariable("dataEvento", dto.getDataEvento());
            context.setVariable("descricao", dto.getDescricao());

            String template = templateEngine.process("notificacao", context);

            CreateEmailOptions mensagem = CreateEmailOptions.builder()
                    .from(nomeRemetente + " <" + ">")
                    .to(dto.getEmailUsuario())
                    .subject("Notificação de Tarefa")
                    .html(template)
                    .build();

            resend.emails().send(mensagem);

        } catch (ResendException e) {
            throw new ResendException("Erro ao enviar email", e.getCause());
        }
    }
}