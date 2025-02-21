-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 14 fév. 2025 à 16:02
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `planvent`
--

-- --------------------------------------------------------

--
-- Structure de la table `avis`
--

CREATE TABLE `avis` (
  `ID` int(11) NOT NULL,
  `Content` varchar(400) DEFAULT NULL,
  `ServiceID` int(11) DEFAULT NULL,
  `OrganisateurID` int(11) DEFAULT NULL,
  `CreatedAt` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `id_commande` int(11) NOT NULL,
  `totalPrice` double NOT NULL,
  `address` varchar(255) NOT NULL,
  `paymentMethod` enum('espece','cheque') NOT NULL,
  `id_evenement` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `id_service` int(11) NOT NULL,
  `id_prestataire` int(11) NOT NULL,
  `id_reservation` int(11) NOT NULL,
  `createdAt` datetime DEFAULT current_timestamp(),
  `updateAt` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `equipement`
--

CREATE TABLE `equipement` (
  `id_equipement` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `image` int(255) NOT NULL,
  `price` int(11) NOT NULL,
  `disponibility` tinyint(1) NOT NULL,
  `quantity` int(11) NOT NULL,
  `id_prestataire` int(11) NOT NULL,
  `createdAt` datetime DEFAULT current_timestamp(),
  `updatedAt` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `evenement`
--

CREATE TABLE `evenement` (
  `id_evenement` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) NOT NULL,
  `budget` float DEFAULT NULL,
  `duration` varchar(10) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `id_type` int(11) DEFAULT NULL,
  `id_organisateur` int(11) DEFAULT NULL,
  `createdAt` datetime DEFAULT current_timestamp(),
  `updatedAt` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `organisateur`
--

CREATE TABLE `organisateur` (
  `id_organisateur` int(11) NOT NULL,
  `yearsOfExperience` int(11) NOT NULL,
  `numberOfEventsOrganized` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `prestataire`
--

CREATE TABLE `prestataire` (
  `id_prestataire` int(11) NOT NULL,
  `speciality` varchar(255) NOT NULL,
  `experience` text NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id_produit` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `category` varchar(255) NOT NULL,
  `id_prestataire` int(11) NOT NULL,
  `createdAt` datetime DEFAULT current_timestamp(),
  `updateAt` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `reclamation`
--

CREATE TABLE `reclamation` (
  `id_reclamation` int(11) NOT NULL,
  `object` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `ticket` int(11) NOT NULL,
  `id_service` int(11) NOT NULL,
  `id_organisateur` int(11) NOT NULL,
  `id_equipement` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `createdAT` datetime DEFAULT current_timestamp(),
  `updateAT` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `reponse`
--

CREATE TABLE `reponse` (
  `id_reponse` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `id_reclamation` int(11) NOT NULL,
  `createdAt` datetime DEFAULT current_timestamp(),
  `updateAT` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE `reservation` (
  `id_reservation` int(11) NOT NULL,
  `id_equipement` int(11) NOT NULL,
  `id_organisateur` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `createdAt` datetime DEFAULT current_timestamp(),
  `updatedAt` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `service`
--

CREATE TABLE `service` (
  `ID` int(11) NOT NULL,
  `Title` varchar(20) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Price` float DEFAULT NULL,
  `Disponibility` tinyint(1) DEFAULT NULL CHECK (`Disponibility` in (0,1)),
  `Type` enum('photographie','decoration','logistique','traiteur') DEFAULT NULL,
  `RateCount` int(11) DEFAULT 0,
  `RateSum` float DEFAULT 0,
  `PrestataireID` int(11) DEFAULT NULL,
  `CreatedAt` datetime DEFAULT current_timestamp(),
  `UpdatedAt` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `typeevenement`
--

CREATE TABLE `typeevenement` (
  `id_typeEvenement` int(11) NOT NULL,
  `name` int(11) NOT NULL,
  `createdAt` datetime DEFAULT current_timestamp(),
  `updateAt` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `cin` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `gender` enum('Femme','Homme') NOT NULL,
  `phone` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL,
  `roles` enum('admin','organisateur','prestataire') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `isLocked` tinyint(1) DEFAULT NULL,
  `failedAttempts` int(11) DEFAULT NULL,
  `lockoutTime` datetime DEFAULT NULL,
  `createdAt` datetime DEFAULT current_timestamp(),
  `updateAt` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `avis`
--
ALTER TABLE `avis`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ServiceID` (`ServiceID`),
  ADD KEY `OrganisateurID` (`OrganisateurID`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`id_commande`),
  ADD KEY `fk_commande_prestataire` (`id_prestataire`),
  ADD KEY `fk_commande_reservation` (`id_reservation`),
  ADD KEY `fk_commande_produit` (`id_produit`),
  ADD KEY `fk_commande_evenement` (`id_evenement`);

--
-- Index pour la table `equipement`
--
ALTER TABLE `equipement`
  ADD PRIMARY KEY (`id_equipement`),
  ADD KEY `FK_equipement_prestataire` (`id_prestataire`);

--
-- Index pour la table `evenement`
--
ALTER TABLE `evenement`
  ADD PRIMARY KEY (`id_evenement`),
  ADD KEY `fk_evenement_organisateur` (`id_organisateur`),
  ADD KEY `fk_evenement_typeEvenement` (`id_type`);

--
-- Index pour la table `organisateur`
--
ALTER TABLE `organisateur`
  ADD PRIMARY KEY (`id_organisateur`),
  ADD KEY `fk_organisateur_user` (`user_id`);

--
-- Index pour la table `prestataire`
--
ALTER TABLE `prestataire`
  ADD PRIMARY KEY (`id_prestataire`),
  ADD KEY `FK_prestataire_user` (`user_id`),
  ADD KEY `speciality` (`speciality`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id_produit`),
  ADD KEY `fk_produit_prestataire` (`id_prestataire`);

--
-- Index pour la table `reclamation`
--
ALTER TABLE `reclamation`
  ADD PRIMARY KEY (`id_reclamation`),
  ADD KEY `fk_reclamtion_service` (`id_service`),
  ADD KEY `fk_reclamation_organisateur` (`id_organisateur`),
  ADD KEY `fk_reclamation_equipement` (`id_equipement`),
  ADD KEY `fk_reclamation_produit` (`id_produit`);

--
-- Index pour la table `reponse`
--
ALTER TABLE `reponse`
  ADD PRIMARY KEY (`id_reponse`),
  ADD KEY `fk_reponse_reclamation` (`id_reclamation`);

--
-- Index pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id_reservation`),
  ADD KEY `FK_reservation_equipement` (`id_equipement`),
  ADD KEY `fk_reservation_organisateur` (`id_organisateur`);

--
-- Index pour la table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `PrestataireID` (`PrestataireID`);

--
-- Index pour la table `typeevenement`
--
ALTER TABLE `typeevenement`
  ADD PRIMARY KEY (`id_typeEvenement`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`),
  ADD KEY `lastName` (`lastName`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `id_commande` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `equipement`
--
ALTER TABLE `equipement`
  MODIFY `id_equipement` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `organisateur`
--
ALTER TABLE `organisateur`
  MODIFY `id_organisateur` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `prestataire`
--
ALTER TABLE `prestataire`
  MODIFY `id_prestataire` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id_produit` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `reclamation`
--
ALTER TABLE `reclamation`
  MODIFY `id_reclamation` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `reponse`
--
ALTER TABLE `reponse`
  MODIFY `id_reponse` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id_reservation` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `typeevenement`
--
ALTER TABLE `typeevenement`
  MODIFY `id_typeEvenement` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `avis`
--
ALTER TABLE `avis`
  ADD CONSTRAINT `avis_ibfk_1` FOREIGN KEY (`ServiceID`) REFERENCES `service` (`ID`),
  ADD CONSTRAINT `avis_ibfk_2` FOREIGN KEY (`OrganisateurID`) REFERENCES `organisateur` (`id_organisateur`);

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `fk_commande_evenement` FOREIGN KEY (`id_evenement`) REFERENCES `evenement` (`id_evenement`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_commande_prestataire` FOREIGN KEY (`id_prestataire`) REFERENCES `prestataire` (`id_prestataire`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_commande_produit` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id_produit`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_commande_reservation` FOREIGN KEY (`id_reservation`) REFERENCES `reservation` (`id_reservation`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `equipement`
--
ALTER TABLE `equipement`
  ADD CONSTRAINT `FK_equipement_prestataire` FOREIGN KEY (`id_prestataire`) REFERENCES `prestataire` (`id_prestataire`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `evenement`
--
ALTER TABLE `evenement`
  ADD CONSTRAINT `fk_evenement_organisateur` FOREIGN KEY (`id_organisateur`) REFERENCES `organisateur` (`id_organisateur`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_evenement_typeEvenement` FOREIGN KEY (`id_type`) REFERENCES `typeevenement` (`id_typeEvenement`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `organisateur`
--
ALTER TABLE `organisateur`
  ADD CONSTRAINT `fk_organisateur_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `prestataire`
--
ALTER TABLE `prestataire`
  ADD CONSTRAINT `FK_prestataire_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `fk_produit_prestataire` FOREIGN KEY (`id_prestataire`) REFERENCES `prestataire` (`id_prestataire`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `reclamation`
--
ALTER TABLE `reclamation`
  ADD CONSTRAINT `fk_reclamation_equipement` FOREIGN KEY (`id_equipement`) REFERENCES `equipement` (`id_equipement`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_reclamation_organisateur` FOREIGN KEY (`id_organisateur`) REFERENCES `organisateur` (`id_organisateur`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_reclamation_produit` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id_produit`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_reclamtion_service` FOREIGN KEY (`id_service`) REFERENCES `service` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `reponse`
--
ALTER TABLE `reponse`
  ADD CONSTRAINT `fk_reponse_reclamation` FOREIGN KEY (`id_reclamation`) REFERENCES `reclamation` (`id_reclamation`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `FK_reservation_equipement` FOREIGN KEY (`id_equipement`) REFERENCES `equipement` (`id_equipement`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_reservation_organisateur` FOREIGN KEY (`id_organisateur`) REFERENCES `organisateur` (`id_organisateur`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `service`
--
ALTER TABLE `service`
  ADD CONSTRAINT `service_ibfk_1` FOREIGN KEY (`PrestataireID`) REFERENCES `prestataire` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
