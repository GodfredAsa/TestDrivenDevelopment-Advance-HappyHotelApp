package com.mockitotutorial.happyhotel.booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Test03ReturnCustomValues {
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

    @Test
    void should_CountAvailablePlacesWhenOneRoomAvailable(){
        when(this.roomServiceMock.getAvailableRooms()).thenReturn(Collections.singletonList(new Room("Room 1", 2)));
        int expectedPlaceCount  = 2;

        int actualPlaceCount = bookingService.getAvailablePlaceCount();

        assertEquals(expectedPlaceCount, actualPlaceCount);
    }

    /**
     * list of rooms parsed
     * the place count is calculated on the sum of the capacity of each room
     */
    @Test
    void should_CountAvailablePlacesWhenMultipleRoomsAvailable(){
        List<Room> rooms = Arrays.asList(new Room("Room 1", 2), new Room("Room 2", 3), new Room("Room 3", 4));
        when(this.roomServiceMock.getAvailableRooms()).thenReturn(rooms);

        int expectedPlaceCount  = 9;

        int actualPlaceCount  = bookingService.getAvailablePlaceCount();

        assertEquals(expectedPlaceCount, actualPlaceCount);
    }
}