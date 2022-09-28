insert into pessoa(nome, sobrenome, telefone, cpf, saldo_carteira, login, senha)
values("Admin", "Admin", "4755964152", "12345678", 1544, "admin", "admin");

insert into cinema(nome, id_administrador, caixa)
values("Cinema 01", 1, 1500);

insert into sala(nome, id_cinema)
values("Sala 01", 1);

insert into cadeira (codigo, id_sala, tipo_cadeira, fileira, ordem_fileira)
values("A01", 1, "Padrão", 1, 1);

insert into cadeira (codigo, id_sala, tipo_cadeira, fileira, ordem_fileira)
values("B01", 1, "Padrão", 2, 1);

insert into cadeira (codigo, id_sala, tipo_cadeira, fileira, ordem_fileira)
values("C01", 1, "Padrão", 3, 1);

insert into sessao(data_sessao, id_sala, id_filme, valor_inteira, valor_meia, tipo_sessao)
values('2022-10-10 19:30:00', 1, 3, 45, 25, "Dublado");

insert into sessao(data_sessao, id_sala, id_filme, valor_inteira, valor_meia, tipo_sessao)
values('2022-10-10 20:00:00', 1, 4, 45, 25, "Dublado");

insert into sessao(data_sessao, id_sala, id_filme, valor_inteira, valor_meia, tipo_sessao)
values('2022-10-11 19:30:00', 1, 3, 45, 25, "Legendado");

insert into sessao(data_sessao, id_sala, id_filme, valor_inteira, valor_meia, tipo_sessao)
values('2022-10-11 20:00:00', 1, 4, 45, 25, "Legendado");

insert into filme(nome, duracao, sinopse, diretor, cartaz)
values("Star Wars: O Despertar da Força", '1:29', "A queda de Darth Vader e do Império levou ao surgimento de uma nova força sombria: a Primeira Ordem. Eles procuram o jedi Luke Skywalker, desaparecido. A resistência tenta desesperadamente encontrá-lo antes para salvar a galáxia.
", "George Lucas",  "https://static.wikia.nocookie.net/ptstarwars/images/d/d5/P%C3%B4sterVII.jpg/revision/latest?cb=20151019202201");

insert into filme(nome, duracao, sinopse, diretor,  cartaz)
values("O Senhor dos Anéis: A Sociedade do Anel", '2:45', "Em uma terra fantástica e única, um hobbit recebe de presente de seu tio um anel mágico e maligno que precisa ser destruído antes que caia nas mãos do mal. Para isso, o hobbit Frodo tem um caminho árduo pela frente, onde encontra perigo, medo e seres bizarros. Ao seu lado para o cumprimento desta jornada, ele aos poucos pode contar com outros hobbits, um elfo, um anão, dois humanos e um mago, totalizando nove seres que formam a Sociedade do Anel.
", "Peter Jackson",  "https://th.bing.com/th/id/R.7836a26a8af8e62de36d84216aaca7f7?rik=r6E4sJ12Oz%2bBBA&riu=http%3a%2f%2fbr.web.img3.acsta.net%2fmedias%2fnmedia%2f18%2f92%2f91%2f32%2f20224832.jpg&ehk=RUj44Sh2FpGHLd0Hxh9fmLNrCU1kn%2bf0AxWDyl7Ljl4%3d&risl=&pid=ImgRaw&r=0");


SELECT id_pessoa FROM ingresso WHERE id_pessoa = 1;

SELECT * FROM (SELECT DISTINCT f.*, avg(a.rating) as avg_rat FROM filme f JOIN sessao s ON s.id_filme = f.id INNER JOIN filme_genero fg ON fg.id_filme = f.id INNER JOIN genero g ON g.id = fg.id_genero JOIN avaliacao a ON a.id_filme = f.id WHERE g.nome = :genero AND s.data_sessao >= :data group by f.id, f.nome) x where x.avg_rat >= :nota;