

import java.util.Objects;

public class Avaliacao {
	private long usuario;
	private long id_filme;
	private int nota;
	
	public Avaliacao(long usuario, long id_filme, int nota) {
		setUsuario(usuario);
		setId_filme(id_filme);
		setNota(nota);;
	}

	public long getUsuario() {
		return usuario;
	}
	
	public void setUsuario(long usuario) {
		if (usuario <= 0) {
			throw new IllegalArgumentException("ID de usuário inválido: deve ser um número positivo.");
		}
		this.usuario = usuario;
	}
	
	public long getId_filme() {
		return id_filme;
	}
	public void setId_filme(long id_filme) {
		if (id_filme <= 0) {
			throw new IllegalArgumentException("ID do filme inválido: deve ser um número positivo.");
		}
		this.id_filme = id_filme;
	}
	
	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
	    if (nota < 0 || nota > 5) {
	        throw new IllegalArgumentException("Nota inválida: a nota deve estar entre 0 e 5.");
	    }
	    this.nota = nota;
	}

	//hash & equals (estudar + sobre)
	@Override
	public int hashCode() {
		return Objects.hash(usuario, id_filme, nota);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Avaliacao other = (Avaliacao) obj;
		return id_filme == other.id_filme && nota == other.nota && usuario == other.usuario;
	}

	@Override
	public String toString() {
		return usuario + "," + id_filme + "," + nota;
	}
	
}
