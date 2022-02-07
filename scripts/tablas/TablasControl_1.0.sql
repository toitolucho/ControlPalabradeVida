CREATE DATABASE control_palabra_vida;

DROP DATABASE control_palabra_vida;

DROP TABLE personas_eventos_permisos;
DROP TABLE usuarios;
DROP TABLE personas_eventos_asistencia;
DROP TABLE eventos_personas;
DROP TABLE Eventos;
DROP TABLE personas_huellas_digitales;
DROP TABLE personas;

CREATE TABLE personas
(
	id_persona		SERIAL,
	ci				CHAR(10),
	nombres			VARCHAR(50) NOT NULL,
	apellidos		VARCHAR(50) NOT NULL,
	sexo			CHAR(1), --'F'->FEMENINO, 'M'->MASCULINO
	celular			VARCHAR(10),
	fecha_nacimiento DATE,
	tipo_persona	CHAR(1),
	ruta_fotografia	VARCHAR(100) NULL,
	CONSTRAINT PK_personas PRIMARY KEY(id_persona),
	CONSTRAINT U_ci_nombrecompleto UNIQUE(ci, nombres, apellidos)
);

CREATE TABLE personas_huellas_digitales
(
	id_huella			SERIAL,
	id_persona			INT	NOT NULL,
	codigo_tipo_mano	CHAR(1) NOT NULL,--'I'-> MANO IZQUIERDA, 'D'->MANO DERECHA
	codigo_tipo_dedo	CHAR(1) NOT NULL,-- 'P'->PULGAR, 'I'->INDICE, 'C'->CENTRO, 'A'->ANULAR, 'M'->MEÑIQUE
	huella_digital			BYTEA NOT NULL,
	observacion			TEXT,
	CONSTRAINT pk_personas_huellas_digitales PRIMARY KEY(id_huella),
	--CONSTRAINT U_huella_dedo_persona UNIQUE (id_persona, codigo_tipo_mano, codigo_tipo_dedo ),
	CONSTRAINT U_huella_persona UNIQUE(huella_digital),
	CONSTRAINT FK_personas_huellas_digitales01 FOREIGN KEY(id_persona) REFERENCES personas(id_persona)	
);

CREATE TABLE Eventos
(
	id_evento	SERIAL,
	nombre_evento	VARCHAR(100) NOT NULL,
	gestion			INT,
	codigo_estado	CHAR(1) DEFAULT 'A', --'A'->ALTA  'B'->BAJA, 'S'->SUSPENDIDO
	codigo_tipo_evento	CHAR(1) DEFAULT 'E',
	control_lunes	BOOLEAN,
	control_martes	BOOLEAN,
	control_miercoles	BOOLEAN,
	control_jueves	BOOLEAN,
	control_viernes	BOOLEAN,
	control_sabado	BOOLEAN,
	control_domingo	BOOLEAN,
	hora_inicio		TIME,
	hora_fin		TIME,
	hora_inicio_antes TIME,
	hora_fin_retraso  TIME,	
	fecha_inicio	DATE,
	fecha_fin		DATE,	
	descripcion		TEXT,
	CONSTRAINT PK_eventos PRIMARY KEY(id_evento),
	CONSTRAINT CH_codigo_estado CHECK(codigo_estado IN ('A','B','S')),
	CONSTRAINT CH_codigo_tipo_evento CHECK(codigo_tipo_evento IN ('E','S'))
	
);

CREATE TABLE eventos_personas
(
	id_evento			INT,
	id_persona			INT,
	fecha_inicio		DATE,
	fecha_fin			DATE,
	CONSTRAINT pk_eventos_personas PRIMARY KEY(id_evento, id_persona ),
	CONSTRAINT fk_eventos_personas_eventos FOREIGN KEY (id_evento) REFERENCES Eventos(id_evento),
	CONSTRAINT fk_eventos_personas_personas FOREIGN KEY (id_persona) REFERENCES personas(id_persona)
);

CREATE TABLE personas_eventos_asistencia
(
	id_asistencia	SERIAL,
	id_evento		INT,
	id_persona		INT,
	fecha_registro	DATE DEFAULT NOW(),
	hora_registro	TIME DEFAULT NOW(),
	tipo_asistencia	CHAR(1) DEFAULT 'P', --'A'->ADELANTADO, 'P'->PUNTUAL, 'R'->RETRASO, 'F'->FALTA
	tipo_ingreso	CHAR(1) DEFAULT 'E',-- 'E'->ENTRADA, 'S'->SALIDA
	codigo_estado 	CHAR(1) DEFAULT 'U', --'U' - ULTIMO REGISTRO, 'A'->ANTIGUO REGISTRO
	CONSTRAINT pk_personas_eventos_asistencia PRIMARY KEY (id_asistencia),
	CONSTRAINT U_persona_evento_fecha_hora UNIQUE(id_evento, id_persona, fecha_registro, hora_registro),
	CONSTRAINT PK_evento_asistencias_personas01 FOREIGN KEY (id_persona) REFERENCES personas(id_persona),
	CONSTRAINT PK_evento_asistencias_eventos FOREIGN KEY(id_evento) REFERENCES eventos(id_evento)
	
);

CREATE TABLE usuarios
(
	id_usuario	SERIAL NOT NULL,	
	fecha_registro	DATE DEFAULT NOW(),
	nombre_usuario	VARCHAR(200) UNIQUE NOT NULL,
	contrasenia	VARCHAR(100) NOT NULL,	
	CONSTRAINT PK_Usuarios PRIMARY KEY(id_usuario)        
);

CREATE TABLE personas_eventos_permisos
(
	id_permiso	SERIAL,
	id_evento		INT,
	id_persona		INT,
	id_usuario		INT,
	fecha_registro	DATE DEFAULT NOW(),
	fecha_permiso	DATE DEFAULT NOW(),	
	fecha_fin_permiso DATE,
	tipo_permiso    CHAR(1),-- 'S'->'SALIDA TEMPORAL', 'L' ->'LICENCIA', 'O'->OTRO
	motivo			TEXT,
	CONSTRAINT pk_personas_eventos_permisos PRIMARY KEY (id_permiso),
	CONSTRAINT U_persona_evento_permiso UNIQUE(id_evento, id_persona, fecha_permiso),
	CONSTRAINT PK_evento_asistencias_permisos_personas01 FOREIGN KEY (id_persona) REFERENCES personas(id_persona),
	CONSTRAINT PK_evento_asistencias_permisos_eventos FOREIGN KEY(id_evento) REFERENCES eventos(id_evento),
	CONSTRAINT PK_evento_asistencias_permisos_usuarios FOREIGN KEY(id_usuario) REFERENCES usuarios(id_usuario)
	
);



