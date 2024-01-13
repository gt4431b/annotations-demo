package bill.example.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import bill.example.annotations.Notification;
import bill.example.annotations.Notifier;

@Component
public class NotificationRegistry implements BeanPostProcessor {

	public List <Object> notifiers = new ArrayList <> ( ) ;

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Notifier n = bean.getClass().getAnnotation ( Notifier.class ) ;
		if ( n != null ) {
			notifiers.add ( bean ) ;
		}
		// We make no changes to the bean.  We just notify register and move on.
		return bean;
	}

	public void doNotification ( Notification n ) {
		for ( Object o : notifiers ) {
			Notifier recipient = o.getClass().getAnnotation ( Notifier.class ) ;
			String notificationMethod = recipient.notificationMethod ( ) ;
			Method notifyMethod = null ;
			// So I'm going to give a few options.  Either you can call a notificationMethod
			// with one arg (the Notification) or no args at all.  And the
			// notificationMethod can have any name so long as it matches an
			// actual method in the class
			try {
				notifyMethod = o.getClass ( ).getDeclaredMethod ( notificationMethod, Notification.class ) ;
				notifyMethod.invoke ( o, n ) ;
				continue ;
			} catch (NoSuchMethodException e) {
				// this is fine, we'll just try the next one
			} catch ( Exception e ) {
				throw new RuntimeException ( e ) ;
			}

			// No arg attempt
			try {
				notifyMethod = o.getClass ( ).getDeclaredMethod ( notificationMethod ) ;
				notifyMethod.invoke ( o ) ;
				continue ;
			} catch (NoSuchMethodException e) {
				// this is not fine!
				throw new RuntimeException ( e ) ;
			} catch ( Exception e ) {
				throw new RuntimeException ( e ) ;
			}
		}
	}
}
