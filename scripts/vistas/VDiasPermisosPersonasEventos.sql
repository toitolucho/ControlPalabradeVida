CREATE OR REPLACE VIEW VDiasPermisosPersonasEventos
AS
SELECT V.*, VDSE.dia
FROM
(
SELECT 	generate_series::date as fecha, (EXTRACT (isodow from generate_series)) as dia_semana,
	CASE WHEN generate_series BETWEEN fecha_permiso and fecha_fin_permiso THEN pep.id_evento else null end as id_evento, 
	CASE WHEN generate_series BETWEEN fecha_permiso and fecha_fin_permiso THEN pep.id_persona else null end as id_persona	
FROM generate_series( (select min(fecha_permiso) from personas_eventos_permisos) , (select max(fecha_fin_permiso) from personas_eventos_permisos) , '1 days'),
personas_eventos_permisos pep
) V
INNER JOIN VDiasSemanaEvento VDSE
ON V.id_evento = vdse.id_evento
AND V.dia_semana = vdse.dia_semana
WHERE V.id_evento IS NOT NULL

-- select * from VDiasPermisosPersonasEventos