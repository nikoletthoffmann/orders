import entity.Address;
import entity.Customer;
import entity.Order;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class JsonFileReader {

    private static List<Order> orders = new ArrayList<>();
    private final static String fileName = "orders.json";
    public static void main(String[] args) throws IOException, ParseException {

        parseJson(fileName);

        getMaxSpending();

        getCityBySpending();


    }

    private static void getMaxSpending(){

        Map<Customer,Double> allSpending = new HashMap<>();

        for (Order o : orders){
            Customer customer = o.getCustomer();
            Double spending = o.getTotal();
            if(allSpending.containsKey(customer)){
                spending += allSpending.get(customer);
                allSpending.put(customer,spending);
            } else {
                allSpending.put(customer,spending);
            }
        }

        Collection<Double> spendingList = allSpending.values();
        Double maxSpending = Collections.max(spendingList);

        Customer result = null;
        Set<Map.Entry<Customer, Double>> entrySet = allSpending.entrySet();
        for (Map.Entry<Customer, Double> entry : entrySet) {
            if (entry.getValue() == maxSpending) {
                result = entry.getKey();
                break;
            }
        }
        System.out.println("the person who spends the most: " + result.getName() + " $" + maxSpending);
    }

    private static void getCityBySpending(){
        Map<Address,Integer> cities = new HashMap<>();

        for(Order o: orders){
            Address address = o.getCustomer().getAddress();
            Integer count = 0;
            if(cities.containsKey(address)){
                cities.put(address, cities.get(address) + 1);
            } else {
                cities.put(address, 1);
            }
        }

        Collection<Integer> counter = cities.values();
        Integer maxCount = Collections.max(counter);

        Address result = null;
        Set<Map.Entry<Address, Integer>> entrySet = cities.entrySet();
        for (Map.Entry<Address, Integer> entry : entrySet) {
            if (entry.getValue() == maxCount) {
                result = entry.getKey();
                break;
            }
        }
        System.out.println("the city with the most orders is: " + result.getCity() + " with " + maxCount + " orders");

    }
    private static void parseJson(String fileName) throws IOException, ParseException {

        Object obj = new JSONParser().parse(new FileReader(fileName));

        JSONArray jsonArray = (JSONArray) obj;
        JSONObject jsonObject;

        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObject = (JSONObject) jsonArray.get(i);

            String id = (String) jsonObject.get("_id");
            LocalDate date = LocalDate.parse((String) jsonObject.get("date"));
            String totalStr = (String) jsonObject.get("total");
            Double total = Double.parseDouble(totalStr.substring(1).replaceAll(",", ""));

            Map customers = ((Map) jsonObject.get("customer"));

            String name  = (String) customers.get("name");
            String phone = (String) customers.get("phone");
            String mail = (String) customers.get("mail");


            Map addresses = (Map) customers.get("address");
            Long zip = (Long) addresses.get("zip");
            String city = (String) addresses.get("city");
            String street = (String) addresses.get("street");

            Address address = new Address(street,city,zip);
            Customer customer = new Customer(name,phone,mail,address);
            Order order = new Order(id,date,total,customer);

            orders.add(order);
        }
    }


}

