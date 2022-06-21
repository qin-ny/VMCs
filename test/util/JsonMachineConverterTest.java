package util;

import objects.Machine;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static org.junit.Assert.*;


public class JsonMachineConverterTest {

    String jsonFile;
    String jsonMachine;
    @Before
    public void setUp() {
        jsonFile = "TestJson.json";
        jsonMachine = "{\n" +
                "  \"password\": \"123456\",\n" +
                "  \"slots\": [\n" +
                "    {\n" +
                "      \"drink\": {\n" +
                "        \"name\": \"testDrink1\",\n" +
                "        \"price\": 75\n" +
                "      },\n" +
                "      \"quantity\": 3\n" +
                "    },\n" +
                "    {\n" +
                "      \"drink\": {\n" +
                "        \"name\": \"testDrink2\",\n" +
                "        \"price\": 75\n" +
                "      },\n" +
                "      \"quantity\": 6\n" +
                "    }\n" +
                "  ],\n" +
                "  \"coins\": [\n" +
                "    {\n" +
                "      \"name\": \"20c\",\n" +
                "      \"weight\": 20,\n" +
                "      \"quantity\": 9\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"10c\",\n" +
                "      \"weight\": 10,\n" +
                "      \"quantity\": 37\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"5c\",\n" +
                "      \"weight\": 5,\n" +
                "      \"quantity\": 5\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        Writer writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(jsonFile));
            writer.write(jsonMachine);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void jsonToMachineObject() {
        Machine machine = JsonMachineConverter.jsonToMachineObject(jsonFile);
        assertEquals(2, machine.getSlots().size());
        assertEquals(3, machine.getCoins().size());
        assertEquals("123456", machine.getPassword());
    }

    @Test
    public void machineObjectToJson() {
        Machine machine = JsonMachineConverter.jsonToMachineObject(jsonFile);
        String convertedJson = JsonMachineConverter.machineObjectToJson(machine, jsonFile);
        assertEquals(jsonMachine, convertedJson);
    }
}