package com.example.users.confirmationToken;


import com.example.users.Users;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenDAO confirmationTokenDAO;

    public ConfirmationTokenService(ConfirmationTokenDAO confirmationTokenDAO) {
        this.confirmationTokenDAO = confirmationTokenDAO;
    }

    @Transactional
    public void mailConfirmation(String confirmationToken) {
        ConfirmationToken token = confirmationTokenDAO.findConfirmationTokenByToken(confirmationToken);

        if (token == null) throw new IllegalStateException("NO CONFIRMATION TOKEN FOUND!");
        if (token.isConfirmed()) throw new IllegalStateException("YOUR ACCOUNT IS ALREADY CONFIRMED!");
        if (token.getExpiresAt().isBefore(LocalDateTime.now())) throw new IllegalStateException("TOKEN EXPIRED!");

        token.setConfirmed(true);
        Users user = token.getUser();
        user.setActive(true);
    }


    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenDAO.save(confirmationToken);
    }
}
