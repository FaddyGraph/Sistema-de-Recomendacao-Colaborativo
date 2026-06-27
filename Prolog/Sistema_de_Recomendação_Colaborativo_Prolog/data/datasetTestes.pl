% ==============================================================================
% formato: filme(ID, Titulo, Genero, Diretor, Ano, ClassificacaoMinima, Duracao)
% ==============================================================================
filme(1, 'Matrix', 'Ficcao', 'Wachowski', 1999, 14, 136).
filme(2, 'Inception', 'Ficcao', 'Nolan', 2010, 14, 148).
filme(3, 'Avatar', 'Ficcao', 'Cameron', 2009, 12, 162).
filme(4, 'Pulp Fiction', 'Crime', 'Tarantino', 1994, 18, 154).
filme(5, 'Interstellar', 'Ficcao', 'Nolan', 2014, 10, 169).
filme(6, 'O Poderoso Chefao', 'Drama', 'Coppola', 1972, 14, 175).
filme(7, 'Titanic', 'Romance', 'Cameron', 1997, 12, 194).
filme(8, 'Bastardos Inglorios', 'Guerra', 'Tarantino', 2009, 16, 153).
filme(9, 'O Cavaleiro das Trevas', 'Acao', 'Nolan', 2008, 12, 152).
filme(10, 'Os Bons Companheiros', 'Crime', 'Scorsese', 1990, 16, 146).

% ==============================================================================
% formato: usuario(ID, Nome, Idade)
% ==============================================================================
usuario(1, 'Alice', 25).
usuario(2, 'Bob', 30).
usuario(3, 'Charlie', 22).
usuario(4, 'Dani', 19).
usuario(5, 'Eduardo', 45).
usuario(6, 'Fernanda', 28).
usuario(7, 'Gabriel', 35).
usuario(8, 'Helena', 40).
usuario(9, 'Igor', 17).
usuario(10, 'Julia', 26).

% ==============================================================================
% formato: avaliacao(UsuarioID, FilmeID, Nota)
% ==============================================================================
% Avaliações da Alice (Gosta de Ficção)
avaliacao(1, 1, 5).
avaliacao(1, 2, 4).
avaliacao(1, 5, 5).

% Avaliações do Bob (Vizinho em potencial da Alice no filme 1 e 2)
avaliacao(2, 1, 5).
avaliacao(2, 2, 4).
avaliacao(2, 9, 5). % Avaliou o filme 9 que a Alice não viu

% Avaliações do Charlie (Gosta de filmes de Crime/Guerra)
avaliacao(3, 4, 5).
avaliacao(3, 8, 4).
avaliacao(3, 10, 5).

% Avaliações da Dani (Perfil "Hater" - Mais notas 1 do que notas 5)
avaliacao(4, 3, 1).
avaliacao(4, 6, 1).
avaliacao(4, 7, 5).

% Avaliações do Eduardo
avaliacao(5, 5, 4).
avaliacao(5, 6, 5).

% Avaliações da Fernanda
avaliacao(6, 2, 5).
avaliacao(6, 9, 4).

% Avaliações do Gabriel
avaliacao(7, 1, 4).
avaliacao(7, 4, 3).

% Avaliações da Helena
avaliacao(8, 6, 5).
avaliacao(8, 10, 4).

% Avaliações do Igor (Gosta de Ação)
avaliacao(9, 9, 5).

% Avaliações da Julia
avaliacao(10, 1, 4).
avaliacao(10, 5, 4).