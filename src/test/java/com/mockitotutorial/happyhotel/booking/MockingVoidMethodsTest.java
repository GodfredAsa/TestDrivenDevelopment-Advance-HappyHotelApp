package com.mockitotutorial.happyhotel.booking;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class MockingVoidMethodsTest {

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
    void shouldThrowExceptionWhenMailReady() {

//        given
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1, 1),
                        LocalDate.of(2020, 1,5),2, false);
       doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(any());

//        when
        Executable executable = () -> underTest.makeBooking(bookingRequest);

//        then
        assertThrows(BusinessException.class, executable);
    }


    @Test
    void shouldNotThrowExceptionWhenMailNotReady() {

//        given
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1, 1),
                        LocalDate.of(2020, 1,5),2, false);
        doNothing().when(mailSenderMock).sendBookingConfirmation(any());
//        doNothing() is the default method so you can comment it out and still get the same result

//        when
       underTest.makeBooking(bookingRequest);

//        then
//        no exception was thrown

    }








}
