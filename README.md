# springTransaction
spring事物的传递性
###### 概念
- 本地事务  
数据库事务，默认事务为自动提交，因此如果一个业务逻辑类中有多次数据库操作将无法保证事务的一致性。
- Spring事务  
对本地事务操作的一次封装，相当于把使用JDBC代码开启、提交、回滚事务进行了封装。
###### 传播特性
该特性是保证事务是否开启，业务逻辑是否使用同一个事务的保证。当事务在传播过程中会受其影响。  
其传播特性包括：
1. 注解@Transactional(propagation = Propagation.REQUIRED)
```
方法被调用时自动开启事务，在事务范围内使用则是在使用同一个事务，否则开启新事务。
若两个事物都不抛出异常，则两个事物都执行成功,有一个事物抛异常，则两个事物都回滚
说明REQUIRED的事物调用，属于同一事物了
```
1. @Transactional(propagation = Propagation.REQUIRES_NEW)
```
1、无论何时自身都会开启事务
2、如果在父事物中抛出异常，父事物数据不能正确提交，子事物中的数据被正确提交。
明父事物和子事物是在两个独立的事务中运行，并且只要方法被调用就开启事务。
3、如果在子事物抛出异常，则两个事物都回滚。
```
1. @Transactional(propagation = Propagation.SUPPORTS)
```
1、自身不会开启事务，在事务范围内则使用相同事务，否则不使用事务
2、总结：在事物中使用了SUPPORTS得情况下：则在父，子事物有异常抛出并不影响事物的提交，会导致事物无效，也就是数据有问题
```
1. @Transactional(propagation = Propagation.NOT_SUPPORTED)
```
1、自身不会开启事务，在事务范围内使用挂起事务，运行完毕恢复事务
2、父子事物抛异常，并不影响事物的提交，说明父子事物并没有开启spring事物，而是用了本地事物来管理
```
1. @Transactional(propagation = Propagation.MANDATORY)
```
自身不开启事务，必须在事务环境使用否则报错
```
1. @Transactional(propagation = Propagation.NEVER)
```
自身不会开启事务，在事务范围使用抛出异常
```
1. @Transactional(propagation = Propagation.NESTED)
```
如果一个活动的事务存在，则运行在一个嵌套的事务中. 如果没有活动事务, 则按TransactionDefinition.PROPAGATION_REQUIRED 属性执行。需要JDBC3.0以上支持。
```
