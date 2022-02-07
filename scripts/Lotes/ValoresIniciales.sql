
INSERT INTO personas(
            id_persona, ci, nombres, apellidos, sexo, celular, fecha_nacimiento, 
            tipo_persona, ruta_fotografia)
    VALUES (DEFAULT, '5680546', 'Luis Antonio', 'Molina Yampa', 'F', '72854863', '21/10/1987', 
            'A', NULL);


INSERT INTO usuarios(
            id_usuario, fecha_registro, nombre_usuario, contrasenia)
    VALUES (DEFAULT, NOW(), 'admin', 'admin');



select * from personas_eventos_permisos