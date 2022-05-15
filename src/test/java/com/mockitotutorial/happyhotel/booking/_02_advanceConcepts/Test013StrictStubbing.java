package com.mockitotutorial.happyhotel.booking._02_advanceConcepts;
import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Test013StrictStubbing {
    @InjectMocks private BookingService bookingService;
    @Mock private PaymentService paymentServiceMock;
    @Mock private RoomService roomServiceMock;
    @Mock private BookingDAO bookingDAOMock;
    @Mock private MailSender mailSenderMock;

    /**
     * Strict stubbing is deactivated by default means defining the behaviour of
     * your classes with when in classic mockito that's if a behaviour is defined and
     * never used in the case mockito will detect and remove those unnecessary behaviours
     * use lenient() when you still want to stick to strict stubbing but do not use it much.
     */

    @Test
    void InvokePaymentWhenPrepared() {
// GIVEN
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                        LocalDate.of(2020, 1,11),2, false);
        lenient().when(paymentServiceMock.pay(any(), anyDouble())).thenReturn("1");
// WHEN
        bookingService.makeBooking(bookingRequest);
// THEN
// NO EXCEPTION IS THROWN

    }

}