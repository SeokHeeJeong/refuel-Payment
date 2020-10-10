package refuel;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PaymentRepository extends PagingAndSortingRepository<Payment, Long>{

    List<Payment> findByReservationId(Long reservationId);

}