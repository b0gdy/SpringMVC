insert into listener(id, name) values (1, 'Listener1');
insert into listener(id, name) values (2, 'Listener2');

insert into info(id, first_name, last_name, listener_id) values (1, 'InfoFirstName1', 'InfoLastName1', 1);
insert into info(id, first_name, last_name, listener_id) values (2, 'InfoFirstName2', 'InfoLastName2', 2);

insert into artist(id, name) values (1, 'Artist1');
insert into artist(id, name) values (2, 'Artist2');

insert into song(id, name, genre, artist_id) values (1, 'Song1', 'Pop', 1);
insert into song(id, name, genre, artist_id) values (2, 'Song2', 'Rock', 1);
insert into song(id, name, genre, artist_id) values (3, 'Song3', 'Rap', 2);
insert into song(id, name, genre, artist_id) values (4, 'Song4', 'Pop', 2);

insert into listener_song(listener_id, song_id) values (1, 1);
insert into listener_song(listener_id, song_id) values (1, 2);
insert into listener_song(listener_id, song_id) values (1, 3);
insert into listener_song(listener_id, song_id) values (2, 3);

insert into favourite(id, listener_id, song_id) values (1, 1, 1);
insert into favourite(id, listener_id, song_id) values (2, 1, 2);
insert into favourite(id, listener_id, song_id) values (3, 1, 3);
insert into favourite(id, listener_id, song_id) values (4, 2, 3);

update awbd.listener set username = 'guest', password = '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', enabled  = 1 where id = 1;
update awbd.listener set username = 'customer', password = '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', enabled  = 1 where id = 2;
