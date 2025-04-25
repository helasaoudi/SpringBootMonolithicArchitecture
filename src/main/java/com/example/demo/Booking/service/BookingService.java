package com.example.demo.Booking.service;

import com.example.demo.Booking.dto.BookingDto;
import com.example.demo.Booking.entity.Booking;
import com.example.demo.Booking.repository.BookingRepository;
import com.example.demo.movie.entity.movie;
import com.example.demo.movie.repository.MovieRepository;
import com.example.demo.notification.Repository.NotificationRepository;
import com.example.demo.notification.entity.Notification;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor

public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final NotificationRepository notificationRepository;
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);


    public List<BookingDto> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private BookingDto convertToDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .seatNumber(booking.getSeatNumber())
                .userId(booking.getUser().getId())
                .movieId(booking.getMovie().getId())
                .cancelled(booking.isCancelled())
                .build();
    }
    public BookingDto book(BookingDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        log.info("Creating booking for user {} and movie {}", dto.getUserId(), dto.getMovieId()); // DÉPLACÉ ICI

        Booking booking = Booking.builder()
                .seatNumber(dto.getSeatNumber())
                .user(user)
                .movie(movie)
                .cancelled(false)
                .bookingTime(LocalDateTime.now())
                .build();

        Booking saved = bookingRepository.save(booking);
        return BookingDto.builder()
                .id(saved.getId())
                .seatNumber(saved.getSeatNumber())
                .userId(user.getId())
                .movieId(movie.getId())
                .cancelled(false)
                .build();
        // Supprimer le log.info() d'ici car après le return
    }
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setCancelled(true);
        bookingRepository.save(booking);

        Notification notif = Notification.builder()
                .user(booking.getUser())
                .message("Votre réservation pour le film '" + booking.getMovie().getTitle() + "' a été annulée.")
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();

        notificationRepository.save(notif);
    }
}

