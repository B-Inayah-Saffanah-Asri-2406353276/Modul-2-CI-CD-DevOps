package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    private List<Payment> payments = new ArrayList<>();

    public Payment save(Payment payment) {
        int index = -1;
        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getId().equals(payment.getId())) {
                index = i;
                break;
            }
        }

        if (index >= 0) {
            payments.set(index, payment);
        } else {
            payments.add(payment);
        }

        return payment;
    }

    public List<Payment> findAll() {
        return new ArrayList<>(payments);
    }

    public Payment findById(String id) {
        for (Payment payment : payments) {
            if (payment.getId().equals(id)) {
                return payment;
            }
        }
        return null;
    }


}
