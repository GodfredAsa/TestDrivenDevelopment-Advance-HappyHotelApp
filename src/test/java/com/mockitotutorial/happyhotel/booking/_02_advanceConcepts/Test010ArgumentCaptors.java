package com.mockitotutorial.happyhotel.booking._02_advanceConcepts;
import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class Test010ArgumentCaptors {
    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;
    private ArgumentCaptor<Double> doubleArgumentCaptor;
    /**
     * sample capturing of a class
     */
//  private ArgumentCaptor<BookingRequest> bookingRequestArgumentCaptor;

    @BeforeEach
    void setUp() {
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);
        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);

        this.doubleArgumentCaptor = ArgumentCaptor.forClass(Double.class);
    }

    /**
     * argument captor is used to capture the arguments parse to methods
     * it's used to capture the value of an argument as seen in the last code
     * and raise assertions on it
     */
    @Test
    void payCorrectPriceWhenInputOk() {
// GIVEN
        BookingRequest bookingRequest =
                new BookingRequest("1", LocalDate.of(2020, 1, 1),
                        LocalDate.of(2020, 1, 11), 2, true);

// WHEN
        bookingService.makeBooking(bookingRequest);
// THEN
        verify(paymentServiceMock     , times(1)).pay(eq(bookingRequest), doubleArgumentCaptor.capture());

        double expectedCapturedArgument = 10 * 2 * 50.0;
        double actualCapturedArgument = doubleArgumentCaptor.getValue();

        assertEquals(expectedCapturedArgument, actualCapturedArgument);
        System.out.println(expectedCapturedArgument);
    }

    /**
     * checks if the payment service is called twice. with different arguments
     */
    @Test
    void payCorrectPriceWithMultipleCals() {
// GIVEN
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 1, 1),LocalDate.of(2020, 1, 11), 2, true);
        BookingRequest bookingRequest2 =new BookingRequest("1", LocalDate.of(2020, 1, 1),LocalDate.of(2020, 1, 5), 2, true);
        List<Double> expectedArgumentCaptured = Arrays.asList(1000.0, 400.0);
// WHEN
        bookingService.makeBooking(bookingRequest);
        bookingService.makeBooking(bookingRequest2);

// THEN
        verify(paymentServiceMock, times(2)).pay(any(), doubleArgumentCaptor.capture());
        List<Double> actualCapturedArguments = doubleArgumentCaptor.getAllValues();
        assertEquals(expectedArgumentCaptured, actualCapturedArguments);
        System.out.println(actualCapturedArguments);
    }
}