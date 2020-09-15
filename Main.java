import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.lang.reflect.Method; 
import java.lang.reflect.Field; 
import java.lang.reflect.Constructor; 

class Main {
  public static void main(String[] args) {
    List<Teste> lista = new ArrayList<>();

    lista.add(new Teste(1,"jão"));
    lista.add(new Teste(2,"maria"));
    lista.add(new Teste(3,"jose"));
    lista.add(new Teste(4,"dudusinho"));

    Map<Integer, String> testeMap = lista.stream().collect(Collectors.toMap(Teste::getId,Teste::getName));

    System.out.println("Map");
    for (Map.Entry<Integer, String> entry : testeMap.entrySet())
      System.out.println(String.format("ID: %s, Name: %s", entry.getKey(), entry.getValue()));

    List<Integer> idLista = testeMap.keySet().stream().collect(Collectors.toList());

    System.out.println("lista de IDs");
    for (Integer id : idLista)
      System.out.println("ID " + id);

    List<String> nameLista = testeMap.values().stream().collect(Collectors.toList());

    System.out.println("lista de nomes");
    for (String name : nameLista)
      System.out.println("Name " + name );

    List<Teste> lista2 = testeMap.entrySet().stream().map(x -> new Teste(x.getKey(), x.getValue())).collect(Collectors.toList());

    System.out.println("lista2");
    lista2.forEach(System.out::println);

    Class clazz = Teste.class;

    System.out.println("\n\nReflection:");


    try { 
      Constructor cons = clazz.getConstructor(new Class[] {Integer.class, String.class});

      Teste t = (Teste)cons.newInstance(123, "josefino");

      Method print = clazz.getDeclaredMethod("print");

      Field name = clazz.getDeclaredField("name");

      name.setAccessible(true);

      System.out.println(name.get(t));

      print.setAccessible(true);
  
      print.invoke(t);
    } catch (Throwable e ) { 
      System.out.println(e);
    }



  }
}

class Teste { 
    private int id;
    private String name;

    public int getId() {
      return id ;
    }
    public String getName(){
      return name;
    }

    private void print(){
      System.out.println("Heyo");
    }

    public Teste(Integer id , String name) {
      this.name = name;
      this.id = id;
    }
    public String toString(){
      return String.format("Name: %s, ID: %s", getName(), getId());
    }
}