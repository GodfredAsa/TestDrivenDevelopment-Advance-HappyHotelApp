package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class Test02DefaultReturnValues {
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
        System.out.println("List of rooms: "  + roomServiceMock.getAvailableRooms());
        System.out.println("Object Returned: "  + roomServiceMock.findAvailableRoomId(null));
        System.out.println("Primitive Return type: "  + roomServiceMock.getRoomCount());
    }


    @Test
    void shouldCountAvailablePlaces(){
/**
 * count available places depends on the room service as a dependent.
 */
        int expected = 0;
        int actual = bookingService.getAvailablePlaceCount();

        assertEquals(expected, actual);




    }
}