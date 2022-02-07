
CREATE OR REPLACE VIEW VDiasSemanaEvento
AS
	select id_evento, 1 as dia_semana, 'LUNES' as dia
	from eventos
	where control_lunes = true-- AND codigo_estado = 'A'
	union
	select id_evento, 2, 'MARTES'
	from eventos
	where control_martes = true-- AND codigo_estado = 'A'
	union
	select id_evento, 3, 'MIERCOLES'
	from eventos
	where control_miercoles = true-- AND codigo_estado = 'A'
	union
	select id_evento, 4, 'JUEVES'
	from eventos
	where control_jueves = true --AND codigo_estado = 'A'
	union
	select id_evento, 5, 'VIERNES'
	from eventos
	where control_viernes = true --AND codigo_estado = 'A'
	union
	select id_evento, 6, 'SABADO'
	from eventos
	where control_sabado = true --AND codigo_estado = 'A'
	union
	select id_evento, 7, 'DOMINGO'
	from eventos
	where control_domingo = true --AND codigo_estado = 'A'
	order by 2, 1


-- SELECT * FROM VDiasSemanaEvento
-- where id_evento = 2