create table resultado
(
	data text, 
	premio1 text, 
	premio2 text, 
	premio3 text, 
	premio4 text, 
	premio5 text, 
	premio6 text, 
	premio7 text, 
	premio8 text, 
	premio9 text, 
	premio10 text, 
	turno integer
);

create table config
(
ultimaAtualizacaoData text,
ultimaAtualizacaoTurno integer
);

insert into config values('01/01/2013', 0)