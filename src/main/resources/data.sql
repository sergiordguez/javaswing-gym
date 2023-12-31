--Datos para carga inicial de la base de datos

delete from Administrador;
insert into Administrador values('AD-1');

delete from Monitor;
insert into Monitor values('MO-1', 'Manolo', 'Pérez');
insert into Monitor values('MO-2', 'Javier', 'Fernández');

delete from Socio;
insert into Socio values('SO-1', 'DNI-1', 'Sergio', 'Álvarez', '666999888');
insert into Socio values('SO-2', 'DNI-2', 'Lino', 'García', 'linogarcia@gmail.com');

delete from TipoActividad;
insert into TipoActividad values('Natacion', 'Baja');
insert into TipoActividad values('Padel', 'Moderada');
insert into TipoActividad values('Ejercicio', 'Alta');

delete from Recurso;
insert into Recurso values('Churros');
insert into Recurso values('Palas');

delete from Necesita;
insert into Necesita values('Natacion', 'Churros');
insert into Necesita values('Padel', 'Palas');

delete from Instalacion;
insert into Instalacion values('Piscina', 20);
insert into Instalacion values('Gimnasio', 5);

delete from Dispone;
insert into Dispone values('Piscina', 'Churros', 60);
insert into Dispone values('Gimnasio', 'Palas', 20);

delete from Actividad;
insert into Actividad values('NAT-1', '2022-12-03 15:00:00', '2022-12-03 17:00:00', 'Natacion', 'Piscina', 'MO-1', 0, 0);
insert into Actividad values('PAD-1', '2022-12-03 15:00:00', '2022-12-03 17:00:00', 'Padel', 'Gimnasio', null, 50, 48);
insert into Actividad values('EJE-1', '2022-11-30 08:00:00', '2022-11-30 09:00:00', 'Ejercicio', 'Gimnasio', 'MO-2', 2, 2);
insert into Actividad values('EJE-2', '2022-11-30 09:00:00', '2022-11-30 10:00:00', 'Ejercicio', 'Gimnasio', 'MO-2', 2, 0);
insert into Actividad values('EJE-3', '2022-11-30 10:00:00', '2022-11-30 11:00:00', 'Ejercicio', 'Gimnasio', 'MO-2', 0, 0);
insert into Actividad values('EJE-4', '2022-12-01 08:00:00', '2022-12-01 09:00:00', 'Ejercicio', 'Gimnasio', 'MO-2', 2, 2);
insert into Actividad values('NAT-10', '2022-12-10 15:00:00', '2022-12-10 17:00:00', 'Natacion', 'Piscina', 'MO-1', 0, 0);
insert into Actividad values('PAD-2', '2022-12-10 15:00:00', '2022-12-10 17:00:00', 'Padel', 'Gimnasio', null, 50, 48);
insert into Actividad values('EJE-5', '2022-12-07 08:00:00', '2022-12-07 09:00:00', 'Ejercicio', 'Gimnasio', 'MO-2', 2, 2);
insert into Actividad values('EJE-6', '2022-12-07 09:00:00', '2022-12-07 10:00:00', 'Ejercicio', 'Gimnasio', 'MO-2', 2, 0);
insert into Actividad values('EJE-7', '2022-12-07 10:00:00', '2022-12-07 11:00:00', 'Ejercicio', 'Gimnasio', 'MO-2', 0, 0);
insert into Actividad values('EJE-8', '2022-12-08 08:00:00', '2022-12-08 09:00:00', 'Ejercicio', 'Gimnasio', 'MO-2', 2, 1);
insert into Actividad values('NAT-2', '2022-11-26 15:00:00', '2022-11-26 17:00:00', 'Natacion', 'Piscina', 'MO-1', 0, 0);
insert into Actividad values('NAT-3', '2022-11-27 15:00:00', '2022-11-27 17:00:00', 'Natacion', 'Piscina', 'MO-1', 0, 0);
insert into Actividad values('NAT-4', '2022-12-01 15:00:00', '2022-12-01 17:00:00', 'Natacion', 'Piscina', 'MO-1', 0, 0);
insert into Actividad values('NAT-5', '2022-12-02 14:00:00', '2022-12-02 16:00:00', 'Natacion', 'Piscina', 'MO-1', 0, 0);
insert into Actividad values('NAT-6', '2022-12-04 15:00:00', '2022-12-04 17:00:00', 'Natacion', 'Piscina', 'MO-1', 6, 2);
insert into Actividad values('NAT-7', '2022-12-06 14:00:00', '2022-12-06 16:00:00', 'Natacion', 'Piscina', 'MO-1', 0, 0);
insert into Actividad values('NAT-8', '2022-12-07 15:00:00', '2022-12-07 16:00:00', 'Natacion', 'Piscina', 'MO-1', 0, 0);
insert into Actividad values('NAT-9', '2022-12-08 15:00:00', '2022-12-08 17:00:00', 'Natacion', 'Piscina', 'MO-1', 4, 1);

delete from Reserva;
insert into Reserva values('SO-1', 'PAD-1');
insert into Reserva values('SO-2', 'PAD-1');
insert into Reserva values('SO-1', 'EJE-2');
insert into Reserva values('SO-2', 'EJE-2');
insert into Reserva values('SO-1', 'PAD-2');
insert into Reserva values('SO-2', 'PAD-2');
insert into Reserva values('SO-1', 'EJE-6');
insert into Reserva values('SO-2', 'EJE-6');
insert into Reserva values('SO-1', 'NAT-6');
insert into Reserva values('SO-2', 'NAT-6');
insert into Reserva values('SO-1', 'NAT-9');
insert into Reserva values('SO-2', 'NAT-9');
insert into Reserva values('SO-1', 'EJE-8');
insert into Reserva values('SO-2', 'EJE-8');


delete from Alquiler;
insert into Alquiler values('ALQ-1', '2022-11-13 15:00:00', '2022-11-13 17:00:00', 1, 0, 'SO-1', 'Piscina');
insert into Alquiler values('ALQ-2', '2022-11-13 15:00:00', '2022-11-13 17:00:00', 1, 0, 'SO-1', 'Gimnasio');
insert into Alquiler values('ALQ-3', '2022-11-16 17:00:00', '2022-11-16 19:00:00', 1, 1, 'SO-1', 'Gimnasio');
insert into Alquiler values('ALQ-4', '2022-11-17 18:00:00', '2022-11-17 19:00:00', 1, 1, 'SO-1', 'Gimnasio');
insert into Alquiler values('ALQ-5', '2022-12-13 20:00:00', '2022-12-13 22:00:00', 1, 1, 'SO-1', 'Gimnasio');
insert into Alquiler values('ALQ-6', '2022-12-04 17:00:00', '2022-12-04 19:00:00', 1, 0, 'SO-1', 'Piscina');
insert into Alquiler values('ALQ-7', '2022-12-04 15:00:00', '2022-12-04 17:00:00', 1, 0, 'SO-1', 'Gimnasio');
insert into Alquiler values('ALQ-8', '2022-12-04 15:00:00', '2022-12-04 17:00:00', 0, 0, 'SO-1', 'Gimnasio');
insert into Alquiler values('ALQ-9', '2022-12-11 15:00:00', '2022-12-11 17:00:00', 1, 0, 'SO-1', 'Piscina');
insert into Alquiler values('ALQ-10', '2022-12-11 15:00:00', '2022-12-11 17:00:00', 1, 0, 'SO-1', 'Gimnasio');
insert into Alquiler values('ALQ-11', '2022-12-11 15:00:00', '2022-12-11 17:00:00', 0, 0, 'SO-1', 'Gimnasio');

delete from Cuota;
insert into Cuota values('SO-1', 'NOV', 50);
insert into Cuota values('SO-1', 'DIC', 25);

