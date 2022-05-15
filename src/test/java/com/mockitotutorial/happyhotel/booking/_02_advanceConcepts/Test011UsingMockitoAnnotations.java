package com.mockitotutorial.happyhotel.booking._02_advanceConcepts;
import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Test011UsingMockitoAnnotations {
    @InjectMocks private BookingService bookingService;
    @Mock private PaymentService paymentServiceMock;
    @Mock private RoomService roomServiceMock;
    @Mock private BookingDAO bookingDAOMock;
    @Mock private MailSender mailSenderMock;
    @Captor private ArgumentCaptor<Double> doubleArgumentCaptor;

    private final double BASIC_COST = 50.0;

    /**
     * this test is a build up for the Test010ArgumentCaptors
     * by using annotations instead of the @BeforeEach in the previous
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
        verify(paymentServiceMock, times(1)).pay(eq(bookingRequest), doubleArgumentCaptor.capture());

        double expectedCapturedArgument = 10 * 2 * BASIC_COST;
        double actualCapturedArgument = doubleArgumentCaptor.getValue();

        assertEquals(expectedCapturedArgument, actualCapturedArgument);
        System.out.println("Expected cost: " + expectedCapturedArgument + ", Actual cost: " + actualCapturedArgument);
    }

    /**
     * checks if the payment service is called twice. with different arguments
     */
    @Test
    void payCorrectPricesWithMultipleCalls() {
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