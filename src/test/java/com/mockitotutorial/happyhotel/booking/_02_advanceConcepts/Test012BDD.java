package com.mockitotutorial.happyhotel.booking._02_advanceConcepts;
import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Test012BDD {
    @InjectMocks private BookingService bookingService;
    @Mock private PaymentService paymentServiceMock;
    @Mock private RoomService roomServiceMock;
    @Mock private BookingDAO bookingDAOMock;
    @Mock private MailSender mailSenderMock;

    /**
     * BDD approach is just to follow Given, When, and Then pattern
     * study it carefully and ascertain the pattern and behaviour it follows
     */
    @Test
    void should_CountAvailablePlacesWhenOneRoomAvailable(){
//  GIVEN
        given(this.roomServiceMock.getAvailableRooms()).willReturn(Collections.singletonList(new Room("Room 1", 2)));
        int expectedPlaceCount  = 2;
//  WHEN
        int actualPlaceCount = bookingService.getAvailablePlaceCount();
//  THEN
        assertEquals(expectedPlaceCount, actualPlaceCount);
    }

    @Test
    void InvokePaymentWhenPrepared() {
// GIVEN
        BookingRequest bookingRequest =
                new BookingRequest("1",  LocalDate.of(2020, 1,1),
                        LocalDate.of(2020, 1,11),2, true);
// WHEN
        bookingService.makeBooking(bookingRequest);
// THEN
        then(paymentServiceMock).should(times(1)).pay(bookingRequest, 1000.0);
        verifyNoMoreInteractions(paymentServiceMock); // checks if any other method from the paymentServiceMock was called
    }

}