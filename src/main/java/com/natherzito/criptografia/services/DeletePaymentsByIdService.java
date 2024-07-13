package com.natherzito.criptografia.services;

import com.natherzito.criptografia.PaymentEntity;
import com.natherzito.criptografia.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeletePaymentsByIdService {

    @Autowired
    private PaymentRepository paymentRepository;


    public void execute(Long id) {
        Optional<PaymentEntity> findPayment = this.paymentRepository.findById(id);

        if(!findPayment.isPresent()) {
            throw  new RuntimeException("Payment not found");
        }

        this.paymentRepository.deleteById(id);
    }

}
