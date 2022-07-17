package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.PaymentActive;
import fpt.edu.capstone.repository.PaymentActiveRepository;
import fpt.edu.capstone.service.PaymentActiveService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentActiveServiceImpl implements PaymentActiveService {
    private final PaymentActiveRepository paymentActiveRepository;
    @Override
    public void save(PaymentActive paymentActive) {
        paymentActiveRepository.save(paymentActive);
    }
}
