--DROP FUNCTION obtener_reporte_asistencia(integer, date, date);

CREATE OR REPLACE FUNCTION obtener_reporte_asistencia(
	id_persona_aux 		INT,
	fecha_inicio_aux	DATE,
	fecha_fin_aux		DATE
)
RETURNS SETOF RECORD
AS
$body$
DECLARE 
	registro	RECORD;
	sql 		text;
	filtro		TEXT;
	fecha_max	DATE;
	fecha_min	DATE;
	fecha_actual	DATE;
BEGIN
	IF (fecha_inicio_aux IS NULL OR  fecha_fin_aux IS NULL) THEN   
		select min(fecha_inicio), max(fecha_fin), CURRENT_DATE::DATE INTO fecha_min, fecha_max, fecha_actual
		from eventos
		where codigo_estado = 'A';
		fecha_max = CASE WHEN fecha_max IS NULL THEN '31/12/2016' ELSE fecha_max END;
		fecha_min = CASE WHEN fecha_min IS NULL THEN '01/01/2016' ELSE fecha_min END;
	ELSE
		fecha_min = fecha_inicio_aux;
		fecha_max = fecha_fin_aux;
		SELECT CURRENT_DATE::DATE INTO fecha_actual;
	END IF;
	
	
	
	sql = 'SELECT 		pea.fecha_registro, pea.hora_registro, 
		pea.id_persona, CASE WHEN pea.tipo_ingreso = ''E'' THEN ''ENTRADA'' WHEN pea.tipo_ingreso = ''S'' THEN ''SALIDA'' ELSE NULL END AS tipo_ingreso, 
		CASE WHEN pea.tipo_asistencia = ''A'' THEN ''ADELANTADO'' WHEN pea.tipo_asistencia = ''P'' THEN ''PUNTUAL'' WHEN pea.tipo_asistencia = ''R'' THEN ''RETRASO'' when dppe.id_evento is not null then ''PERMISO'' ELSE ''FALTA'' END AS tipo_asistencia, 
		dias_asistencia.dia_semana, dias_asistencia.id_evento,
		dias_asistencia.fecha, dias_asistencia.id_persona, dias_asistencia.Valido, dias_asistencia.nombre_Evento,
		p.nombres || '' '' || p.apellidos as nombre_completo, dias_asistencia.dia
	FROM personas_eventos_asistencia pea
	RIGHT JOIN
	(

		SELECT TA3.*, EP1.id_persona
		FROM
		(
		SELECT TA.*, Dias_Semana.*, CASE WHEN fecha BETWEEN e.fecha_inicio and e.fecha_fin then TRUE else FALSE END as Valido, e.nombre_evento
		FROM
		(
			SELECT CURRENT_DATE+i as fecha, EXTRACT(ISODOW FROM  CURRENT_DATE+i) as dia_semana2
			FROM generate_series(date ''' || fecha_min::text || '''-CURRENT_DATE,date ''' || fecha_max::text || '''-CURRENT_DATE) i
		) TA
		LEFT JOIN VDiasSemanaEvento AS Dias_Semana		
		on TA.dia_semana2  = Dias_Semana.dia_semana
		INNER JOIN eventos e
		ON e.id_evento = Dias_Semana.id_evento
-- 		WHERE e.id_evento = 2
-- 		order by fecha
		) TA3, eventos_personas EP1
		where EP1.id_evento  = TA3.id_evento
		order by  TA3.id_evento, TA3.fecha, EP1.id_persona

	) dias_asistencia
	on dias_asistencia.id_evento = pea.id_evento
	and dias_asistencia.fecha = pea.fecha_registro
	and pea.codigo_estado = ''U''
	and pea.id_persona = dias_asistencia.id_persona
	LEFT JOIN VDiasPermisosPersonasEventos dppe
	ON dias_asistencia.fecha = dppe.fecha
	and dias_asistencia.id_evento = dppe.id_evento
	and dias_asistencia.id_persona = dppe.id_persona
	LEFT JOIN Personas P
	ON dias_asistencia.id_persona = P.id_persona
	where valido = true
 	--and dppe.id_evento is null
 	and dias_asistencia.fecha <= ''' || fecha_actual::text || '''
	 ';


	filtro = ' ';
	IF id_persona_aux IS NOT NULL THEN
		filtro = filtro || ' AND p.id_persona = ' || id_persona_aux::text || ' ';
	END IF;

	IF(filtro IS NOT NULL AND filtro <> ' ') THEN
		
		sql = sql || ' ' || filtro;
	END IF;
	
	RAISE NOTICE ' SQL: (%)', sql;
	
	FOR registro IN EXECUTE sql
	LOOP
	RETURN next registro;
	end loop;
	RETURN;

END;
$body$ LANGUAGE 'plpgsql' VOLATILE;


select * from VDiasPermisosPersonasEventos


SELECT * FROM obtener_reporte_asistencia(NULL, '01/07/2016', '15/07/2016') 
AS 
(	
	fecha_registro 	DATE,
	hora_registro 	TIME,	
	id_persona 	 INTEGER,	
	tipo_ingreso 	TEXT,
	tipo_asistencia TEXT,
	dia_semana	INTEGER,
	id_evento	INTEGER,
	fecha 		DATE,
	id_persona2	INTEGER,
	Valido		BOOLEAN,
	nombre_Evento	CHARACTER VARYING(100),
	nombre_completo	TEXT,
	dia		TEXT
)
order by nombre_completo, nombre_Evento,  fecha;


select * from usuarios


SELECT 		pea.fecha_registro, pea.hora_registro, 
		pea.id_persona, CASE WHEN pea.tipo_ingreso = 'E' THEN 'ENTRADA' WHEN pea.tipo_ingreso = 'S' THEN 'SALIDA' ELSE NULL END AS tipo_ingreso, 
		CASE WHEN pea.tipo_asistencia = 'A' THEN 'ADELANTADO' WHEN pea.tipo_asistencia = 'P' THEN 'PUNTUAL' WHEN pea.tipo_asistencia = 'R' THEN 'RETRASO' ELSE 'FALTA' END AS tipo_asistencia, 
		dias_asistencia.dia_semana, dias_asistencia.id_evento,
		dias_asistencia.fecha, dias_asistencia.id_persona, dias_asistencia.Valido, dias_asistencia.nombre_Evento,
		p.nombres || ' ' || p.apellidos as nombre_completo, dias_asistencia.dia
	FROM personas_eventos_asistencia pea
	RIGHT JOIN
	(

		SELECT TA3.*, EP1.id_persona
		FROM
		(
		SELECT TA.*, Dias_Semana.*, CASE WHEN fecha BETWEEN e.fecha_inicio and e.fecha_fin then TRUE else FALSE END as Valido, e.nombre_evento
		FROM
		(
			SELECT CURRENT_DATE+i as fecha, EXTRACT(ISODOW FROM  CURRENT_DATE+i) as dia_semana2
			FROM generate_series(date '2016-01-01'-CURRENT_DATE,date '2016-12-31'-CURRENT_DATE) i
		) TA
		LEFT JOIN VDiasSemanaEvento AS Dias_Semana		
		on TA.dia_semana2  = Dias_Semana.dia_semana
		INNER JOIN eventos e
		ON e.id_evento = Dias_Semana.id_evento
-- 		WHERE e.id_evento = 2
-- 		order by fecha
		) TA3, eventos_personas EP1
		where EP1.id_evento  = TA3.id_evento
		order by  TA3.id_evento, TA3.fecha, EP1.id_persona

	) dias_asistencia
	on dias_asistencia.id_evento = pea.id_evento
	and dias_asistencia.fecha = pea.fecha_registro
	and pea.codigo_estado = 'U'
	and pea.id_persona = dias_asistencia.id_persona
	LEFT JOIN VDiasPermisosPersonasEventos dppe
	ON dias_asistencia.fecha = dppe.fecha
	and dias_asistencia.id_evento = dppe.id_evento
	and dias_asistencia.id_persona = dppe.id_persona
	LEFT JOIN Personas P
	ON dias_asistencia.id_persona = P.id_persona
	where valido = true
 	and dppe.id_evento is null
 	and dias_asistencia.fecha <= '2016-07-25'


SELECT 	pea.fecha_registro, pea.hora_registro, 
		pea.id_persona, CASE WHEN pea.tipo_ingreso = 'E' THEN 'ENTRADA' WHEN pea.tipo_ingreso = 'S' THEN 'SALIDA' ELSE NULL END AS tipo_ingreso, 
		CASE WHEN pea.tipo_asistencia = 'A' THEN 'ADELANTADO' WHEN pea.tipo_asistencia = 'P' THEN 'PUNTUAL' WHEN pea.tipo_asistencia = 'R' THEN  'RETRASO'  when dppe.id_evento is not null then 'PERMISO' ELSE 'FALTA' END AS tipo_asistencia, 
		dias_asistencia.dia_semana, dias_asistencia.id_evento,
		dias_asistencia.fecha, dias_asistencia.id_persona, dias_asistencia.Valido, dias_asistencia.nombre_Evento,
		p.nombres || ' ' || p.apellidos as nombre_completo, dias_asistencia.dia
	FROM personas_eventos_asistencia pea
	RIGHT JOIN
	(

		SELECT TA3.*, EP1.id_persona
		FROM
		(
		SELECT TA.*, Dias_Semana.*, CASE WHEN fecha BETWEEN e.fecha_inicio and e.fecha_fin then TRUE else FALSE END as Valido, e.nombre_evento
		FROM
		(
			SELECT CURRENT_DATE+i as fecha, EXTRACT(ISODOW FROM  CURRENT_DATE+i) as dia_semana2
			FROM generate_series(date '01/01/2016'-CURRENT_DATE,date '30/12/2016'-CURRENT_DATE) i
		) TA
		LEFT JOIN VDiasSemanaEvento AS Dias_Semana		
		on TA.dia_semana2  = Dias_Semana.dia_semana
		INNER JOIN eventos e
		ON e.id_evento = Dias_Semana.id_evento
-- 		WHERE e.id_evento = 2
-- 		order by fecha
		) TA3, eventos_personas EP1
		where EP1.id_evento  = TA3.id_evento
		order by  TA3.id_evento, TA3.fecha, EP1.id_persona

	) dias_asistencia
	on dias_asistencia.id_evento = pea.id_evento
	and dias_asistencia.fecha = pea.fecha_registro
	and pea.codigo_estado = 'U'
	and pea.id_persona = dias_asistencia.id_persona
	LEFT JOIN VDiasPermisosPersonasEventos dppe
	ON dias_asistencia.fecha = dppe.fecha
	and dias_asistencia.id_evento = dppe.id_evento
	and dias_asistencia.id_persona = dppe.id_persona
	LEFT JOIN Personas P
	ON dias_asistencia.id_persona = P.id_persona
--  	where dias_asistencia.id_evento = 2 and valido = true
	where valido = true
--  	and dppe.id_evento is null
-- 	and dias_asistencia.fecha <= current_date::date
 	and dias_asistencia.fecha BETWEEN '01/06/2016' and '30/06/2016'
	order by 12,11, fecha

select  * from VDiasPermisosPersonasEventos