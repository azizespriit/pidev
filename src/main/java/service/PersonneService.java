package service;

import entite.Personne;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonneService implements IService<Personne>{

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public PersonneService(){
        cnx= DataSource.getInstance().getConnection();
    }


    @Override
    public void insert(Personne personne) {
        String requete="insert into personne (nom,prenom) " +
                "values('"+personne.getNom()+"','"+personne.getPrenom()+"')";
        try {
            ste=cnx.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void insertPst(Personne p){
        String requete="insert into personne (nom,prenom) values(?,?)";
        try {
            pst=cnx.prepareStatement(requete);
            pst.setString(1,p.getNom());
            pst.setString(2,p.getPrenom());
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Personne personne) {

    }

    @Override
    public void delete(Personne personne) {
        String requete="delete from personne where id=?";
        try {
            pst= cnx.prepareStatement(requete);
            pst.setInt(1,personne.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Personne> readAll() {
        List<Personne> list=new ArrayList<>();
        String requete="select * from personne";
        try {
            ste=cnx.createStatement();
            rs=ste.executeQuery(requete);
            while(rs.next()){

                list.add(new Personne(rs.getInt(1),
                        rs.getString(2),
                        rs.getString("prenom")));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Personne readById(int id) {
        return null;
    }
}
