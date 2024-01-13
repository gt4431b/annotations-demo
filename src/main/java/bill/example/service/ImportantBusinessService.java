package bill.example.service;

/**
 * I will do this in a very old fashioned way to simplify the code,
 * but it is no longer necessary (or even a good idea) to have separate
 * interface and impl classes that effect the same signatures.  The
 * reason that Spring originally required this was exactly for the
 * purpose I am illustrating here on annotations being used to use
 * the decorator pattern.
 */
public interface ImportantBusinessService {

	public String goForthAndDoThus ( ) ;
}
