INSERT INTO conturi (nume_utilizator, parola) VALUES
    ('superadmin', 'parola123'),
    ('receptioner', 'receptioner123'),
    ('resurseumane', 'resurse123'),
    ('economic', 'economic123'),
    ('medic', 'medic123'),
    ("medic2", "medic111"),
    ("asistent", "asistent123");


INSERT INTO utilizatori (id_cont, departament, adresa, cnp, nume, prenume, telefon, email, iban, data_angajarii, rol) VALUES
    (1, 'Administratie', 'Strada Administratiei, nr. 1, Bucuresti', '1234567890123', 'Ion', 'Popescu', '0720123456', 'ion.popescu@email.com', 'RO123456789012345678901234567890', '2022-01-01', 'super_admin'),
    (2, 'Medical', 'Strada Receptiei, nr. 2, Bucuresti', '2345678901234', 'Maria', 'Ionescu', '0720345678', 'maria.ionescu@email.com', 'RO234567890123456789012345678901', '2022-02-01', 'utilizator'),
    (3, 'Resurse Umane', 'Strada Resurselor, nr. 3, Bucuresti', '3456789012345', 'Alex', 'Georgescu', '0720567890', 'alex.georgescu@email.com', 'RO345678901234567890123456789012', '2022-03-01', 'utilizator'),
    (4, 'Economic', 'Strada Comertului, nr. 4, Bucuresti', '4567890123456', 'Elena', 'Dumitrescu', '0720789012', 'elena.dumitrescu@email.com', 'RO456789012345678901234567890123', '2022-04-01', 'utilizator'),
    (5, 'Medical', 'Strada Medicilor, nr. 5, Bucuresti', '5678901234567', 'Radu', 'Stoica', '0720901234', 'radu.stoica@email.com', 'RO567890123456789012345678901234', '2022-05-01', 'utilizator'),
	(6, 'Medical', 'Strada Medicilor Alta, nr. 5, Cluj', '3213131313154', 'Ionel', 'Marcel', '0720903214', 'ionel.marcel@email.com', 'RO567890123456789012345678901234', '2020-05-01', 'utilizator'),
	(7, 'Medical', 'Strada Asistentilor, nr. 7, Cluj', '3213137846554', 'Pop', 'Ana', '0720928364', 'ana.pop@email.com', 'RO567890123459453826345678901234', '2010-04-02', 'utilizator');

    
INSERT INTO program_functionare (duminica, luni, marti, miercuri, joi, vineri, sambata) VALUES
    ('09:00:00-17:00:00', '08:00:00-18:00:00', '08:00:00-18:00:00', '08:00:00-18:00:00', '08:00:00-18:00:00', '08:00:00-16:00:00', '10:00:00-14:00:00'),
    ('10:00:00-16:00:00', '09:00:00-17:00:00', '09:00:00-17:00:00', '09:00:00-17:00:00', '09:00:00-17:00:00', '09:00:00-15:00:00', '11:00:00-13:00:00'),
    ('08:00:00-14:00:00', '07:00:00-15:00:00', '07:00:00-15:00:00', '07:00:00-15:00:00', '07:00:00-15:00:00', '07:00:00-13:00:00', '09:00:00-12:00:00'),
    ('11:00:00-18:00:00', '10:00:00-19:00:00', '10:00:00-19:00:00', '10:00:00-19:00:00', '10:00:00-19:00:00', '10:00:00-17:00:00', '12:00:00-16:00:00'),
    ('12:00:00-20:00:00', '11:00:00-21:00:00', '11:00:00-21:00:00', '11:00:00-21:00:00', '11:00:00-21:00:00', '11:00:00-19:00:00', '13:00:00-17:00:00');


INSERT INTO policlinici (id_program_functionare, adresa, denumire) VALUES
    (1, 'Strada Sanatatii, nr. 1, Bucuresti', 'Policlinica Centrala'),
    (2, 'Strada Mediciilor, nr. 2, Bucuresti', 'Policlinica Nord'),
    (3, 'Strada Pacientilor, nr. 3, Bucuresti', 'Policlinica Sud'),
    (1, 'Strada Doctorilor, nr. 4, Bucuresti', 'Policlinica Vest'),
    (2, 'Strada Asistentilor, nr. 5, Bucuresti', 'Policlinica Est');
    
    INSERT INTO angajati (id_utilizator, id_policlinica, functie, salariu_negociat, numar_ore) VALUES
    (1, 1, 'administrator', 5000.00, 40),
    (2, 3, 'receptioner', 2500.00, 30),
    (3, 3, 'resurse_umane', 3500.00, 35),
    (4, 4, 'economic', 3000.00, 37),
    (5, 5, 'medic', 7000.00, 45),
    (6, 3, 'medic', 9000.00, 40),
	(7, 3, 'asistent_medical', 8000.00, 30);
    
    INSERT INTO pacienti (nume, prenume) VALUES
    ('Popescu', 'Ana'),
    ('Ionescu', 'Mihai'),
    ('Georgescu', 'Elena'),
    ('Dumitrescu', 'Ionut'),
    ('Stoica', 'Cristina');
    
    INSERT INTO medici (id_angajat, cod_parafa, titlu_stiintific, post_didactic, venit_aditional)
VALUES
    (5, 'MP12345', 'Doctor in Medicina', 'Conferentiar Universitar', 0.2),
    (6, 'MP67890', 'Medic Primar Cardiologie', 'Asistent Universitar', 0.4);
--     (25, 'MP54321', 'Medic Specialist ORL', 'Lector Universitar', 0.2),
--     (25, 'MP98765', 'Doctor in Stomatologie', 'Profesor Universitar', 0),
--     (25, 'MP24680', 'Medic Specialist Neurologie', 'Conferentiar Universitar', 0.3);
    
    INSERT INTO asistenti_medicali (id_angajat, tip, grad) VALUES 
    (7, 'generalist', 'grad2');
    
    INSERT INTO specialitati (id_medic, nume_specialitate, grad) VALUES
    (1, 'Cardiologie', 'Primar'),
    (2, 'Radiologie', 'Specialist'),
    (1, 'ORL', 'Primar'),
    (1, 'Stomatologie', 'Primar'),
    (2, 'Neurologie', 'Primar');
    
    INSERT INTO servicii (nume_serviciu, pret, durata) VALUES
    ('Consultatie Medic Primar', 150.00, '00:30:00'),
    ('Radiografie', 70.00, '00:15:00'),
    ('Tratament ORL', 100.00, '00:45:00'),
    ('Extractie Dentara', 120.00, '00:20:00'),
    ('Consultatie Neurolog', 180.00, '00:40:00');
    
    INSERT INTO orar_medici (id_medic, zi_sau_data, zi_saptamana_sau_data, ora_inceput, ora_sfarsit) VALUES
    (1, false, 'Luni', '10:00:00', '18:00:00'),
    (2, false, 'Miercuri', '09:00:00', '17:00:00'),
    (1, false, 'Vineri', '07:30:00', '15:30:00'),
    (1, true, '2023-06-30', '11:00:00', '17:00:00'),
    (2, true, '2023-08-15', '13:00:00', '19:00:00');

-- INSERT INTO policlinica.angajati (id_utilizator, id_policlinica, functie, salariu_negociat, numar_ore)
-- VALUES  (5, 1, "medic", 6969.69, 55),
-- 	(4, 1, "super erou", 6969.69, 55);

INSERT INTO servicii_specialitate VALUES
	(1, 1),
	(2, 2),
	(3, 3),
	(4, 4),
    (5, 5),
    (2, 1);

INSERT INTO zi_saptamana VALUES
	(1, "Duminica"),
	(2, "Luni"),
	(3, "Marti"),
	(4, "Miercuri"),       
	(5, "Joi"),
	(6, "Vineri"),
	(7, "Sambata");

insert into programari (id_policlinica, id_angajat, id_pacient, id_medic, _data, ora_inceput, ora_sfarsit, inregistrat) values
(5, 5, 1, 1, "2024-01-19", "11:00:00", "13:00:00", false);
insert into programari (id_policlinica, id_angajat, id_pacient, id_medic, _data, ora_inceput, ora_sfarsit, inregistrat) values
(5, 5, 2, 1, "2024-01-19", "13:00:00", "14:30:00", false);
insert into programari (id_policlinica, id_angajat, id_pacient, id_medic, _data, ora_inceput, ora_sfarsit, inregistrat) values
(5, 5, 1, 1, "2024-01-22", "10:00:00", "12:00:00", false);

insert into servicii_oferite_policlinica values
	(1, 1),
    (1, 3),
    (1, 4),
    (1, 5),
    (2, 4),
    (2, 5),
    (3, 1),
    (3, 2),
    (3, 3),
    (3, 4),
    (3, 5),
    (4, 1),
    (4, 2),
    (4, 3),
    (5, 1),
    (5, 2),
    (5, 4);

