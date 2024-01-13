package bill.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bill.example.annotations.Notification;
import bill.example.config.NotificationRegistry;

@Service
public class NotificationService {

	@Autowired
	private NotificationRegistry registry ;

	public void notify ( String s ) {
		registry.doNotification ( new Notification ( s ) ) ;
	}
}
