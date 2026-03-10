package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderService orderService;

    Order order;
    Map<String, String> bankTransferData;
    Map<String, String> voucherData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudajat");

        bankTransferData = new HashMap<>();
        bankTransferData.put("bankName", "BCA");
        bankTransferData.put("referenceCode", "REF123456");

        voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testAddPaymentBankTransferSuccess() {
        Payment payment = new Payment("P-001", "BANK_TRANSFER", bankTransferData);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", bankTransferData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testAddPaymentVoucherSuccess() {
        Payment payment = new Payment("P-002", "VOUCHER", voucherData);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER", voucherData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testAddPaymentBankTransferRejected() {
        bankTransferData.put("bankName", "");
        Payment payment = new Payment("P-003", "BANK_TRANSFER", bankTransferData);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", bankTransferData);

        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testSetStatusSuccessUpdatesOrder() {
        Payment payment = new Payment("P-004", "BANK_TRANSFER", bankTransferData);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderService).findById(order.getId());

        Payment result = paymentService.setStatus(payment, order, "SUCCESS");

        assertEquals("SUCCESS", result.getStatus());
        verify(orderService, times(1)).updateStatus(order.getId(), "SUCCESS");
    }

    @Test
    void testSetStatusRejectedUpdatesOrder() {
        Payment payment = new Payment("P-005", "BANK_TRANSFER", bankTransferData);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderService).findById(order.getId());

        Payment result = paymentService.setStatus(payment, order, "REJECTED");

        assertEquals("REJECTED", result.getStatus());
        verify(orderService, times(1)).updateStatus(order.getId(), "FAILED");
    }

    @Test
    void testSetStatusInvalid() {
        Payment payment = new Payment("P-006", "BANK_TRANSFER", bankTransferData);

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, order, "MEOW");
        });
    }

    @Test
    void testGetPaymentFound() {
        Payment payment = new Payment("P-007", "BANK_TRANSFER", bankTransferData);
        doReturn(payment).when(paymentRepository).findById("P-007");

        Payment result = paymentService.getPayment("P-007");

        assertEquals("P-007", result.getId());
    }

    @Test
    void testGetPaymentNotFound() {
        doReturn(null).when(paymentRepository).findById("zzz");

        assertThrows(NoSuchElementException.class, () -> {
            paymentService.getPayment("zzz");
        });
    }

    @Test
    void testGetAllPayments() {
        Payment p1 = new Payment("P-001", "BANK_TRANSFER", bankTransferData);
        Payment p2 = new Payment("P-002", "VOUCHER", voucherData);
        doReturn(List.of(p1, p2)).when(paymentRepository).findAll();

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
    }
}