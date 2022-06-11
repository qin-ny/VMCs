package util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import objects.Coin;
import objects.Drink;
import objects.Machine;
import objects.Slot;

import java.io.*;
import java.util.*;

public class JsonMachineConverter {

    //测试json转换
    public static void main(String[] args) {
        machineObjectToJson(jsonToMachineObject().get());
    }

    /**
     *
     * @return
     */
    public static Optional<Machine> jsonToMachineObject() {
        Gson gson = new Gson();
        try {
            Reader reader = new BufferedReader(new FileReader(Constants.DATA_SOURCE));
            Machine sallowConversion = gson.fromJson(reader, Machine.class);
            Machine machine = unifyDrink(sallowConversion);
//            System.out.println(gson.toJson(machine));
            reader.close();
            return Optional.of(machine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
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

        sallowConversion.getSlots().forEach(slot -> {
            String drinkName = slot.getDrink().getName();
            int drinkPrice = slot.getDrink().getPrice();
            int drinkQuantity = slot.getQuantity();
            if(!drinksMap.containsKey(drinkName)){
                Drink drink = new Drink(drinkName, drinkPrice);
                drinksMap.put(drinkName, drink);
                slots.add(new Slot(drink, drinkQuantity));
            } else {
                slots.add(new Slot(drinksMap.get(drinkName),drinkQuantity));
            }
        });

        return new Machine(slots, coins, password);
    }

    /**
     *
     * @param machine
     */
    public static void machineObjectToJson(Machine machine) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(machine);
        try {
            Writer writer = new BufferedWriter(new FileWriter(Constants.DATA_SOURCE));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
