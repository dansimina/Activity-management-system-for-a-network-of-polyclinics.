-- "NU AVEM FOREIGN KEY AICI, FACEM LA ALTER TABLE IN ALT FISIER"  Dan asked calmly

CREATE SCHEMA IF NOT EXISTS policlinica;
USE policlinica;

create table if not exists conturi(
	id int auto_increment primary key not null,
	nume_utilizator varchar(20) unique not null,
    parola varchar(20) not null
);

create table if not exists utilizatori(
	id int auto_increment primary key not null,
    id_cont int unique not null,
    departament varchar(20) not null,
    adresa varchar(50) not null,
    cnp varchar(13) not null,
    nume varchar(30) not null,
    prenume varchar(30) not null,
    telefon varchar(10) not null,
    email varchar(30) not null,
	iban varchar(34) not null,
    data_angajarii date not null,
    rol varchar(15) not null
);

create table if not exists policlinici(
	id int auto_increment primary key not null,
    id_program_functionare int not null,
    adresa varchar(100) not null,
    denumire varchar(50) not null
);

create table if not exists angajati(
	id int auto_increment primary key not null,
    id_utilizator int unique not null,
    id_policlinica int not null,
    functie varchar(20) not null,
    salariu_negociat decimal(8, 2) not null,
    numar_ore int not null
);

create table if not exists medici(
	id int auto_increment primary key not null,
    id_angajat int unique not null,
    cod_parafa varchar(10) unique not null,
    titlu_stiintific varchar(50),
    post_didactic varchar(50),
    venit_aditional decimal(3, 2) default 0
);

create table if not exists asistenti_medicali(
	id int auto_increment primary key not null,
    id_angajat int unique not null,
    tip varchar(20) not null,
    grad varchar(20) not null
);

create table if not exists program_functionare(
	id int auto_increment primary key not null,
    duminica varchar(20) not null,
    luni varchar(20) not null,
    marti varchar(20) not null,
    miercuri varchar(20) not null,
    joi varchar(20) not null,
    vineri varchar(20) not null,
    sambata varchar(20) not null
);

create table if not exists pacienti(
	id int auto_increment primary key not null,
    nume varchar(20),
    prenume varchar(20)
);

create table if not exists specialitati(
	id int auto_increment primary key not null,
    id_medic int not null,
    nume_specialitate varchar(20) not null,
    grad varchar(20) not null
);

create table if not exists concedii(
    id int auto_increment primary key not null,
    id_angajat int not null,
    data_inceput date not null,
    data_sfarsit date not null
);

create table if not exists bonuri_fiscale(
    id int auto_increment primary key not null,
    id_raport int not null,
    id_angajat int not null,
    total decimal(10,2) not null,
    data_emitere date not null
);

create table if not exists rapoarte(
    id int auto_increment primary key not null,
    id_programare int unique not null,
    id_medic int not null,
    id_asistent int,
    nume_medic_recomandare varchar(20) default " ",
    prenume_medic_recomandare varchar(20) default " ",
    istoric varchar(5000) default " ",
    simptome varchar(1000) default " ",
    diagnostic varchar(200) default " ",
    recomandari varchar(5000) default " ",
    parafa boolean default false
);

create table if not exists programari(
    id int auto_increment primary key not null,
    id_policlinica int not null,
    id_angajat int not null,
    id_pacient int not null,
    id_medic int not null,
    _data date not null,
    ora_inceput time not null,
    ora_sfarsit time not null,
    inregistrat boolean not null
);

create table if not exists servicii(
    id int auto_increment primary key not null,
    nume_serviciu varchar(50) not null,
    pret decimal(10,2) not null,
    durata time not null
);

create table if not exists orar_medici(
    id_medic int not null,
    zi_sau_data boolean not null default false,
    zi_saptamana_sau_data varchar(20) not null,
    ora_inceput time not null,
    ora_sfarsit time not null
);

create table if not exists servicii_oferite_policlinica(
    id_policlinica int not null,
    id_serviciu int not null
);

create table if not exists servicii_specialitate(
    id_specialitate int not null,
    id_serviciu int not null
);

create table if not exists servicii_oferite_programare(
    id_programare int not null,
    id_serviciu int not null
);

create table if not exists servicii_oferite_raport(
    id_raport int not null,
    id_serviciu int not null,
    investigatii varchar(500) default " "
);

create table if not exists servicii_specialitate_medic(
    id_serviciu int not null,
    id_specialitate int not null
);

create table if not exists zi_saptamana(
	id int not null,
    zi varchar(10) not null
);
