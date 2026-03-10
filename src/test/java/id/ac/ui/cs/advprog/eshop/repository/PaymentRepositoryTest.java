package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private Map<String, String> voucherData;
    private Map<String, String> bankTransferData;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");

        bankTransferData = new HashMap<>();
        bankTransferData.put("bankName", "BCA");
        bankTransferData.put("referenceCode", "REF123456");
    }

    @Test
    void testSaveNewPayment() {
        Payment payment = new Payment("P-001", "VOUCHER", voucherData);
        Payment saved = paymentRepository.save(payment);

        assertEquals(payment.getId(), saved.getId());
        assertEquals(payment.getMethod(), saved.getMethod());
        assertEquals(payment.getStatus(), saved.getStatus());
        assertEquals(payment.getPaymentData(), saved.getPaymentData());
    }

    @Test
    void testSaveUpdatesExistingPayment() {
        Payment payment = new Payment("P-002", "BANK_TRANSFER", bankTransferData);
        paymentRepository.save(payment);

        payment.setStatus("REJECTED");
        Payment updated = paymentRepository.save(payment);

        assertEquals("REJECTED", updated.getStatus());
        assertEquals(1, paymentRepository.findAll().size());
    }

    @Test
    void testFindByIdFound() {
        Payment payment = new Payment("P-001", "VOUCHER", voucherData);
        paymentRepository.save(payment);

        Payment found = paymentRepository.findById("P-001");

        assertNotNull(found);
        assertEquals("P-001", found.getId());
        assertEquals("VOUCHER", found.getMethod());
        assertEquals("SUCCESS", found.getStatus());
    }

    @Test
    void testFindByIdNotFound() {
        Payment payment = new Payment("P-001", "VOUCHER", voucherData);
        paymentRepository.save(payment);

        Payment found = paymentRepository.findById("P-100");

        assertNull(found);
    }

    @Test
    void testFindAllEmpty() {
        List<Payment> allPayments = paymentRepository.findAll();
        assertTrue(allPayments.isEmpty());
    }

    @Test
    void testFindAllReturnsAllPayments() {
        Payment payment1 = new Payment("P-001", "VOUCHER", voucherData);
        Payment payment2 = new Payment("P-002", "BANK_TRANSFER", bankTransferData);
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        List<Payment> allPayments = paymentRepository.findAll();

        assertEquals(2, allPayments.size());
    }

    @Test
    void testFindAllReturnsCopy() {
        Payment payment = new Payment("P-001", "VOUCHER", voucherData);
        paymentRepository.save(payment);

        List<Payment> allPayments = paymentRepository.findAll();
        allPayments.clear();

        assertEquals(1, paymentRepository.findAll().size());
    }
}