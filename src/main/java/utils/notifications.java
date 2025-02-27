package utils;

import java.awt.*;
import java.awt.TrayIcon.MessageType;


public class notifications {

    public static void showNotification(String title, String message) {
        // Vérifier si SystemTray est supporté sur le système
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray non supporté !");
            return;
        }

        try {
            // Obtenir l'instance du SystemTray
            SystemTray tray = SystemTray.getSystemTray();

            // Charger une icône (REMPLACE "icon.png" par le chemin réel de ton icône)
            Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

            // Créer une icône pour la barre des tâches
            TrayIcon trayIcon = new TrayIcon(image, "Sportify");
            trayIcon.setImageAutoSize(true); // Adapter la taille de l'icône automatiquement
            trayIcon.setToolTip("Sportify Notification");

            // Ajouter l'icône au SystemTray
            tray.add(trayIcon);

            // Afficher la notification
            trayIcon.displayMessage(title, message, MessageType.INFO);

        } catch (AWTException e) {
            System.out.println("Erreur lors de l'affichage de la notification !");
            e.printStackTrace();
        }
    }
}
