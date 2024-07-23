/**
 *
 */
create database reserva;
create role kapi with login encrypted password 's3cr37' ;
grant all privileges on database reserva to kapi;