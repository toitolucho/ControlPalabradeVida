-- CREATE OR REPLACE FUNCTION ObtenerFechaServidor()
-- RETURNS varchar(10) AS
-- 
-- $BODY$
-- 
-- BEGIN 
--    RETURN (SELECT NOW()::varchar(10)) ;
-- END;
-- $BODY$
--   LANGUAGE 'plpgsql' VOLATILE
--   COST 100;
-- 
-- --  SELECT ObtenerFechaServidor()
-- select now()
-- select  current_time
-- select 


CREATE OR REPLACE FUNCTION ObtenerFechaServidor()
RETURNS DATE AS

$BODY$

BEGIN 
   RETURN (SELECT NOW()::DATE) ;
END;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;