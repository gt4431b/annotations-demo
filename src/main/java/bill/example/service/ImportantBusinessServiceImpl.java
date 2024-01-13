package bill.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bill.example.annotations.Profile;

@Service
public class ImportantBusinessServiceImpl implements ImportantBusinessService {

	@Autowired
	private NotificationService notifSvc ;

	@Profile
	public String goForthAndDoThus ( ) {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			throw new RuntimeException ( e ) ;
		}
		notifSvc.notify ( "I got called!" ) ;
		return "Done!" ;
	}
}
