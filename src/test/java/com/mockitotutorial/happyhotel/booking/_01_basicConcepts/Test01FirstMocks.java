package com.mockitotutorial.happyhotel.booking._01_basicConcepts;
import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class Test01FirstMocks {
    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    private final int GUEST_COUNT = 2;

    @BeforeEach
    void setUp() {
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);
        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
    }

    @Test
    void shouldCalculateCorrectPriceWithCorrectInput() {
/**
 * this is calculated based on the number of days difference between the two days
 * the 2 refers to the guess counts
 * 50.0 refers to the price per day
 * described in the booking request class
 */
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                        LocalDate.of(2020, 1,10),GUEST_COUNT, false);
        double expected  =  9 * GUEST_COUNT * 50.0;

        double actual = bookingService.calculatePrice(bookingRequest);

        assertEquals(expected, actual);
    }
}