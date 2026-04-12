package sistemaFilm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SistemaPrincipal {
	// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	private ArrayList<Filme> filmes = new ArrayList<Filme>();
	private ArrayList<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();

	private HashMap<Long, Filme> mapFilmes = new HashMap<>();
	private HashMap<String, Filme> mapfilmeTitulo = new HashMap<>();
	private HashMap<String, ArrayList<Filme>> mapfilmesPorGenero = new HashMap<>();
	private HashMap<Long, Avaliacao> mapaAvali = new HashMap<>();
	private HashMap<Long, ArrayList<Avaliacao>> mapAvaliPorUsuario = new HashMap<>();

	public void mapearFilmesID() {
		for (Filme filme : filmes) {
			mapFilmes.put(filme.getId(), filme);
		}
	}

	public void mapearFilmTitulo() {
		for (Filme filme : filmes) {
			mapfilmeTitulo.put(filme.getTitulo(), filme);
		}
	}

	public void mapearFilmGeneros() {
		for (Filme f : filmes) {
			for (String genero : f.getGeneros()) {

				if (!mapfilmesPorGenero.containsKey(genero)) {
					mapfilmesPorGenero.put(genero, new ArrayList<>());
				}
				mapfilmesPorGenero.get(genero).add(f);
			}
		}
	}

	public void mapearAvaliID() {
		for (Avaliacao avaliacao : avaliacoes) {
			mapaAvali.put(avaliacao.getUsuario(), avaliacao);
		}
	}

	public void mapearAvaliUsuario() {
		for (Avaliacao a : avaliacoes) {

			if (!mapAvaliPorUsuario.containsKey(a.getUsuario())) {
				mapAvaliPorUsuario.put(a.getUsuario(), new ArrayList<>());
			}
			mapAvaliPorUsuario.get(a.getUsuario()).add(a);

		}
	}

	public void listarTodosFilmes() {
		System.out.println("___________________________________________");
		System.out.println("Filmes: ");
		if (filmes == null) {
			System.out.println("-- Nenhum filme Cadastrado --");
			return;
		}
		for (int i = 0; i < filmes.size(); i++) {
			System.out.println(filmes.get(i).toString());
		}
	}

	public void listFilmePorGenero(String generoAlvo) {
		ArrayList<Filme> listaFiltrada = mapfilmesPorGenero.get(generoAlvo);

		if (listaFiltrada != null) {
			System.out.println("___________________________________________");
			System.out.println("- Filmes de " + generoAlvo + " :");
			for (Filme f : listaFiltrada) {
				System.out.println(f.toString());
			}
		} else {
			System.out.println("-- Nenhum filme encontrado para o gênero: " + generoAlvo);
		}
	}

	public void BuscarFilmePorTitulo(String titulo) {
		Filme filmeEncontrado = mapfilmeTitulo.get(titulo);

		if (filmeEncontrado != null) {
			System.out.println("___________________________________________");
			System.out.println("- Filme: " + filmeEncontrado + " :");
			System.out.println(filmeEncontrado.toString());

		} else {
			System.out.println("-- Nenhum filme com titulo : " + titulo);
		}
	}

	public void listarTodasAvaliacoes() {
		System.out.println("___________________________________________");
		System.out.println("Avaliacoes: ");
		if (avaliacoes == null) {
			System.out.println("-- Nenhuma avaliação Cadastrada ");
		}
		for (int i = 0; i < avaliacoes.size(); i++) {
			System.out.println(avaliacoes.get(i).toString());
		}
	}

	public void listarAvaliacoesUsuario(Long usuario) {
		ArrayList<Avaliacao> avaliacoesUsua = mapAvaliPorUsuario.get(usuario);

		if (avaliacoesUsua != null) {
			System.out.println("___________________________________________");
			System.out.println("- Avaliações do usuário: " + usuario + " :");
			for (Avaliacao avaliacao : avaliacoesUsua)
				System.out.println(avaliacao.toString());

		} else {
			System.out.println("-- Nenhuma avaliações do usuário : " + usuario);
		}
	}

	public ArrayList<String> LerDadosPArquivo(String caminhoArqv) {
		try (BufferedReader br = new BufferedReader(new FileReader(caminhoArqv))) {
			String linha;
			ArrayList<String> conteudo = new ArrayList<String>();

			// Lendo linha por linha até o fim do arquivo (null)
			while ((linha = br.readLine()) != null) {

				conteudo.add(linha);
			}
			if (conteudo != null && conteudo.get(0).equals("id,titulo,generos,diretor,ano")) {
				for (int i = 1; i < conteudo.size(); i++) {
					String[] itens = conteudo.get(i).split(",");

					Filme novoFilme = new Filme(Long.parseLong(itens[0]), itens[1], itens[2], itens[3],
							Integer.parseInt(itens[4]));
					filmes.add(novoFilme);
				}
				mapearFilmesID();
				mapearFilmGeneros();
				mapearFilmTitulo();
			} else if (conteudo != null && conteudo.get(0).equals("usuario,id_filme,nota")) {
				for (int i = 1; i < conteudo.size(); i++) {
					String[] itens = conteudo.get(i).split(",");

					Avaliacao novaAvaliacao = new Avaliacao(Long.parseLong(itens[0]), Long.parseLong(itens[1]),
							Integer.parseInt(itens[2]));
					avaliacoes.add(novaAvaliacao);
				}
				mapearAvaliID();
				mapearAvaliUsuario();
			} else {
				System.out.println("Erro: Cabeçalho inválido");
				return null;
			}

			return conteudo;
		} catch (IOException e) {
			System.err.println("Erro ao ler o arquivo: " + e.getMessage());
		}
		return null;

	}
}
