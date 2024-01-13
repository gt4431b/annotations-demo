package bill.example.service;

import org.springframework.stereotype.Component;

import bill.example.annotations.Notification;
import bill.example.annotations.Notifier;
import lombok.extern.slf4j.Slf4j;

@Notifier
@Slf4j
@Component
public class OfficialChannel {

	public void ping ( Notification n ) {
		log.info ( "Officially notified that {}", n.getMessage ( ) ) ;
	}
}
