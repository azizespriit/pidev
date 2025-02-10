package test;

import entite.Personne;
import service.PersonneService;
import utils.DataSource;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Personne p=new Personne();
        p.setId(6);

        PersonneService ps=new PersonneService();
        ps.readAll().forEach(System.out::println);
    }
}