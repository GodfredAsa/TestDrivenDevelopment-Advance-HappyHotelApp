package com.mockitotutorial.happyhotel.booking.firstTestingFollowed;



import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;


@ExtendWith({MockitoExtension.class})
class StrictStubbingTest {

    @InjectMocks
    BookingService underTest;

    @Mock
    RoomService roomServiceMock;
    @Mock
    MailSender mailSenderMock;
    @Mock
    PaymentService paymentServiceMock;
    @Spy
    BookingDAO bookingDAOMock;

    @Captor private ArgumentCaptor<Double> doubleArgumentCaptor;





    @Test
    void shouldInvokePaymentServiceIfPrepaid() {
//        given
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                        LocalDate.of(2020, 1,5),2, false);

        lenient().when(paymentServiceMock.pay(any(), anyDouble())).thenReturn("1");
//       when
        underTest.makeBooking(bookingRequest);


//        then
//        no exception is thrown


    }





}