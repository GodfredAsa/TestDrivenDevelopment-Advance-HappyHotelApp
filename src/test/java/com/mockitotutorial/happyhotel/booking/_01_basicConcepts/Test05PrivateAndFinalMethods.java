package com.mockitotutorial.happyhotel.booking._01_basicConcepts;
import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Test05PrivateAndFinalMethods {
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
     * for final methods use mockito inline
     *
     * NB: NEVER TRY TO MOCK A PRIVATE METHOD BCOS YOU CAN'T MOCK A PRIVATE METHOD
     */

    @Test
    void should_CountAvailablePlacesWhenOneRoomAvailable(){
        when(this.roomServiceMock
                .getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room("Room 1", 2)));

        int expectedPlaceCount  = 2;

        int actualPlaceCount = bookingService.getAvailablePlaceCount();

        assertEquals(expectedPlaceCount, actualPlaceCount);
    }
}