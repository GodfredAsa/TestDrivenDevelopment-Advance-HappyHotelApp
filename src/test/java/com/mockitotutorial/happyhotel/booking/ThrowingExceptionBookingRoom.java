package com.mockitotutorial.happyhotel.booking;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ThrowingExceptionBookingRoom {

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
    void shouldThrowExceptionWhenNoRoomAvailable() {
        // throws exception when only bookingRequest is used in the executable
        // if another instance of bookingRequest is created and passed the test will fail

//        given
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1, 1),
                        LocalDate.of(2020, 1,5),2, false);

        when(roomServiceMock.findAvailableRoomId(bookingRequest)).thenThrow(BusinessException.class);


//        when
        Executable executable = () -> underTest.makeBooking(bookingRequest);

// then
        assertThrows(BusinessException.class, executable);

    }


//    this tests passes no matter the bookingRequest passed
//    in the executable using the argument matchers



}
