package com.mockitotutorial.happyhotel.booking._01_basicConcepts;
import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Test04MultipleThenCalls {
    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setUp() {
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);
        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
    }

    /**
     * returns different values when called multiple time times
     */

    @Test
    void should_CountAvailablePlaces_WhenCalled_Multiple_Times(){
        when(this.roomServiceMock.getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room("Room 1", 2)))
                .thenReturn(Collections.emptyList());
        int firstExpectedPlaceCount  = 2;
        int secondExpectedPlaceCount  = 0;

        int firstActualPlaceCount = bookingService.getAvailablePlaceCount();
        int secondActualPlaceCount = bookingService.getAvailablePlaceCount();

        assertAll(
                ()-> assertEquals(firstExpectedPlaceCount, firstActualPlaceCount),
                ()-> assertEquals(secondExpectedPlaceCount, secondActualPlaceCount)
                );
    }
}