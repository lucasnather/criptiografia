package com.natherzito.criptografia;

import com.natherzito.criptografia.dto.CreatePaymentDto;
import com.natherzito.criptografia.services.CreatePaymentService;
import com.natherzito.criptografia.services.DeletePaymentsByIdService;
import com.natherzito.criptografia.services.FindManyPaymentsService;
import com.natherzito.criptografia.services.UpdatePaymentsByIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController()
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private CreatePaymentService createPaymentService;

    @Autowired
    private FindManyPaymentsService findManyPayments;

    @Autowired
    private DeletePaymentsByIdService deletePaymentsByIdService;

    @Autowired private UpdatePaymentsByIdService updatePaymentsByIdService;

    @PostMapping
    public ResponseEntity<PaymentEntity> post(@RequestBody CreatePaymentDto createPaymentDto) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String userDocument = createPaymentDto.userDocument();
        String creditCardToken = createPaymentDto.creditCardToken();
        Long value = createPaymentDto.value();

        PaymentEntity payement = new PaymentEntity();
        payement.setCreditCardToken(creditCardToken);
        payement.setUserDocument(userDocument);
        payement.setValue(value);

        PaymentEntity paymentEntity = this.createPaymentService.execute(payement);

        return ResponseEntity.status(HttpStatus.CREATED).body(paymentEntity);

    }

    @GetMapping
    public  ResponseEntity<List<PaymentEntity>> get() {
        List<PaymentEntity> payments = this.findManyPayments.execute();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(payments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            this.deletePaymentsByIdService.execute(id);

            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body("Delete Successfully");
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }

    @PutMapping("/{id}")
    public  ResponseEntity<PaymentEntity> update(@PathVariable Long id, @RequestBody CreatePaymentDto createPaymentDto) {
        String userDocument = createPaymentDto.userDocument();
        String creditCardToken = createPaymentDto.creditCardToken();
        Long value = createPaymentDto.value();

        PaymentEntity payement = new PaymentEntity();
        payement.setId(id);
        payement.setCreditCardToken(creditCardToken);
        payement.setUserDocument(userDocument);
        payement.setValue(value);

        try {
            PaymentEntity paymentEntity = this.updatePaymentsByIdService.execute(payement, id);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(paymentEntity);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

}
