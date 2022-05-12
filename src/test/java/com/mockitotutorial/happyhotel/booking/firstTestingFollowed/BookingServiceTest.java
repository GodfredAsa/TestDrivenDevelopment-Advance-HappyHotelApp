package com.mockitotutorial.happyhotel.booking.firstTestingFollowed;
import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookingServiceTest {

    BookingService underTest;
    RoomService roomServiceMock;
    MailSender mailSenderMock;
    PaymentService paymentServiceMock;
    BookingDAO bookingDAOMock;

    @BeforeEach
    void setUp() {
        roomServiceMock = mock(RoomService.class);
        mailSenderMock = mock(MailSender.class);
        paymentServiceMock = mock(PaymentService.class);
        bookingDAOMock = mock(BookingDAO.class);
        underTest = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock,mailSenderMock);
    }

    @Test
    void shouldCalculatePriceWithCorrectInput() {
//        given
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                                               LocalDate.of(2020, 1,5),2, false);
        double expected  =  4 * 2 * 50.0;
//        when
        double actual = underTest.calculatePrice(bookingRequest);
// then
        assertEquals(expected, actual);
    }

    @Test
    void countAvailablePlacesWithOneRoom() {
//     given
        when(this.roomServiceMock.getAvailableRooms()).thenReturn(Collections.singletonList(new Room("Room 1", 2)));
        int expected = 2; // it's based on the capacity
//     when
        int actual = underTest.getAvailablePlaceCount();
//     then
        assertEquals(expected, actual);
    }

    @Test
    void countAvailablePlacesWithManyRooms() {

        List<Room> rooms = Arrays.asList(new Room("Room 1", 2), new Room("Room 1", 20));
        when(this.roomServiceMock.getAvailableRooms()).thenReturn(rooms);
        int expected = 22;

        int actual = underTest.getAvailablePlaceCount();

        assertEquals(expected, actual);
    }

//    Multiple thenReturn calls
//    On the first call returns 2 as the capacity
//    but on the second call returns 0 as the list is empty
//    this is an alternating type of test

    @Test
    public void shouldCountAvailablePlacesWhenCalledMultipleTimes(){
        //     given
        List<Room> rooms = Arrays.asList(new Room("Room 1", 2), new Room("Room 1", 20));
        when(this.roomServiceMock.getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room("Room 1", 2)))
                .thenReturn(Collections.emptyList())
                .thenReturn(rooms);

        int expectedFirst = 2; // it's based on the capacity
        int expectedSecond = 0; // it's based on the capacity
        int expectedThird = 22; // it's based on the capacity

//     when
        int actualFirst = underTest.getAvailablePlaceCount();
        int actualSecond = underTest.getAvailablePlaceCount();
        int actualThird = underTest.getAvailablePlaceCount();

//     then
        assertAll(
                () -> assertEquals(expectedFirst, actualFirst, "first call 2 in the room"),
                () -> assertEquals(expectedSecond, actualSecond, "second call empty room"),
                () -> assertEquals(expectedThird, actualThird, "tests list of rooms 22")
        );
    }





    @Test
    void shouldCountAvailablePlaces() {
//     given
        int expected = 0;

//     when

        int actual = underTest.getAvailablePlaceCount();
//     then

        assertEquals(expected, actual);

    }



    @Test
    public void shouldFailWithIncorrectInputs(){

//        given
        BookingRequest bookingRequest = new BookingRequest("1",  LocalDate.of(2020, 1, 1),
                                                                        LocalDate.of(2020, 1, 5),  2, false);

        double expected  =  4 * 10 * 50.0;

//        when
        double actual = this.underTest.calculatePrice(bookingRequest);

//        then
        assertNotEquals(expected, actual, 0.0);
    }


}