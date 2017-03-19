package rest;

public class FestivAndesServices {
	
	/**
	 * Retorna el mensaje de una exception formateado.
	 * @param e Exception al que se le va a sacar el mensaje.
	 * @return El mensaje de la exception formateado.
	 */
	protected String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
}
