-- Tipos de documentos
INSERT INTO msvc_clientes_unc.tipo_documento (id, abreviatura, longitud, nombre) VALUES (1, 'DNI', '8', 'Documento Nacional de Identidad');
INSERT INTO msvc_clientes_unc.tipo_documento (id, abreviatura, longitud, nombre) VALUES (2, 'CE', '9', 'Carné de Extranjería');
INSERT INTO msvc_clientes_unc.tipo_documento (id, abreviatura, longitud, nombre) VALUES (3, 'PASS', '9', 'Pasaporte');
INSERT INTO msvc_clientes_unc.tipo_documento (id, abreviatura, longitud, nombre) VALUES (4, 'RUC', '11', 'Registro Único de Contribuyentes');
INSERT INTO msvc_clientes_unc.tipo_documento (id, abreviatura, longitud, nombre) VALUES (5, 'LIC', '10', 'Licencia de Conducir');
INSERT INTO msvc_clientes_unc.tipo_documento (id, abreviatura, longitud, nombre) VALUES (6, 'TI', '10', 'Tarjeta de Identidad');
INSERT INTO msvc_clientes_unc.tipo_documento (id, abreviatura, longitud, nombre) VALUES (7, 'DIE', '12', 'Documento de Identidad Extranjero');


--Clientes
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (1, 'Juan', 'Pérez', 'juan.perez@gmail.com', '987654321', '12345678', 1);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (2, 'Ana', 'Ramírez', 'ana.ramirez@gmail.com', '912345678', 'Y23456789', 1);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (3, 'Carlos', 'López', 'carlos.lopez@hotmail.com', '998877665', 'P12345678', 1);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (4, 'Lucía', 'Fernández', 'lucia.fernandez@outlook.com', '923456789', '20123456789', 1);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (5, 'Miguel', 'Torres', 'miguel.torres@yahoo.com', '987112233', 'B123456789', 0);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (6, 'Rosa', 'García', 'rosa.garcia@gmail.com', '944556677', 'TI98765432', 1);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (7, 'Eduardo', 'Martínez', 'eduardo.martinez@gmail.com', '933221144', 'DIE45678901', 1);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (1, 'Verónica', 'Silva', 'veronica.silva@gmail.com', '998822334', '87654321', 1);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (2, 'Luis', 'Reyes', 'luis.reyes@gmail.com', '911223344', 'CE12312312', 1);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (3, 'Carmen', 'Delgado', 'carmen.delgado@hotmail.com', '922334455', 'P87654321', 1);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (4, 'Alberto', 'Morales', 'alberto.morales@outlook.com', '933445566', '20112233445', 1);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (5, 'Isabel', 'Ortega', 'isabel.ortega@yahoo.com', '944556677', 'LIC1234567', 1);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (6, 'Diego', 'Salazar', 'diego.salazar@gmail.com', '955667788', 'TI11223344', 0);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (7, 'Patricia', 'Campos', 'patricia.campos@gmail.com', '966778899', 'DIE99887766', 1);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (1, 'Óscar', 'Guzmán', 'oscar.guzman@gmail.com', '977889900', '23456789', 1);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (2, 'Teresa', 'Rojas', 'teresa.rojas@gmail.com', '988990011', 'CE87654321', 1);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (3, 'Mario', 'Vega', 'mario.vega@hotmail.com', '999001122', 'P12398745', 1);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (4, 'Flor', 'Castillo', 'flor.castillo@outlook.com', '900112233', '20998877665', 0);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (5, 'Ricardo', 'Medina', 'ricardo.medina@yahoo.com', '911223344', 'LIC8765432', 1);
INSERT INTO msvc_clientes_unc.cliente (tipo_documento_id, nombre, apellido, correo, telefono, numero_identificacion, estado) VALUES (6, 'Sofía', 'Mendoza', 'sofia.mendoza@gmail.com', '922334455', 'TI44556677', 1);
