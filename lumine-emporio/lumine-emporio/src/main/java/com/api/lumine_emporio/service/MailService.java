package com.api.lumine_emporio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.resend.*;

@Service
public class MailService {
	@Value("${spring.mail.username}")
	private String remetente;

	private final Resend resend;

	public MailService(){
		this.resend = Resend("re_fp2C6EAD_KuQNkr9UtUQY5ALWujDvRUE4");
	}


	@Async
	public void enviarEmailTexto(String destinatario, String assunto, String mensagem) {
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Acme <onboarding@resend.dev>")
                .to(destinatario)
                .subject(assunto)
                .html("<strong>mensagem</strong>")
                .build();
         try {
            CreateEmailResponse data = resend.emails().send(params);
            System.out.println(data.getId());
        } catch (ResendException e) {
            e.printStackTrace();
        }
    }
}