package bill.example.annotations;

import lombok.Data;

@Data
public class Notification {

	private String message ;

	public Notification ( String s ) {
		this.message = s ;
	}
}
