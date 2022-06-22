package util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import objects.Coin;
import objects.Drink;
import objects.Machine;
import objects.Slot;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class JsonMachineConverter {

    //测试json转换
//    public static void main(String[] args) {
//        machineObjectToJson(jsonToMachineObject().get());
//    }

    public static Machine jsonToMachineObject(String inputJsonFile) {

        Gson gson = new Gson();
        try {
            Reader reader = new BufferedReader(new FileReader(inputJsonFile));
            Machine sallowConversion = gson.fromJson(reader, Machine.class);
            Machine machine = unifyDrink(sallowConversion);
            //            System.out.println(gson.toJson(machine));
            reader.close();
            return machine;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *
     * @param sallowConversion
     * @return
     */
    private static Machine unifyDrink(Machine sallowConversion){
        String password = sallowConversion.getPassword();
        List<Slot> slots = new ArrayList<>();
        List<Coin> coins = sallowConversion.getCoins();
        Map<String, Drink> drinksMap = new HashMap<>();

        coins = coins.stream().sorted(Comparator.comparing(Coin::getWeight).reversed())
                .collect(Collectors.toList());

        sallowConversion.getSlots().forEach(slot -> {
            String drinkName = slot.getName();
            int drinkPrice = slot.getPrice();
            int drinkQuantity = slot.getQuantity();
            int slotId = slot.getId();
            if(!drinksMap.containsKey(drinkName)){
                Drink drink = new Drink(drinkName, drinkPrice);
                drinksMap.put(drinkName, drink);
                slots.add(new Slot(slotId, drink, drinkQuantity));
            } else {
                slots.add(new Slot(slotId, drinksMap.get(drinkName),drinkQuantity));
            }
        });

        return new Machine(slots, coins, password);
    }

    /**
     *
     * @param machine
     */
    public static String machineObjectToJson(Machine machine, String outputJsonFile) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            String json = gson.toJson(machine);
            Writer writer = new BufferedWriter(new FileWriter(outputJsonFile));
            writer.write(json);
            writer.close();
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
