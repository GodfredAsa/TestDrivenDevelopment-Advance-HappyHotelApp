package com.mockitotutorial.happyhotel.booking._02_advanceConcepts;
import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Test014MockingStaticMethods {
    @InjectMocks private BookingService bookingService;
    @Mock private PaymentService paymentServiceMock;
    @Mock private RoomService roomServiceMock;
    @Mock private BookingDAO bookingDAOMock;
    @Mock private MailSender mailSenderMock;

    /**
     * Mocking static methods in the pom.xml file change the core to inline
     * of the mockito dependency
     *
     *  to understand the class being tested click on the CurrencyConverter to
     *  observe the internal static method being tested
     *
     *  NB: Take keynote in observing the structure followed in this procedure to fully grasp,
     *  understand and be able to test static methods
     */

    @Test
    void calculateCorrectPrice_WithDummyConvertor() {
        try(MockedStatic<CurrencyConverter> mockCurrencyConverter = mockStatic(CurrencyConverter.class)){
            // GIVEN
            BookingRequest bookingRequest =
                    new BookingRequest("1",  LocalDate.of(2020, 1,1),
                            LocalDate.of(2020, 1,5),2, false);
            double expectedPrice = 400.0;

           mockCurrencyConverter.when(()-> CurrencyConverter.toEuro(anyDouble())).thenReturn(400.0);

// WHEN
            double actualPrice = bookingService.calculatePriceEuro(bookingRequest);

// THEN
            assertEquals(expectedPrice, actualPrice);
        }
    }

    /**
     * The reason for the duplication of this method is to make the static method intelligent as the code above
     * does not have in effect on the output. The mockito way to achieve this is to use answer
     * observe critically certain changes have been made.
     */
    @Test
    void calculateCorrectPrice_WithIntelligentConvertor() {
        try(MockedStatic<CurrencyConverter> mockCurrencyConverter = mockStatic(CurrencyConverter.class)){
            // GIVEN
            BookingRequest bookingRequest =
                    new BookingRequest("1",  LocalDate.of(2020, 1,1),
                            LocalDate.of(2020, 1,5),2, false);
            double expectedPrice = 400.0 * 0.8;

            mockCurrencyConverter
                    .when(()-> CurrencyConverter.toEuro(anyDouble()))
                    .thenAnswer(price -> (double) price.getArgument(0) * 0.8);

// WHEN
            double actualPrice = bookingService.calculatePriceEuro(bookingRequest);

// THEN
            assertEquals(expectedPrice, actualPrice);
        }
    }
}