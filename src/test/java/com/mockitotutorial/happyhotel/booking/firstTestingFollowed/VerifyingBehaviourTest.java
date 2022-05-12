package com.mockitotutorial.happyhotel.booking.firstTestingFollowed;

import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

class VerifyingBehaviourTest {

    BookingService underTest;

    RoomService roomServiceMock;
    MailSender mailSenderMock;
    PaymentService paymentServiceMock;
    BookingDAO bookingDAOMock;


    @BeforeEach
    void setUp() {
        roomServiceMock = mock(RoomService.class);
        mailSenderMock = mock(MailSender.class);
        paymentServiceMock = mock(PaymentService.class);
        bookingDAOMock = mock(BookingDAO.class);

        underTest = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock,mailSenderMock);

    }

    @Test
    void shouldInvokePaymentServiceIfPrepaid() {
//        given
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                                               LocalDate.of(2020, 1,5),2, true);

        //        when
        underTest.makeBooking(bookingRequest);


//        then
        verify(paymentServiceMock).pay(bookingRequest, 400.0);
/*
 * checks if any other method from these mock were called.
 * if pay() was called more than one will throw exception
 * if it passes means the pay() was only invoked once
 */
        verifyNoMoreInteractions(paymentServiceMock);


    }


    @Test
    void shouldNot_InvokePaymentService_IfNotPrepaid() {
//        given
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                        LocalDate.of(2020, 1,5),2, false); // prepaid determine the payment method.

//        when
        underTest.makeBooking(bookingRequest);


//        then
        verify(paymentServiceMock, never()).pay(any(BookingRequest.class),anyDouble());

    }


}