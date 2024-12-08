import java.util.*;

class SLLNode<E> {
    protected E element;
    protected SLLNode<E> succ;

    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}

class MapEntry<K, E> {
    // Each MapEntry object is a pair consisting of a key
    // and a value (an arbitrary object).
    K key;
    E value;

    public MapEntry(K key, E val) {
        this.key = key;
        this.value = val;
    }

    public String toString() {
        return "<" + key + "," + value + ">";
    }
}

class CBHT<K, E> {
    // An object of class CBHT is a closed-bucket hash table, containing
    // entries of class MapEntry.
    private SLLNode<MapEntry<K, E>>[] buckets;

    @SuppressWarnings("unchecked")
    public CBHT(int m) {
        // Construct an empty CBHT with m buckets.
        buckets = (SLLNode<MapEntry<K, E>>[]) new SLLNode[m];
    }

    private int hash(K key) {
        // Translate key to an index of the array buckets.
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public SLLNode<MapEntry<K, E>> search(K targetKey) {
        // Find which if any node of this CBHT contains an entry whose key is equal to targetKey.
        // Return a link to that node (or null if there is none).
        int b = hash(targetKey);
        SLLNode<MapEntry<K, E>> currNode = buckets[b];
        while (currNode != null) {
            MapEntry<K, E> currEntry = currNode.element;
            if (currEntry.key.equals(targetKey)) return currNode;
            else currNode = currNode.succ;
        }
        return null;
    }

    public void insert(K key, E val) {
        // Insert the entry <key, val> into this CBHT.
        // If entry with same key exists, overwrite it.
        MapEntry<K, E> newEntry = new MapEntry<>(key, val);
        int b = hash(key);
        SLLNode<MapEntry<K, E>> currNode = buckets[b];
        while (currNode != null) {
            MapEntry<K, E> currEntry = currNode.element;
            if (currEntry.key.equals(key)) {
                // Make newEntry replace the existing entry ...
                currNode.element = newEntry;
                return;
            } else currNode = currNode.succ;
        }
        // Insert newEntry at the front of the SLL in bucket b ...
        buckets[b] = new SLLNode<>(newEntry, buckets[b]);
    }

    public void delete(K key) {
        // Delete the entry (if any) whose key is equal to key from this CBHT.
        int b = hash(key);
        SLLNode<MapEntry<K, E>> predNode = null, currNode = buckets[b];
        while (currNode != null) {
            MapEntry<K, E> currEntry = currNode.element;
            if (currEntry.key.equals(key)) {
                if (predNode == null) buckets[b] = currNode.succ;
                else predNode.succ = currNode.succ;
                return;
            } else {
                predNode = currNode;
                currNode = currNode.succ;
            }
        }
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < buckets.length; i++) {
            temp += i + ":";
            SLLNode<MapEntry<K, E>> curr = buckets[i];
            while (curr != null) {
                temp += curr.element.toString() + " ";
                curr = curr.succ;
            }
            temp += "\n";
        }
        return temp;
    }
}

class Person implements Comparable<Person> {
    String name;
    int budget;
    String ipAddress;
    String time;
    String city;
    int price;
    int counter;

    public Person(String name, int budget, String ipAddress, String time, String city, int price, int counter) {
        this.name = name;
        this.budget = budget;
        this.ipAddress = ipAddress;
        this.time = time;
        this.city = city;
        this.price = price;
        this.counter = counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return budget == person.budget && price == person.price && counter == person.counter && Objects.equals(name, person.name) && Objects.equals(ipAddress, person.ipAddress) && Objects.equals(time, person.time) && Objects.equals(city, person.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, budget, ipAddress, time, city, price, counter);
    }

    @Override
    public String toString() {
        return name + " with salary " + budget + " from address " + ipAddress + " who logged in at " + time;
    }

    @Override
    public int compareTo(Person o) {
        return this.time.compareTo(o.time);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        CBHT<String, Person> table = new CBHT<>(2*n); //key = grad(String), value = person

        for (int i = 0; i < n; i++) {
            String[] line = sc.nextLine().split("\\s+");
            String name = line[0] + " " + line[1];
            int budget = Integer.parseInt(line[2]);
            String ipAddress = line[3];
            String time = line[4];
            String []t = time.split(":");
            int hours = Integer.parseInt(t[0]);
            int minutes = Integer.parseInt(t[1]);
            String city = line[5];
            int price = Integer.parseInt(line[6]);

            SLLNode<MapEntry<String, Person>> p = table.search(city);

            if (hours >= 12){
                if (p != null){ //aku postojt zapis so toj grad
                    Person current_person = p.element.value;
                    int prev_counter = p.element.value.counter;
                    if (time.compareTo(current_person.time) < 0){ //sporedi go novoto vreme so postoeckoto vreme, aku e pomalo LEKSIKOGRAFSKI togas dodaj nov coek so novoto vreme
                        table.insert(city, new Person(name, budget, ipAddress, time, city, price, prev_counter+1)); //aku ne e prv coek, togas kolku lugje bile pred to +1
                    }
                    else{ //aku postoeckoto vreme e pomalo od novoto vreme, dodaj go postoeckiot coek
                        table.insert(city, new Person(current_person.name, current_person.budget, current_person.ipAddress, current_person.time, current_person.city, current_person.price, prev_counter+1)); //aku ne e prv coek, togas kolku lugje bile pred to +1
                    }
                }
                else{ //aku ne postojt zapis so toj grad
                    table.insert(city, new Person(name, budget, ipAddress, time, city, price, 1)); //aku e prv coek, counterot na 1
                }
            }
        }

        //System.out.println(table);
        //System.out.println("Searching...");

        int m = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < m; i++) {
            String[] line = sc.nextLine().split("\\s+");
            String city = line[5];

            SLLNode<MapEntry<String, Person>> p = table.search(city);
            if (p != null) { //aku postojt zapis za toj grad
                System.out.println("City: " + city + " has the following number of customers: \n" + p.element.value.counter);
                System.out.println("The user who logged on earliest after noon from that city is: \n" + p.element.value.toString());
            }
            else{ //aku ne postojt zapis za toj grad
                System.out.println("Nema zapis za toj grad");
            }
            System.out.println();
        }
    }
}
