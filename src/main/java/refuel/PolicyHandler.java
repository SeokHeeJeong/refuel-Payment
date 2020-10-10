package refuel;

import refuel.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    PaymentRepository PaymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationCanceled_Cancel(@Payload ReservationCanceled reservationCanceled){

        if(reservationCanceled.isMe()){
            System.out.println("##### listener Cancel : " + reservationCanceled.toJson());

            // view 객체 조회
            List<Payment> paymentList = PaymentRepository.findByReservationId(reservationCanceled.getId());
            for(Payment payment : paymentList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                payment.setReservationStatus("CANCELED");
                payment.setPaymentStatus("CANCELED");
                // view 레파지 토리에 save
                PaymentRepository.save(payment);
            }
        }
    }

}
