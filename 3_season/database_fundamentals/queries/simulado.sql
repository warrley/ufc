--s1
select e.nome, count(v.id_visita) as total_visitas from ecoparque e
left join visita v on v.id_ecoparque = e.id_ecoparque 
group by e.nome order by total_visitas desc

--s2
select * from ecoparque e
left join gestor g on g.id_ecoparque = e.id_ecoparque
left join infraestrutura i on i.id_ecoparque = e.id_ecoparque
where (g.id_gestor is null and i.id_infraestrutura is not null) or (g.id_gestor is not null and i.id_infraestrutura is null)

--s3
select ea.titulo, e.nome, count(ev.id_equipe) as total_equipes from eventoambiental ea
left join ecoparque e on e.id_ecoparque = ea.id_ecoparque
left join equipeevento ev on ev.id_evento = ea.id_evento
group by ea.titulo, e.nome order by total_equipes desc

--s4
select e.nome, e.bairro from ecoparque e
left join eventoambiental ea on ea.id_ecoparque = e.id_ecoparque
where ea.id_evento is null

--s5
select ve.nome, e.nome from visitante ve
inner join visita va on ve.id_visitante = va.id_visitante
inner join ecoparque e on va.id_ecoparque = e.id_ecoparque
where ve.id_visitante in (
	select vaa.id_visitante from visita vaa
	group by vaa.id_visitante
	having count(distinct vaa.id_ecoparque) > 3
)
group by ve.nome, e.nome

--s6
select * from ecoparque e
where e.id_ecoparque not in (
	select v.id_ecoparque from visita v
	where v.avaliacao is not null
)

--s7
select e.nome, count(distinct i.tipo) from ecoparque e
left join infraestrutura i on i.id_ecoparque = e.id_ecoparque 
group by e.nome

--s8 
select e.bairro, count(ea.id_evento) as total_eventos from ecoparque e
join eventoambiental ea on e.id_ecoparque = ea.id_ecoparque
group by e.bairro order by total_eventos desc

--s9
select em.nome, count(ev.id_evento) from equipemanutencao em
left join equipeevento ev on ev.id_equipe = em.id_equipe
group by em.nome

--s10
select ve.nome, count(distinct va.id_ecoparque) from visitante ve
join visita va on va.id_visitante = ve.id_visitante
where ve.id_visitante not in (
	select vaa.id_visitante from visita vaa
	where vaa.avaliacao is null
)
group by ve.nome having count(distinct va.id_ecoparque) >= 2
--or 
select ve.nome, count(distinct va.id_ecoparque) from visitante ve, visita va
where ve.id_visitante = va.id_visitante and ve.id_visitante not in (
	select vaa.id_visitante from visita vaa where vaa.avaliacao is null
) group by ve.nome having count(distinct va.id_ecoparque) > 1 
