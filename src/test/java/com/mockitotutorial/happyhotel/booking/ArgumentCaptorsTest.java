package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ArgumentCaptorsTest {

    BookingService underTest;

    RoomService roomServiceMock;
    MailSender mailSenderMock;
    PaymentService paymentServiceMock;
    BookingDAO bookingDAOMock;

    private ArgumentCaptor<Double> doubleArgumentCaptor;
//    private ArgumentCaptor<BookingRequest>  bookingRequestArgumentCaptor;


    @BeforeEach
    void setUp() {
        roomServiceMock = mock(RoomService.class);
        mailSenderMock = mock(MailSender.class);
        paymentServiceMock = mock(PaymentService.class);
        bookingDAOMock = mock(BookingDAO.class);

        this.doubleArgumentCaptor = ArgumentCaptor.forClass(Double.class);

        underTest = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock,mailSenderMock);

    }

    @Test
    void shouldPayCorrectPriceWhenInputOk() {
//        given
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                                               LocalDate.of(2020, 1,5),2, true);

        //        when
        underTest.makeBooking(bookingRequest);


//        then
        verify(paymentServiceMock).pay( eq(bookingRequest), doubleArgumentCaptor.capture());

//        getting the value from the captor
        double capturedArgument = doubleArgumentCaptor.getValue();
        assertEquals(400.0, capturedArgument);


    }


    @Test
    void shouldPayCorrectPriceWhenMultipleCalls() {
//        given
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                        LocalDate.of(2020, 1,5),2, true);
        BookingRequest bookingRequest1 =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                        LocalDate.of(2020, 1,2),2, true);

        List<Double> expectedValues = Arrays.asList(400.0, 100.0);

        //        when

        underTest.makeBooking(bookingRequest);
        underTest.makeBooking(bookingRequest1);


//        then
        verify(paymentServiceMock, times(2)).pay(any(), doubleArgumentCaptor.capture());


        List<Double> capturedArgument = doubleArgumentCaptor.getAllValues();
        assertEquals(expectedValues, capturedArgument);


    }






}