package com.natherzito.criptografia.services;

import com.natherzito.criptografia.CryptographyUtils;
import com.natherzito.criptografia.PaymentEntity;
import com.natherzito.criptografia.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class CreatePaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CryptographyUtils cryptographyUtils;

    public PaymentEntity execute(PaymentEntity paymentEntity) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String userDocumentHash = this.cryptographyUtils.hash(paymentEntity.getUserDocument());
        String creditCardToken = this.cryptographyUtils.hash(paymentEntity.getCreditCardToken());

        PaymentEntity payment = new PaymentEntity();
        payment.setUserDocument(userDocumentHash);
        payment.setCreditCardToken(creditCardToken);
        payment.setValue(paymentEntity.getValue());

        return this.paymentRepository.save(payment);

    }
}
