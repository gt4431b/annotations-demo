package bill.example.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import bill.example.annotations.Profile;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
// This is an example of Case 1:
public class Profiler implements BeanPostProcessor {

	// Most important thing to note here?  I don't care what bean is.
	// For true separation-of-concerns items for architectural use
	// these are best when the decorator does something utterly agnostic
	// to the object you're decorating.  Not always the case, and
	// a good example for when it can take at least a passing interest
	// is with Spring Rest (which is implemented this way), where it can
	// automatically inject certain values for declared types like
	// HttpServletRequest or examine query params.  But the beauty of
	// using this approach is that a Rest controller does not really have to
	// conform to any interface or structure and you can make it what you want.
	public Object postProcessBeforeInitialization ( final Object bean, String beanName ) throws BeansException {
		Profile instructions = getProfileInstructions ( bean ) ;
		if ( instructions == null ) {
			return bean ; // because the annotation is not present
		}
		Class <?> ifc = getPreferredInterface ( bean ) ;
		return Proxy.newProxyInstance(getClass ( ).getClassLoader(), new Class <?> [ ] { ifc }, new InvocationHandler ( ) {

			@Override
			public Object invoke ( Object proxy, Method method, Object [ ] args ) throws Throwable {
				// DECORATOR PATTERN.  We follow the same interface/contract as the inner object.
				// We can think for a moment before invoking a method or after.
				// In this case we do both!
				// Before invocation:
				long start = System.currentTimeMillis ( ) ;
				// Invocation:
				Object retVal = method.invoke ( bean, args ) ; // note we use the "bean" from the outer method here
				// After invocation:
				long end = System.currentTimeMillis ( ) ;
				Profile methodProfile = getOverridingProfileInstructions ( method, instructions ) ;
				String logEntryName = getLogEntryName ( methodProfile, method ) ;
				log.info ( "Performance: {}: {} ms", logEntryName, ( end - start ) ) ;
				return retVal ;
			} } ) ;
	}

	private String getLogEntryName ( Profile methodProfile, Method method ) {
		if ( methodProfile.overrideName ( ).isEmpty ( ) ) {
			return method.getName ( ) ;
		} else {
			return methodProfile.overrideName ( ) ;
		}
	}

	private Profile getOverridingProfileInstructions ( Method method, Profile parent ) {
		Profile annotation = method.getAnnotation ( Profile.class ) ;
		return annotation == null ? parent : annotation ;
	}

	// This is a typical use case for these kinds of annotations.  You can
	// either have the annotation declared for the class as a whole or else
	// as on individual methods.  In either case you have to treat them
	// more or less the same in a BeanPostProcessor
	private Profile getProfileInstructions ( Object bean ) {
		Class <?> czz = bean.getClass ( ) ; // once again, no NPE worries
		Profile [ ] profileFound = czz.getAnnotationsByType ( Profile.class ) ;
		if ( profileFound != null && profileFound.length > 0 ) {
			return profileFound [ 0 ] ;
		}
		Method [ ] ms = czz.getDeclaredMethods ( ) ;
		for ( Method m : ms ) {
			Profile cand = m.getAnnotation ( Profile.class ) ;
			if ( cand != null ) {
				return cand ;
			}
		}
		return null ; // nothing found anywhere
	}

	private Class<?> getPreferredInterface ( Object bean ) {
		// no need to worry about NPE here
		return getPreferredInterfaceFromClass ( bean.getClass ( ) ) ;
	}
 
	private Class <?> getPreferredInterfaceFromClass ( Class <?> czz ) {
		if ( czz.equals ( Object.class ) ) {
			throw new IllegalStateException ( "No valid interface found. " ) ;
		}
		for ( Class <?> candidate : czz.getInterfaces ( ) ) {
			if ( candidate.getName ( ).startsWith ( "bill.example" ) ) {
				return candidate ;
			}
		}
		return getPreferredInterfaceFromClass ( czz.getSuperclass ( ) ) ;
	}	
}
