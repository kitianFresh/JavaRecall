# JavaRecall
pick up my java

## Basics
  1. 向函数传递可变参数,不管arguments是多个参数还是数组都行，因为实际上compiler会把varargs转换为数组

  2. 使用三目运算符或者!可以反转一个boolean,！无法用在integer和real以及object reference上，只能用在boolean上

  3. except boolean, only integers(byte,char,short,int,long) can use bitwise operator

### static blocks && class initialize && instance initialize

#### example BBOperator.java
  1. 理清头绪后可以看出，Java虚拟机在看到左边的类出现的时候，就会去寻找该类的代码，首先初始化该类的类属性，也就是static属性(包括static variable，static blocks)，按照在代码中出现的先后顺序，依次执行可以执行的初始化语句或者代码块。这也是为啥static里面无法访问在后面顺序申明的静态成员变量，并且更加不能访问非静态变量即实例变量的原因，因为这不符合先有类后有类实例的原则；并且类在jvm加载一次初始化完成之后就不会再执行初始化了，也就是所有的static在类被首次load到jvm时仅仅执行一次;
  2. 对于类的实例，则是当java虚拟机扫描到new关键字的时候，就会执行构造函数创建类的实例,此时非static{}代码块会被插入到类的构造函数最前面(其实就等价于在{}里面的代码写到构造函数最前面一样),这也是为啥jvm在扫描类的时候不管{}代码块的原因，因为他是属于类实例的，和类无关,并且此时的实例变量都是默认值，因为这个实例还没有完全构造出来，{}只是插在构造函数前面的一段代码,所以t7的初始化要看他插在{}代码块儿之前还是之后，结果是不一样的，也有先后次序之分;
  
#### summary
  1. jvm解释执行代码时，遇到新的类时，首先加载这个类；
  2. 类初始化过程中，按static顺序执行静态成员初始化语句或static blocks；且只执行一次；
  3. 先有类，后才有类实例原则，静态成员属于类，实例成员属于实例-->一系列规则;
    - 静态方法中可以存取静态变量和其他静态方法，不可以存取实例变量和方法
    - 非静态方法可以存取一切静态方法和静态成员变量
    - 类实例可以调用一切静态方法
  4. 实例从new中产生，必定由构造函数构造出;
  5. 类实例的初始化不管时成员初始化private a = 3;还是{}代码块初始化，实际上都是按照在代码中出现的先后顺序被插入到构造函数最前面；
  
### interface && inheritance
#### interface
  1. 接口定义中可以包含method signatures, default methods, static methods and constant definitions. 其中default and static methods.需要给出具体实现；constant默认就是public static，并且只能是。所有方法默认都是public，且只能时public；
  2. 实现某接口的类必须实现该接口的所有方法；当需要扩展接口而又不想更改实现该接口的所有子类代码，两种方法：第一，定义一个新接口继承自该接口，添加新方法；第二，使用default method添加新方法；
  3. An interface name can be used anywhere a type can be used.
  4. 继承一个接口，实现新接口时，default method的三种方式
    - Not mention the default method at all, which lets your extended interface inherit the default method.
    - Redeclare the default method, which makes it abstract.
    - Redefine the default method, which overrides it.

#### inheritance
  1.当父类或者父接口们提供了相同的方法，java compiler遵循一下原则:
    - Instance methods are preferred over interface default methods.
    - Methods that are already overridden by other candidates are ignored. 
    - two or more independently defined default methods conflict, or a default method conflicts with an abstract method, then the Java compiler produces a compiler error. You must explicitly override the supertype methods.
    
#### object 几大常用方法
比较打印克隆类信息
  1. protected Object clone() throws CloneNotSupportedException
      Creates and returns a copy of this object.
  2. public boolean equals(Object obj)
      Indicates whether some other object is "equal to" this one.
  3. protected void finalize() throws Throwable
      Called by the garbage collector on an object when garbage
      collection determines that there are no more references to the object
  4. public final Class getClass()
      Returns the runtime class of an object.
  5. public int hashCode()
      Returns a hash code value for the object.
  6. public boolean equals()
  
  7. public String toString()
      Returns a string representation of the object.

同步方法
  1. public final void notify()
  2. public final void notifyAll()
  3. public final void wait()
  4. public final void wait(long timeout)
  5. public final void wait(long timeout, int nanos)
  
### Java CLASSPATH
#### 当运行的java文件不含package指令
&emsp;&emsp;当java文件不含package指令，都处于当前工作目录src下面时，使用javac XXX.java && java XXX没有任何问题；因为classloader默认从当前目录开始寻找名叫XXX的类，可以找到；
#### 当出现package指令的时候

&emsp;&emsp;加入有package strategy;指令，那么也就是XXX.java文件在src/strategy目录下面,此时，还使用以上命令就会出现Error: Could not find or load main class XXX，为什么？因为带包之后，被编译的XXX.java已经不叫XXX了，包的作用就是名字空间，类名已经是strategy.XXX了，不是XXX；你可以通过javap -verbose XXX.class来查看他的类名；

&emsp;&emsp;那么是不是就是在当前目录src/strategy下面使用java strategy.XXX就可以了？还是出现Error: Could not find or load main class strategy.XXX。What！！！这又是为什么？？因为classloader对于类名foo.bar.XXX这种形式，默认是在当前目录下搜索foo/bar/XXX.class文件来加载，虽然他的类名其实是foo.bar.XXX;而当前目录src/foo/bar下面没有一个foo/bar/XXX.class;同理，你现在在src/strategy工作目录下面，也找不到叫strategy/XXX.class的文件，就失败了；解决方法很简单，到达顶层包所在的目录src，然后再使用java strategy.XXX就可以了，当然你也到达其他目录(比如src/strategy)，加上classpath，java -cp %src_parent%/src strategy.XXX

#### 参考
  1. [Error: Could not find or load main class](http://javarevisited.blogspot.com/2015/04/error-could-not-find-or-load-main-class-helloworld-java.html)
  2. [How to Set Classpath for Java on Windows and Linux](http://javarevisited.blogspot.sg/2011/01/how-classpath-work-in-java.html)

## Advance
### Collection
#### Collection 遍历的同时修改其结构（大小等而不是值）
 1. for-each,但此时是不能修改集合结构的，否则抛出 java.util.ConcurrentModificationException
 ```
 for (Integer d : data) {
			if (d == 5) {
				System.out.println("remove: " + d);
				data.remove(d);
			}
 }
 ```
 2. 使用 Iterator 遍历，就可以在遍历的同时修改其结构了；但是当并发访问时，还是会失败;
 ```
 for (Iterator<Integer> it = data.iterator(); it.hasNext(); ) {
	        if (it.next() == 4) {
	        	it.remove();
	        	System.out.println("remove: " + 4);
	        }
 }
 ```
上述现象产生的原因就是，在Collection内部都有一个Iterator，Iterator会对集合的结构做检查，如果发现和预期不符合，就会抛出异常。对于 for-each，实际上还是调用了Iterator获取每一个元素，但是没有调用Iterator的add或者remove方法，因此抛出了异常。以下源码就可以看出来，Itr.remove完成之后，会进行expectedModCount = modCount;操作，从而防止在checkForComodification()时抛出异常，而如果直接调用collection的add remove，他会进行 modCount++；然而Itr里的expectedModCount并没有跟着变，因此在获取下一个元素的时候就check失败了
```java
private class Itr implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            checkForComodification();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = ArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                ArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final int size = ArrayList.this.size;
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = ArrayList.this.elementData;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount) {
                consumer.accept((E) elementData[i++]);
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
}

```
 3. 如果在遍历的时候，还需要添加元素呢？你会发现data.iterator()返回的Iterator是没有add方法的！非常奇怪。而data.listIterator()是有的，这个ListIterator含有add，remove，set方法！
 - Methods of Iterator:
   * hasNext()
   * next()
   * remove()
 - Methods of ListIterator:
   * add(E e)
   * hasNext()
   * hasPrevious()
   * next()
   * nextIndex()
   * previous()
   * previousIndex()
   * remove()
   * set(E e)
```java
/**
     * An optimized version of AbstractList.ListItr
     */
    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public E previous() {
            checkForComodification();
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = ArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                ArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            checkForComodification();

            try {
                int i = cursor;
                ArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
```
 4. 注意使用Iterator的时候，对set,add,remove等的调用一定不能混合，在it.next或者it.previous后立马操作，如果不立即操作，例如，set之前add了一下，那么操作的元素就错了

#### Collection 并发访问修改
解决单线程遍历同时修改结构，那么如何防止对Collection的并发修改异常？因为Iterator并不保证并发，显然是同步枷锁了，但是枷锁粒度最好不要太细，否则很容易出错，直接加在for 循环外面。

#### 参考
 - [ConcurrentModificationException](https://docs.oracle.com/javase/7/docs/api/java/util/ConcurrentModificationException.html)

## 设计模式
### 策略模式strategy pattern
#### 原则
  1. 封装变化，找出可能变化的部分；
    - 策略模式将将来可能变化的行为(如鸭子会飞和不会飞)定义成一个接口
  2. 多用组合（has-a），少用继承（is-a）；
    - 策略模式将不同鸭子的飞行行为封装成一个类，并成为鸭子成员
    - 不是采用继承（虽然也可以复用代码，但是却造成没有飞行能力的鸭子飞行），一旦需求改变(如有不会飞的鸭子)，要更改大量代码，当然你可以选择不会飞的鸭子出现时fly()中什么也不做，但是这不是一个好习惯
    - 之所以不直接采用一个飞行接口是因为这样做会造成哪些具有同样飞行能力的鸭子代码冗余（因为他们都必须实现飞行接口），而不能复用代码。
    - 使用组合成员成功解决了代码复用(相同飞行行为的鸭子赋值同一种飞行具体行为类)和动态更改飞行行为的能力
  3. 对接口编程，不针对实现编程；
    - 策略模式中我们只要协商好fly接口，就只用针对接口编程，例如抽象鸭子类不用管怎么飞，只需要知道鸭子的飞行属性有飞行接口方法就行。

#### 类图
![strategymodel](https://github.com/kitianFresh/JavaRecall/blob/master/src/designpatterns/strategy/strategymodel.jpg)

#### 模式
&emsp;&emsp;策略模式： 定义**算法族，分别封装起来，让他们之间可以相互替换**，此模式让算法的变化独立于使用算法的客户。
&emsp;&emsp;OOP中，我们一般会把实体抽象成对象，实体的状态和行为就抽象为成员变量和成员函数；但是，在策略模式中，为了解决复用性和迎合需求变化，我们把**行为也抽象成一个接口**，再将**具体行为封装成实现行为接口的类，以数据成员的形式放入实体中，可以使得实体能动态改变行为**；

### 观察者模式observer pattern
#### 原则
  1. 封装变化，找出可能变化的部分；
    - 观察者模式中，被观察者状态是变化的，在他之中注册的观察者数量可以变化
  2. 多用组合（has-a）， 少用继承（is-a）；
    - 观察者模式中，观察者以n：1的形式被组合进被观察者
  3. 针对接口编程，不针对具体实现编程；
    - 观察者利用被观察者接口register向被观察者注册，而被观察者利用观察者接口update通知观察者更新数据
  4. 为交互对象之间的松耦合设计而努力；

#### 类图
![observermodel](https://github.com/kitianFresh/JavaRecall/blob/master/src/designpatterns/observer/observermodel.jpg)

#### 模式
&emsp;&emsp;观察者模式： 在对象之间定义一对多的依赖关系，一旦单个对象状态发生变化，依赖他的对象都会收到通知，并自动更新状态。

&emsp;&emsp;典型的是subject-subscribe模型，消息订阅模型，消息更新，订阅者自动获得更新；实际上java中还有swing的监听器也是观察者模型，比如一个JButton就是一个被观察者，你可以addListener来添加观察者（监听器），一旦事件发生，被观察者就通知监听器，监听器就做相应处理；java.util中内置了观察者模型，包括observable类和observer接口

### 装饰器模式 decorator pattern
#### 原则
  1. 封装变化，找出可能变化的部分；
    - 装饰器模式中，被装饰者的行为除了基本行为，其他行为灵活多变，例如饮料的配料种类繁多可加可不加
  2. 多用组合（has-a）和委托（delegate）， 少用继承（is-a）；
    - 装饰者委托被装饰者完成某项任务返回（相当于一层层函数调用），这样被装饰者就只关注于核心功能，其他的可变修饰交给装饰器（如饮料加入各种调料），当然前提是装饰器和被装饰者拥有同样的超类才能完成装饰过程和委托过程
  3. 针对接口编程，不针对具体实现编程；
  4. 为交互对象之间的松耦合设计而努力；
  5. 对扩展开放，对修改关闭；

#### 类图
![decoratormodel](https://github.com/kitianFresh/JavaRecall/blob/master/src/designpatterns/decorator/decoratormodel.jpg)

#### 序列图
![decoratorseq](https://github.com/kitianFresh/JavaRecall/blob/master/src/designpatterns/decorator/decoratorseq.jpg)
  
#### 模式
&emsp;&emsp;装饰器模式：动态地将责任附加到对象上，要想扩展功能，装饰器提供了有别于继承的另一种选择。

&emsp;&emsp;继承虽然也是提供扩展的方式之一，但是他有时候并不是最佳方案！因为继承是静态的扩展功能，在代码层面复用了代码。 而组合和委托却可以在运行时动态加上新的行为！

&emsp;&emsp;JavaIO中的各种输入输出流就是装饰器的典范。例如：
```
InputStream
    FileInputStream(组件)
    StringBufferInputStream(组件)
    ByteArrayInputStream(组件)
    FilterInputStream（抽象装饰器）
        PushbackInputStream（装饰器）
        BufferedInputStream（装饰器）
        DataInputStream（装饰器）
        LineNumberInputStream（装饰器）
```

### 工厂模式 factory pattern

#### 1. factory method pattern

#### 原则
  1. 依赖倒置原则（Dependency Inversion Principle）
   - 依赖抽象而不是依赖具体类。

#### 类图
![factory](https://github.com/kitianFresh/JavaRecall/blob/master/src/designpatterns/factory/factory.jpg)

#### 模式
&emsp;&emsp;工厂方法模式： 定义了一个创建对象的接口，但由子类决定要实例化的类是哪一个。工厂方法让类把实例化推迟到子类。

&emsp;&emsp;Product和Creator之间是解耦的。Creator实现所有操纵Product的方法，但是不实现工厂方法，他有一个protected abstract factoryMethod()；继承自Creator的ConcreteCreator生产实际的继承自Product的ConcreteProduct。

#### 2. abstract factory pattern

#### 原则
  1. 依赖倒置原则（Dependency Inversion Principle）
   - 依赖抽象而不是依赖具体类
   
#### 类图
![abstractfactory](https://github.com/kitianFresh/JavaRecall/blob/master/src/designpatterns/abstractfactory/abstractfactory.jpg)

![abstractfactory-digram](https://github.com/kitianFresh/JavaRecall/blob/master/src/designpatterns/abstractfactory/abstractfactory-digram.png)

#### 模式
&emsp;&emsp;抽象工厂方法模式： 提供一个接口，用于创建相关或依赖对象的家族，而不需要明确指定具体类。


&emsp;&emsp;针对抽象编程，而不是针对具体类编程。**工厂方法使用继承：把对象的创建委托给子类，子类实现工厂方法来创建对象**。**抽象工厂使用对象组合：对象的创建被实现在工厂接口所暴露出来的方法中**。

### 单件模式
#### 原则
#### 模式
&emsp;&emsp;单件模式：确保一个类只有一个实例，并提供全局访问点。

#### 单件模式的编程范式:
  1. 私有化构造器，private Singleton(){},因为不能有多个实例；这就导致需要一个public全局访问点来实例化一个类，并且一定是static方法，因为调用他的时候这个类的实例还没有出生，因此只能是static方法。
  2. 自我管理，需要有一个静态变量 private static Singleton uniqueInstance，因为需要维护全局唯一的状态；private是因为不能被共享，static是因为他需要实例出生之前就存在。
  3. 全局访问点，public static Singleton getInstance(){}。
  
#### 单件模式演化史

线程不安全版本
```java
public class Singleton1 {
	//自我管理
	private static Singleton1 uniqueInstance = null;
	private Singleton1() {}
	public static Singleton1 getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Singleton1();
		}
		return uniqueInstance;
	}
}
```
安全低性能版
```java
public class Singleton2 {
	// 自我管理
	private static Singleton2 uniqueInstance = null;
	private Singleton2() {}
	// 线程安全，但是性能不好，因为每一次getInstance都要枷锁解锁,
	// 对于频繁使用getInstance的程序，性能急剧下降
	public static synchronized Singleton2 getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Singleton2();
		}
		return uniqueInstance;
	}
}
```
类加载器eager版本
```java
public class Singleton3 {
	// 借助虚拟机类加载器在加载的时候就创建出类实例（eagerly），即使并未使用，以后就不用再检查是否非空了 
	// 但是，当你的程序中使用多个类加载器加载同一个类时候，还是有可能出现多个实例的，
	// 因此此版本只适用于没哟多个类加载器加载同一个类的情况
	private static Singleton3 uniqueInstance = new Singleton3();
	private Singleton3() {}
	public static Singleton3 getInstance() {
		return uniqueInstance;
	}
}
```
终极安全高性能版本（double check）
```java
public class Singleton4 {
	private volatile static Singleton4 uniqueInstance = null;
	private Singleton4() {}
	// 终极方法，double check法！只有在第一次实例化时会枷锁检查，以后就不用了
	public static Singleton4 getInstance() {
		if (uniqueInstance == null) {
			synchronized (Singleton4.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new Singleton4();
				}
			}
		}
		return uniqueInstance;
	}
	
}
```