DELIMITER //
CREATE PROCEDURE CautaAngajat(IN input VARCHAR(30))
BEGIN
   
    SELECT a.id, a.id_utilizator, a.id_policlinica, u.nume, u.prenume, a.functie, a.salariu_negociat, a.numar_ore FROM angajati a
    INNER JOIN utilizatori u ON a.id_utilizator = u.id
    WHERE u.nume = input OR u.prenume = input OR a.functie = input;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE AfiseazaAngajati()
BEGIN
	SELECT a.id, a.id_utilizator, a.id_policlinica, u.nume, u.prenume, a.functie, a.salariu_negociat, a.numar_ore FROM angajati a
	INNER JOIN utilizatori u ON a.id_utilizator = u.id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE ModificaOrar(IN idMedic INT, IN ziuaSauData VARCHAR(20), IN oraInceput TIME, IN oraSfarsit TIME)
BEGIN
    
    UPDATE orar_medici
    SET ora_inceput = oraInceput, ora_sfarsit = oraSfarsit
    WHERE id_medic = idMedic AND zi_saptamana_sau_data = ziuaSauData;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE SeteazaConcediu(IN idAngajat INT, IN dataInceput DATE, IN dataSfarsit DATE)
BEGIN
    
    INSERT INTO concedii (id_angajat, data_inceput, data_sfarsit)
    VALUES (idAngajat, dataInceput, dataSfarsit);
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE ExtrageOrarReadOnly(IN idMedic INT)
BEGIN
    
    SELECT * FROM orar_medici
    WHERE id_medic = idMedic;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE ExtrageConcediiReadOnly(IN idAngajat INT)
BEGIN
    
    SELECT * FROM concedii
    WHERE id_angajat = idAngajat;
END //
DELIMITER ;

DELIMITER $
CREATE DEFINER=`root`@`localhost` PROCEDURE `AdaugaOrarMedic`(
	IN id INT,
    IN tip BOOLEAN,
    IN input VARCHAR(10),
    IN ora_i TIME,
    IN ora_s TIME
    )
BEGIN
	
	SET @exista = (SELECT count(*) from orar_medici
					where id_medic = id and zi_saptamana_sau_data = input);
	if @exista > 0 then
		UPDATE orar_medici
        SET ora_inceput = ora_i, ora_sfarsit = ora_s
        WHERE zi_saptamana_sau_data = input AND id_medic = id;
	else
		INSERT INTO orar_medici	VALUES
        (id, tip, input, ora_i, ora_s);
	end if;
END $
DELIMITER ;
