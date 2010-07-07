up:
create table version(version integer not null);
insert into version(version) values(0);

down:
drop table version;