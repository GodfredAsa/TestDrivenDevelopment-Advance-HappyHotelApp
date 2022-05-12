package com.mockitotutorial.happyhotel.booking.firstTestingFollowed;

import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TestArgumentMatchers {


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
    void shouldNotCompleteBookingWhenPriceIsTooHigh() {
        /*
         *   inside the any() you can parse  [BookingRequest.class] but is not required  also
         *   as the test does not depend on the price we parse anyDouble() as the price
         *   so instead of this
         *   when(this.paymentServiceMock.pay(any(BookingRequest.class), 200.0)).thenThrow(BusinessException.class);
         *   we do this
         *   when(this.paymentServiceMock.pay(any(), anyDouble())).thenThrow(BusinessException.class);
         *   using any() with exact price value wrap the price value in [ eq(priceValue) ]
         *   Eg. when(this.paymentServiceMock.pay(any(), eq(200.0)).thenThrow(BusinessException.class);
         */

//        given
        BookingRequest bookingRequest =
                new BookingRequest("2",  LocalDate.of(2020, 1, 1),
                        LocalDate.of(2020, 1,5),2, true);

        when(this.paymentServiceMock.pay(any(), anyDouble())).thenThrow(BusinessException.class);


//        when
        Executable executable = () -> underTest.makeBooking(bookingRequest);

// then
        assertThrows(BusinessException.class, executable);

    }




}
