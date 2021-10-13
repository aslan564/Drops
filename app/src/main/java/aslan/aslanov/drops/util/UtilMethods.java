package aslan.aslanov.drops.util;

import java.util.ArrayList;
import java.util.List;

import aslan.aslanov.drops.model.TextMessage;

public class UtilMethods {

    public static ArrayList<TextMessage> newSortedList(List<TextMessage> textMessages) {
        ArrayList<TextMessage> sortedList = new ArrayList<>();
        for (int i = 0; i < textMessages.size(); i++) {
            for (int t = i; t < textMessages.size(); t++) {
                if (textMessages.get(i).getDate().before(textMessages.get(t).getDate())) {
                    if (!sortedList.contains(textMessages.get(i))) {
                        sortedList.add(textMessages.get(i));
                    }
                }
            }
        }

        return sortedList;
    }
}
