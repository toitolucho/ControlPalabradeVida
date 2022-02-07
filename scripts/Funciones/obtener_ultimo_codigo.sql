CREATE OR REPLACE FUNCTION obtener_ultimo_codigo(nombre_tabla character varying)
  RETURNS integer AS
$BODY$
DECLARE	
	codigo_secuencial INTEGER;
	nro_filas	   INT;
BEGIN 

	IF(nombre_tabla = 'eventos') THEN
	   SELECT last_value into codigo_secuencial 
	   FROM eventos_id_evento_seq 
	   ORDER BY last_value
	   DESC LIMIT 1 OFFSET 0;

	   SELECT COUNT(*) into nro_filas 
	   FROM eventos;
	   
	ELSIF (nombre_tabla = 'personas_eventos_asistencia') THEN
	   SELECT last_value into codigo_secuencial 
	   FROM personas_eventos_asistencia_id_asistencia_seq 
	   ORDER BY last_value
	   DESC LIMIT 1 OFFSET 0;

	   SELECT COUNT(*) into nro_filas 
	   FROM personas_eventos_asistencia;
	ELSIF (nombre_tabla = 'personas_huellas_digitales') THEN
	   SELECT last_value into codigo_secuencial 
	   FROM personas_huellas_digitales_id_huella_seq 
	   ORDER BY last_value
	   DESC LIMIT 1 OFFSET 0;

	   SELECT COUNT(*) into nro_filas 
	   FROM personas_huellas_digitales;

	ELSIF (nombre_tabla = 'personas') THEN
	   SELECT last_value into codigo_secuencial 
	   FROM personas_id_persona_seq 
	   ORDER BY last_value
	   DESC LIMIT 1 OFFSET 0;

	   SELECT COUNT(*) into nro_filas 
	   FROM personas;

	   --personas_eventos_permisos
	ELSIF (nombre_tabla = 'personas_eventos_permisos') THEN
	   SELECT last_value into codigo_secuencial 
	   FROM personas_eventos_permisos_id_permiso_seq 
	   ORDER BY last_value
	   DESC LIMIT 1 OFFSET 0;

	   SELECT COUNT(*) into nro_filas 
	   FROM personas_eventos_permisos;
	ELSIF (nombre_tabla = 'usuarios') THEN
	   SELECT last_value into codigo_secuencial 
	   FROM usuarios_id_usuario_seq 
	   ORDER BY last_value
	   DESC LIMIT 1 OFFSET 0;

	   SELECT COUNT(*) into nro_filas 
	   FROM usuarios;

	END IF;

   
    	   
   IF(nro_filas = 0 and codigo_secuencial = 1) THEN
	codigo_secuencial := 1;
   END IF;
   RETURN codigo_secuencial;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION obtener_ultimo_codigo(character varying)
  OWNER TO postgres;

