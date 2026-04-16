
public enum Comandos {
	SAIR("SAIR"),
	DESCONHECIDO(""),
	GENERO("GENERO"),
	ANO_SUPERIOR("ANO_SUPERIOR"),
	MEDIA_AVALIACOES("MEDIA_AVALIACOES"),
	NAO_AVALIADOS("NAO_AVALIADOS"),
	DIRETORES_COMUNS("DIRETORES_COMUNS"),
	HATER("HATER");

	private String comandoString;

	private Comandos(String texto) {
		this.comandoString = texto;
	}

	public static Comandos deString(String texto) {
		for (Comandos c : Comandos.values()) {
			if (c.comandoString.equalsIgnoreCase(texto)) {
				return c;
			}
		}
		return DESCONHECIDO;
	}
}
