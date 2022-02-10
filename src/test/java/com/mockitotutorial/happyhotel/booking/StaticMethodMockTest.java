package com.mockitotutorial.happyhotel.booking;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;


@ExtendWith({MockitoExtension.class})
class StaticMethodMockTest {

    @InjectMocks BookingService underTest;

    @Mock RoomService roomServiceMock;
    @Mock MailSender mailSenderMock;
    @Mock PaymentService paymentServiceMock;
    @Spy BookingDAO bookingDAOMock;

    @Captor private ArgumentCaptor<Double> doubleArgumentCaptor;





    @Test
    void shouldInvokePaymentServiceIfPrepaid() {
        try(MockedStatic<CurrencyConverter> mockedConverter = mockStatic(CurrencyConverter.class)){

            //        given
            BookingRequest bookingRequest =
                    new BookingRequest("1",  LocalDate.of(2020, 1,1),
                            LocalDate.of(2020, 1,5),2, false);

            double expected = 400.0;
            mockedConverter.when(()-> CurrencyConverter.toEuro(anyDouble())).thenReturn(400.0);

//       when
            double actual = underTest.calculatePriceEuro(bookingRequest);

//        then
            assertEquals(expected, actual);
        }
    }





}