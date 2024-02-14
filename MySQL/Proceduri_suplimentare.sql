use policlinica
DELIMITER //
CREATE PROCEDURE CautaCont(IN numeUtilizator VARCHAR(30), IN parola VARCHAR(30))
BEGIN
    select * from conturi where conturi.nume_utilizator = numeUtilizator and conturi.parola = parola;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE CautaUtilizatorDupaCont(IN id INT)
BEGIN
    select * from utilizatori where id_cont = id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE CautaAngajatDupaUtilizator(IN id INT)
BEGIN
    select * from angajati where id_utilizator = id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE CautaAsistentMedical(IN id INT)
BEGIN
    select * from asistenti_medicali where id_angajat = id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE CautaMedic(IN id INT)
BEGIN
    select * from medici where id_angajat = id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE AfiseazaSpecialitati(IN id INT)
BEGIN
    select * from specialitati where id_medic = id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PoliclinicaDeCareApartineMedic(IN id INT)
BEGIN
    select id_policlinica from angajati inner join medici where angajati.id = medici.id_angajat and medici.id = id;
END //
DELIMITER ;

DELIMITER $
CREATE PROCEDURE AdaugaUtilizator (
	IN user_n varchar(20),
    IN pas VARCHAR(20),
	IN dep VARCHAR(20),
    IN adr VARCHAR(50),
    IN cn VARCHAR(13),
    IN num VARCHAR(30),
    IN prenum VARCHAR(30),
    IN tel VARCHAR(10),
    IN emai VARCHAR(30),
    IN iba VARCHAR(34),
    IN _data DATE,
    IN ro VARCHAR(15)
)
BEGIN
	INSERT INTO policlinica.conturi(nume_utilizator, parola) VALUES
    (user_n, pas);
    SET @id_cont = (SELECT conturi.id FROM conturi WHERE nume_utilizator = user_n);
    INSERT INTO utilizatori(id_cont, departament, adresa, cnp, nume, prenume, telefon, email, iban, data_angajarii, rol) VALUES
    (@id_cont, dep, adr, cn, num, prenum, tel, emai, iba, _data, ro);
END $
DELIMITER ;
