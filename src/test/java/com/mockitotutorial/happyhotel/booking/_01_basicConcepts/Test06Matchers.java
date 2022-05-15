package com.mockitotutorial.happyhotel.booking._01_basicConcepts;
import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Test06Matchers {
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
     * this follows when then throw pattern

     * this time throwing exception from the payment service

     * the intent of test is to throw exception no matter the input
     *
     *  * any() could be replaced with the class  " any(BookingRequest.class)" although not necessary
     *  * to mix any matcher with exact values use eq(parse in value) example eq(400.0) as below, eq => equals
     *  * instead of the anyDouble() which is a primitive antMatcher
     *
     */

    @Test
    void shouldNotCompleteBooking_When_PriceTooHigh() {
// when
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                        LocalDate.of(2020, 1,10),2, true);

        when(this.paymentServiceMock.pay(any(BookingRequest.class), anyDouble())).thenThrow(BusinessException.class);
// then
        Executable executable = () -> bookingService.makeBooking(bookingRequest);
// throw
        assertThrows(BusinessException.class, executable);
    }
}



