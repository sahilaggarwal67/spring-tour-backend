insert into users(username, password, enabled)values('admin','admin12',true);
insert into authorities(username,authority)values('admin','ROLE_ADMIN');
 
insert into users(username, password, enabled)values('john','john12',true);
insert into authorities(username,authority)values('john','ROLE_USER');