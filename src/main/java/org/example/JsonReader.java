package org.example;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonReader {
    public void readJson(){
        ArrayList<Node> nodeList = new ArrayList();
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(
                    new FileReader("C:/Users/cr4nk/OneDrive/Рабочий стол/4 курс/Диплом/LpSolveTest/files/test.json"));

            JSONObject jsonObject = (JSONObject) obj;
            JSONObject elements = (JSONObject) jsonObject.get("elements");


//            for (Object node : nodes) {
//                JSONObject data = (JSONObject) ((JSONObject) node).get("data");
//                String nodetype = (String) data.get("nodetype");
//                String id = (String) data .get("id");
//                String systemtype = (String) data.get("systemtype");
//                JSONArray equipment = (JSONArray) data.get("equipment");
//                List<Node.Equipment> list = equipment.stream().toList();
//
//                nodeList.add(new Node(id, nodetype, systemtype, list));
//                System.out.println(nodeList.get(0).getEquipment().get(0).getLabel());
//            }

            JSONArray nodes = (JSONArray) elements.get("nodes");
            JSONArray edges = (JSONArray) elements.get("edges");
            ArrayList<String> b = new ArrayList();
            ArrayList<String> max = new ArrayList();
            ArrayList<String> min = new ArrayList();
            ArrayList<String> goal = new ArrayList();
            ArrayList<ArrayList<String>> rezVect = new ArrayList();

            nodes.forEach((node) -> {
                var data = (JSONObject) ((JSONObject) node).get("data");
                JSONArray equipments = (JSONArray) data.get("equipment");
                //сделать проверку на длину массива если больше 1 то формируем мат постановку если нет то уходим дальше
                var equipment = equipments.get(0);
                var value = ((JSONObject) equipment).get("value");
                var nodeType = data.get("nodetype");

                if (nodeType.toString().equals("потребитель")) {
                    value = -(Integer.parseInt(value.toString()));
                }
                b.add(value.toString());
            });

            edges.forEach((edge) -> {
                var data = (JSONObject) ((JSONObject) edge).get("data");
                JSONArray equipments = (JSONArray) data.get("equipment");

                equipments.forEach((equipment) -> {

                    var maxValue = ((JSONObject) equipment).get("max_value");
                    var minValue = ((JSONObject) equipment).get("min_value");
                    var price = ((JSONObject) equipment).get("price");
                    max.add(maxValue.toString());
                    min.add(minValue.toString());
                    goal.add(price.toString());
                });
            });

            ArrayList<ArrayList<String>> matrix = new ArrayList();

            nodes.forEach((node) -> {
                var nodeData = (JSONObject) ((JSONObject) node).get("data");
                var nodeId = nodeData.get("id");
                System.out.println(nodeId);
                ArrayList<String> row = new ArrayList();
                edges.forEach((edge) -> {
                    var edgeData = (JSONObject) ((JSONObject) edge).get("data");
                    var edgeSrcId = edgeData.get("source");
                    var edgeTrgId = edgeData.get("target");
                    JSONArray equipments = (JSONArray) edgeData.get("equipment");

                    equipments.forEach((t) -> {
                        if (nodeId.equals(edgeSrcId)) {
                            row.add("1");
                        } else if (nodeId.equals(edgeTrgId)) {
                            row.add("-1");
                        } else {
                            row.add("0");
                        }
                    });
                });
                matrix.add(row);
            });

            rezVect.add(b);
            rezVect.add(max);
            rezVect.add(min);
            rezVect.add(goal);

//            rezVect.forEach((item) -> {
//                item.forEach((el) -> {
//                    System.out.print(el);
//                    System.out.print(" ");
//                });
//                System.out.println();
//            });
//
//            System.out.println();
//
            matrix.forEach((item) -> {
                item.forEach((el) -> {
                    System.out.print(el);
                    System.out.print(" ");
                });
                System.out.println();
            });
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

}
