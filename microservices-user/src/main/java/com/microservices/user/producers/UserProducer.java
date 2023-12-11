package com.microservices.user.producers;

import com.microservices.user.dtos.EmailDto;
import com.microservices.user.models.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(UserModel userModel) {
        var emailDto = new EmailDto();
        emailDto.setUserId(userModel.getUserId());
        emailDto.setEmailTo(userModel.getEmail());
        emailDto.setSubject("Registration successfully completed!");
        emailDto.setText(userModel.getName() + ", welcome! \nThank you for registering, enjoy all the features of our platform now!");

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }

}