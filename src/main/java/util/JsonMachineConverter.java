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

    public Machine machine;
    private boolean simulationStatus;

    //测试json转换
//    public static void main(String[] args) {
//        machineObjectToJson(jsonToMachineObject().get());
//    }

    public JsonMachineConverter() {
        jsonToMachineObject();
        this.simulationStatus = false;
    }

    public void beginSimulation() {
        this.simulationStatus = true;
    }

    public void endSimulation() {
        this.simulationStatus = false;
    }

    public boolean getSimulationStatus() {
        return this.simulationStatus;
    }

    private void jsonToMachineObject() {
        Gson gson = new Gson();
        try {
            Reader reader = new BufferedReader(new FileReader(Constants.DATA_SOURCE));
            Machine sallowConversion = gson.fromJson(reader, Machine.class);
            this.machine = unifyDrink(sallowConversion);
//            System.out.println(gson.toJson(machine));
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param sallowConversion
     * @return
     */
    private Machine unifyDrink(Machine sallowConversion){
        String password = sallowConversion.getPassword();
        List<Slot> slots = new ArrayList<>();
        List<Coin> coins = sallowConversion.getCoins();
        Map<String, Drink> drinksMap = new HashMap<>();

        coins = coins.stream().sorted(Comparator.comparing(Coin::getWeight).reversed())
                .collect(Collectors.toList());

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
    public void machineObjectToJson(Machine machine) {
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
