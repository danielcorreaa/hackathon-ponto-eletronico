package com.techchallenge.application.gateway;

import com.techchallenge.domain.entity.Email;

public interface EmailGateway {

    void send(Email email);
}
