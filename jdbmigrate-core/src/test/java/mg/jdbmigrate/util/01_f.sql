down:
drop table version1;

up:
create table version1(version integer not null);
insert into version1(version) values(1);

