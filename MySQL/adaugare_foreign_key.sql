ALTER TABLE policlinici
ADD FOREIGN KEY (id_program_functionare) REFERENCES program_functionare(id);

ALTER TABLE servicii_oferite_policlinica
ADD FOREIGN KEY (id_policlinica) REFERENCES policlinici(id),
ADD FOREIGN KEY (id_serviciu) REFERENCES servicii(id);

ALTER TABLE servicii_specialitate
ADD FOREIGN KEY (id_specialitate) REFERENCES specialitati(id),
ADD FOREIGN KEY (id_serviciu) REFERENCES servicii(id);

ALTER TABLE servicii_oferite_programare
ADD FOREIGN KEY (id_programare) REFERENCES programari(id),
ADD FOREIGN KEY (id_serviciu) REFERENCES servicii(id);

ALTER TABLE servicii_oferite_raport
ADD FOREIGN KEY (id_raport) REFERENCES rapoarte(id),
ADD FOREIGN KEY (id_serviciu) REFERENCES servicii(id);

ALTER TABLE specialitati
ADD FOREIGN KEY (id_medic) REFERENCES medici(id);

ALTER TABLE utilizatori
ADD FOREIGN KEY (id_cont) REFERENCES conturi(id);

ALTER TABLE angajati
ADD FOREIGN KEY (id_utilizator) REFERENCES utilizatori(id),
ADD FOREIGN KEY (id_policlinica) REFERENCES policlinici(id);

ALTER TABLE concedii
ADD FOREIGN KEY (id_angajat) REFERENCES angajati(id);

ALTER TABLE asistenti_medicali
ADD FOREIGN KEY (id_angajat) REFERENCES angajati(id);

ALTER TABLE medici
ADD FOREIGN KEY (id_angajat) REFERENCES angajati(id);

ALTER TABLE bonuri_fiscale
ADD FOREIGN KEY (id_raport) REFERENCES rapoarte(id),
ADD FOREIGN KEY (id_angajat) REFERENCES angajati(id);

ALTER TABLE orar_medici
ADD FOREIGN KEY (id_medic) REFERENCES medici(id);

ALTER TABLE rapoarte
ADD FOREIGN KEY (id_programare) REFERENCES programari(id),
ADD FOREIGN KEY (id_medic) REFERENCES medici(id),
ADD FOREIGN KEY (id_asistent) REFERENCES asistenti_medicali(id);

ALTER TABLE programari
ADD FOREIGN KEY (id_policlinica) REFERENCES policlinici(id),
ADD FOREIGN KEY (id_angajat) REFERENCES angajati(id),
ADD FOREIGN KEY (id_pacient) REFERENCES pacienti(id),
ADD FOREIGN KEY (id_medic) REFERENCES medici(id);