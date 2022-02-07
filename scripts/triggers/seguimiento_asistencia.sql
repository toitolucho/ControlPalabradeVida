CREATE OR REPLACE FUNCTION seguimiento_asistencia() RETURNS TRIGGER AS $personas_eventos_asistencia$
DECLARE 
	v_hora_inicio		TIME;
	v_hora_fin		TIME;
	v_hora_inicio_antes 	TIME;
	v_hora_fin_retraso  	TIME;
	v_tipo_evento		CHAR(1);
BEGIN
	--
	-- Create a row in emp_audit to reflect the operation performed on emp,
	-- make use of the special variable TG_OP to work out the operation.
	--
	IF (TG_OP = 'INSERT') THEN    
		SELECT 	hora_inicio into v_hora_inicio			
		FROM eventos
		where id_evento = NEW.id_evento;

		SELECT 	hora_fin INTO v_hora_fin			
		FROM eventos
		where id_evento = NEW.id_evento;

		SELECT hora_inicio_antes INTO v_hora_inicio_antes			
		FROM eventos
		where id_evento = NEW.id_evento;

		SELECT hora_fin_retraso INTO v_hora_fin_retraso
		FROM eventos
		where id_evento = NEW.id_evento;

		SELECT codigo_tipo_evento INTO v_tipo_evento
		FROM eventos
		where id_evento = NEW.id_evento;
		
	    UPDATE personas_eventos_asistencia
		set tipo_ingreso = CASE WHEN( v_tipo_evento IS NULL) THEN 'E' ELSE v_tipo_evento END,
		    tipo_asistencia =  CASE WHEN (NEW.hora_registro >= v_hora_inicio_antes AND NEW.hora_registro < v_hora_inicio ) THEN 'A' 
					WHEN (NEW.hora_registro >= v_hora_inicio AND NEW.hora_registro <= v_hora_fin ) THEN 'P'  ELSE 'R' END
	    WHERE id_asistencia = NEW.id_asistencia;



	    UPDATE personas_eventos_asistencia
		SET codigo_estado = 'A'
	    WHERE id_asistencia <> NEW.id_asistencia
	    AND id_evento = NEW.id_evento
	    AND id_persona = NEW.id_persona
	    AND fecha_registro = NEW.fecha_registro;
	    
	    RETURN NEW;
	-- ELSIF (TG_OP = 'UPDATE') THEN
	--             INSERT INTO emp_audit SELECT 'U', now(), user, NEW.*;
	--             RETURN NEW;
	--         ELSIF (TG_OP = 'INSERT') THEN
	--             INSERT INTO emp_audit SELECT 'I', now(), user, NEW.*;
	--             RETURN NEW;
	END IF;
	RETURN NULL; -- result is ignored since this is an AFTER trigger
END;
$personas_eventos_asistencia$ LANGUAGE plpgsql;

CREATE TRIGGER t_seguimiento_asistencia
AFTER INSERT ON personas_eventos_asistencia
    FOR EACH ROW EXECUTE PROCEDURE seguimiento_asistencia();

DROP TRIGGER t_seguimiento_asistencia ON personas_eventos_asistencia;

SELECT * FROM personas_eventos_asistencia
SELECT * FROM EVENTOS


INSERT INTO personas_eventos_asistencia(
            id_asistencia, id_evento, id_persona, fecha_registro, hora_registro, 
            tipo_asistencia, tipo_ingreso)
    VALUES (DEFAULT, 2, 2, NOW(), NOW(), 
            NULL, NULL);
