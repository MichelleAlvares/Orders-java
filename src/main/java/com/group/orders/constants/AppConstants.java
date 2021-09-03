package com.group.orders.constants;

import java.util.List;
import java.util.Map;

import static java.util.Map.of;
import static java.util.Map.ofEntries;

public class AppConstants {

    public static final List<String> orderPriorityList = List.of("M", "H", "L", "C");

    public static final List<String> salesChannels = List.of("Online", "Offline");

    public static final List<Character> nricSartingCharacter = List.of('S', 'T', 'F', 'G');

    public static final List<Character> add4Characters = List.of('T', 'G');

    public static final List<Character> foreignerCharacters = List.of('F', 'G');

    public static final Map<Integer, Integer> multipliers = of(
            0, 2, 1, 7, 2, 6, 3, 5, 4, 4, 5, 3, 6, 2
    );

    public static final Map<Integer, Character> singaporean = ofEntries(
            Map.entry(0, 'J'),
            Map.entry(1, 'Z'),
            Map.entry(2, 'I'),
            Map.entry(3, 'H'),
            Map.entry(4, 'G'),
            Map.entry(5, 'F'),
            Map.entry(6, 'E'),
            Map.entry(7, 'D'),
            Map.entry(8, 'C'),
            Map.entry(9, 'B'),
            Map.entry(10, 'A')
    );

    public static final Map<Integer, Character> foreigner = ofEntries(
            Map.entry(0, 'X'),
            Map.entry(1, 'W'),
            Map.entry(2, 'U'),
            Map.entry(3, 'T'),
            Map.entry(4, 'R'),
            Map.entry(5, 'Q'),
            Map.entry(6, 'P'),
            Map.entry(7, 'N'),
            Map.entry(8, 'M'),
            Map.entry(9, 'L'),
            Map.entry(10, 'K')
    );
}
