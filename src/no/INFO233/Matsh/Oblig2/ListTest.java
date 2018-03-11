
package no.INFO233.Matsh.Oblig2;

import no.INFO233.Matsh.Oblig2.IList;
import no.INFO233.Matsh.Oblig2.LinkedList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;


import java.lang.reflect.Executable;
import java.time.Duration;
import java.util.*;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {

    private IList<String> list;
    private Iterator iterator;

    @BeforeEach
    void setUp() {
        list = new LinkedList<>();
        iterator = list.iterator();
    }

    @Test
    void oppg8_sortIntegers() {
        // Se oppgave 8
        IList<Integer> list = new LinkedList<>();
        List<Integer> values = Arrays.asList(3, 8, 4, 7, 10, 6,
                1, 2, 9, 5);
        for (Integer value : values) {
            list.add(value);
        }
        list.sort(Comparator.comparingInt(x -> x));
        int n = list.remove();
        while (list.size() > 0) {
            int m = list.remove();
            if (n > m) {
                fail("Integer list is not sorted.");
            }
            n = m;
        }
    }

    @Test
    void oppg8_sortStrings() {
        // Se oppgave 8
        List<String> values = Arrays.asList(
                "g", "f", "a", "c", "b", "d", "e", "i", "j", "h"
        );
        for (String value : values) {
            list.add(value);
        }
        list.sort(Comparator.naturalOrder());
        String n = list.remove();
        while (list.size() > 0) {
            String m = list.remove();
            if (n.compareTo(m) > 0) {
                fail("String list is not sorted.");
            }
        }
    }

    @Test
    void oppg9_filter() {
        // Se oppgave 9
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7,
                8, 9, 10);
        IList<Integer> list = new no.INFO233.Matsh.Oblig2.LinkedList<>(1);
        for (Integer value : values) {
            list.add(value);
        }
        list.filter(n -> n % 2 == 1);
        int n = list.remove();
        while (list.size() > 0) {
            if (n % 2 == 1) {
                fail("List contains filtered out elements.");
            }
            n = list.remove();
        }
    }

    @Test
    void oppg10_map() {
        // Se oppgave 10
        List<String> values = Arrays.asList("1", "2", "3", "4", "5");

        for (String value : values) {
            list.add(value);
        }

        IList<Integer> result = list.map(Integer::parseInt);

        List<Integer> target = Arrays.asList(1, 2, 3, 4, 5);


        for (int t : target) {
            if (result.remove() != t) {
                fail("Result of map gives the wrong value.");
            }
        }
    }

    @Test
    void oppg11_reduceInts() {
        // Se oppgave 11
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5);
        IList<Integer> list = new no.INFO233.Matsh.Oblig2.LinkedList<>(1);
        for (Integer value : values) {
            list.add(value);
        }
        int result = list.reduce(0, Integer::sum);
        assertEquals(result, 5 * ((1 + 5) / 2));
    }

    @Test
    void oppg11_reduceStrings() {
        List<String> values = Arrays.asList("e", "s", "t");
        for (String s : values) {
            list.add(s);
        }
        String result = list.reduce("t", (acc, s) -> acc + s);
        assertEquals(result, "test");
    }

    @Test
    void ex1_FastSort() {
        // Se ekstraoppgave 1
        Random r = new Random();
        IList<Integer> list = new LinkedList<>();
        for (int n = 0; n < 1000000; n++) {
            list.add(r.nextInt());
        }
        assertTimeout(Duration.ofSeconds(2), () -> {
            list.sort(Integer::compare);
        });
        int n = list.remove();
        for (int m = list.remove(); !list.isEmpty(); n = m) {
            if (n > m) {
                fail("List is not sorted");
            }
        }
    }

    //Deloppgave 1.1
    //Skriv tester for hva som skjer hvis listen er tom og du kaller metodene first,
    // rest, add, put, og remove. Kommenter testene med hva du tester for.

    // Tester for hva som skal skje om man
    // First() og listen er tom. Den skal da kaste NoSuchElementException. Testen feiler
    // om listen inneholder et element.
    @Test
    void calledFirstOnEmpty() {
        if (list.isEmpty()) {
            assertThrows(NoSuchElementException.class, () -> {
                list.first();
            }, "Listen er tom");
        } else {
            fail("Listen er ikke tom");
        }
    }

    @Test
    void calledRemoveOnEmpty() {
        if (list.isEmpty()) {
            assertThrows(NoSuchElementException.class, () -> {
                list.remove();
            }, "Listen er tom");
        } else {
            fail("List is not empty");
        }
    }

    // Kaster NullPointerException om listen er tom og man kaller Rest().
    @Test
    void calledRestOnEmpty() {
        if (list.isEmpty()) {
            assertThrows(NullPointerException.class, () -> {
                list.rest();
            }, "Listen er tom");
        } else {
            fail("Listen er ikke tom");
        }
    }

    // Sjekker om det første elementet i listen er "test", som ble lagt til ved add()
    @Test
    void calledAddOnEmpty() {
        list.add("test");
        assertTrue(list.first().equals("test"));

    }

    // Sjekker om det første elementet er lik "test", som er elementet passert inn med put()
    @Test
    void calledPutOnEmpty() {
        list.put("test");
        assertTrue(list.first().equals("test"));


    }
    // Oppgave 1.2
    // Skriv tester for hva som skjer hvis listen inneholder kun 1 element
    // og du kaller metodene first, rest, add, put, og remove.
    // Kommenter testene med hva du tester for.

    @Test
    void calledFirstOnSizeOne() {
        //Sjekker om first faktisk er "test" som blir lagt til når man lager en ny lenket liste
        list.put("test");
        assertEquals("test", list.first());
    }

    @Test
    void calledAddOnSizeOne() {
        // Sjekker at elementet som blir lagt til med add() legger seg i halen på listen, og ikke først.
        list.add("test");
        list.add("test2");
        assertTrue(list.first().equals("test") && !list.first().equals("test2"));

    }

    @Test
    void calledPutOnSizeOne() {
        // Sjekker også om first er det siste elementet som ble lagt inn, "test2".
        list.add("test");
        list.put("test2");
        assertTrue(list.first().equals("test2"));
    }

    @Test
    void calledRemoveOnSizeOne() {
        // Sjekker at størrelsen på listen er 0 etter å ha fjernet det eneste elementet.
        list.put("test");
        list.remove();
        assertTrue(list.size() == 0);
    }

    // Siden "test" er det første elementet i listen, skal ikke et kall til rest()
    // returnere dette elementet. Dette sjekkes i testen.
    @Test
    void calledRestOnSizeOne() {
        list.put("test");
        assertTrue(!list.rest().equals("test"));
    }

    // Oppgave 1.3
    // Skriv tester for hva som skjer hvis listen inneholder flere enn 1 element og
    // du kaller metodene first, rest, add, put, og remove. Kommenter testene med hva du tester for.

    @Test
    void calledFirstOnSizeAboveOne() {
        // Forventer at "test2" skal være den første elementet i listen, som legges inn med put().
        // Testen feiler om dette ikke er tilfellet
        list.put("test");
        list.put("test2");
        assertEquals("test2", list.first());
    }

    @Test
    void calledAddOnSizeAboveOne() {
        //Forventer at størrelsen på listen skal være 3 etter å ha lagt til tre elementer med add()
        list.add("test2");
        list.add("test3");
        assertEquals("test2", list.first());
        assertEquals(2, list.size());
    }

    @Test
    void calledPutOnSizeAboveOne() {
        // Sjekket at "test3" er det første elementet i listen etter å ha lagt inn 2 elementer med Put()
        list.put("test");
        list.put("test2");
        list.put("test3");
        assertTrue(list.first().equals("test3"));
    }

    @Test
    void calledRemoveOnSizeAboveOne() {
        // Sjekker at "test2" er det første elementet i listen etter å ha fjernet det første elementet i listen
        list.add("test");
        list.add("test2");
        list.add("test3");
        list.remove();
        assertEquals("test2", list.first());
    }

    @Test
    void calledRestOnSizeAboveOne() {
        // Legger inn tre elementer. bruker deretter rest() på newList, og sjekker at størrelsen har sunket til 2, og
        // "test er det første elementet i listen.
        list.put("test");
        list.put("test2");
        list.put("test3");
        IList<String> newList = list.rest();
        assertEquals(2, newList.size());
        assertEquals("test2", newList.first());
    }

    // Deloppgave 2.1
    @Test
    void calledParameterizedRemoveOnEmpty() {
        // Remove(o) ska returnere false dersom listen er tom og man ikke finner referansen til gitt object.
        assertFalse(list.remove("test"));
    }

    @Test
    void calledParameterizedRemoveOnSizeOne() {
        // legger inn "test" med put(), sjekker deretter om list.remove() var lik "test", og at den nye størrelsen er 0.
        list.put("test");
        assertTrue(list.remove("test") && list.size() == 2);
    }

    @Test
    void calledParameterizedRemoveOnSizeAboveOne() {
        // Sjekker at "test2" returnerer true på remove. Deretter sjekkes det
        // om size synker til 2, og at det første elementet i listen nå er "test3".
        list.put("test");
        list.put("test2");
        list.put("test3");
        assertTrue(list.remove("test2"));
        assertTrue(list.size() == 2 && list.first().equals("test3"));
    }

// deloppgave 3.1

    @Test
    void calledContainsOnEmpty() {
        //sjekker at testen returnerer false dersom man søker etter et element som ikke eksisterer
        assertFalse(list.contains("test"));
    }

    @Test
    void calledContainsOnSizeAboveOne() {
        // Sjekker at "test2" og "test3" ekesisterer i listen, og at "test" ikke eksisterer i listen.
        list.put("test2");
        list.put("test3");
        assertTrue(list.contains("test3") && list.contains("test2") && !list.contains("test"));
    }

    @Test
    void calledIsEmptyOnEmpty() {
        // Sjekker at testen returnerer True om listen er tom og man kaller isEmpty()
        assertTrue(list.isEmpty());
    }

    @Test
    void calledIsEmptyOnSizeOne() {
        // sjekke at isEmpty() returnerer false dersom listen ikke er tom
        list.put("test");
        assertFalse(list.isEmpty());
    }

    @Test
    void calledSizeOnEmpty() {
        // sjekker at størrelsen er null om man kaller size og lsiten er tom
        assertEquals(0, list.size());
    }

    @Test
    void calledSizeWhileNotEmpty() {
        // Sjekker at størrelsen er 3 dersom man legger til 3 elementer.

        list.put("test");
        list.put("test2");
        list.put("test3");
        assertEquals(3, list.size());

    }
    @Test
    void first() {
    }

    @Test
    void rest() {
    }

    @Test
    void add() {
    }

    @Test
    void put() {
    }

    @Test
    void remove() {

    }

    @Test
    void remove1() {
        // Sjekker at "test" ble fjernet. Returnerer true hvis sant.
        list.add("test");
        list.add("test2");
        assertTrue(list.remove("test"));
    }

    @Test
    void contains() {
        // Sjekker at både "put2" og "put1" er i listen.
        list.put("put1");
        list.put("put2");
        assertTrue(list.contains("put2") && list.contains("put1"));
    }

    @Test
    void isEmpty() {
        // Sjekker at listen er tom. True hvis sant.
        assertTrue(list.isEmpty());
    }

    // Deloppgave 4.2

    @Test
    void append() {
        // Testen sjekker om listen "list" inneholder elemtentet som var i listen som ble appendet.
        // Sjekker deretter at "AppendedList"-elementet ikke er først i listen, men bakerst.
        // Sjekker til slutt at størrelsen er lik antatt størrelse.

        IList<String> appendList = new LinkedList<>();
        // Legger til to elementer i listen fra setUp() metoden.
        list.put("test");
        list.put("test2");

        appendList.put("AppendedList");
        appendList.put("AppendedList2");

        list.append(appendList);

        assertTrue(list.contains("AppendedList"));
        assertFalse(list.first().equals("AppendedList"));
        assertTrue(list.last().equals("AppendedList"));
        assertEquals(4, list.size());

    }

    @Test
    void assertOnOne(){
        // Lager to lister, en tom og en mer størrelse 1.
        // Den tomme listen appender den andre. Sjekker at størrelsen øker, og at
        // elementet fra den andre listen nå er det første og siste elementet i den første listen
        // Legger til et ekstra element i den første listen
        // Sjekker at 2 er det første elementet i den første listen, og at 1 er det siste.
        IList<Integer> one = new LinkedList<>();
        IList<Integer> appendTwo = new LinkedList<>(1);

        one.append(appendTwo);
        assertEquals(1, one.size());
        assertTrue(one.first().equals(1) && one.last().equals(1));

        one.put(2);

        assertTrue(one.first().equals(2) && one.last().equals(1));

    }


    @Test
    void prepend() {

        // Sjekker at listen som blir prepended av list inneholder elemtene fra listen "prepend", og at det
        // første elementet er "prependItem2", slik det skal være.
        // Sjekker til slutt at størrelsen er så stor som man antar.
        list.put("test");
        list.put("test2");
        IList<String> prependList = new LinkedList<>();
        prependList.put("prependItem");
        prependList.put("prependItem2");

        list.prepend(prependList);

        assertTrue(list.contains("prependItem") && list.contains("prependItem2"));
        assertTrue(list.first().equals("prependItem2"));
        assertEquals(4, list.size());
    }

    @Test
    void prependOnOne(){

        // Legger til en tom liste, og en tom liste hvor man legger til ett element i front.
        // Sjekker om den første og den siste er den samme
        // Integerlist prepender den andre listen
        // Sjekker at størrelsen har økt og at den første er lik den siste
        //  Legger til slutt til et element på slutten av den første listen
        // Sjekker at first() fortsatt er lik elementet fra listen som ble prependet, og at det siste
        // er "2" fra integerList.
        IList<Integer> IntegerList = new LinkedList<>();
        IList<Integer> prependOne = new LinkedList<>();
        prependOne.put(1);

        IntegerList.prepend(prependOne);
        assertEquals(1, IntegerList.size());
        assertTrue(IntegerList.first().equals(1) && IntegerList.last().equals(1));

        IntegerList.add(2);
        assertTrue(IntegerList.first().equals(1) && IntegerList.last().equals(2));

    }

    // Deloppgave 5.1
    @Test
    void concat() {
        // Oppretter to lister, som blir sveiset sammen av list som en ny liste.
        // Sjekker deretter at størrelsen er det man antar at den skal være
        // Sjekker også at det første elementet er "Item in list2-2", siden dette er første element i list-2,
        // og på venstre del av parameteret.
        IList<String> list2 = new LinkedList<>("test2");
        IList<String> list3 = new LinkedList<>("test3");
        list2.put("Item in list2");
        list2.put("Item in list2-2");
        list3.put("Item in list3");
        list3.put("Item in list3-2");
        IList<String> concatinated = list.concat(list2, list3);
        assertEquals(6, concatinated.size());
        assertEquals("Item in list2-2", concatinated.first());
        assertEquals("test3", concatinated.last());
    }

    @Test
    void concatOnEmtpy(){
        // Ganske lik den over. Sjekker at det ikke skjer noe uventet om man legger til et element til i listen
        // etter at man har kalt concat.
        IList<Integer> list1 = new LinkedList<>();
        IList<Integer> list2 = new LinkedList<>();
        IList<Integer> list3 = new LinkedList<>();

        IList<Integer> concatinated = list1.concat(list2, list3);

        assertEquals(0, concatinated.size());
        concatinated.put(2);
        assertEquals(1, concatinated.size());
     }

    // Deloppgave 8.1
    @Test
    void sortIntegers() {
        // Oppretter en ny liste av typen Integer. Lager deretter et array med
        // den forventete sorterte listen.
        // Legger til en rekke usorterte tall.
        // Kaller sort metoden på listen, hvor man bruker en sammenligningsfunksjon
        // Bruker assertArrayEquals for å sammenligne "expected"-arrayet mot Integerlist,
        // som kaller en toArray-metode.
        IList<Integer> integerList = new LinkedList<>();
        Integer[] expected = {76, 56, 54, 43, 23};
        integerList.add(54);
        integerList.add(43);
        integerList.add(56);
        integerList.add(23);
        integerList.add(76);

        integerList.sort(Comparator.comparingInt(x -> x));
        assertArrayEquals(expected, integerList.toArray());
    }

    @Test
    void filter() {

        list.put("test");
        list.add("test2");
        list.add("test3");
        list.add("test4");


    }

    @Test
    void map() {
    }

    @Test
    void reduce() {
    }

    @Test
    void size() {
    }

    @Test
    void clear() {
        // Legger til to elementer i listen, og kaller deretter Clear.
        // Sjekker deretter om listen er tom.
        IList<String> list = new LinkedList<>();
        list.put("test");
        list.put("test2");
        list.clear();
        assertTrue(list.isEmpty());
    }


    // Deloppgave 7.1 . tester for iterator
    @Test
    void IteratorhasNextOnEmpty() {
        // Sjekker at hasNext() returnerer false når listen er tom.
        assertFalse(iterator.hasNext());
    }

    
    @Test
    void iteratorHasNextOnOne() {
        // Sjekker at hasNext() returnerer true når det er ett element i listen
        list.add("test");
        assertTrue(iterator.hasNext());

        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    void iteratorHasNextOnSeveral() {
        // legger til et par elementer i listen.
        // Sjekker at hasNexT() returnerer true.
        // Mens hasNext() returnerer true, skal den fjerne et element fra listen.
        // deretter skal den sjekke at listen er tom, og returnere true.
        // Ved et kall til next() skal unntaket kastes.
        list.put("test");
        list.put("test2");
        list.put("test3");
        list.put("test4");
        assertTrue(iterator.hasNext());
        while (iterator.hasNext()) {
            iterator.next();
        }
        assertThrows(NoSuchElementException.class, () -> {
            iterator.next();
        }, "listen er tom");
        assertFalse(iterator.hasNext());
    }


}

