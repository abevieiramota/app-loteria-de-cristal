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
	turno integer,
	tipo integer
);

create table config
(
	ultimaAtualizacaoData text,
	ultimaAtualizacaoTurno integer,
	ultimaAtualizacaoTipo integer,
    tipo integer
);

insert into config values('01/01/2013', 0, 0, 0);
insert into config values('01/01/1960', 0, 0, 1);

select count(*)
from resultado
where tipo = 1;

select distinct data
from resultado
where tipo = 1;

delete from resultado
where tipo = 1;

select *
from resultado
where tipo = 0
order by substr(data, 7) || substr(data, 4, 2) || substr(data, 1, 2) desc
limit 10;

select *
from resultado
where tipo = 1
order by substr(data, 7) || substr(data, 4, 2) || substr(data, 1, 2) desc
limit 10;