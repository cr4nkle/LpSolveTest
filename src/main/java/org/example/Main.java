package org.example;
import lpsolve.*;
import java.util.Arrays;

import static org.example.Optimator.*;

public class Main {
    public static void main(String[] args) {
        try {
//            PostgresHelper postgresHelper = new PostgresHelper();
//            postgresHelper.readFromDb();
//            try (var greeter = new Neo4jHelper("bolt://localhost:7687", "neo4j", "rootroot")) {
//                greeter.write();
//                greeter.read();
//            }
            JsonReader jsonReader = new JsonReader();
            jsonReader.readJson();
            //в 0 положения поставил 0 так как библиотека берёт с 1-го элемента
            double[] f = {0, 10, 0, 0, 9, 2};//коэффициенты целевой функции
            double [][] m = {
                    {0, 1, 0, 0, 1, 1},
                    {0, -1, 1, 1, -1, -1},
                    {0, 0, -1, 0, 0, 0},
                    {0, 0, 0, -1, 0, 0},

            };//матрица коэффициентов
            int[] sin = {1, 3, 3, 3, 3, 3, 3};//вектор знаков 1 это <=, 3 это =
            double[] b = {10, 0, -3 , -4, 0, 0, 0};//вектор ограничений правой части
            double[] min = {0, 0, 0, 0, 0};//ограничения по минимальному значению
            double[] max = {11, 4, 5, 9, 2};//ограничения по максимальному значению

            optimate(f, m, sin, b, min, max);//вызываем функцию оптимизации
            System.out.println(getObjective());//выводим значение целевой функции
            System.out.println(Arrays.toString(getVariables()));//выводим значения переменных
        } catch (LpSolveException e) {
            e.printStackTrace();
        }
    }
}