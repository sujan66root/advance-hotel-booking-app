package com.sujan.hotelbooking.service.serviceinterface;

import com.sujan.hotelbooking.dto.ResponseDto;
import com.sujan.hotelbooking.entity.Booking;

public interface IBookingService {
/*
save Booking, findBookingByConfirmationCode
get all booking, cancel booking

 */
    ResponseDto saveBooking(Booking booking, Long userId, Long roomId);

    ResponseDto getAllBooking();

    ResponseDto cancelBooking(Long id);

    ResponseDto findBookingByConfirmationCode(String confirmationCode);

}
