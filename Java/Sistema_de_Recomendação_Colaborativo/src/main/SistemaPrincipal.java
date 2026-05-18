package main;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SistemaPrincipal {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	private ArrayList<Filme> filmes = new ArrayList<Filme>();
	private ArrayList<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();

	// HashMaps
	private HashMap<Long, Filme> mapFilmes = new HashMap<>();
	private HashMap<String, ArrayList<Filme>> mapfilmesPorGenero = new HashMap<>();
	private HashMap<Long, ArrayList<Avaliacao>> mapAvaliPorUsuario = new HashMap<>();
	private HashMap<Long, ArrayList<Avaliacao>> mapAvaliPorFilme = new HashMap<>();

	// Enums
	Comandos comando;

	public void main() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		lerDados("D://CEFET/CLP/Sistema-de-Recomendacao-Colaborativo/data/filmes.csv",
				 "D:/CEFET/CLP/Sistema-de-Recomendacao-Colaborativo/data/avaliacoes.csv" ); //Ex: "src/dados/filmes.csv" , "src/dados/avaliacoes.csv"
		//lerDados(br);

		String linha;

		try {
			while ((linha = br.readLine()) != null) {
				linha = linha.trim(); //O trim() remove os espaços em branco do início e do fim de uma String.
				if (linha.isEmpty())
					continue;

				String comandoString[] = linha.split(" ");

				comando = Comandos.deString(comandoString[0].toUpperCase());

				switch (comando) {
				case GENERO: {
					if (comandoString.length > 1) {
						listarFilmePorGenero(comandoString[1]);
					}
					break;
				}
				case ANO_SUPERIOR: {
					if (comandoString.length > 1) {
						listarFilmesAnoAcima(Integer.parseInt(comandoString[1]));
					}
					break;
				}
				case MEDIA_AVALIACOES: {
					if (comandoString.length > 1) {
						mediaAvaliacoesUsuario(Long.parseLong(comandoString[1]));
					}
					break;
				}
				case NAO_AVALIADOS: {
					if (comandoString.length > 1) {
						listarFilmesNAvaliados(Long.parseLong(comandoString[1]));
					}
					break;
				}
				case HATER: {
					if (comandoString.length > 1) {
						usuarioHATER(Long.parseLong(comandoString[1]));
					}
					break;
				}
				case DIRETORES_COMUNS: {
					if (comandoString.length > 2) {
						ListarFilmesDirectCo(Long.parseLong(comandoString[1]), Long.parseLong(comandoString[2]));
					}
					break;
				}
				case RECOMENDAR: {
					if (comandoString.length > 2) {
						recomendar(Long.parseLong(comandoString[1]), (comandoString[2]));
					}
					break;
				}
				case SAIR: {
					return;
				}
				default:
					System.out.println("Comando desconhecido.");
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Erro na leitura: " + e.getMessage());
		}
	}

	public void mapearFilmesID() {
		for (Filme filme : filmes) {
			mapFilmes.put(filme.getId(), filme);
		}
	}

//	public void mapearFilmTitulo() {
//		for (Filme filme : filmes) {
//			mapfilmeTitulo.put(filme.getTitulo(), filme);
//		}
//	}

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

	public void mapearAvaliUsuario() {
		for (Avaliacao a : avaliacoes) {

			if (!mapAvaliPorUsuario.containsKey(a.getUsuario())) {
				mapAvaliPorUsuario.put(a.getUsuario(), new ArrayList<>());
			}
			mapAvaliPorUsuario.get(a.getUsuario()).add(a);

		}
	}
	public void mapearAvaliFilme() {
		for (Avaliacao a : avaliacoes) {
			
			if (!mapAvaliPorFilme.containsKey(a.getId_filme())) {
				mapAvaliPorFilme.put(a.getId_filme(), new ArrayList<>());
			}
			mapAvaliPorFilme.get(a.getId_filme()).add(a);
			
		}
	}

//	public void listarTodosFilmes() {
//		System.out.println("___________________________________________");
//		System.out.println("Filmes: ");
//		if (filmes == null) {
//			System.out.println("-- Nenhum filme Cadastrado --");
//			return;
//		}
//		for (int i = 0; i < filmes.size(); i++) {
//			System.out.println(filmes.get(i).toString());
//		}
//	}

	public void listarFilmePorGenero(String generoAlvo) {
		ArrayList<Filme> listaFiltrada = mapfilmesPorGenero.get(generoAlvo);

		System.out.println("--- GENERO " + generoAlvo + " ---");
		if (listaFiltrada != null) {
			listaFiltrada.sort(Comparator.comparing(Filme::getTitulo)); // ardenar por titulo
			for (Filme f : listaFiltrada) {
				System.out.println("- " + f.getTitulo());
			}
		}
	}

	public void listarFilmesAnoAcima(int anoCorte) {
		System.out.println("--- ANO_SUPERIOR " + anoCorte + " ---");

		filmes.stream().filter(f -> f.getAno() > anoCorte) // compara cada filme com o ano de corte
				.sorted((Comparator.comparing(Filme::getAno, Comparator.reverseOrder()))// ordenar do maior para o menor
						.thenComparing(Filme::getTitulo)) // o que sobrou é ordenado por titulo
				.forEach(f -> System.out.println("- " + f.getTitulo())); // printa o titulo
	}

	public void mediaAvaliacoesUsuario(Long usuario) {
		ArrayList<Avaliacao> avaliacoesUsua = mapAvaliPorUsuario.get(usuario);

		System.out.println("--- MEDIA_AVALIACOES " + usuario + " ---");
		if (avaliacoesUsua != null) {

			long numAvali = 0;
			double somaAvali = 0;

			for (Avaliacao avaliacao : avaliacoesUsua) {
				numAvali++;
				somaAvali += avaliacao.getNota();
			}
			System.out.printf(Locale.US, "%.2f\n", (somaAvali / numAvali));
		} else {
			System.out.println("0.00");
		}
	}

	public void listarFilmesNAvaliados(Long usuario) {
		System.out.println("--- NAO_AVALIADOS " + usuario + " ---");
		Set<Long> idsAvaliados = avaliacoes.stream().filter(a -> a.getUsuario() == usuario) // Filtra só as notas desse
																							// user
				.map(Avaliacao::getId_filme).collect(Collectors.toSet());

		filmes.stream().filter(f -> !idsAvaliados.contains(f.getId())) // se nao tiver em idsAvaliados
				.sorted(Comparator.comparing(Filme::getTitulo)).forEach(f -> System.out.println("- " + f.getTitulo()));

	}

	public void ListarFilmesDirectCo(Long usuario1, Long usuario2) {
	    System.out.println("--- DIRETORES_COMUNS " + usuario1 + " " + usuario2 + " ---");

	    // getOrDefault retorna uma lista vazia se o usuário não existir, evitando NullPointerException.
	    Set<String> diretoresUsu1 = mapAvaliPorUsuario.getOrDefault(usuario1, new ArrayList<>())
	        .stream()
	        .map(a -> mapFilmes.get(a.getId_filme())) // troca cada avaliação pelo seu Filme correspondente (busca O(1) no mapa)
	        .filter(Objects::nonNull) // descarta avaliações cujo filme não foi encontrado no mapa
	        .map(Filme::getDiretor)                    // extrai só o nome do diretor de cada filme
	        .collect(Collectors.toSet());              // coleta em um Set, que já elimina nomes repetidos automaticamente

	    mapAvaliPorUsuario.getOrDefault(usuario2, new ArrayList<>())
	        .stream()
	        .map(a -> mapFilmes.get(a.getId_filme()))
	        .filter(Objects::nonNull)
	        .map(Filme::getDiretor)
	        .filter(diretoresUsu1::contains) 			// mantém só se o usuário1 também avaliou esse diretor
	        .distinct()                                // remove duplicatas que possam ter sobrado no fluxo
	        .sorted()                                  // ordena alfabeticamente (A-Z)
	        .forEach(d -> System.out.println("- " + d)); // imprime cada diretor
	}

	public void usuarioHATER(long usuario) {
		System.out.println("--- HATER " + usuario + " ---");

		ArrayList<Avaliacao> avaliacoesUsua = mapAvaliPorUsuario.get(usuario);

		if (avaliacoesUsua == null) {
			System.out.println("Nao");
			return;
		}

		long notas1 = avaliacoesUsua.stream().filter(a -> a.getNota() == 1).count();

		long notas5 = avaliacoesUsua.stream().filter(a -> a.getNota() == 5).count();

		System.out.println(notas1 > notas5 ? "Sim" : "Nao");
	}
	
	public void recomendar(long usuario , String tipoVinculo ) {
		
		Set<Filme> filmesRecomendados;
		System.out.println("--- RECOMENDAR " + usuario +" "+tipoVinculo+ " ---");
//		1 - Média das Notas Fornecidas Entre os Vizinhos Elegíveis (Ordem Decrescente de relevância).
//		2 - Ano de Publicação (Mais recentes exibidos primeiro).
//		3 - Alfabético pelo Título da Película.
		
		filmesRecomendados = recomendarRestrito(usuario);
		
		// ### Calcular a média das notas dos vizinhos para cada filme recomendado
		filmesRecomendados.stream()
	    .sorted(
	        Comparator.comparing(Filme::getAno).reversed() // ### trocar a primeira comparação pela media
	            //.thenComparing(/* critério 2 */)
	            .thenComparing(Filme::getTitulo)
	    )
	    .forEach(f -> System.out.println("- " + f.getTitulo()));
	}
	private Set<Filme> recomendarRestrito(long usuario) {
		Set<Long> idFilmesUsuarioAlvo = mapAvaliPorUsuario.getOrDefault(usuario, new ArrayList<>())
				.stream()
				.filter(a -> a.getNota()>=4) // fitra por nota maior ou igual a 4
				.map(Avaliacao::getId_filme) //extrai so o id de cada filme 
				.collect(Collectors.toSet()); // coleta em um Set, que elimina repetição
		
		Set<Long> vizinhos = idFilmesUsuarioAlvo.stream() // itera sobre cada id de filme
				.flatMap(idFilme -> mapAvaliPorFilme.getOrDefault(idFilme, new ArrayList<>()).stream()) // para cada filme, busca todas as avaliações desse filme e "achata" em um único fluxo
				.filter(a -> a.getNota()>=4)
				.map(Avaliacao::getUsuario)
				.filter(id -> !id.equals(usuario)) // remove o próprio usuário alvo
				.collect(Collectors.toSet());
						
		
		// Para cada vizinho, buscar todos os filmes que ele avaliou, e filtra só os que o usuário alvo ainda não assistiu
		
		Set<Filme> filmesRecomendados = vizinhos.stream() 
		    .flatMap(idVizinho -> mapAvaliPorUsuario       // para cada vizinho, busca suas avaliações
		        .getOrDefault(idVizinho, new ArrayList<>())// retorna lista vazia se não tiver avaliações
		        .stream())                                 // transforma todas as listas em um único fluxo
		    .map(a -> mapFilmes.get(a.getId_filme()))      // troca cada avaliação pelo objeto Filme correspondente
		    .filter(Objects::nonNull)                      // descarta casos onde o filme não foi encontrado no mapa
		    .filter(f -> !idFilmesUsuarioAlvo.contains(f.getId())) // remove filmes que o alvo já assistiu
		    .collect(Collectors.toSet());                  
		
		return filmesRecomendados;
	}

	public void lerDados(BufferedReader br) {
		try {
			String tam[] = (br.readLine().split(" "));
			
			carregarDados(lerDadosPSistemIn(Integer.parseInt(tam[0]), br),
					lerDadosPSistemIn(Integer.parseInt(tam[1]), br));
			
		} catch (IOException e) {
			System.err.println("Erro ao ler dados: " + e.getMessage());
		}
		
	}
	public void lerDados(String filmesCsv, String avaliacoesCsv) {
		carregarDados(lerDadosPArquivo(filmesCsv), lerDadosPArquivo(avaliacoesCsv));
	}
	

	private ArrayList<String> lerDadosPArquivo(String caminhoArqv) {
		try (BufferedReader br = new BufferedReader(new FileReader(caminhoArqv))) {

//			return br.lines()
//					.collect(Collectors.toCollection(ArrayList::new));
			return new ArrayList<>(Files.readAllLines(Paths.get(caminhoArqv)));

		} catch (IOException e) {
			System.err.println("Erro ao ler o arquivo: " + e.getMessage());
		}
		return null;
	}

	

	public ArrayList<String> lerDadosPSistemIn(int n, BufferedReader br) {
		return Stream.generate(() -> {
			try {
				return br.readLine();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}).limit(n) // A "seta" acima gera as linhas, e o limit(n) diz quantas queremos
				.collect(Collectors.toCollection(ArrayList::new)); // Transforma em ArrayList
	}

	public void carregarDados(ArrayList<String> conteudoFilme, ArrayList<String> conteudoAvaliacao) {

		if (conteudoFilme != null) {
			for (int i = 0; i < conteudoFilme.size(); i++) {
				if (i == 0 && conteudoFilme.get(0).equals("id,titulo,generos,diretor,ano")) {
					continue;
				}
				String[] itens = conteudoFilme.get(i).split(",");

				Filme novoFilme = new Filme(Long.parseLong(itens[0]), itens[1], itens[2], itens[3],
						Integer.parseInt(itens[4]));

				filmes.add(novoFilme);
			}
			mapearFilmesID();
			mapearFilmGeneros();
			// mapearFilmTitulo();
		}
		if (conteudoAvaliacao != null) {
			
		    HashMap<String, Avaliacao> mapaUnico = new HashMap<>();
		    
			for (int i = 0; i < conteudoAvaliacao.size(); i++) {
				if (i == 0 && conteudoAvaliacao.get(0).equals("usuario,id_filme,nota")) {
					continue;
				}
				String[] itens = conteudoAvaliacao.get(i).split(",");

				Avaliacao novaAvaliacao = new Avaliacao(Long.parseLong(itens[0]), 
														Long.parseLong(itens[1]),
														Double.parseDouble(itens[2]));

				String chaveString = itens[0] +"_"+itens[1];
				mapaUnico.put(chaveString, novaAvaliacao); // sobrescreve automaticamente se já existir
			}
			
			avaliacoes.addAll(mapaUnico.values()); // Passa o mapa para o vetor
			mapearAvaliUsuario();
			mapearAvaliFilme();
		}

	}
}
