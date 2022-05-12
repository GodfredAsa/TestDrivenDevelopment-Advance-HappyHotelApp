package com.mockitotutorial.happyhotel.booking.firstTestingFollowed;

import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

class Spies_PartialMocksTest {

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

        bookingDAOMock = spy(BookingDAO.class);

        underTest = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock,mailSenderMock);

    }

    @Test
    void shouldMakeBookingWhenInputOK() {

        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                                               LocalDate.of(2020, 1,5),2, true);
       String bookingId =  underTest.makeBooking(bookingRequest);

        verify(bookingDAOMock).save(bookingRequest);
        System.out.println("booking Id: " + bookingId);
/*
 * checks if the bookingDAO is used and the save() is invoked
 * then s-out the booking id
 * if it passes means the pay() was only invoked once
 * spy calls the actual method on the class
 * NB: Observe the spy implementation carefully as spy is used instead of mock
 */
    }


    @Test
    void shouldCancelBookingWhenInputOK() {
//        given
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                        LocalDate.of(2020, 1,5),2, true);
        bookingRequest.setRoomId("1.3");

        String bookingId = "1";
        doReturn(bookingRequest).when(bookingDAOMock).get(bookingId);


//        when
        underTest.cancelBooking(bookingId);

//        then









    }



}