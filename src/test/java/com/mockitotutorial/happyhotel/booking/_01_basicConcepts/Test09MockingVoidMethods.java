package com.mockitotutorial.happyhotel.booking._01_basicConcepts;
import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class Test09MockingVoidMethods {
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
     * void methods use doThrow approach
     */
    @Test void shouldThrowExceptionWhenMailNotReady() {
// when
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                        LocalDate.of(2020, 1,10),2, false);

        doThrow(BusinessException.class).when(mailSenderMock).sendBookingConfirmation(any());
// then
        Executable executable = () -> bookingService.makeBooking(bookingRequest);
// throw
        assertThrows(BusinessException.class, executable);
    }

    @Test void shouldNotThrowExceptionWhenMailNotReady() {
// given
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                        LocalDate.of(2020, 1,10),2, false);

        doNothing().when(mailSenderMock).sendBookingConfirmation(any());
// when
      bookingService.makeBooking(bookingRequest);
// then
// No exception
    }
}



