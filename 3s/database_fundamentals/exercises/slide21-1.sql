--s1
select u.login, p.texto, count(p.id_post), count(c.id_post) from usuario u 
natural join post p
left join curtida c on c.id_post = p.id_post
group by u.login, p.texto
having count(c.id_post) > 3

--s3
select u.login from usuario u
left join perfil p on p.id_usuario = u.id_usuario
left join enderecousuario eu on eu.id_usuario = u.id_usuario
where p.id_usuario is null or eu.id_usuario is null

--s4
select u.login, u.data_cadastro, count(distinct seguidores.data_hora) as seguidores, count(distinct seguindo.data_hora) as seguindo from usuario u
left join seguir seguidores on seguidores.id_seguido = u.id_usuario
left join seguir seguindo on seguindo.id_seguidor = u.id_usuario
group by u.login, u.data_cadastro order by count(seguidores) desc

--s5
select u.login, p.texto, p.data_hora from usuario u
join post p on p.id_usuario = u.id_usuario
left join curtida c on c.id_post = p.id_post
where c.id_post is null
group by u.login, p.texto, p.data_hora

--s6
select u.login, ut.telefone from usuario u
natural join usuariotelefone ut
natural join post p
join curtida c on p.id_post = c.id_post
group by u.login, ut.telefone order by u.login, ut.telefone

--s7
select u.login from usuario u
left join mensagemprivada mp1 on mp1.id_remetente  = u.id_usuario
left join mensagemprivada mp2 on mp2.id_destinatario = u.id_usuario
where mp1.data_hora is null and mp2.data_hora is null

--s8
select u.login, p.texto, count(c.data_hora) as curtidas from usuario u
natural join post p
left join curtida c on c.id_post = p.id_post
group by u.login, p.texto order by curtidas desc

--s9
select u.login, count(c.data_hora) curtidas from usuario u
natural join curtida c
group by u.login having count(c.data_hora) >= all(
	select count(cc.data_hora) from curtida cc group by cc.id_usuario
)

--s10
select u.login, p.texto, c.data_hora from usuario u
natural join post p
left join curtida c on c.id_post = p.id_post

--s11
select u.login, count(p.id_post) qtd_post from post p
natural join usuario u
group by u.login order by qtd_post desc
