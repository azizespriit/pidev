#include "connexion_bd.h"

connexion_bd::connexion_bd()
{

}

bool connexion_bd::createconnect()
{
    bool test=false;
    QSqlDatabase db = QSqlDatabase::addDatabase("QODBC");
    db.setDatabaseName("test-bd");
    /*db.setUserName("nnoobb");//inserer nom de l'utilisateur
    db.setPassword("b");//inserer mot de passe de cet utilisateur*/

    db.setUserName("aziz");//inserer nom de l'utilisateur
    db.setPassword("a");//inserer mot de passe de cet utilisateur

    if (db.open())
        test=true;

    return  test;
}
