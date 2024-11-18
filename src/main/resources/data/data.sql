
--ADMIN JUST ONE ADMIN WITH ROLE
--username:admin
--password:admin
INSERT INTO USERS (ID,EMAIL,FIRST_NAME,LAST_NAME,PASSWORD,SERIAL_NUMBER)
SELECT 1,'admin@prova.com','admin','admin','$2a$10$5HoNE/hOpqvtIqY2G0wwSu5olSxGrvzWXDxxErH1QEF.PZ0BAo1sW',12345
WHERE NOT EXISTS (SELECT ID FROM USERS WHERE ID = '1');

INSERT INTO CLASSROOMS (ID,NAME,CUBE,FLOOR,CAPABILITY,NUM_SOCKET,PROJECTOR,AVAILABLE)
SELECT 1,'MT11',30,2,200,4,true,true
WHERE NOT EXISTS (SELECT ID FROM CLASSROOMS WHERE ID = '1');

INSERT INTO CLASSROOMS (ID,NAME,CUBE,FLOOR,CAPABILITY,NUM_SOCKET,PROJECTOR,AVAILABLE)
SELECT 2,'MT10',30,2,180,4,true,true
WHERE NOT EXISTS (SELECT ID FROM CLASSROOMS WHERE ID = '2');

INSERT INTO CLASSROOMS (ID, NAME, CUBE, FLOOR, CAPABILITY, NUM_SOCKET, PROJECTOR, AVAILABLE)
SELECT 4, 'MT12', 25, 2, 150, 3, true, true
WHERE NOT EXISTS (SELECT ID FROM CLASSROOMS WHERE ID = '4');

INSERT INTO CLASSROOMS (ID, NAME, CUBE, FLOOR, CAPABILITY, NUM_SOCKET, PROJECTOR, AVAILABLE)
SELECT 5, 'MT13', 20, 1, 100, 2, false, true
WHERE NOT EXISTS (SELECT ID FROM CLASSROOMS WHERE ID = '5');

INSERT INTO CLASSROOMS (ID, NAME, CUBE, FLOOR, CAPABILITY, NUM_SOCKET, PROJECTOR, AVAILABLE)
SELECT 6, 'MT14', 35, 3, 250, 6, true, false
WHERE NOT EXISTS (SELECT ID FROM CLASSROOMS WHERE ID = '6');

INSERT INTO CLASSROOMS (ID, NAME, CUBE, FLOOR, CAPABILITY, NUM_SOCKET, PROJECTOR, AVAILABLE)
SELECT 7, 'MT15', 40, 3, 300, 8, true, true
WHERE NOT EXISTS (SELECT ID FROM CLASSROOMS WHERE ID = '7');

INSERT INTO CLASSROOMS (ID, NAME, CUBE, FLOOR, CAPABILITY, NUM_SOCKET, PROJECTOR, AVAILABLE)
SELECT 8, 'MT16', 15, 1, 90, 2, false, true
WHERE NOT EXISTS (SELECT ID FROM CLASSROOMS WHERE ID = '8');

INSERT INTO CLASSROOMS (ID, NAME, CUBE, FLOOR, CAPABILITY, NUM_SOCKET, PROJECTOR, AVAILABLE)
SELECT 9, 'MT17', 50, 4, 350, 10, true, false
WHERE NOT EXISTS (SELECT ID FROM CLASSROOMS WHERE ID = '9');


--END SCRIPT

