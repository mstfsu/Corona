package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javafx.fxml.FXML;

public class Controller {
    @FXML TextField inputURL;
    @FXML
    Label info;
    static ArrayList<Country> countries =new ArrayList<>();
    static ArrayList<String> dateArray=new ArrayList();
    static ArrayList<Country> allCountryData= new ArrayList<>();
    static ArrayList<Integer> asia =new ArrayList<>();
    static ArrayList<Integer> america=new ArrayList();
    static ArrayList<Integer> europe= new ArrayList<>();
    static ArrayList<Integer> oceania= new ArrayList<>();
    static ArrayList<Integer> africa= new ArrayList<>();
    static ArrayList<Integer> asiaDeaths =new ArrayList<>();
    static ArrayList<Integer> americaDeaths=new ArrayList();
    static ArrayList<Integer> europeDeaths= new ArrayList<>();
    static ArrayList<Integer> oceaniaDeaths= new ArrayList<>();
    static ArrayList<Integer> africaDeaths= new ArrayList<>();


    public void info(ActionEvent actionEvent) throws  Exception{
        System.out.println("burda");
        info.setText("Veriler Getiriliyor ..");

    }

    public void getData(ActionEvent actionEvent) throws Exception{
        String url = inputURL.getText();
        StringBuffer response = new StringBuffer();
        try {
            System.out.println(url);
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            Scanner scan = new Scanner (con.getInputStream());
            while (scan.hasNext()) {
                response.append(scan.nextLine());
            }
            scan.close();
            } catch (Exception e) {
             System.out.println("localde bir dosya yolu verildi.");
            }
            LocalDateTime now = LocalDateTime.now();
            int monthNow = now.getMonthValue();
            int dayNow = now.getDayOfMonth();
            String monthStr = String.valueOf(monthNow);
            String daytr = String.valueOf(dayNow);
            LocalDate today = LocalDate.now();
            LocalDate yesterday = today.minus(Period.ofDays(1));
            String previosDay= String.valueOf(yesterday.getDayOfMonth());
            boolean controlDay=true;
            File xmlFile= new File(url);
            Document doc;
            if(xmlFile.length()==0){
                doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new InputSource(new StringReader(response.toString())));
            }else{
                doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(xmlFile);
            }
            NodeList errNodes = doc.getElementsByTagName("record");
            for (int temp = 0; temp < errNodes.getLength(); temp++) {
                org.w3c.dom.Node nNode = errNodes.item(temp);
                Element eElement = (Element) nNode;

                    String day=String.valueOf(eElement.getElementsByTagName("day").item(0).getTextContent());
                    day=day.startsWith("0") ? day.substring(1): day;
                    String month=String.valueOf(eElement.getElementsByTagName("month").item(0).getTextContent());
                    month= month.startsWith("0") ? month.substring(1): month;
                    if(monthStr.equals(month) && daytr.equals(day)){
                        

                    String countryName=  String.valueOf(eElement.getElementsByTagName("countriesAndTerritories").item(0).getTextContent());
                    int newCase=  Integer.valueOf(eElement.getElementsByTagName("cases").item(0).getTextContent());
                    int newDeath=  Integer.valueOf(eElement.getElementsByTagName("deaths").item(0).getTextContent());
                    int pop=0;
                    if(!eElement.getElementsByTagName("popData2019").item(0).getTextContent().isEmpty()){
                        pop= Integer.valueOf(eElement.getElementsByTagName("popData2019").item(0).getTextContent());

                    }
                    countries.add(new Country(countryName,1,newCase,newDeath,1,pop,1,BigDecimal.valueOf(1)));
                    controlDay=false;

                }
                if(controlDay){
                    if(previosDay.equals(day) && monthStr.equals(month)){
                        String countryName=  String.valueOf(eElement.getElementsByTagName("countriesAndTerritories").item(0).getTextContent());
                        int newCase=  Integer.valueOf(eElement.getElementsByTagName("cases").item(0).getTextContent());
                        int newDeath=  Integer.valueOf(eElement.getElementsByTagName("deaths").item(0).getTextContent());
                        int pop=0;

                        if(!eElement.getElementsByTagName("popData2019").item(0).getTextContent().isEmpty()){
                           pop= Integer.valueOf(eElement.getElementsByTagName("popData2019").item(0).getTextContent());

                        }
                        countries.add(new Country(countryName,1,newCase,newDeath,1,pop,1,BigDecimal.valueOf(1)));
                    }
                }
            }
            getTotalData(countries,errNodes);

            Parent tableview = FXMLLoader.load(getClass().getResource("tableview.fxml"));
            Scene scene=new Scene(tableview,1050,900);

            Stage window=(Stage)((javafx.scene.Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

    }
    public void getTotalData(ArrayList<Country> countries, NodeList errNodes){
        info.setText("İşlem Devam Ediyor Lütfen Bekleyiniz...");

        for(int i=0; i<countries.size(); i++){
            int totalcase=0;
            int totaldeaths=0;
            double mortality;
            double attackRate=0;
            BigDecimal attackRateBigDecimal= BigDecimal.valueOf(0);

            for (int temp = 0; temp < errNodes.getLength(); temp++){
                org.w3c.dom.Node nNode = errNodes.item(temp);
                Element eElement = (Element) nNode;
                if(countries.get(i).getCountryName().equals(eElement.getElementsByTagName("countriesAndTerritories").item(0).getTextContent())){
                    totalcase = totalcase + Integer.valueOf(eElement.getElementsByTagName("cases").item(0).getTextContent());
                    totaldeaths = totaldeaths + Integer.valueOf(eElement.getElementsByTagName("deaths").item(0).getTextContent());
                }
            }
            mortality= Double.valueOf(totaldeaths)/Double.valueOf(totalcase);
            if(countries.get(i).getPopulation()!=0) {
                double pop = Double.valueOf(countries.get(i).getPopulation())/1000.0;
                BigDecimal hundred = new BigDecimal("1000");
                attackRate = Double.valueOf(totalcase)/ pop;
                BigDecimal bd = new BigDecimal(attackRate);
                BigDecimal result = bd.divide(hundred);
                attackRateBigDecimal=BigDecimal.valueOf(result.doubleValue());

            }
            countries.get(i).setTotalCase(totalcase);
            countries.get(i).setTotalDeaths(totaldeaths);
            countries.get(i).setMortality(mortality);
            countries.get(i).setAttackRate(attackRateBigDecimal);
        }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy ");
            Calendar cal = Calendar.getInstance();
            Date date=cal.getTime();
            String[] days = new String[45];
            days[0]=sdf.format(date);
            for(int i = 1; i< 45; i++){
                cal.add(Calendar.DAY_OF_MONTH,-1);
                date=cal.getTime();
                days[i]=sdf.format(date);
            }
            dateArray.addAll(Arrays.asList(days));
            Collections.reverse(dateArray);

            for (int i = 0; i < dateArray.size(); i++) {
                String filterDateArray = dateArray.get(i).replaceAll("[^a-zA-Z0-9]", "");
                int asiaCount=0;
                int africaCount=0;
                int americaCount=0;
                int europeCount=0;
                int oceaniaCount=0;
                int asiaDeathsCount=0;
                int africaDeathsCount=0;
                int americaDeathsCount=0;
                int europeDeathsCount=0;
                int oceaniaDeathsCount=0;
                for (int temp = 0; temp < errNodes.getLength(); temp++) {
                    org.w3c.dom.Node nNode = errNodes.item(temp);
                    Element eElement = (Element) nNode;
                    String countryDate = String.valueOf(eElement.getElementsByTagName("dateRep").item(0).getTextContent()).replaceAll("[^a-zA-Z0-9]", "");
                    String continentName=String.valueOf(eElement.getElementsByTagName("continentExp").item(0).getTextContent());
                    String countryDateNoFilter=String.valueOf(eElement.getElementsByTagName("dateRep").item(0).getTextContent());
                    int death= Integer.valueOf(eElement.getElementsByTagName("deaths").item(0).getTextContent());
                    if(filterDateArray.equals(countryDate)){
                        if(continentName.equals("Asia") && death>=0){
                            asiaCount= asiaCount + Integer.valueOf(eElement.getElementsByTagName("cases").item(0).getTextContent());
                            asiaDeathsCount= asiaDeathsCount+death;
                        }else if(continentName.equals("Europe") && death>=0){
                            europeCount= europeCount + Integer.valueOf(eElement.getElementsByTagName("cases").item(0).getTextContent());
                            europeDeathsCount= europeDeathsCount + death ;
                        }else if(continentName.equals("Africa") && death>=0){
                            africaCount= africaCount + Integer.valueOf(eElement.getElementsByTagName("cases").item(0).getTextContent());
                            africaDeathsCount= africaDeathsCount + death;
                        }else if(continentName.equals("America") && death>=0){
                            americaCount= americaCount + Integer.valueOf(eElement.getElementsByTagName("cases").item(0).getTextContent());
                            americaDeathsCount= americaDeathsCount +death;
                        }else if(continentName.equals("Oceania") && death>=0){
                            oceaniaCount= oceaniaCount + Integer.valueOf(eElement.getElementsByTagName("cases").item(0).getTextContent());
                            oceaniaDeathsCount= oceaniaDeathsCount +death;
                        }

                        String countryName = String.valueOf(eElement.getElementsByTagName("countriesAndTerritories").item(0).getTextContent());
                        int countrynewCase = Integer.valueOf(eElement.getElementsByTagName("cases").item(0).getTextContent());
                        int countrynewDeaths = Integer.valueOf(eElement.getElementsByTagName("deaths").item(0).getTextContent());

                        allCountryData.add(new Country(countryName, countryDateNoFilter, countrynewCase,countrynewDeaths));
                    }
                }
                asia.add(asiaCount);
                europe.add(europeCount);
                africa.add(africaCount);
                america.add(americaCount);
                oceania.add(oceaniaCount);
                asiaDeaths.add(asiaDeathsCount);
                europeDeaths.add(europeDeathsCount);
                africaDeaths.add(africaDeathsCount);
                americaDeaths.add(americaDeathsCount);
                oceaniaDeaths.add(oceaniaDeathsCount);
        }

    }
}
