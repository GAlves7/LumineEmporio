package com.api.lumine_emporio.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;


@Service
public class MailService {
	@Value("${spring.mail.username}")
	private String remetente;

	private final Resend resend;

	public MailService(){
		this.resend = new Resend("re_SPd1dAFv_PfGkp7cpQr5gp2zqEskAvfW5");
	}


	@Async
	public void enviarEmailTexto(String destinatario, String assunto, String mensagem) {
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("notificacao@lumineemporio.store")
                .to(destinatario)
                .subject(assunto)
                .html(mensagem)
                .build();
         try {
            CreateEmailResponse data = resend.emails().send(params);
            System.out.println(data.getId());
        } catch (ResendException e) {
            e.printStackTrace();
            System.out.println("Falha ao enviar email: "+e.getMessage());
        }
    }
}