use policlinica;
-- policlinici.id, policlinici.id_program_functionare, policlinici.adresa, policlinici.denumire
-- SELECT * FROM 
-- ((policlinici inner join servicii_oferite_policlinica on policlinici.id = servicii_oferite_policlinica.id_policlinica) inner join servicii on servicii.id = servicii_oferite_policlinica.id_serviciu)
-- inner join 
-- ((medici inner join specialitati on medici.id = specialitati.id_medic) inner join servicii_specialitate on specialitati.id = servicii_specialitate.id_specialitate) on servicii.id = servicii_specialitate.id_serviciu where medici.id = 1;

-- select pCurenta.id, pCurenta.id_program_functionare, pCurenta.adresa, pCurenta.denumire,
-- (SELECT count(*) FROM 
-- ((policlinici inner join servicii_oferite_policlinica on policlinici.id = servicii_oferite_policlinica.id_policlinica) inner join servicii on servicii.id = servicii_oferite_policlinica.id_serviciu)
-- inner join 
-- ((medici inner join specialitati on medici.id = specialitati.id_medic) inner join servicii_specialitate on specialitati.id = servicii_specialitate.id_specialitate) on servicii.id = servicii_specialitate.id_serviciu where medici.id = 1 and policlinici.id = pCurenta.id) as nr_servicii_compatibile,
-- (SELECT count(*) FROM (policlinici inner join angajati on policlinici.id = angajati.id_policlinica) inner join medici on angajati.id = medici.id_angajat where policlinici.id = pCurenta.id) as nr_medici
-- from policlinici as pCurenta;

DELIMITER //
CREATE PROCEDURE repartizare(IN id_medic INT)
BEGIN
	select pCurenta.id, pCurenta.id_program_functionare, pCurenta.adresa, pCurenta.denumire,
	(SELECT count(*) FROM 
	((policlinici inner join servicii_oferite_policlinica on policlinici.id = servicii_oferite_policlinica.id_policlinica) inner join servicii on servicii.id = servicii_oferite_policlinica.id_serviciu)
	inner join 
	((medici inner join specialitati on medici.id = specialitati.id_medic) inner join servicii_specialitate on specialitati.id = servicii_specialitate.id_specialitate) on servicii.id = servicii_specialitate.id_serviciu where medici.id = id_medic and policlinici.id = pCurenta.id) as nr_servicii_compatibile,
	(SELECT count(*) FROM (policlinici inner join angajati on policlinici.id = angajati.id_policlinica) inner join medici on angajati.id = medici.id_angajat where policlinici.id = pCurenta.id) as nr_medici
	from policlinici as pCurenta;
END
//