
package no.INFO233.Matsh.Oblig2;

import no.INFO233.Matsh.Oblig2.IList;
import no.INFO233.Matsh.Oblig2.LinkedList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




import java.lang.reflect.Executable;
import java.time.Duration;
import java.util.*;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {

    private IList<String> list;
    private Iterator iterator;

    @BeforeEach
    void setUp(){
        list = new LinkedList<>();
        iterator = list.iterator();
    }

    @Test
    void oppg8_sortIntegers() {
        // Se oppgave 8
        IList<Integer> list = new LinkedList<>(1);
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

    void ex1_FastSort() {
        // Se ekstraoppgave 1
        Random r = new Random();
        IList<Integer> list = new LinkedList<>(1);
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
        }else{
            fail("Listen er ikke tom");
        }
    }

    @Test
    void calledRemoveOnEmpty(){
        if (list.isEmpty()){
            assertThrows(NoSuchElementException.class, () -> {
                list.remove();
            }, "Listen er tom");
        }else{
            fail("List is not empty");
        }
    }

    // Kaster NullPointerException om listen er tom og man kaller Rest().
    @Test
    void calledRestOnEmpty(){
        if (list.isEmpty()){
            assertThrows(NullPointerException.class, () -> {
                list.rest();
            }, "Listen er tom");
        }else{
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
    void calledPutOnEmpty(){
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
    void calledAddOnSizeOne(){
        // Sjekker at elementet som blir lagt til med add() legger seg i halen på listen, og ikke først.
        list.add("test");
        list.add("test2");
        assertTrue(list.first().equals("test") && !list.first().equals("test2"));

    }

    @Test
    void calledPutOnSizeOne(){
        // Sjekker også om first er det siste elementet som ble lagt inn, "test2".
        list.add("test");
        list.put("test2");
        assertTrue(list.first().equals("test2"));
    }

    @Test
    void calledRemoveOnSizeOne(){
        // Sjekker at størrelsen på listen er 0 etter å ha fjernet det eneste elementet.
        list.put("test");
        list.remove();
        assertTrue(list.size() == 0);
    }

    // Siden "test" er det første elementet i listen, skal ikke et kall til rest()
    // returnere dette elementet. Dette sjekkes i testen.
    @Test
    void calledRestOnSizeOne(){
        list.put("test");
        assertTrue(!list.rest().equals( "test"));
    }

    // Oppgave 1.3
    // Skriv tester for hva som skjer hvis listen inneholder flere enn 1 element og
    // du kaller metodene first, rest, add, put, og remove. Kommenter testene med hva du tester for.

    @Test
    void calledFirstOnSizeAboveOne(){
        // Forventer at "test2" skal være den første elementet i listen, som legges inn med put().
        // Testen feiler om dette ikke er tilfellet
        list.put("test");
        list.put("test2");
        assertEquals("test2", list.first());
    }

    @Test
    void calledAddOnSizeAboveOne(){
        //Forventer at størrelsen på listen skal være 3 etter å ha lagt til tre elementer med add()
        list.add("test2");
        list.add("test3");
        assertEquals("test2", list.first());
        assertEquals(2, list.size());
    }

    @Test
    void calledPutOnSizeAboveOne(){
        // Sjekket at "test3" er det første elementet i listen etter å ha lagt inn 2 elementer med Put()
        list.put("test");
        list.put("test2");
        list.put("test3");
        assertTrue(list.first().equals("test3"));
    }

    @Test
    void calledRemoveOnSizeAboveOne(){
        // Sjekker at "test2" er det første elementet i listen etter å ha fjernet det første elementet i listen
        list.add("test");
        list.add("test2");
        list.add("test3");
        list.remove();
        assertEquals("test2", list.first());
    }

    @Test
    void calledRestOnSizeAboveOne(){
        // Legger inn tre elementer. bruker deretter rest() på newList, og sjekker at størrelsen har sunket til 2, og
        // "test er det første elementet i listen.
        list.put("test");
        list.put("test2");
        list.put("test3");
        IList<String> newList = list.rest();
        assertEquals(2, newList.size());
        assertEquals("test2", newList.first());
    }

    @Test
    void calledParameterizedRemoveOnSizeOne(){
        // legger inn "test" med put(), sjekker deretter om list.remove() var lik "test", og at den nye størrelsen er 0.
        list.put("test");
        assertTrue(list.remove("test") && list.size() == 2);
    }

    @Test
    void calledParameterizedRemoveOnSizeAboveOne(){
        // Sjekker at "test2" returnerer true på remove. Deretter sjekkes det
        // om size synker til 2, og at det første elementet i listen nå er "test3".
        list.put("test");
        list.put("test2");
        list.put("test3");
        assertTrue(list.remove("test2"));
        assertTrue(list.size() == 2 && list.first().equals("test3"));
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
        list.add("test");
        list.add("test2");
        assertTrue(list.remove("test"));
    }

    @Test
    void contains() {
        list.put("put1");
        list.put("put2");
        assertTrue(list.contains("put2") && list.contains("put1"));
    }

    @Test
    void isEmpty() {

        if (list.size() == 0){
            assertTrue(list.isEmpty());
        }

    }

    @Test
    void append() {
        IList<String> appendList = new LinkedList<>();
        list.put("test");
        list.put("test2");

        String appendThis = "AppendedList";
        appendList.put(appendThis);
        list.append(appendList);
        assertTrue(list.contains("AppendedList"));
        assertFalse(list.first().equals(appendThis));

    }

    @Test
    void prepend() {

        list.put("test");
        list.put("test2");
        IList<String> prependList = new LinkedList<>();
        prependList.put("prependItem");
        prependList.put("prependItem2");

        list.prepend(prependList);

        assertTrue(list.contains("prependItem"));
        assertTrue(list.first().equals("prependItem2"));
    }

    @Test
    void concat() {
        IList<String> list2 = new LinkedList<>("test2");
        IList<String> list3 = new LinkedList<>("test3");
        list2.put("Item in list2");
        list3.put("Item in list3");
        IList<String> concatinated = list.concat(list2, list3);
        assertEquals(4, concatinated.size());
        assertEquals("Item in list2", concatinated.first());
    }

    @Test
    void sort() {
        IList<Integer> integerList = new LinkedList<>();
        List<Integer> expected = Arrays.asList(23, 43, 54, 56, 76);
        integerList.add(54);
        integerList.add(43);
        integerList.add(56);
        integerList.add(23);
        integerList.add(76);

        integerList.sort(Comparator.comparingInt(x -> x));
        for (Integer i : integerList) {
            
        }
        assert
    }

    @Test
    void filter() {
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
        IList<String> list = new LinkedList<>();
        list.put("test");
        list.put("test2");
        list.clear();
        assertTrue(list.isEmpty());
    }


    // tester for iterator
    @Test
    void IteratorhasNextOnEmpty(){
        assertFalse(iterator.hasNext());
        }


     @Test
    void iteratorNextOnEmpty(){
         assertThrows(NullPointerException.class, () -> {
                 iterator.next();
             }, "Listen er tom");
     }

     @Test
    void iteratorHasNextOnOne(){
        list.add("test");
        assertTrue(iterator.hasNext());
     }

     @Test
    void iteratorHasNextOnSeveral(){
        list.put("test");
        list.put("test2");
        list.put("test3");
        list.put("test4");
        assertTrue(iterator.hasNext());
         while (iterator.hasNext()) {
             list.remove();
         }
         assertTrue(list.isEmpty());
         assertThrows(NullPointerException.class, () -> {
             iterator.next();
         }, "listen er tom");
     }

     }
