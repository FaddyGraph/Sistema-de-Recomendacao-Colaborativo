%___ Carregar fatos:

%:- [data/dataset].
:- [data/datasetTestes]. % mais facil de ver funcionando

%___ Regras 

%_____ Fase 1
filmes_por_genero(Genero, Lista)  % "filmes_por_genero(Genero, Lista)" nome do predicado com os dois argumentos 'entrada'(Genero) e 'saida'(Lista). 
:-                                % ":-" operador 'se' o que ta na esquerda é fato se o que ta na direita tiver sucesso

    findall(Titulo,                         % enconte tudo e pegue o titulo
    filme(_, Titulo, Genero, _, _, _, _),   % mostra o que e onde, onde comparar e onde pegar (nesse caso, em filme, pegar o Titulo e comparar o Genero)
    Lista).                                 % armazene na variavel Lista

filmes_de_ano_superior(AnoAlvo, Lista) :- findall(Titulo, (filme(_, Titulo, _, _, Ano, _, _),
    Ano > AnoAlvo), % Filtro
    Lista).

notas_do_usuario(UsuarioID, Lista) :-
    findall(Nota, avaliacao(UsuarioID, _, Nota), Lista).

%_____ Fase 2

media_avaliacoes(UsuarioID, Media) :-
    findall(Nota, avaliacao(UsuarioID, _,Nota), Notas),
    sum_list(Notas, Soma),
    length(Notas, Qtd),

    Media is (truncate((Soma / Qtd) * 100) / 100).

usuario_hater(UsuarioID) :-
    findall(Nota, avaliacao(UsuarioID, _,1), NotasUm),
    length(NotasUm, QtdUm),
    
    findall(Nota, avaliacao(UsuarioID, _,5), NotasDois),
    length(NotasDois, QtdDois),

    QtdUm > QtdDois.

filmes_nao_avaliados(UsuarioID, Filmes) :-
    findall(FilmeID, avaliacao(UsuarioID, FilmeID, _), IdFilmesJaVistos),
    findall(ID, (filme(ID, _, _, _, _, _, _), \+
    member(ID, IdFilmesJaVistos)), Filmes).