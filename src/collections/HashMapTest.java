package collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class Person {  
    String Id;  
    String name;  
    Person(String id, String name){  
        this.Id = id;  
        this.name = name;  
    }  
    @Override  
    public String toString() {  
        // TODO Auto-generated method stub  
        return  Id + ":" + name;  
    }  
    @Override  
    public int hashCode() {  
        // TODO Auto-generated method stub  
        return this.name.hashCode()^this.Id.hashCode();  
    }  
    @Override  
    public boolean equals(Object obj) {  
        // TODO Auto-generated method stub  
        if (obj instanceof Person ) {  
            Person p = (Person)obj;  
            if (this.Id == p.Id || (this.Id != null && this.Id.equals(p.Id)))  
                return true;  
            else  
                return false;  
        }  
        return false;  
    }  
      
}  

public class HashMapTest {
	public static void main(String[] args) {
		Map<Integer, Integer> maps = new HashMap<Integer, Integer>();
		Integer i1 = 1;
		Integer i2 = 2;
		maps.put(i1, 1);
		maps.put(2, 2);
		System.out.println(maps.get(i1));
		System.out.println(maps.get(2));
		maps.put(null, 33);
		maps.put(null, 10000);
		System.out.println(maps.get(null));
		System.out.println(maps.containsValue(33));
		
        Map<Person,String> m = new HashMap<Person,String>();  
		m.put(null,null);  
        m.put(null, "si");  
        m.put(null, "ti");   
        Set<Map.Entry<Person, String>> sets = m.entrySet();  
        Iterator<Map.Entry<Person, String>> itm = sets.iterator();  
       
        while(itm.hasNext()){  
            Map.Entry<Person, String> e = itm.next();  
            Person pp = e.getKey();  
            System.out.println(e.getKey() + ": " + e.getValue());
        }  
	}
}
