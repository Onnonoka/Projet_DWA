package org.donnees;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-03-22T14:30:04")
@StaticMetamodel(Joueur.class)
public class Joueur_ { 

    public static volatile SingularAttribute<Joueur, String> motDePasse;
    public static volatile SingularAttribute<Joueur, String> ville;
    public static volatile SingularAttribute<Joueur, BigDecimal> codeJoueur;
    public static volatile SingularAttribute<Joueur, Character> sexe;
    public static volatile SingularAttribute<Joueur, String> pseudo;
    public static volatile SingularAttribute<Joueur, BigInteger> age;

}