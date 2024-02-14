
DELIMITER //
CREATE FUNCTION VenitTotalPoliclinici() RETURNS DECIMAL(10, 2)
DETERMINISTIC
BEGIN
    DECLARE totalVenit DECIMAL(10, 2);

    SELECT SUM(total) INTO totalVenit
    FROM (
        SELECT SUM(total) AS total
        FROM programari p
        INNER JOIN servicii_oferite_programare sop ON p.id = sop.id_programare
        INNER JOIN servicii s ON sop.id_serviciu = s.id
        GROUP BY p.id

        UNION ALL

        SELECT SUM(total) AS total
        FROM bonuri_fiscale
    ) AS venituri;

    RETURN totalVenit;
END //
DELIMITER ;


DELIMITER //
CREATE FUNCTION Cheltuieli() RETURNS DECIMAL(10, 2)
DETERMINISTIC
BEGIN
    DECLARE totalCheltuieli DECIMAL(10, 2);

    SELECT SUM(salariu_negociat * numar_ore) INTO totalCheltuieli
    FROM angajati;

    RETURN totalCheltuieli;
END //
DELIMITER ;


DELIMITER //
CREATE FUNCTION ProfitLunar(luna INT, an INT) RETURNS DECIMAL(10, 2)
DETERMINISTIC
BEGIN
    DECLARE venit DECIMAL(10, 2);
    DECLARE cheltuieli DECIMAL(10, 2);

    SELECT VenitTotalPoliclinici() INTO venit;
    SELECT Cheltuieli() INTO cheltuieli;

    RETURN venit - cheltuieli;
END //
DELIMITER ;


DELIMITER //
CREATE FUNCTION ProfitMedicLocatieSpecialitate(idMedic INT) RETURNS DECIMAL(10, 2)
DETERMINISTIC
BEGIN
    DECLARE venit DECIMAL(10, 2);
    DECLARE cheltuieli DECIMAL(10, 2);

    SELECT SUM(total) INTO venit
    FROM (
        SELECT SUM(total) AS total
        FROM programari p
        INNER JOIN servicii_oferite_programare sop ON p.id = sop.id_programare
        INNER JOIN servicii s ON sop.id_serviciu = s.id
        WHERE p.id_medic = idMedic
        GROUP BY p.id

        UNION ALL

        SELECT SUM(total) AS total
        FROM bonuri_fiscale bf
        INNER JOIN rapoarte r ON bf.id_raport = r.id
        WHERE r.id_medic = idMedic
    ) AS venituri;

    SELECT SUM(salariu_negociat * numar_ore) INTO cheltuieli
    FROM angajati
    WHERE id_utilizator = idMedic;

    RETURN venit - cheltuieli;
END //
DELIMITER ;


DELIMITER //
CREATE FUNCTION SalariuAngajat(idAngajat INT) RETURNS DECIMAL(10, 2)
DETERMINISTIC
BEGIN
    DECLARE salariu DECIMAL(10, 2);

    SELECT salariu_negociat * numar_ore INTO salariu
    FROM angajati
    WHERE id = idAngajat;

    RETURN salariu;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE TotalServiciiOferiteSpecialitate(IN idSpecialitate INT, IN _data DATE)
BEGIN 
	select sum(servicii.pret) as total_servicii_oferite from ((((specialitati 
	inner join servicii_specialitate on servicii_specialitate.id_specialitate = specialitati.id)
	inner join servicii on servicii_specialitate.id_serviciu = servicii.id) 
	inner join servicii_oferite_raport on servicii_oferite_raport.id_serviciu = servicii.id)
	inner join rapoarte on servicii_oferite_raport.id_raport = rapoarte.id)
    inner join bonuri_fiscale on bonuri_fiscale.id_raport = rapoarte.id
    where specialitati.id = idSpecialitate and month(bonuri_fiscale.data_emitere) = month(_data) and year(bonuri_fiscale.data_emitere) = year(_data);
END 
//
DELIMITER ;

