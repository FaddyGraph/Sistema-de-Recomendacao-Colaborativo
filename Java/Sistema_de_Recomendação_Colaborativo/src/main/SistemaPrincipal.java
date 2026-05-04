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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SistemaPrincipal {
	// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	private ArrayList<Filme> filmes = new ArrayList<Filme>();
	private ArrayList<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();

	// HashMaps
	private HashMap<Long, Filme> mapFilmes = new HashMap<>();
	private HashMap<String, ArrayList<Filme>> mapfilmesPorGenero = new HashMap<>();

	private HashMap<Long, ArrayList<Avaliacao>> mapAvaliPorUsuario = new HashMap<>();

	// Enums
	Comandos comando;

	// Constantes para formatação

	public void main() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//lerDadosPArquivo("", ""); //Ex: "src/dados/filmes.csv" , "src/dados/avaliacoes.csv"
		lerDadosPSistemIn(br);

		String linha;

		try {
			while ((linha = br.readLine()) != null) {
	            linha = linha.trim();
	            if (linha.isEmpty()) continue;
	            
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
			listaFiltrada.sort(Comparator.comparing(Filme::getTitulo)); //ardenar por titulo
			for (Filme f : listaFiltrada) {
				System.out.println("- "+f.getTitulo());
			}
		}
	}

	public void listarFilmesAnoAcima(int anoCorte) {
		System.out.println("--- ANO_SUPERIOR " + anoCorte + " ---");

		filmes.stream().filter(f -> f.getAno() > anoCorte) // compara cada filme com o ano de corte
				.sorted((Comparator.comparing(Filme::getAno, Comparator.reverseOrder()))// ordenar do mior para o menor
						.thenComparing(Filme::getTitulo)) // o que sobrou é ordenado por titulo
				.forEach(f -> System.out.println("- "+f.getTitulo())); // printa o titulo
	}

//	public void BuscarFilmePorTitulo(String titulo) {
//		Filme filmeEncontrado = mapfilmeTitulo.get(titulo);
//
//		if (filmeEncontrado != null) {
//			System.out.println("___________________________________________");
//			System.out.println("- Filme: " + filmeEncontrado + " :");
//			System.out.println(filmeEncontrado.toString());
//
//		} else {
//			System.out.println("-- Nenhum filme com titulo : " + titulo);
//		}
//	}

//	public void listarTodasAvaliacoes() {
//		System.out.println("___________________________________________");
//		System.out.println("Avaliacoes: ");
//		if (avaliacoes == null) {
//			System.out.println("-- Nenhuma avaliação Cadastrada ");
//		}
//		for (int i = 0; i < avaliacoes.size(); i++) {
//			System.out.println(avaliacoes.get(i).toString());
//		}
//	}

	public void mediaAvaliacoesUsuario(Long usuario) {
		ArrayList<Avaliacao> avaliacoesUsua = mapAvaliPorUsuario.get(usuario);

		System.out.println("--- MEDIA_AVALIACOES " + usuario + " ---");
		if (avaliacoesUsua != null) {
			
			int numAvali =0 ;
			double somaAvali =0 ;
			for (Avaliacao avaliacao : avaliacoesUsua) {
				numAvali++;
				somaAvali+= avaliacao.getNota();
			}
			System.out.printf(Locale.US,"%.2f\n",(somaAvali/numAvali));
		}else {
			System.out.println("0.00");			
		}
	}
	
	public void listarFilmesNAvaliados(Long usuario) {
		System.out.println("--- NAO_AVALIADOS "+ usuario+" ---");
		Set<Long> idsAvaliados = avaliacoes.stream()
		        .filter(a -> a.getUsuario() == usuario) // Filtra só as notas desse user
		        .map(Avaliacao::getId_filme)            
		        .collect(Collectors.toSet());
		
	    filmes.stream()
	    .filter(f -> !idsAvaliados.contains(f.getId())) //se nao tiver em idsAvaliados
	    .sorted(Comparator.comparing(Filme::getTitulo))
	    .forEach(f -> System.out.println("- "+ f.getTitulo()));
		
	}
	
	public void ListarFilmesDirectCo(Long usuario1,Long usuario2) {
		
		System.out.println("--- DIRETORES_COMUNS "+usuario1+" "+usuario2+ " ---");
		
	    Set<Long> vistosUsu1 = avaliacoes.stream()
	            .filter(a -> a.getUsuario() == usuario1)
	            .map(Avaliacao::getId_filme)
	            .collect(Collectors.toSet());

	    Set<String> diretoresVistosUsu1 = filmes.stream()
	            .filter(f -> vistosUsu1.contains(f.getId()))
	            .map(Filme::getDiretor)
	            .collect(Collectors.toSet()); // O Set já remove duplicatas aqui

	    Set<Long> vistosUsu2 = avaliacoes.stream()
	            .filter(a -> a.getUsuario() == usuario2)
	            .map(Avaliacao::getId_filme)
	            .collect(Collectors.toSet());

	    
	    filmes.stream()
	        .filter(f -> vistosUsu2.contains(f.getId()))      // Filmes que usuario 2  viu
	        .map(Filme::getDiretor)                            // Transforma o pesquisa de Filmes para Diretor 
	        .filter(diretoresVistosUsu1::contains)          // mantem só se o usuario 1 tambem viu esse diretor
	        .distinct()                                        // remove nomes repetidos no fluxo
	        .sorted()                                          
	        .forEach(diretor -> System.out.println("- " + diretor));
	}
	
	public void usuarioHATER(long usuario) {
		System.out.println("--- HATER "+ usuario +" ---");
		ArrayList<Integer> notas = new ArrayList<Integer>();
				avaliacoes.stream()
		        .filter(a -> a.getUsuario() == usuario) // Filtra só as notas desse user           
		        .forEach(a -> notas.add(a.getNota()));
				int resposta = 0;
		for(int n : notas) {
			if (n==5) {
				resposta++;
			}else if (n<=1) {
				resposta--;
			}
		}
		if (resposta>=0) {
			System.out.println("Nao");
		}else {
			System.out.println("Sim");
		}
	}

	public void lerDadosPArquivo(String filmesCsv, String avaliacoesCsv) {
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

	public void lerDadosPSistemIn(BufferedReader br) {
		try {
			String tam[] = (br.readLine().split(" "));

			carregarDados(lerDadosPSistemIn(Integer.parseInt(tam[0]), br),
					lerDadosPSistemIn(Integer.parseInt(tam[1]), br));

		} catch (IOException e) {
			System.err.println("Erro ao ler dados: " + e.getMessage());
		}

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
			//mapearFilmTitulo();
		}
		if (conteudoAvaliacao != null) {
			for (int i = 0; i < conteudoAvaliacao.size(); i++) {
				if (i == 0 && conteudoAvaliacao.get(0).equals("usuario,id_filme,nota")) {
					continue;
				}
				String[] itens = conteudoAvaliacao.get(i).split(",");

				Avaliacao novaAvaliacao = new Avaliacao(Long.parseLong(itens[0]), Long.parseLong(itens[1]),
						Integer.parseInt(itens[2]));
				
				avaliacoes.add(novaAvaliacao);
			}
			mapearAvaliUsuario();
		}

	}
}
