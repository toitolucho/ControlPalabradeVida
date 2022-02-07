select *
from eventos
where current_date at time zone ('BOT') between fecha_inicio and fecha_fin
and current_time at time zone ('BOT') between hora_inicio_antes and hora_fin_retraso
and codigo_estado = 'A'
and (select extract (year from current_date) ) = gestion
and(
( control_lunes = true  and  (SELECT EXTRACT(DOW FROM current_date)) = 1)
or ( control_martes = true  and  (SELECT EXTRACT(DOW FROM current_date)) = 2)
or ( control_miercoles = true  and  (SELECT EXTRACT(DOW FROM current_date)) = 3)
or ( control_jueves = true  and  (SELECT EXTRACT(DOW FROM current_date)) = 4)
or ( control_viernes = true  and  (SELECT EXTRACT(DOW FROM current_date)) = 5)
or ( control_sabado = true  and  (SELECT EXTRACT(DOW FROM current_date)) = 6)
or ( control_domingo = true  and  (SELECT EXTRACT(DOW FROM current_date)) = 7)
)


SELECT EXTRACT(DOW FROM current_date)


select * from pg_timezone_names
where name like '%La_Paz%'


SELECT 
  personas_huellas_digitales.id_huella, 
  personas_huellas_digitales.id_persona, 
  personas_huellas_digitales.codigo_tipo_mano, 
  personas_huellas_digitales.codigo_tipo_dedo, 
  personas_huellas_digitales.huella_digital, 
  personas_huellas_digitales.observacion
FROM 
  public.eventos_personas
JOIN
  public.personas_huellas_digitales
ON
  eventos_personas.id_persona = personas_huellas_digitales.id_persona
JOIN
  public.eventos
ON
  eventos.id_evento = eventos_personas.id_evento
where current_date at time zone ('BOT') between eventos.fecha_inicio and eventos.fecha_fin
and current_time at time zone ('BOT') between eventos.hora_inicio_antes and eventos.hora_fin_retraso
and eventos.codigo_estado = 'A'
and (select extract (year from current_date) ) = gestion
and(
( control_lunes = true  and  (SELECT EXTRACT(DOW FROM current_date)) = 1)
or ( control_martes = true  and  (SELECT EXTRACT(DOW FROM current_date)) = 2)
or ( control_miercoles = true  and  (SELECT EXTRACT(DOW FROM current_date)) = 3)
or ( control_jueves = true  and  (SELECT EXTRACT(DOW FROM current_date)) = 4)
or ( control_viernes = true  and  (SELECT EXTRACT(DOW FROM current_date)) = 5)
or ( control_sabado = true  and  (SELECT EXTRACT(DOW FROM current_date)) = 6)
or ( control_domingo = true  and  (SELECT EXTRACT(DOW FROM current_date)) = 7)
)


select * from personas_eventos_asistencia
WHERE FECHA_REGISTRO IS NULL