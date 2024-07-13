package com.natherzito.criptografia.services;

import com.natherzito.criptografia.CryptographyUtils;
import com.natherzito.criptografia.PaymentEntity;
import com.natherzito.criptografia.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UpdatePaymentsByIdService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CryptographyUtils cryptographyUtils;

    public PaymentEntity execute(PaymentEntity paymentEntity, Long id) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Optional<PaymentEntity> findPayment = this.paymentRepository.findById(id);

        if(!findPayment.isPresent()) {
            throw  new RuntimeException("Payment not found");
        }

        String userDocumentHash = this.cryptographyUtils.hash(paymentEntity.getUserDocument());
        String creditCardToken = this.cryptographyUtils.hash(paymentEntity.getCreditCardToken());

        paymentEntity.setCreditCardToken(creditCardToken);
        paymentEntity.setUserDocument(userDocumentHash);
        paymentEntity.setCreatedAt(findPayment.get().getCreatedAt());
        paymentEntity.setUpdatedAt(LocalDateTime.now());


        return this.paymentRepository.save(paymentEntity);

    }
}
