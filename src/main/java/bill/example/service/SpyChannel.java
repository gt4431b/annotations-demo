package bill.example.service;

import org.springframework.stereotype.Component;

import bill.example.annotations.Notifier;
import lombok.extern.slf4j.Slf4j;

@Notifier ( notificationMethod = "sneak" )
@Slf4j
@Component
public class SpyChannel {

	public void sneak ( ) {
		log.info ( "Unofficially made aware that something interesting is afoot!" ) ;
	}
}
