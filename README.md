# JavaRecall
pick up my java

## Basics
  1. 向函数传递可变参数,不管arguments是多个参数还是数组都行，因为实际上compiler会把varargs转换为数组

  2. 使用三目运算符或者!可以反转一个boolean,！无法用在integer和real以及object reference上，只能用在boolean上

  3. only integers(byte,char,short,int,long) can use bitwise operator

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
  6. public String toString()
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

### 设计模式
#### 策略模式strategy pattern
##### 原则
  1. 封装变化，找出可能变化的部分；
  2. 多用组合（has-a），少用继承（is-a）；
  3. 对接口编程，不针对实现编程；

#### 模式
&emsp;&emsp;策略模式： 定义算法族，分别分装起来，让他们之间可以相互替换，此模式让算法的变化独立于使用算法的客户。
&emsp;&emsp;OOP中，我们一般会把实体抽象成对象，实体的状态和行为就抽象为成员变量和成员函数；但是，在策略模式中，为了解决复用性和迎合需求变化，我们把行为也抽象成一个接口，再将具体行为封装成实现行为接口的类，以数据成员的形式放入实体中，可以使得实体能动态改变行为；