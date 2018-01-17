package proiektuAntzerakoa;
import java.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;
public class BTCKud {
private static final BTCKud prezioaKud = new BTCKud();
	private double preziorikMerkeena;
	public static BTCKud getInstantzia(){
		return prezioaKud;
	}

	private BTCKud() {
		// Singleton patroia
	}
	
public List<String[]> getSaltzekoPrezioak(){
	DBKudeatzaileSQLite dbkud = DBKudeatzaileSQLite.getInstantzia();
	String aux = "SELECT  JSON_EXTRACT(message, '$.price')prezioa, JSON_EXTRACT(message,'$.remaining_size') bolumena FROM transactions where JSON_EXTRACT(message, '$.side') = 'sell' and JSON_EXTRACT(message, '$.product_id') = 'BTC-EUR' and JSON_EXTRACT(message, '$.type') = 'open'";
	aux = aux.replace('\\',',');
	ResultSet rs = dbkud.execSQL(aux);		
				List<String[]> emaitzaSaldu = new ArrayList<String[]>();
		
			try {
				while(rs.next()){
					String[] res = new String[2];
					res[0] = rs.getString("prezioa");
					res[1] = rs.getString("bolumena");
					emaitzaSaldu.add(res);
					//System.out.println(res[0]);
					//System.out.println(res[1]);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			return emaitzaSaldu;
	}
public List<String[]> getErostekoPrezioak(){
	DBKudeatzaileSQLite dbkud = DBKudeatzaileSQLite.getInstantzia();
	String aux = "SELECT  JSON_EXTRACT(message, '$.price')prezioa, JSON_EXTRACT(message,'$.remaining_size') bolumena FROM transactions where JSON_EXTRACT(message, '$.side') = 'buy' and JSON_EXTRACT(message, '$.product_id') = 'BTC-EUR' and JSON_EXTRACT(message, '$.type') = 'open'";
	aux = aux.replace('\\',',');
	ResultSet rs = dbkud.execSQL(aux);
	
			List<String[]> emaitzaErosi = new ArrayList<String[]>();
	
		try {
			while(rs.next()){
				String[] res = new String[2];
				res[0] = rs.getString("prezioa");
				res[1] = rs.getString("bolumena");
				emaitzaErosi.add(res);
//				System.out.println(res[0]);
//				System.out.println(res[1]);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return emaitzaErosi;
}

public List<String[]> getBitcoin(){
	DBKudeatzaileSQLite dbkud = DBKudeatzaileSQLite.getInstantzia();
	String aux = "SELECT JSON_EXTRACT(message, '$.product_id') txanpona,  JSON_EXTRACT(message,'$.price') prezioa,  JSON_EXTRACT(message, '$.remaining_size') bolumena,  JSON_EXTRACT(message,'$.side') eragiketa FROM transactions where JSON_EXTRACT(message, '$.product_id') = 'BTC-EUR' and JSON_EXTRACT(message, '$.type') = 'open'";
	aux = aux.replace('\\',',');
	ResultSet rs = dbkud.execSQL(aux);
	
	
			List<String[]> bitcoin = new ArrayList<String[]>();
	
		try {
			while(rs.next()){
				String[] res = new String[4];
				res[0] = rs.getString("txanpona");
//				System.out.println("TXANPONA: "+res[0]);
				res[1] = rs.getString("prezioa");
//				System.out.println("PREZIOA: "+res[1]);
				res[2] = rs.getString("bolumena");
//				System.out.println("BOLUMENA: "+res[2]);
				res[3] = rs.getString("eragiketa");
//				System.out.println("ERAGIKETA: "+res[3]);
				bitcoin.add(res);
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return bitcoin;
}


public double SaltzekoPreziorikMerkeena(HashMap<Double, Double> hm){ // Aurrebaldintza: HashMap-a txikienetik handienera ordenatuta egon behar da
	List<Double> keys = new LinkedList<Double>(hm.keySet());
	double txikiena = 0;
	try{
	txikiena = keys.get(0);
//	System.out.println("txikiena: "+txikiena);
	}catch (Exception e) {
		// TODO: handle exception
	}
	preziorikMerkeena = txikiena;
	return txikiena;
}

public double getPreziorikMerkeena() {
	return preziorikMerkeena;
}

public double ErostekoPreziorikAltuena(HashMap<Double, Double> hm){	// Aurrebaldintza: HashMap-a txikienetik handienera ordenatuta egon behar da
	List<Double> keys = new LinkedList<Double>(hm.keySet());
	double handiena = 0;
	try {
		int azkena = keys.size()-1;
		handiena = keys.get(azkena);
//		System.out.println("handiena: "+handiena);
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	return handiena;
}
public HashMap<Double, Double> prezioBolumenHM(List<String[]> Lista){
	HashMap<Double,Double> emaitza = new HashMap<Double, Double>(); //HashMap<prezioa(proportzioa),bolumena>

	for(String[] s : Lista ){
	double prezioa = Double.parseDouble(s[0]);
	double bolumena = Double.parseDouble(s[1]);
	
			emaitza.put(prezioa, bolumena);		// posizio berria sortu (prezioa, bolumena)
//			System.out.println("prezioa: "+ prezioa+"bolumena: "+ bolumena);
	}		
	return emaitza;
}
public HashMap <Double, Double> tarteakAteraErosi( HashMap<Double,Double> hm, Double handiena){
	HashMap<Double,Double> emaitza = new HashMap<Double, Double>();
	List<Double> keys = new LinkedList<Double>(hm.keySet());
	int i = 0;
	Double tarteaGoitik= handiena;
	System.out.println("tartea goitik: "+tarteaGoitik);
	while(i<=9){
//		System.out.println("whilean sartu da 	i: "+i);
		
	Double tarteaBehetik = tarteaGoitik - 100;
	System.out.println("tartea behetik: "+tarteaBehetik);
	System.out.println("tartea goitik: " + tarteaGoitik);
	DBKudeatzaileSQLite dbkud = DBKudeatzaileSQLite.getInstantzia();
	String aux = "SELECT SUM( JSON_EXTRACT(message, '$.remaining_size')) suma FROM transactions WHERE JSON_EXTRACT(message, '$.side') = 'buy' AND JSON_EXTRACT(message, '$.product_id') = 'BTC-EUR' AND JSON_EXTRACT(message, '$.type') = 'open' AND JSON_EXTRACT(message, '$.price') < '"+ tarteaGoitik+"' AND JSON_EXTRACT(message, '$.price') > '"+tarteaBehetik+"';";
	aux = aux.replace('\\',',');
	ResultSet rs1 = dbkud.execSQL(aux);
			
			try {
				rs1.next();
				Double emaitzaa =rs1.getDouble("suma");
//				System.out.println("emaitza: "+emaitzaa);
				emaitza.put(tarteaBehetik, emaitzaa);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	
	tarteaGoitik= tarteaBehetik;
	
	i++;
	
	}
	return emaitza;
}



public static HashMap<Double,Double> ordenatuTH(HashMap <Double, Double> hm){ //HashMap-ak ordenatzen ditu txikienetik handienera

    List<Double> keys = new LinkedList<Double>(hm.keySet());

    Collections.sort(keys);
    

    HashMap<Double,Double> ordenatua = new LinkedHashMap<Double,Double>();

    for(Double key: keys){
        ordenatua.put(key, hm.get(key));
//        System.out.println(key + ordenatua.get(key));

    }

    return ordenatua;
}

public HashMap<Double, Double> tarteakAteraSaldu(HashMap<Double, Double> hm, Double txikiena) {
	HashMap<Double,Double> emaitza = new HashMap<Double, Double>();
	List<Double> keys = new LinkedList<Double>(hm.keySet());
	int i = 0;
	Double tarteaBehetik= txikiena;
//	System.out.println("tartea goitik: "+tarteaBehetik);
	while(i<=9){
//		System.out.println("whilean sartu da 	i: "+i);
		
	Double tarteaGoitik = tarteaBehetik + 100;
//	System.out.println("tartea behetik: "+tarteaBehetik);
//	System.out.println("tartea goitik: " + tarteaGoitik);
	DBKudeatzaileSQLite dbkud = DBKudeatzaileSQLite.getInstantzia();
	String aux = "SELECT SUM( JSON_EXTRACT(message, '$.remaining_size'))suma FROM transactions WHERE JSON_EXTRACT(message, '$.side') = 'sell' AND JSON_EXTRACT(message, '$.product_id') = 'BTC-EUR' AND JSON_EXTRACT(message, '$.type') = 'open' AND JSON_EXTRACT(message, '$.price') < '"+ tarteaGoitik+"' AND JSON_EXTRACT(message, '$.price') > '"+tarteaBehetik+"';";
	aux = aux.replace('\\',',');
	ResultSet rs1 = dbkud.execSQL(aux);
			
			try {
				rs1.next();
				Double emaitzaa =rs1.getDouble("suma");
//				System.out.println("emaitza: "+emaitzaa);
				emaitza.put(tarteaBehetik, emaitzaa);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	
	tarteaBehetik= tarteaGoitik;
	
	i++;
	
	}
	return emaitza;
}
}
