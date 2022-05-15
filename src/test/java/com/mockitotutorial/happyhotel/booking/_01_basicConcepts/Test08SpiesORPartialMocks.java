package com.mockitotutorial.happyhotel.booking._01_basicConcepts;
import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.mockito.Mockito.*;

/**
 * mock pattern when(mock.method()).thenReturn()
 * spy pattern doReturn().when(spy).method()
 */

class Test08SpiesORPartialMocks {

    BookingService bookingService;
    RoomService roomServiceMock;
    MailSender mailSenderMock;
    PaymentService paymentServiceMock;
    BookingDAO bookingDAOMock;

    @BeforeEach
    void setUp() {
        roomServiceMock = mock(RoomService.class);
        mailSenderMock = mock(MailSender.class);
        paymentServiceMock = mock(PaymentService.class);
        bookingDAOMock = spy(BookingDAO.class);
        bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock,mailSenderMock);
    }
    /**
     * mocks returns default values such as nulls, empty lists, 0 in terms of numeric values
     * spy returns real objects with real logics than can be modified. Spies call the actual methods from the actual
     * classes
     * Hence, we used it on the BookingDAO to spy the bookingId
     * checks if the bookingDAO is used and the save() is invoked
     * then s-out the booking id
     * if it passes means the pay() was only invoked once
     * spy calls the actual method on the class
     * NB: Observe the spy implementation carefully as spy is used instead of mock
     */
    @Test
    void makeBooking() {

        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                                               LocalDate.of(2020, 1,5),2, true);
       String bookingId =  bookingService.makeBooking(bookingRequest);

       verify(bookingDAOMock).save(bookingRequest);
       System.out.println("booking Id: " + bookingId);
    }
    /**
     * checks if the bookingDAO is used and the save() is invoked
     * then s-out the booking id
     * if it passes means the pay() was only invoked once
     * spy calls the actual method on the class
     * NB: Observe the spy implementation carefully as spy is used instead of mock
     */

    @Test
    void shouldCancelBookingWhenInputOK() {
//        given
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                        LocalDate.of(2020, 1,5),2, true);
        bookingRequest.setRoomId("1.3");

        String bookingId = "1";
        doReturn(bookingRequest).when(bookingDAOMock).get(bookingId);

//        when
        bookingService.cancelBooking(bookingId);
    }
}