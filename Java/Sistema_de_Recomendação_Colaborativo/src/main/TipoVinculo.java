package main;


public enum TipoVinculo {
	RESTRITO("RESTRITO"),
	TOLERANTE("TOLERANTE"),
	ESPECIALIDADE("ESPECIALIDADE");
	
	private String tipoVinculoString;
	
	private TipoVinculo(String texto) {
		this.tipoVinculoString = texto;
	}
	
	public static TipoVinculo deString(String texto) {
		for (TipoVinculo tipo : TipoVinculo.values()) {
			if (tipo.tipoVinculoString.equalsIgnoreCase(texto)) {
				return tipo;
			}
		}
		return null;
	}
}
