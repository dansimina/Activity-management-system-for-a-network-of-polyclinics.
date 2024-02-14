

INSERT INTO programari (id_policlinica, id_angajat, id_pacient, id_medic, _data, ora_inceput, ora_sfarsit, inregistrat)
VALUES
    (5, 5, 1, 1, '2024-01-16', '14:00:00', '15:30:00', true),
    (4, 5, 2, 1, '2024-01-21', '09:30:00', '11:00:00', false),
    (3, 6, 1, 2, '2024-01-22', '14:30:00', '16:00:00', false),
    (2, 6, 2, 2, '2024-01-10', '10:00:00', '12:00:00', true),
    (1, 5, 1, 1, '2024-01-24', '11:30:00', '13:00:00', false);
    

INSERT INTO rapoarte (id_programare, id_medic, nume_medic_recomandare, prenume_medic_recomandare, istoric, simptome, diagnostic, recomandari, parafa)
VALUES
    (1, 1, ' ', ' ', 'Pacientul a prezentat dureri toracice și dificultăți de respirație...', 'Pacientul a acuzat greață și amețeli...', 'Infarct miocardic acut', 'Internare imediată și administrare de medicamente anti-coagulante.', true),
    (2, 2, ' ', ' ', 'Pacienta a prezentat dureri abdominale și senzație de arsură la stomac...', 'Pacienta a relatat episoade frecvente de greață...', 'Gastrită acută', 'Prescriere medicament antiacide și regim alimentar.', true),
    (3, 1, ' ', ' ', 'Pacientul a raportat dureri de cap severe și confuzie...', 'Pacientul prezintă stare de oboseală și lipsă de energie...', 'Migrenă severă', 'Recomandat tratament pentru migrenă și odihnă.', false),
    (4, 1, ' ', ' ', 'Pacientul a fost expus la un alergen și a dezvoltat reacție alergică...', 'Pacientul prezintă erupții cutanate și mâncărimi...', 'Reacție alergică', 'Administrare antihistaminice și evitarea alergenului.', false),
    (5, 2, ' ', ' ', 'Pacientul se confruntă cu oboseală cronică și insomnii...', 'Pacientul a relatat dificultăți în concentrare...', 'Sindrom de oboseală cronică', 'Consiliere psihologică și evaluare amănunțită.', true);

INSERT INTO rapoarte (id_programare, id_medic, nume_medic_recomandare, prenume_medic_recomandare, istoric, simptome, diagnostic, recomandari, parafa)
VALUES
    (7, 2, ' ', ' ', 'Pacientul a prezentat dureri toracice și dificultăți de respirație...', 'Pacientul a acuzat greață și amețeli...', 'Infarct miocardic acut', 'Internare imediată și administrare de medicamente anti-coagulante.', true);

INSERT INTO bonuri_fiscale (id_raport, id_angajat, total, data_emitere)
VALUES
    (1, 5, 250.00, '2024-01-20'),
    (2, 6, 120.00, '2024-01-21'),
    (3, 5, 180.00, '2024-01-22'),
    (4, 5, 70.00, '2024-01-23'),
    (5, 6, 150.00, '2024-01-24');
