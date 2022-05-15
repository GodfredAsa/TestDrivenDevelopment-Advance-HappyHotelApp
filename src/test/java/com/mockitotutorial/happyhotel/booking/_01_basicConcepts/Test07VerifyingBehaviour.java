package com.mockitotutorial.happyhotel.booking._01_basicConcepts;
import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.mockito.Mockito.*;

class Test07VerifyingBehaviour {
    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setUp() {
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);
        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
    }

    /**
     * verify if payment service mock is called
     */
    @Test
    void InvokePaymentWhenPrepared() {
// GIVEN
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                        LocalDate.of(2020, 1,11),2, true);
// WHEN
        bookingService.makeBooking(bookingRequest);
// THEN
        verify(paymentServiceMock, times(1)).pay(bookingRequest, 1000.0);
        verifyNoMoreInteractions(paymentServiceMock); // checks if any other method from the paymentServiceMock was called
    }

    @Test
    void shouldNotInvokePaymentWhenNotPrepared() {
        BookingRequest bookingRequest = new BookingRequest("1",
                LocalDate.of(2020, 1,1),
                LocalDate.of(2020, 1,10),2, false);

        bookingService.makeBooking(bookingRequest);

        verify(paymentServiceMock, never()).pay(any(), anyDouble()); // ensures the method is never called
    }
}