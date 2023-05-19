package com.example.mytestapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class generates an outfit for the user
 *
 */
public class OutfitMaker {

    static List<String> TypeT = Arrays.asList("short-sleeved shirt", "long-sleeved shirt", "tank-top");
    static List<String> TypeB = Arrays.asList("shorts", "pants", "skirt");
    static ArrayList<Item> closetTops = new ArrayList<>();
    static ArrayList<Item> closetBots = new ArrayList<>();
    static Random rando = new Random();

    /**
     * Generates a top and a bottom for the user
     */
    public ArrayList<Item> Generator(List<Item> clothes) {

//        int fits = 0;
//
//        while (fits < 25) {
//            Item f = new Item(TypeT.get(rando.nextInt(TypeT.size())), colorL.get(rando.nextInt(colorL.size())),
//                    rando.nextBoolean(), rando.nextBoolean(), rando.nextBoolean(), rando.nextBoolean(),
//                    rando.nextBoolean());
//            closetTops.add(f);
//            fits++;
//        }
//
//        while (fits < 50) {
//            Item f = new Item(TypeB.get(rando.nextInt(TypeB.size())), colorL.get(rando.nextInt(colorL.size())),
//                    rando.nextBoolean(), rando.nextBoolean(), rando.nextBoolean(), rando.nextBoolean(),
//                    rando.nextBoolean());
//            closetBots.add(f);
//            fits++;
//        }

        for(Item i: clothes) {
            if(TypeT.contains(i.Type())) {
                closetTops.add(i);
            }
            else if(TypeB.contains(i.Type())) {
                closetBots.add(i);
            }
        }

        Item gen1 = closetTops.get(rando.nextInt(closetTops.size()));
        while(gen1.Color().equals("----")) {
            gen1 = closetTops.get(rando.nextInt(closetTops.size()));
        }
        Item gen2 = closetBots.get(rando.nextInt(closetBots.size()));
        String[] c = gen1.getColorMatch(gen1.colorIndex());
        List<String> cmatch = Collections.singletonList(Arrays.toString(c));



//        while (!(cmatch.get(0).contains(gen2.Color()))) {
//            gen2 = closetBots.get(rando.nextInt(closetBots.size()));
//        }

        for(int i = 0; i < closetBots.size(); i++) {
            if(!(cmatch.get(0).contains(gen2.Color()))) {
                gen2 = closetBots.get(i);
            }
            else {
                break;
            }
        }

        ArrayList<Item> outfit = new ArrayList<Item>();
        outfit.add(gen1);
        outfit.add(bestMatch(gen1));
        return outfit;

    }


    /**
     * Finds the bottom that best matches with a generated top
     * @param top - a top that is going to be matched
     * @return a bottom that matches with the top
     */
    public static Item bestMatch(Item top) {

        int temp;
        int highest = 0;
        Item gen2 = closetBots.get(0);
        String[] c = top.getColorMatch(top.colorIndex());
        List<String> cmatch = Collections.singletonList(Arrays.toString(c));
        List<Integer> numOfMatches = new ArrayList<Integer>();
        List<Item> prospects = new ArrayList<Item>();

        for (int i = 0; i < closetBots.size(); i++) {

            temp = 0;

            if( (!(top.Pattern() == closetBots.get(i).Pattern())) && (cmatch.get(0).contains(closetBots.get(i).Color())))
                temp++;

            if ((top.W() == closetBots.get(i).W()) && (cmatch.get(0).contains(closetBots.get(i).Color())))
                temp++;

            if ((top.Sp() == closetBots.get(i).Sp()) && (cmatch.get(0).contains(closetBots.get(i).Color())))
                temp++;

            if ((top.Su() == closetBots.get(i).Su()) && (cmatch.get(0).contains(closetBots.get(i).Color())))
                temp++;

            if ((top.F() == closetBots.get(i).F()) && (cmatch.get(0).contains(closetBots.get(i).Color())))
                temp++;

            numOfMatches.add(temp);

            if (temp >= highest) {
                highest = temp;
                gen2 = closetBots.get(i);
            }
        }

        for (int j = 0; j<closetBots.size(); j++) {
            if(numOfMatches.get(j)==highest)
                prospects.add(closetBots.get(j));
        }

        gen2=prospects.get(rando.nextInt(prospects.size()));

        return gen2;
    }

}
