--q1
select l.titulo, l.ano_publicacao from livro l
where l.ano_publicacao > (
	select avg(ll.ano_publicacao) from livro ll
)

--q2
select l.titulo, l.ano_publicacao from livro l
where l.ano_publicacao >= all (
	select ll.ano_publicacao from livro ll
)

--q3
select l.titulo, l.ano_publicacao from livro l
where l.ano_publicacao <= all (
	select ll.ano_publicacao from livro ll
)

--q4
select u.id_usuario, u.nome from usuario u
where u.id_usuario in (
	select e.id_usuario from emprestimo e
)

--q5
select l.id_livro, l.titulo from livro l
where l.id_livro in (
	select e.id_livro from emprestimo e
)

--q6
select u.nome, (
	select count(*) from emprestimo e
	where u.id_usuario = e.id_usuario
) from usuario u

--q7
select e.id_funcionario, count (*) from emprestimo e
group by e.id_funcionario
having count(*) > (
	select avg(qtd) from (
		select count(*) as qtd from emprestimo ee
		group by ee.id_funcionario
	)
)

--q8
select u.id_usuario, u.nome from usuario u, emprestimo e
where u.id_usuario = e.id_usuario
group by u.id_usuario, u.nome having count(*) > (
	select avg(qtd) from (
		select count(*) as qtd from emprestimo ee
		group by ee.id_usuario
	)
)
--or
select e.id_usuario, (
	select u.nome from usuario u where u.id_usuario = e.id_usuario
) from emprestimo e
group by e.id_usuario having count(*) > (
	select avg(qtd) from (
		select count(*) as qtd from emprestimo ee
		group by ee.id_usuario
	)
)

--q9
select a.id_autor, a.nome from autor a
where a.id_autor in (
	select la.id_autor from livro_autor la, livro_categoria lc
	where la.id_livro = lc.id_livro
	group by la.id_autor having count (distinct lc.id_categoria) > 2
)
--or 
select a.id_autor, a.nome from autor a
where (
	select count(distinct lc.id_categoria) from livro_categoria lc, livro_autor la
	where la.id_autor = a.id_autor and la.id_livro = lc.id_livro
) > 2

--q10
select l.id_livro, l.titulo from livro l
where l.id_livro not in (
	select e.id_livro from emprestimo e
) and l.id_livro in (
	select r.id_livro from reserva r
) 

--q11 
select f.id_funcionario, f.nome from funcionario f
where f.id_funcionario in (
	select e.id_funcionario from emprestimo e 
	group by e.id_funcionario having count (*) >= all (
		select count(*) from emprestimo ee group by ee.id_funcionario
	)
)
--or 
select f.id_funcionario, f.nome from funcionario f, emprestimo e
where f.id_funcionario = e.id_funcionario group by f.id_funcionario
having count (*) >= all (
	select count(*) from emprestimo ee group by ee.id_funcionario
)

--q12
select u.id_usuario, u.nome from usuario u, endereco en
where u.id_usuario = en.id_usuario and en.cidade = 'Cidade 10' and u.id_usuario in (
	select e.id_usuario from emprestimo e
	where e.data_devolucao is not null and (e.data_devolucao-e.data_emprestimo) > 10
)

--q13
select c.id_categoria, c.nome from categoria c
where c.id_categoria not in (
	select lc.id_categoria from livro_categoria lc
	where lc.id_livro in (
		select e.id_livro from emprestimo e
		where e.id_usuario = 5
	)
)
--or
select c.id_categoria, c.nome from categoria c
where c.id_categoria not in (
	select lc.id_categoria from livro_categoria lc, emprestimo e
	where lc.id_livro = e.id_livro and e.id_usuario = 5
)

--q14
select l.id_livro, l.titulo from livro l, livro_autor la
where la.id_livro = l.id_livro
group by l.id_livro having count (*) >= all (
	select count(*) from livro_autor laa
	group by laa.id_livro
)

--q15
select l.id_livro, l.titulo from livro l, emprestimo e
where l.id_livro = e.id_livro group by l.id_livro
having count(distinct e.id_usuario) > 0.1 * (
	select count (*) from usuario
)

--q16
select a.id_autor, a.nome from autor a
where a.id_autor not in (
	select la.id_autor from livro_autor la
	where la.id_livro not in (
		select r.id_livro from reserva r
	)
)

--q17
select u.id_usuario, u.nome from usuario u
where not exists (
	select 1 from livro l where l.ano_publicacao = 2020
	and l.id_livro not in (
		select e.id_livro from emprestimo e
		where e.id_usuario = u.id_usuario
	)
)
