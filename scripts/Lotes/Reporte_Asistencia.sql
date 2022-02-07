select i::date
from generate_series(date '2012-01-01'-CURRENT_DATE,date '2012-12-31'-CURRENT_DATE, '1 DAY'::interval) i

SELECT * FROM generate_series('2008-03-01 00:00'::timestamp,
                              '2008-03-04 12:00', '10 hours');

INSERT INTO personas_eventos_asistencia(
            id_asistencia, id_evento, id_persona, fecha_registro, hora_registro, 
            tipo_asistencia, tipo_ingreso, codigo_estado)
    VALUES (default, 2, 2, '27/06/2016', '19:51:15', 'P', 'E', 'U');

select * from personas_eventos_asistencia
where id_persona = 2 --and id_evento = 2 and codigo_estado = 'U'
order by fecha_registro 

select * from eventos_personas
select * from personas_eventos_permisos

SELECT *
FROM personas_eventos_asistencia pea
RIGHT JOIN
(
	SELECT *
	FROM
	(
		SELECT CURRENT_DATE+i as fecha, EXTRACT(ISODOW FROM  CURRENT_DATE+i) as dia_semana
		FROM generate_series(date '01/01/2016'-CURRENT_DATE,date '31/12/2016'-CURRENT_DATE) i
	) TA
	LEFT JOIN 
	(	
		select id_evento, 1 as dia_semana
		from eventos
		where control_lunes = true
		union
		select id_evento, 2
		from eventos
		where control_martes = true
		union
		select id_evento, 3
		from eventos
		where control_miercoles = true
		union
		select id_evento, 4
		from eventos
		where control_jueves = true
		union
		select id_evento, 5
		from eventos
		where control_viernes = true
		union
		select id_evento, 6
		from eventos
		where control_sabado = true
		union
		select id_evento, 7
		from eventos
		where control_domingo = true
		order by 2, 1
	) Dias_Semana
	on TA.dia_semana  = Dias_Semana.dia_semana

) dias_asistencia
on dias_asistencia.id_evento = pea.id_evento
and dias_asistencia.fecha = pea.fecha_registro
and pea.codigo_estado = 'U'
where dias_asistencia.id_evento = 2
and dias_asistencia.fecha <= current_date::date
order by dias_asistencia.fecha

select *
from generate_series(1,7)

SELECT * FROM EVENTOS
select * from personas_eventos_permisos
select min(fecha_inicio), max(fecha_fin) from eventos
where codigo_estado = 'A'

select * 
from personas_eventos_permisos



select 1 as dia_semana
from eventos
where id_evento = 1 and control_lunes = true
union
select 2
from eventos
where id_evento = 1 and control_martes = true
union
select 3
from eventos
where id_evento = 1 and control_miercoles = true
union
select 4
from eventos
where id_evento = 1 and control_jueves = true
union
select 5
from eventos
where id_evento = 1 and control_viernes = true
union
select 6
from eventos
where id_evento = 1 and control_sabado = true
union
select 7
from eventos
where id_evento = 1 and control_domingo = true
order by 1





select id_evento, 1 as dia_semana
from eventos
where control_lunes = true
union
select id_evento, 2
from eventos
where control_martes = true
union
select id_evento, 3
from eventos
where control_miercoles = true
union
select id_evento, 4
from eventos
where control_jueves = true
union
select id_evento, 5
from eventos
where control_viernes = true
union
select id_evento, 6
from eventos
where control_sabado = true
union
select id_evento, 7
from eventos
where control_domingo = true
order by 2, 1
