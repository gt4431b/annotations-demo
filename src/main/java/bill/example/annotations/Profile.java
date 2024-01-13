package bill.example.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME) // Super important.  Java won't find your annotations unless they're bound
					// at Runtime.  The only place this won't matter is if you're doing
					// injections at the compiler phase in which case you should use CLASS
					// value or just leave it default.  I've never seen anyone use the
					// SOURCE value but could be used for effective injections to
					// Javadoc
@Target({ TYPE, METHOD }) // For what you're using, either of these will be most common
@Inherited // I'm not doing any inherited scenarios in this example because they get complicated
			// but in a real world situation you'd probably want to allow the annotation
			// to be declared in superclasses or even interfaces.
public @interface Profile {
	String overrideName ( ) default "" ; // just to illustrate flexibility
}
