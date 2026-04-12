package main;

import java.util.Objects;

public class Filme {

	private long id; //fornecido pelo usuario
	private String titulo;
	private String[] generos; 
	private String diretor;
	private int ano;
	
	//Construtor
	public Filme(long id, String titulo, String generos, String diretor, int ano){
		if (id < 0) {
	        throw new IllegalArgumentException("ID não pode ser negativo.");
	    }
		this.id = id;
		setTitulo(titulo);
		setGeneros(generos);
		setDiretor(diretor);
		setAno(ano);
	}

	//Getters e Setters
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		if (titulo == null || titulo.isBlank()) {
	        throw new IllegalArgumentException("Título é obrigatório.");
	    }
		this.titulo = titulo;
	}

	public String[] getGeneros() {
		return generos;
	}

	public void setGeneros(String generos) {
		if (generos == null || generos.isBlank()) {
	    	throw new IllegalArgumentException("Genero é obrigatório.");
	    }
		this.generos = generos.split(";");
	}

	public String getDiretor() {
		return diretor;
	}

	public void setDiretor(String diretor) {
		if (diretor == null || diretor.isBlank()) {
			throw new IllegalArgumentException("Diretor é obrigatório.");
		}
		this.diretor = diretor;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		if (ano < 1888) {
	        throw new IllegalArgumentException("Ano de lançamento inválido.");
	    }
		this.ano = ano;
	}

	public long getId() {
		return id;
	}

	//hash & equals (estudar + sobre)
	@Override
	public int hashCode() {
		return Objects.hash(id, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Filme other = (Filme) obj;
		return ano == other.ano && Objects.equals(diretor, other.diretor) && Objects.equals(generos, other.generos)
				&& id == other.id && Objects.equals(titulo, other.titulo);
	}

	// toString
	@Override
	public String toString() {
		String generosFormatados = String.join(";", generos);
		return id + "," + titulo + "," + generosFormatados + "," + diretor + ","
				+ ano;
	}	
	
}
