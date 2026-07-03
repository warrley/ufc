--q1
select * from cliente order by nome asci;

--q2
select * from produto order by valorunitario desc;

--q3
select * from servico where valor >= 100;

--q4
select * from funcionario where telefone is null;

--q5
select * from produto where pesoliquido is null;

--q6
select * from produto where valorunitario > 50 and pesoliquido is not null;

--q7
select * from cliente where endereco is not null and telefone is null;

--q8
select c.nome, a.nome
from cliente c, animal a, possuem p 
where c.codcliente = p.codcliente and a.codanimal = p.codanimal;

--q10
select f.nome, l.endereco
from funcionario f, loja l, alocado a
where f.codfuncionario = a.codfuncionario and l.codloja = a.codloja;

--q11
select p.marca, l.endereco
from produto p, loja l, oferece o
where p.codproduto = o.codproduto and l.codloja = o.codloja;

--q12
select s.tipo, c.nome, a.nome
from servico s, cliente c, animal a
where c.codcliente = s.codcliente
and a.codanimal = s.codanimal
and s.tipo = 'Consulta'

--q13
select c.nome, f.tipo, f.nome
from atendem a, cliente c, funcionario f
where c.codcliente = a.codcliente
and f.codfuncionario = a.codfuncionario
and f.tipo = 'Veterinário'
