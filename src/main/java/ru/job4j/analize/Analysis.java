package ru.job4j.analize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analysis {

    /**
     * Метод определяет рязницу между начальным состоянием коллекции и текущим.
     * Предыдущую коллекцию(previous) вводим к карту, используя как ключ id объекта User,
     * а значение - объект User.Проходим в цикле по второй коллекции(current) и проверяем
     * содержится ли объекты из коллекции в карте.
     * Определяем сколько элементов изменилось(changed++) и осталось
     * неизменными(noChanged++) в текущей коллекции по сравнению с начальной коллекцией.
     *      Вычисляем разницу:
     * remained = noChange + changes - количество объектов присутствующих в обоих коллекциях.
     * deleted = previous.size() - remained - количество объектов которые есть в старой коллекции,
     *                                        но отсутствуют в новой(объекты были удалены).
     * added = current.size() - remained - - количество объектов которые есть в новой коллекции,
     *      *                                но отсутствуют в старой(объекты были добавлены).
     * @param previous - начальная коллекция объектов User.
     * @param current - текущая коллекция объектов User.
     * @return - объект класса Info, содержащий количество изменений в коллекции.
     */

    public Info diff(List<User> previous, List<User> current) {
        Info changes = new Info();
        Map<Integer, User> prev = new HashMap<>();
        int noChange = 0;
        for (User prevUser : previous) {
            prev.put(prevUser.id, prevUser);
        }
        for (User currUser : current) {
            if (prev.containsKey(currUser.id)) {
                if (prev.get(currUser.id).name.equals(currUser.name)) {
                    noChange++;
                } else {
                    changes.changed++;
                }
            }
        }
        int remained = noChange + changes.changed;
        changes.deleted = previous.size() - remained;
        changes.added = current.size() - remained;
        return changes;
    }

    public static class User {
        int id;
        String name;
    }

    public static class Info {
        int added;
        int changed;
        int deleted;
    }
}
