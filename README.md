# lessbatis
Auto generate mybatis single table Sql



Work in mysql

# How to use

```java
// Modify (yourMapper and Object) to your class

public interface yourMapper extends LessBatisMapper<Object> {
    
}
```

More example see `DaoTest.class`
