package com.mockitotutorial.happyhotel.booking;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class AnnotationsTest {

    @InjectMocks BookingService underTest;

    @Mock RoomService roomServiceMock;
    @Mock MailSender mailSenderMock;
    @Mock PaymentService paymentServiceMock;
    @Spy BookingDAO bookingDAOMock;

    @Captor private ArgumentCaptor<Double> doubleArgumentCaptor;



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