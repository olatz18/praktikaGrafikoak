package proiektuAntzerakoa;
import java.sql.ResultSet;

public class SSCCE {

private void go() {

DBKudeatzaileSQLite dbkud = DBKudeatzaileSQLite.getInstantzia();

String aux = "SELECT  JSON_EXTRACT(message, '$.price') prezioa FROM transactions";

System.out.println(aux);

ResultSet rs = dbkud.execSQL(aux);

}

public static void main(String[] args) {

new SSCCE().go();

}

}