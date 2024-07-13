package com.natherzito.criptografia.services;

import com.natherzito.criptografia.PaymentEntity;
import com.natherzito.criptografia.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindManyPaymentsService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<PaymentEntity> execute() {
        List<PaymentEntity> payments = this.paymentRepository.findAll();

        return  payments;
    }

}
