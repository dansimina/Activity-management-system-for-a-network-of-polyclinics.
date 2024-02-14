DELIMITER //
CREATE PROCEDURE InserarePacient(
    IN numePacient VARCHAR(20),
    IN prenumePacient VARCHAR(20)
)
BEGIN
   
    INSERT INTO pacienti (nume, prenume)
    VALUES (numePacient, prenumePacient);
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE ProgramarePacient(
    IN idPacient INT,
    IN idMedic INT,
    IN dataSiOraProgramare DATETIME
)
BEGIN
   
    INSERT INTO programari (id_pacient, id_medic, data_si_ora)
    VALUES (idPacient, idMedic, dataSiOraProgramare);
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE SelectareSpecialitatiMedic(
    IN idMedic INT
)
BEGIN
    
    SELECT nume_specialitate, grad
    FROM specialitati
    WHERE id_medic = idMedic;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE EmitereBonFiscal(
    IN idRaport INT,
    IN idAngajat INT,
    IN totalBon DECIMAL(10, 2)
)
BEGIN
    
    INSERT INTO bonuri_fiscale (id_raport, id_angajat, total)
    VALUES (idRaport, idAngajat, totalBon);
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE ExtrageRaport(
    IN idRaport INT
)
BEGIN
   
    SELECT * FROM rapoarte
    WHERE id = idRaport;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE ActualizareRaport(
    IN idRaport INT,
    IN istoric VARCHAR(100),
    IN simptome VARCHAR(100),
    IN diagnostic VARCHAR(100),
    IN recomandari VARCHAR(1000),
    IN parafa BOOLEAN
)
BEGIN
   
    UPDATE rapoarte
    SET istoric = istoric, simptome = simptome, diagnostic = diagnostic, recomandari = recomandari, parafa = parafa
    WHERE id = idRaport;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE ValidareRaport(
    IN idRaport INT
)
BEGIN
   
    UPDATE rapoarte
    SET parafa = TRUE
    WHERE id = idRaport;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE ExtragePacienti()
BEGIN
    
    SELECT * FROM pacienti;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE ExtragePacientiProgramatiMedic(IN id_medic INT)
BEGIN
    SELECT programari.id, programari.id_policlinica, programari.id_angajat, programari.id_pacient, programari.id_medic, programari._data, programari.ora_inceput, programari.ora_sfarsit, programari.inregistrat, pacienti.nume, pacienti.prenume
    FROM pacienti inner join programari where pacienti.id = programari.id_pacient and programari.id_medic = id_medic;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE ExtrageRapoarte()
BEGIN
    
    SELECT * FROM rapoarte;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE AfiseazaMedici()
BEGIN
    
	SELECT m.id, m.id_angajat, m.cod_parafa, m.titlu_stiintific, m.post_didactic, m.venit_aditional, u.nume, u.prenume FROM utilizatori u, medici m
	JOIN angajati a 
	WHERE a.functie = "medic" AND a.id_utilizator = u.id and m.id_angajat = a.id;


END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE OrarAngajat(
	IN id_angajat INT,
    IN numar_zi INT
)
BEGIN
    
    set @zi = "duminica";
    CASE
		WHEN numar_zi = 2 THEN set @zi = "luni";
		WHEN numar_zi = 3 THEN set @zi = "marti";
		WHEN numar_zi = 4 THEN set @zi = "miercuri";
        WHEN numar_zi = 5 THEN set  @zi = "joi";
        WHEN numar_zi = 6 THEN set @zi = "vineri";
        WHEN numar_zi = 7 THEN set @zi = "sambata";
        ELSE set @zi = "duminica";
	END CASE;
    SET @query = CONCAT('SELECT ', @zi,", SUBSTRING_INDEX(", @zi, ",'-',1) AS ora_inceput, SUBSTRING_INDEX(", @zi, ",'-',-1) AS ora_sfarsit FROM program_functionare pf INNER JOIN angajati a INNER JOIN policlinici p WHERE a.id = ", id_angajat, " AND a.id_policlinica = p.id AND p.id_program_functionare = pf.id");
        PREPARE stmt FROM @query;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
	-- SELECT @zi FROM program_functionare pf
	-- INNER JOIN angajati a
	-- INNER JOIN policlinici p
	-- WHERE a.id = id_angajat AND a.id_policlinica = p.id AND p.id_program_functionare = pf.id;

END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE OrarPentruDataProgramare (IN id INT, IN _data varchar(10))
BEGIN

    set @id_angajat = ( select id_angajat from medici
                        where medici.id = id);
    set @a = (select count(*) from orar_medici
    where id_medic = id and zi_saptamana_sau_data = _data and zi_sau_data = 1);
    if @a > 0 then
        select om.ora_inceput, om.ora_sfarsit from orar_medici om
        where id_medic = id and zi_saptamana_sau_data = _data and zi_sau_data = 1;
    
    else 
        set @b =
        (select count(*) from orar_medici om
        join zi_saptamana z
        where id_medic = id and zi_sau_data = 0 and dayofweek(_data) = z.id and z.zi = om.zi_saptamana_sau_data);
        if @b > 0 then
            select om.ora_inceput, om.ora_sfarsit from orar_medici om
            join zi_saptamana z
            where id_medic = id and zi_sau_data = 0 and dayofweek(_data) = z.id and z.zi = om.zi_saptamana_sau_data;
        else
            call OrarAngajat(@id_angajat, dayofweek(_data));
        end if;
    end if;
    
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE FinalProgramari (IN id INT, IN _data varchar(10))
BEGIN
	select max(ora_sfarsit) as final_ultima_programare from programari where programari._data = date(_data) and id_medic = id;
END //
DELIMITER ;


