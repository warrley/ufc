--q0
select f.pnome, d.dnome from funcionario f
left join departamento d on d.dnumero = f.dnr 

--q2
select d.dnome, count(*) from departamento d
left join funcionario f on d.dnumero = f.dnr
group by d.dnumero

--q3
select * from departamento d
left join funcionario f on d.dnumero = f.dnr
where f.cpf is null

--q4
select * from funcionario f1
join departamento d on f1.dnr = d.dnumero
where f1.salario > (
	select avg(f2.salario) from funcionario f2
	where f2.dnr = d.dnumero
)

--q5
select f.pnome, d.dnome from funcionario f
cross join departamento d
