import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;


public class BikePark extends CSP {

    private static Set<Object> vSport = new HashSet<Object>(Arrays.asList("baseball", "basketball", "hockey", "soccer", "swimming"));
    private static Set<Object> vBike = new HashSet<Object>(Arrays.asList("black", "blue", "green", "red", "white"));
    private static Set<Object> vJuice = new HashSet<Object>(Arrays.asList("apple", "cranberry", "grapefruit", "orange", "pineapple"));
    private static Set<Object> vAge = new HashSet<Object>(Arrays.asList("12", "13", "14", "15", "16"));
    private static Set<Object> vSando = new HashSet<Object>(Arrays.asList("bacon", "chicken", "cheese", "pepperoni", "tuna"));
    private static Set<Object> vName = new HashSet<Object>(Arrays.asList("adrian", "charles", "henry", "joel", "richard"));


    public boolean isGood(Object X, Object Y, Object x, Object y) {
        //if X is not even mentioned in the constraints, just return true
        if (!C.containsKey(X))
            return true;
        if (!C.get(X).contains(Y))
            return true;

        // owner of the white bike is somewhere between the 15 yr old boy and the youngest
        if (X.equals("white") && Y.equals("15") && (Integer) x <= (Integer) y)
            return false;
        if (X.equals("white") && Y.equals("13") && (Integer) x >= (Integer) y)
            return false;

        // one who likes swimming is next to one who likes baseball
        if (X.equals("swimming") && Y.equals("baseball") && Math.abs((Integer) x - (Integer) y) != 1)
            return false;

        // cyclist who eats grapefruit is between tuna and pineapple
        if (X.equals("pineapple") && Y.equals("14") && (Integer) x <= (Integer) y)
            return false;
        if (X.equals("pineapple") && Y.equals("orange") && (Integer) x >= (Integer) y)
            return false;

        // the boy who like the ice sport is going to eat pepperoni
        if (X.equals("hockey") && Y.equals("pepperoni") && !x.equals(y))
            return false;

        // henry exactly left of soccer player
        if (X.equals("henry") && Y.equals("soccer") && (Integer) x + 1 != (Integer) y)
            return false;

        //grapefruit between tuna and pineapple
        if (X.equals("grapefruit") && Y.equals("tuna") && (Integer) x <= (Integer) y)
            return false;
        if (X.equals("grapefruit") && Y.equals("pineapple") && (Integer) x >= (Integer) y)
            return false;

        // joel next to 16 yr old cyclist
        if (X.equals("joel") && Y.equals("16") && Math.abs((Integer) x - (Integer) y) != 1)
            return false;

        // adrian exactly to the left of the boy who is gonna eat pepperoni sando
        if (X.equals("adrian") && Y.equals("pepperoni") && (Integer) x + 1 != (Integer) y)
            return false;

        //12 between 14 and 16
        if (X.equals("12") && Y.equals("14") && (Integer) x <= (Integer) y)
            return false;
        if (X.equals("12") && Y.equals("16") && (Integer) x >= (Integer) y)
            return false;

        //bacon somewhere right of  white
        if (X.equals("bacon") && Y.equals("white") && (Integer) x <= (Integer) y)
            return false;

        // 16 yr old brought cheese
        if (X.equals("16") && Y.equals("cheese") && !x.equals(y))
            return false;

        // cyclist with white bike between richard and red bike
        if (X.equals("white") && Y.equals("richard") && (Integer) x <= (Integer) y)
            return false;
        if (X.equals("white") && Y.equals("red") && (Integer) x >= (Integer) y)
            return false;

        //baseball next to apple
        if (X.equals("baseball") && Y.equals("apple") && Math.abs((Integer) x - (Integer) y) != 1)
            return false;

        //charles between richard and adrian
        if (X.equals("charles") && Y.equals("richard") && (Integer) x <= (Integer) y)
            return false;
        if (X.equals("charles") && Y.equals("adrian") && (Integer) x >= (Integer) y)
            return false;

        // Uniqueness constraints
        return (!vBike.contains(X) || !vBike.contains(Y) || X.equals(Y) || !x.equals(y)) && (!vName.contains(X) ||
                !vName.contains(Y) || X.equals(Y) || !x.equals(y)) && (!vSando.contains(X) || !vSando.contains(Y) ||
                X.equals(Y) || !x.equals(y)) && (!vJuice.contains(X) || !vJuice.contains(Y) || X.equals(Y) || !x.equals(y)) &&
                (!vAge.contains(X) || !vAge.contains(Y) || X.equals(Y) || !x.equals(y)) && (!vSport.contains(X) ||
                !vSport.contains(Y) || X.equals(Y) || !x.equals(y));

    }

    public static void main(String[] args) {
        BikePark csp = new BikePark();

        Integer[] dom = {1, 2, 3, 4, 5};

        for (Object X : vBike)
            csp.addDomain(X, dom);

        for (Object X : vName)
            csp.addDomain(X, dom);

        for (Object X : vSando)
            csp.addDomain(X, dom);

        for (Object X : vJuice)
            csp.addDomain(X, dom);

        for (Object X : vAge)
            csp.addDomain(X, dom);

        for (Object X : vSport)
            csp.addDomain(X, dom);


        //unary constraints: just remove values from domains

        // In the middle is the boy that likes baseball.
        for (int i = 1; i <= 5; i++)
            if (i != 3)
                csp.D.get("baseball").remove(i);

        // The cyclist who is going to eat Tuna sandwich is at one of the ends
        for (int i = 1; i <= 5; i++)
            if (i != 1 && i != 5)
                csp.D.get("tuna").remove(i);

        // fourth likes pineapple
        for (int i = 1; i <= 5; i++)
            if (i != 4)
                csp.D.get("pineapple").remove(i);

        // green bike at one of the ends
        for (int i = 1; i <= 5; i++)
            if (i != 1 && i != 5)
                csp.D.get("green").remove(i);

        // black bike is third
        for (int i = 1; i <= 5; i++)
            if (i != 3)
                csp.D.get("black").remove(i);

        // fifth position is the 13 yr old boy
        for (int i = 1; i <= 5; i++)
            if (i != 5)
                csp.D.get("13").remove(i);

        // boy who likes hockey is fifth
        for (int i = 1; i <= 5; i++)
            if (i != 5)
                csp.D.get("hockey").remove(i);

        //binary constraints: add constraint arcs

        //hockey eats pepperoni
        csp.addBidirectionalArc("hockey", "pepperoni");

        //swimming is next to baseball
        csp.addBidirectionalArc("swimming", "baseball");

        //henry left of soccer
        csp.addBidirectionalArc("henry", "soccer");

        //baseball next to apple
        csp.addBidirectionalArc("baseball", "apple");

        //16 year old has cheese
        csp.addBidirectionalArc("16", "cheese");

        //bacon right of white
        csp.addBidirectionalArc("bacon", "white");

        //12 between 14 and 16
        csp.addBidirectionalArc("12", "14");
        csp.addBidirectionalArc("12", "16");

        //joel is next to 16
        csp.addBidirectionalArc("joel", "16");

        //adrian left to pepperoni
        csp.addBidirectionalArc("adrian", "pepperoni");

        //white between richard and red
        csp.addBidirectionalArc("white", "richard");
        csp.addBidirectionalArc("white", "red");

        //charles between richard and adrian
        csp.addBidirectionalArc("charles", "richard");
        csp.addBidirectionalArc("charles", "adrian");

        //owner of white between 15 and 12
        csp.addBidirectionalArc("white", "15");
        csp.addBidirectionalArc("white", "12");

        //grapefruit between tuna and pineapple
        csp.addBidirectionalArc("grapefruit", "tuna");
        csp.addBidirectionalArc("grapefruit", "pineapple");

        //pineapple between 14 and orange juice
        csp.addBidirectionalArc("pineapple", "14");
        csp.addBidirectionalArc("pineapple", "orange");

        //white between blue and black
        csp.addBidirectionalArc("white", "blue");
        csp.addBidirectionalArc("white", "black");


        //Uniqueness constraints
        for (Object X : vBike)
            for (Object Y : vBike)
                csp.addBidirectionalArc(X, Y);

        for (Object X : vName)
            for (Object Y : vName)
                csp.addBidirectionalArc(X, Y);

        for (Object X : vSando)
            for (Object Y : vSando)
                csp.addBidirectionalArc(X, Y);

        for (Object X : vJuice)
            for (Object Y : vJuice)
                csp.addBidirectionalArc(X, Y);

        for (Object X : vAge)
            for (Object Y : vAge)
                csp.addBidirectionalArc(X, Y);

        for (Object X : vSport)
            for (Object Y : vSport)
                csp.addBidirectionalArc(X, Y);


        // Solution

        Search search = new Search(csp);
        System.out.println(search.BacktrackingSearch());
    }
}