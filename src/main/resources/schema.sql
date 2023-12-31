--Primero se deben borrar todas las tablas (de detalle a maestro) y lugo anyadirlas (de maestro a detalle)
--(en este caso en cada aplicacion se usa solo una tabla, por lo que no hace falta)

drop table Administrador;
create table Administrador (idAdministrador varchar(32) primary key not null);

drop table Monitor;
create table Monitor (idMonitor varchar(32) primary key not null,
nombre varchar(32),
apellido varchar(32));

drop table Socio;
create table Socio (idSocio varchar(32) primary key not null,
dni varchar(32),
nombre varchar(32),
apellido varchar(32),
infoContacto varchar(32));

drop table TipoActividad;
create table TipoActividad (nombre varchar(32) primary key not null,
intensidad varchar(32));

drop table Recurso;
create table Recurso(nombre varchar(32) primary key not null);

drop table Necesita;
create table Necesita (nombreTipoActividad varchar(32) not null,
nombreRecurso varchar(32) not null,
PRIMARY KEY (nombreTipoActividad, nombreRecurso),
FOREIGN KEY (nombreTipoActividad) REFERENCES TipoActividad (nombre),
FOREIGN KEY (nombreRecurso) REFERENCES Recurso (nombre));

drop table Instalacion;
create table Instalacion (nombre varchar(32) primary key not null,
precioPorHora decimal(3,0));

drop table Reserva;
create table Reserva(idSocio varchar(32) not null,
idActividad varchar(32) not null,
PRIMARY KEY (idSocio, idActividad),
FOREIGN KEY (idSocio) REFERENCES Socio (idSocio),
FOREIGN KEY (idActividad) REFERENCES Actividad (idActividad));

drop table Dispone;
create table Dispone(nombreInstalacion varchar(32) not null,
nombreRecurso varchar(32) not null,
cantidad decimal(3,0),
PRIMARY KEY (nombreInstalacion, nombreRecurso),
FOREIGN KEY (nombreInstalacion) REFERENCES Instalacion (nombre),
FOREIGN KEY (nombreRecurso) REFERENCES Recurso (nombre));

drop table Actividad;
create table Actividad (idActividad varchar(32) primary key not null,
fechaInicio timestamp,
fechaFin timestamp,
tipo varchar(32),
instalacion varchar(32),
monitor varchar(32),
plazasTotales decimal(2,0),
plazasDisponibles decimal (2,0),
FOREIGN KEY (tipo) REFERENCES TipoActividad (nombre), 
FOREIGN KEY (instalacion) REFERENCES Instalacion (nombre), 
FOREIGN KEY (monitor) REFERENCES Monitor (idMonitor));

drop table Alquiler;
create table Alquiler(idAlquiler varchar(32) primary key not null,
fechaInicio timestamp,
fechaFin timestamp,
enVigor integer,
pagado integer,
idSocio varchar(32),
instalacion varchar(32),
FOREIGN KEY (idSocio) REFERENCES Socio (idSocio),
FOREIGN KEY (instalacion) REFERENCES Instalacion (nombre));

drop table Cuota;
create table Cuota(idSocio varchar(32) not null,
mes varchar(32) not null,
total decimal(6,0), 
PRIMARY KEY(idSocio, mes),
FOREIGN KEY (idSocio) REFERENCES Socio (idSocio));