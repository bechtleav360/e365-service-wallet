package com.bechtle.eagl.UserMappingsService.services;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.EnmeshedConnectorClient;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.MessageContent;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.enums.ContentType;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.requests.SendMessageRequest;
import com.bechtle.eagl.UserMappingsService.model.events.MessageSentEvent;
import com.bechtle.eagl.UserMappingsService.model.events.RelationshipAcceptedEvent;
import com.bechtle.eagl.UserMappingsService.model.events.RelationshipLinkedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Bundles functionality to exchange messages with user through enmeshed app
 */
@Slf4j
@Service
public class MessageService {

    private final EnmeshedConnectorClient connectorClient;
    private final ApplicationEventPublisher eventPublisher;

    public MessageService(@Autowired EnmeshedConnectorClient connectorClient,
                          @Autowired ApplicationEventPublisher eventPublisher) {
        this.connectorClient = connectorClient;
        this.eventPublisher = eventPublisher;
    }


    @EventListener
    public void sendWelcomeMessage(RelationshipAcceptedEvent event) {
        String body = "Wir freuen uns, dich auf unserer Plattform für Lernpfade im Web begrüßen zu dürfen." +
                "Bitte geben sie im nächsten Schritt den folgenden Code ein, damit wir ihren Login verknüpfen können:" +
                "<strong>"+event.getLinkingCode()+"</strong>";

        MessageContent content = MessageContent.builder()
                .type(ContentType.MAIL)
                .recipient(event.getRelationship().getPeer())
                .subject("Willkommen bei EAGL")
                .body(body)
                .build();

        SendMessageRequest request = new SendMessageRequest();
        request.setRecipients(content.getRecipients());
        request.setContent(content);


        this.connectorClient.sendMessage(request)
                .subscribe(message -> eventPublisher.publishEvent(new MessageSentEvent(content)));
    }


    @EventListener
    public void sendWelcomeMessage(RelationshipLinkedEvent event) {

        MessageContent content = MessageContent.builder()
                .type(ContentType.MAIL)
                .recipient(event.getRelation().getAddress())
                .subject("Dein Account wurde erfolgreich verknüpft. ")
                .body("Du hast einen korrekten Code eingegeben. Dein Lernfortschritt kann nun in deiner Wallet gespeichert werden")
                .build();

        SendMessageRequest request = new SendMessageRequest();
        request.setRecipients(content.getRecipients());
        request.setContent(content);


        this.connectorClient.sendMessage(request)
                .subscribe(message -> eventPublisher.publishEvent(new MessageSentEvent(content)));
    }
}
